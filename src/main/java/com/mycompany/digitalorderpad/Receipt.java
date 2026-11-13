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

public class Receipt {
    private static int idCounter = 1;
    
    private int id;
    private String customerName;
    private ArrayList<Menu> items = new ArrayList<>();
    
    public Receipt(String customerName){
        this.id = idCounter++;
        this.customerName = customerName;
        this.items = new ArrayList<>(); //creates new list holding all the items everytime there's a new receipt
    }
    
    public int getId(){return id;}
    public String getCustomerName(){return customerName;}
    public ArrayList<Menu> getItems(){return items;}
    
    public int calculateTotal(){
        int grandTotal = 0;
        
        for (Menu item : items){
            grandTotal += item.getSubTotal();
        }
        return grandTotal;
        
    }
    
    public void addItem(Menu item){
        items.add(item); //method to add the selected menu to items arraylist
    }
    
    public String getReceiptDetails(){
        
        StringBuilder sb = new StringBuilder(); //using string builder for memory efficiency
        sb.append("Receipt Id: ").append(id).append("\n");
        sb.append("Customer: ").append(customerName).append("\n");
        
        for (Menu item: items){
            sb.append(item.getMenuName()).append(" x").append(item.getQuantity()).append("\n");
            sb.append(item.getSubTotal()).append("\n");
        }
        
        sb.append("Discount: ").append(getDiscountAmount()).append("\n");
        sb.append("Total price: ").append(calculateFinalTotal());
        
        return sb.toString();
    }
    
    public int calculateFinalTotal(){
        int total = calculateTotal();
        if (total >= 100000){
            return total * 90 / 100;
        }
        return total;
    }
    
    public int getDiscountAmount(){
        int total = calculateTotal();
 
        if (total >= 100000){
            return total * 10 / 100;
        }
        return 0;
    }
    
    public boolean removeLastItem() {
        if (!items.isEmpty()) {
            items.remove(items.size() - 1);
            return true;
        }
        return false;
}
}
