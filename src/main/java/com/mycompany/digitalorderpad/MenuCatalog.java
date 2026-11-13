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

public class MenuCatalog {
    private ArrayList<Menu> availableItems = new ArrayList<>();
    
    public MenuCatalog(){
        availableItems.add(new Menu("Nasi Kuning", 12000, 0));
        availableItems.add(new Menu("Cheetos", 7000,0));
        availableItems.add(new Menu("iphone", 9000, 0));
    }
    
    public ArrayList<Menu> getAvailableItems(){
        return availableItems;
    }
    
    public Menu getItem(int index){
        if (index >= 0 && index < availableItems.size()){
            return availableItems.get(index);
        }
        return null;
    }
}
