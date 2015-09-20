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
        User user = searchUser(userId);
        Channel channel = searchChannel(channelName);
        if(user == null){
            return Status.NOT_FOUND;
        }else if(channel == null){
            channel = new Channel(channelName);
            channel.addUser(user);
            channels.add(channel);
            user.addPendingMessage("Admin||" + channelName + "||You have joined this channel");
            return Status.SUCCESS;
        }else{
            channel.addUser(user);
            user.addPendingMessage("Admin||" + channelName + "||You have joined this channel");
            return Status.SUCCESS;
        }
    }
    
    @Override
    public int leave(int userId, String channelName) throws TException {
        Channel channel = searchChannel(channelName);
        if(channel == null){
            return Status.NOT_FOUND;
        }else{
            searchUser(userId).addPendingMessage("Admin||" + channelName + "||You have left this channel");
            return channel.removeUser(userId);
        }
    }

    @Override
    public String getMessage(int userId) throws TException {
        User user = searchUser(userId);
        String message = user.getPendingMessages();
        if(message != null){
            return message;
        }
        return "";
    }

    @Override
    public int sendMessage(int userId, String channelName, String message) throws TException {
        User user = searchUser(userId);
        if(user == null)
            return Status.NOT_FOUND;
        if(channelName == null || channelName.equals("")){
            boolean joinedAChannel = false;
            for(Channel channel : channels){
                if(channel.isUserExist(userId)){
                    joinedAChannel = true;
                    String newMessage = user.getNick() + "||" + channel.getName() + "||" + message;
                    for(User u : channel.getUsers()){
                        u.addPendingMessage(newMessage);
                    }
                }
            }
            if(!joinedAChannel){
                user.addPendingMessage("Admin|| ||You haven't joined any channel yet");
            }
            return Status.SUCCESS;
        }else{
            Channel channel = searchChannel(channelName);
            String newMessage = user.getNick() + "||" + channelName + "||" + message;
            for(User u : channel.getUsers()){
                u.addPendingMessage(newMessage);
            }
            return Status.SUCCESS;
        }
            
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
        user.addPendingMessage("Admin|| ||You have logged in as " + user.getNick());
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
    
    public Channel searchChannel(String channelName){
        Channel channel = null;
        for(int i=0; i<channels.size(); i++){
            if(channels.get(i).getName().equals(channelName)){
                channel = channels.get(i);
                break;
            }
        }
        return channel;
    }
    
    public User searchUser(int userId){
        User user = null;
        for(int i=0; i<users.size(); i++){
            if(users.get(i).getId() == userId){
                user = users.get(i);
                break;
            }
        }
        return user;
    }
    
    
    
}
