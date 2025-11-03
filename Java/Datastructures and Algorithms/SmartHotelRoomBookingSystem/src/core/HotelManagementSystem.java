
package core;

import data.HashMap;
import data.LinkedQueue;
import data.SinglyLinkedList;
import model.Guest;
import model.Room;

/**
 * System class for the hotel management system
 * @author brand
 */

public class HotelManagementSystem {
    
    //hashmap that stores the floor number as a key, and the rooms as a singly linked list
    private HashMap<Integer, SinglyLinkedList<Room>> hotelMap;
    //the checkout history of guests is stored as a linked queue
    private LinkedQueue<Guest> checkOutHistory;
    //the last 10 guests that checked out of the hotel must be kept track of
    private static final int HISTORY_SIZE = 10;
    //variables that will help to initialize the size of the hotel
    private int totalFloors, roomsPerFloor;
    
    /**
     * Constructor of the system
     * @param totalFloors the total floors that the hotel will consist out of
     * @param roomsPerFloor the total amount of rooms that each floor of the hotel will have
     */
    public HotelManagementSystem(int totalFloors, int roomsPerFloor) {
        this.totalFloors = totalFloors;
        this.roomsPerFloor = roomsPerFloor;
        this.hotelMap = new HashMap<>();
        this.checkOutHistory = new LinkedQueue<>();
        
        //Create the hotel
        initializeRooms();
    }
    
    /**
     * Create the hotel map that contains all the rooms and the floors associated with them
     */
    private void initializeRooms() {
        //We want to start at the first floor, and add the roomsPerfloor as a singly linked list
        for (int i = 1; i <= totalFloors; i++) {
            SinglyLinkedList<Room> floorList = new SinglyLinkedList<>();
            
            //Fill all the buckets of the custom hashmap with the floorlist
            for (int j = 1; j <= roomsPerFloor; j++) {
                floorList.addLast(new Room(j, i));
            }
            
            //Add the floorNumber and rooms to the hashmap
            hotelMap.put(i, floorList);
        }
        
        //display to the user that a map of the hotel has been created
        System.out.println("Hotel initialized with " + (totalFloors * roomsPerFloor) + " rooms across " + totalFloors + " floors. ");
    }
    
    /**
     * helper method to find a certain room on a certain floor in the hotel map
     * @param floorNumber the floor that the room is on
     * @param RoomNumber the number of the room
     * @return the room if it is found
     */
    private Room findRoom(int floorNumber, int roomNumber) {
        //Get all the rooms on the entered floor
        SinglyLinkedList<Room> rooms = hotelMap.get(floorNumber);
        
        //there are no rooms on that floor
        if (rooms == null) {
            return null;
        }
        
        //There are rooms on the floor, now we find the room number and return it
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        
        //the room was not found
        return null;
    }
    
    /**
     * Find a room available on a floor and check the guest into the room
     * @param guestName the name of the new guest that needs to be checked into a room
     * @param guestId the id of the guest that needs to be checked into a room
     * @return the result of the check in process as a string
     */
    public String checkInGuest(String guestName, String guestId) {
        Guest newGuest = new Guest(guestName, guestId);
        
        //Go through the entire map to find a floor that is available for the guest
        for (int i = 1; i <= totalFloors; i++) {
            //get all the rooms on that floor
            SinglyLinkedList<Room> rooms = hotelMap.get(i);
            
            //ensure that there are rooms on that floor and find one that is not occupied. Once a room is found, check the guest into that room
            if (rooms != null) {
                for (Room room : rooms) {
                    if (!room.getIsOccupied()) {
                        room.checkIn(newGuest);
                        return newGuest.getGuestName() + " assigned to Room " + room.getRoomNumber() + " on Floor " + room.getFloorNumber() + ".";
                    }
                }
            }
        }
        
        //the hotel is empty
        return "The hotel is full. Please try again later";
    }
    
    /**
     * Check the guest out of the room when they leave the hotel
     * @param floorNumber the floor of the room where the guest was booked
     * @param roomNumber the room where the guest was checked into
     * @return if the check out process was a success or not
     */
    public String checkOutGuest(int floorNumber, int roomNumber) {
        Room checkOutRoom = findRoom(floorNumber, roomNumber);
        
        //The room was not found in the hotelMap
        if (checkOutRoom == null) {
            return "The room number " + roomNumber + " on floor number " + floorNumber + " does not exist";
        }
        
        //the room is already empty
        if (!checkOutRoom.getIsOccupied()) {
            return "The room number " + roomNumber + " on floor number " + floorNumber + " is already empty";
        }
        
        //get the guest that is checking out and add them to the checkOutHistory linkedQueue
        Guest guest = checkOutRoom.checkOut();
        
        if (guest != null) {
            checkOutHistory.enQueue(guest);
            
            //check to see if the queue is past the limit of 10 guests. If so, remove the first guest in the checkout history queue
            if (checkOutHistory.size() > HISTORY_SIZE) {
                checkOutHistory.dequeue();
            }
            
            return guest.getGuestName() + " left room " + roomNumber + " on floor " + floorNumber + ".";
        }
        
        //the guest wasn't found
        return "There was an error checking out " + guest.getGuestName() + ". Please try again";
    }
    
    /**
     * Print the LinkedQueue that stores the last 10 guests that have been checked out to the system
     */
    public void printCheckoutHistory() {
       if (checkOutHistory.isEmpty()) {
           System.out.println("No Guests have been checked out from the hotel yet.");
           return;
       }
       
       System.out.println(checkOutHistory.toString());
    }
    
    /**
     * Generate a daily report that will be displayed to the user
     */
    public void generateDailyReport() {
        System.out.println("Guest count per floor");
        //variable that will store the total guests that are checked into the hotel
        int totalGuests = 0;
        
        //loop through each floor, find which rooms are occupied
        for (int i = 1; i <= totalFloors; i++) {
            SinglyLinkedList<Room> rooms = hotelMap.get(i);
            //variable that will hold the total amount of guests on the floor
            int guestsOnFloor = 0;
            
            //update guests on floor variable if the room is occupied or not
            if (rooms != null) {
                for (Room room : rooms) {
                    if (room.getIsOccupied()) {
                        guestsOnFloor++;
                    }
                }
            }
            
            //display the total guests on the floor and update the totalguest count with the guests on the floor
            System.out.println("Total Guest on floor " + i + " = " + guestsOnFloor);
            totalGuests += guestsOnFloor;
        }
        
        System.out.println("Total guests checked into the hotel " + totalGuests);
    }
}
