/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.digitalorderpad;

/**
 *
 * @author Emily
 */
import java.util.ArrayList;
import java.io.Serializable;
public class OrderHistory implements Serializable{
    
    private ArrayList <Receipt> orderHistory = new ArrayList<>();
    
    public void addReceipt (Receipt receipt){
    orderHistory.add(receipt);
    }
    
    public String getHistorySummary(){
        
        if (orderHistory.isEmpty()){
            return "";}
            
        StringBuilder sb = new StringBuilder();
        
        sb.append("=== ALL COMPLETED ORDERS ===\n\n");
        
        for (Receipt r : orderHistory){ //gets all the receipts in orderHistory through loop
            sb.append(r.getReceiptDetails()).append("\n\n");
        }
        
        return sb.toString();
    }
   
}


