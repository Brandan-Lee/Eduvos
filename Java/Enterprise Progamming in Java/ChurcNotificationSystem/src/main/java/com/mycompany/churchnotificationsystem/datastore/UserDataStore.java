
package com.mycompany.churchnotificationsystem.datastore;

import com.mycompany.churchnotificationsystem.model.User;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class UserDataStore {
    
    private static final Map<String, User> users = new ConcurrentHashMap<>();
    
    //Function to help to check if the username exists within the hashmap
    public boolean userNameExists(String userName) {
        return users.containsKey(userName.toLowerCase());
    }
    
    //Function to help to save the username to the hashmap
    public void saveUser(User user) {
        users.put(user.getUserName().toLowerCase(), user);
    }
    
    //Function to find the username within the hashmap
    public User findUserByUserName(String userName) {
        return users.get(userName.toLowerCase());
    }
    
}
