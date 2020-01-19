package Business.DB4OUtil;

import Business.ConfigureASystem;
import Business.EcoSystem;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.ta.TransparentPersistenceSupport;
import java.nio.file.Paths;
import org.apache.log4j.Logger;
import userinterface.MainJFrame;

/**
 *
 * @author rrheg
 * @author Lingfeng
 */
public class DB4OUtil {
    
    final static Logger logger = Logger.getLogger(DB4OUtil.class);
    private static final String FILENAME = Paths.get("Databank.db4o").toAbsolutePath().toString();// path to the data store
    private static DB4OUtil dB4OUtil;
    
    public synchronized static DB4OUtil getInstance() {
        if (dB4OUtil == null) {
            dB4OUtil = new DB4OUtil();
        }
        return dB4OUtil;
    }
    
    protected synchronized static void shutdown(ObjectContainer conn) {
        if (conn != null) {
            conn.close();
        }
    }
    
    private ObjectContainer createConnection() {
        try {
            
            EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
            config.common().add(new TransparentPersistenceSupport());

            //Controls the number of objects in memory
            config.common().activationDepth(Integer.MAX_VALUE);

            //Controls the depth/level of updation of Object
            config.common().updateDepth(Integer.MAX_VALUE);

            //Register your top most Class here
            config.common().objectClass(EcoSystem.class).cascadeOnUpdate(true); // Change to the object you want to save

            ObjectContainer db = Db4oEmbedded.openFile(config, FILENAME);
            return db;
            
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return null;
    }
    
    public synchronized void storeSystem(EcoSystem system) {
        ObjectContainer conn = createConnection();
        conn.store(system);
        conn.commit();
        conn.close();
        logger.info("Stored data in database file");
    }
    
    public EcoSystem retrieveSystem() {
        ObjectContainer conn = createConnection();
        ObjectSet<EcoSystem> systems = conn.query(EcoSystem.class); // Change to the object you want to save
        EcoSystem system;
        if (systems.size() == 0) {
            logger.info("Creating database file ecosystem model");
            system = ConfigureASystem.configure();  // If there's no System in the record, create a new one
        } else {
            system = systems.get(systems.size() - 1);
            logger.info("Reading existing database file " + FILENAME);
        }
        conn.close();
        return system;
    }
}
