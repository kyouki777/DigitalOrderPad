/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.digitalorderpad;

/**
 *
 * @author Emily
 */
public class Menu {
    private String menuName;
    private int price;
    private int quantity;
    
    public Menu(String menuName, int price, int quantity){
        this.menuName = menuName;
        this.price = price;
        this.quantity = quantity;
    }
    
    public String getMenuName(){return menuName;}
    public int getPrice(){return price;}
    public int getQuantity(){return quantity;}
    
    public int getSubTotal(){
        int subTotal = quantity * price;
//        System.out.print("Subtotal of: " + menuName + " " + subTotal);
        return subTotal;
    }
    
    @Override
    public String toString() {
    return menuName;
}
}
