
package model;

/**
 * A model class representing guests of the hotel
 * @author brand
 */

public class Guest {
    
    //store the guests name and their PID
    private String guestName, guestId;
    
    /**
     * Constructor for the guest model
     * @param guestName the full name of the guest
     * @param guestId the guest PID that acts as a unique identifier
     */
    public Guest(String guestName, String guestId) {
        this.guestName = guestName;
        this.guestId = guestId;
    }
    
    /**
     * Get the guests full name
     * @return the guest full name as a string
     */
    public String getGuestName() {
        return guestName;
    }
    
    /**
     * Get the guest PID
     * @return the guests PID as a string
     */
    public String getGuestId() {
        return guestId;
    }
    
    /**
     * display the guests information
     * @return the guests information as a string 
     */
    @Override
    public String toString() {
        return guestName + " ID: " + guestId;
    }
}
