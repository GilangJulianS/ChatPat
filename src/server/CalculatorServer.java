/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import handler.CalculatorHandler;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TServerTransport;
import service.CalculatorService;

/**
 *
 * @author gilang
 */
public class CalculatorServer {
    
    public static CalculatorHandler handler;
    
    public static CalculatorService.Processor processor;
    
    public static void main(String[] args){
        try{
            handler = new CalculatorHandler();
            processor = new CalculatorService.Processor(handler);
            
            Runnable run = new Runnable() {

                @Override
                public void run() {
                    simple(processor);
                }
            };
            new Thread(run).start();
        }catch(Exception x){
            x.printStackTrace();
        }
    }
    
    public static void simple(CalculatorService.Processor processor){
        try{
            TServerTransport serverTransport = new TNonblockingServerSocket(9090);
            TServer server = new TSimpleServer(new Args(serverTransport).processor(processor));
            System.out.println("Starting server");
            server.serve();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
