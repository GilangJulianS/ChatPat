/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author gilang
 */
public class User {
    
    private final static String[] defaultNicks = {"alpha", "beta", "echo", "delta"};
    private static int idCounter = 0;
    private int id;
    private String nick;
    private List<String> messages;

    public User(){
        id = idCounter;
        idCounter++;
        nick = getRandomNick();
        messages = new ArrayList<>();
    }
    
    public User(String nickName){
        id = idCounter;
        idCounter++;
        nick = nickName;
        messages = new ArrayList<>();
    }
    
    public int getId() {
        return id;
    }

    public String getNick() {
        return nick;
    }
    
    public String getRandomNick(){
        int nickCount = defaultNicks.length;
        Random r = new Random();
        return defaultNicks[r.nextInt(nickCount)];
    }
    
    public String getPendingMessages(){
        StringBuilder builder = new StringBuilder();
        for(String message : messages){
            builder.append(message + "///");
        }
        messages = new ArrayList<>();
        return builder.toString();
    }
    
    public void addPendingMessage(String message){
        messages.add(message);
    }
}
