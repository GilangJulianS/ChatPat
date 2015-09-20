/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gilang
 */
public class Channel {
    
    private static int idCounter = 0;
    private int id;
    private String name;
    private List<User> users;
    
    public Channel(String channelName){
        name = channelName;
        id = idCounter;
        idCounter++;
        users = new ArrayList<>();
    }
    
    public void join(User user){
        users.add(user);
    }

    public static int getIdCounter() {
        return idCounter;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<User> getUsers() {
        return users;
    }
    
    
    
    
}
