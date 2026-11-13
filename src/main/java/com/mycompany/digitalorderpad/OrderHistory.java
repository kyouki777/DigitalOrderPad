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
public class OrderHistory {
    
    private ArrayList <Receipt> orderHistory = new ArrayList<>();
    
    public void addReceipt (Receipt receipt){
    orderHistory.add(receipt);
    }
    
    public String getHistorySummary(){
        String summary = "=== ALL COMPLETED ORDERS ===\n\n";
        
        for (Receipt r : orderHistory){ //gets all the receipts in orderHistory through loop
            summary += r.getReceiptDetails(); //passes the details to summary and the method returns all the strings
        }
        
        return summary;
    }
   
}


