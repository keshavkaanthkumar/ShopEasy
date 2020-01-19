/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.WorkQueue;
import Business.UserAccount.UserAccount;
import static java.lang.Math.random;
import java.util.Date;

/**
 *
 * @author raunak
 */
public abstract class WorkRequest {

    private String RequestType;
    private int requestID;

    public int getRequestID() {
        return requestID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }


    public String getRequestType() {
        return RequestType;
    }

    public void setRequestType(String RequestType) {
        this.RequestType = RequestType;
    }
    private UserAccount sender;
    private UserAccount receiver;
    private String status;
    private Date requestDate;
    private Date resolveDate;
    
    public WorkRequest(){
        requestDate = new Date();
        int Min=1,Max=Integer.MAX_VALUE;
         requestID=Min + (int)(Math.random() * ((Max - Min) + 1));
        
    }

    

    public UserAccount getSender() {
        return sender;
    }

    public void setSender(UserAccount sender) {
        this.sender = sender;
    }

    public UserAccount getReceiver() {
        return receiver;
    }

    public void setReceiver(UserAccount receiver) {
        this.receiver = receiver;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public Date getResolveDate() {
        return resolveDate;
    }

    public void setResolveDate(Date resolveDate) {
        this.resolveDate = resolveDate;
    }
    @Override
    public String toString(){
        return String.valueOf(this.requestID);
    }
}
