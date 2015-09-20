/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handler;

import java.util.ArrayList;
import java.util.List;
import model.Channel;
import model.Status;
import model.User;
import org.apache.thrift.TException;
import service.*;

/**
 *
 * @author gilang
 */
public class ChatHandler implements ChatService.Iface{

    List<User> users;
    List<Channel> channels;
    
    public ChatHandler(){
        users = new ArrayList<>();
        channels = new ArrayList<>();
    }
    
    @Override
    public int join(int userId, String channelName) throws TException {
        return 0;
    }

    @Override
    public int leave(String channelName) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int sendMessage(int userId, String channelName, String message) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int createUser(String nick) throws TException {
        User user;
        if(nick == null || nick.equals("")){
            user = new User();
            users.add(user);
        }else{
            user = new User(nick);
            users.add(user);
        }
        return user.getId();
    }

    @Override
    public int deleteUser(int userId) throws TException {
        boolean found = false;
        int i=0;
        while(i<users.size() && !found){
            if(users.get(i).getId() == userId){
                users.remove(i);
                found = true;
            }
        }
        if(found){
            return Status.SUCCESS;
        }
        return Status.FAIL;
    }
    
    
    
}