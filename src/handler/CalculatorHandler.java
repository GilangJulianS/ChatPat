/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handler;

import service.*;
/**
 *
 * @author gilang
 */
public class CalculatorHandler implements CalculatorService.Iface{
    
    public int multiply(int n1, int n2){
        System.out.println("Multiply(" + n1 + "," + n2 + ")");
        return n1*n2;
    }
    
    public int add(int n1, int n2){
        System.out.println("Add(" + n1+ "," + n2 + ")");
        return n1+n2;
    }
}
