package client;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import service.ChatService;
import model.Status;

/**
 *
 * @author muntahailmi
 */
public class ChatClient {
	public static void main(String [] args) {
		try {
			TTransport transport;
			transport = new TFramedTransport(new TSocket("localhost", 9090));
			transport.open();
			TProtocol protocol = new TBinaryProtocol(transport);
			ChatService.Client client = new ChatService.Client(protocol);
			perform(client);
			transport.close();
		} catch (TException x) {
			x.printStackTrace();
		}
	}
	private static void perform(ChatService.Client client) throws TException {
    	Scanner scanner = new Scanner(System.in);
    	boolean exit=false;
    	int nick=-1;
    	long fetch_interval=1000;
		class FetchTask extends TimerTask{
			private ChatService.Client client;
			private int nick;
			public FetchTask(ChatService.Client in_client, int in_nick){
				client=in_client;nick=in_nick;
			}
			public void run() {
    			try{
    				//fetch message
    				for (String str:client.getMessage(nick).split("///")){
    					if (str.length()>0){
	    					String[] stp=str.split("\\|\\|");
	    					System.out.println("["+stp[1]+"] ("+stp[0]+") "+stp[2]);
    					}
    				}
    			} catch (Exception e){
    				e.printStackTrace();
    			}
			}
		};
		Timer timer=new Timer();
    	while (!exit){
        	String input = scanner.nextLine();
    		if (input.equals("/exit")){
    			if (client.deleteUser(nick)==Status.SUCCESS){
    				exit=true;
    			}
    		} else if (input.equals("/nick")){
    			if (nick!=-1)client.deleteUser(nick);
    			String new_nick="";
    			nick = client.createUser(new_nick);
    		} else if (input.startsWith("/nick ")){
    			if (nick!=-1)client.deleteUser(nick);
    			String new_nick=input.split(" ")[1];
    			nick = client.createUser(new_nick);
    		} else if (input.startsWith("/join ")){
    			String channel=input.split(" ")[1];
    			if (client.join(nick, channel)==Status.SUCCESS){
//        			System.out.println(nick+" joined channel |"+channel+"|");
                } else {
                    System.out.println("You failed to join channel " + channel);
                }
            } else if (input.startsWith("/leave ")) {
                String channel = input.split(" ")[1];
                if (client.leave(nick, channel) == Status.SUCCESS) {
//        			System.out.println(nick+" left channel |"+channel+"|");
                } else {
                    System.out.println("You failed to leave channel " + channel);
                }
            } else if (input.startsWith("@") && input.contains(" ")) {
                String[] in_ar = input.split(" ", 2);
                String channel = in_ar[0].substring(1);
                String msg = in_ar[1];
                if (client.sendMessage(nick, channel, msg) == Status.SUCCESS) {
//        			System.out.println("["+channel+"] ("+nick+") |"+msg+"|");
                } else {
                    System.out.println("You failed to send '" + msg + "' to channel " + channel);
                }
            } else {
                if (client.sendMessage(nick, null, input) == Status.SUCCESS) {
//        			System.out.println("[ALL] ("+nick+") |"+input+"|");
                } else {
                    System.out.println("You failed to send " + input + " to all channel");
                }
            }
            if ((!exit) && (nick != -1)) {
                timer.cancel();
                timer.purge();
                timer = new Timer();
                timer.schedule(new FetchTask(client, nick), 0, fetch_interval);
            }
        }
        timer.cancel();
        timer.purge();
        scanner.close();
        System.out.println("Exit program");
    }
}
