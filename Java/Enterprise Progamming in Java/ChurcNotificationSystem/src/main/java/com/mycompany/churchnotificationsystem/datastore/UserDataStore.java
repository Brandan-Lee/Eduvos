
package com.mycompany.churchnotificationsystem.datastore;

import com.mycompany.churchnotificationsystem.model.User;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class UserDataStore {
    
    private static final Map<String, User> users = new ConcurrentHashMap<>();
    
    public boolean userNameExists(String userName) {
        return users.containsKey(userName.toLowerCase());
    }
    
    public void saveUser(User user) {
        users.put(user.getUserName().toLowerCase(), user);
    }
    
    public User findUserByUserName(String userName) {
        return users.get(userName.toLowerCase());
    }
    
    public Map<String, User> getAllUsers() {
        return users;
    }
    
}
