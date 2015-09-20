/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;

import handler.ChatHandler;
import service.ChatService;


/**
 *
 * @author muntahailmi
 */
public class ChatServer {

	public static handler.ChatHandler handler;
	public static ChatService.Processor processor;
	public static void main(String [] args) {
		try {
			handler = new ChatHandler();
			processor = new ChatService.Processor(handler);
			Runnable simple = new Runnable() {
				public void run() {
					simple(processor);
				}
			};
			new Thread(simple).start();
		} catch (Exception x) {
			x.printStackTrace();
		}
	}
	public static void simple(ChatService.Processor processor) {
		try {
			TNonblockingServerTransport serverTransport = new TNonblockingServerSocket(9090);
			TServer server = new TNonblockingServer(new TNonblockingServer.Args(serverTransport).processor(processor));
			System.out.println("Starting the chat server...");
			server.serve();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
