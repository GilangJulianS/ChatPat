/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import service.CalculatorService;

/**
 *
 * @author gilang
 */
public class CalculatorClient {
    public static void main(String[] args){
        try{
            TTransport transport;
            transport = new TSocket("localhost", 9090);
            transport.open();
            
            TProtocol protocol = new TBinaryProtocol(transport);
            CalculatorService.Client client = new CalculatorService.Client(protocol);
            perform(client);
            transport.close();
        }catch(TException e){
            e.printStackTrace();
        }
    }
    
    public static void perform(CalculatorService.Client client)throws TException{
        int product = client.multiply(3, 5);
        System.out.println("3*5=" + product);
    }
}
