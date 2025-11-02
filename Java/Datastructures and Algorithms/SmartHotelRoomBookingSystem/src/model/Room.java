
package model;

/**
 * A model class representing a room in the hotel
 * @author brand
 */

public class Room {
    
    //the identity of the room and which floor it exists on
    private int roomNumber, floorNumber;
    //variable to help determine if the room is occupied or not
    private boolean isOccupied;
    //variable that holds a guest object that shows which guest is occupying the current room
    private Guest currentGuest;
    
    /**
     * Constructor for the room object. When creating a new room, the room is not occupied and no guests has been assigned to the room
     * @param roomNumber the number of the room
     * @param floorNumber the floor that the room is on
     */
    public Room(int roomNumber, int floorNumber) {
        this.roomNumber = roomNumber;
        this.floorNumber = floorNumber;
        this.isOccupied = false;
        this.currentGuest = null;
    }
    
    /**
     * Check a user into the room if it is not occupied
     * @param guest the guest thats checking into the room
     */
    public void checkIn(Guest guest) {
        if (!this.isOccupied) {
            currentGuest = guest;
            isOccupied = true;
        } else {
            System.out.println("Error: Room F" + floorNumber + " R" + roomNumber + " is occupied");
        }
    }
    
    /**
     * Check a guest out of the room if it is occupied
     * @return the guest that occupied the room
     */
    public Guest checkOut() {
        if (this.isOccupied) {
            Guest guest = currentGuest;
            currentGuest = null;
            isOccupied = false;
            
            return guest;
        }
        
        return null;
    }
    
    /**
     * Get the room number
     * @return the room number as an int
     */
    public int getRoomNumber() {
        return roomNumber;
    }
    
    /**
     * Get the floor number that the room is located on
     * @return the floor number as an int
     */
    public int getFloorNumber() {
        return floorNumber;
    }
    
    /**
     * Get the status of the room
     * @return if the room is occupied or not 
     */
    public boolean getIsOccupied() {
        return isOccupied;
    }
    
    /**
     * Get the current guest that is occupying the room
     * @return the guest as an object
     */
    public Guest getGuest() {
        return currentGuest;
    }
    
    /**
     * Get the rooms information
     * @return if the room is occupied or not, and by which guest it is occupied
     */
    @Override
    public String toString() {
        boolean status = isOccupied;
        
        if (status) {
            return "Floor " + floorNumber + ", Room " + roomNumber + " Occupied by " + currentGuest.getGuestName();
        } else {
            return "Floor " + floorNumber + ", Room " + roomNumber + " is available";
        }
    }
}
