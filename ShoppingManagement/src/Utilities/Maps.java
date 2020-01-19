/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author kesha
 */
public class Maps {
    public static Address getAddressDistance(String addressinput) throws IOException{
        Address addressformatted=new Address();
        String address=addressinput.replace(' ', '+');
     double lat1=0,lat2=0,lon1 = 0,lon2=0;
     float result=0;
      // String address="address=360,+Huntington+Avenue+Boston";
       URL myURL;
        try {
            myURL = new URL("https://maps.googleapis.com/maps/api/geocode/json?address="+address+"&key=AIzaSyAzbAPiIQ0Tauw0F-ldRVPlWN-KwT5uR5k");
        
        HttpURLConnection connection = (HttpURLConnection) myURL.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        connection.connect();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder results = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            results.append(line);
        }

        connection.disconnect();
        System.out.println(results.toString());
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(results.toString());
        System.out.println(rootNode.findValue("lat"));
        System.out.println(rootNode.findValue("lng"));
        lat1=42.3289969;
        lon1=-71.10178379999999;
                lat2=Double.valueOf(rootNode.findValue("lat").toString());
                lon2=Double.valueOf(rootNode.findValue("lng").toString());
                addressformatted.setAddress(rootNode.findValue("formatted_address").toString());
         lon1 = Math.toRadians(lon1); 
        lon2 = Math.toRadians(lon2); 
        lat1 = Math.toRadians(lat1); 
        lat2 = Math.toRadians(lat2); 
  
        // Haversine formula  
        double dlon = lon2 - lon1;  
        double dlat = lat2 - lat1; 
        double a = Math.pow(Math.sin(dlat / 2), 2) 
                 + Math.cos(lat1) * Math.cos(lat2) 
                 * Math.pow(Math.sin(dlon / 2),2); 
              
        double c = 2 * Math.asin(Math.sqrt(a)); 
  
        // Radius of earth in kilometers. Use 3956  
        // for miles 
        double r = 6371; 
         result=(float)(c*r);
  
        // calculate the result 
        
        }
         catch (MalformedURLException ex) {
            Logger.getLogger(Maps.class.getName()).log(Level.SEVERE, null, ex);
        }
        BigDecimal bd = new BigDecimal(Float.toString(result));
    bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
   
        addressformatted.setDistance(bd.floatValue());
        return addressformatted;
    }
    
}
