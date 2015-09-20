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
    
    public void addUser(User user){
        users.add(user);
    }
    
    public int removeUser(int userId){
        int status = Status.NOT_FOUND;
        for(int i=0; i<users.size(); i++){
            if(users.get(i).getId() == userId){
                users.remove(i);
                status = Status.SUCCESS;
            }
        }
        return status;
    }
    
    public boolean isUserExist(int userId){
        boolean found = false;
        for(int i=0; i<users.size(); i++){
            if(users.get(i).getId() == userId){
                found = true;
                break;
            }
        }
        return found;
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
