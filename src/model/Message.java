/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author gilang
 */
public class Message {
    
    private static int idCounter = 0;
    private int id;
    private String content;
    
    public Message(String message){
        id = idCounter;
        idCounter++;
        content = message;
    }
    
    public int getId(){
        return id;
    }
    
    public String getContent(){
        return content;
    }
}
