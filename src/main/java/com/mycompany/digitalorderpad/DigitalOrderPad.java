/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.digitalorderpad;

/**
 *
 * @author Emily
 */
import javax.swing.JOptionPane;
import java.util.ArrayList;

import javax.swing.JScrollPane; //for the show history scroll bar
import javax.swing.JTextArea;
import java.awt.Dimension;

import java.io.FileInputStream; //for saving data when the program stops
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

public class DigitalOrderPad {
    
    private static MenuCatalog catalog = new MenuCatalog();
    private static OrderHistory history = new OrderHistory();

    public static void main(String[] args) {
        
        //debugging stuff
//        Menu item1 = new Menu("Teh", 3000, 1);
//        Menu item2 = new Menu("Nasi Goreng", 10000, 2);
//        
//        Receipt order1 = new Receipt("Emily");
//        order1.addItem(item1);
//        order1.addItem(item2);
//        System.out.println("Total" + order1.calculateTotal());
//        System.out.println("Subtotal " + item1.getSubTotal());
//        System.out.println("Subtotal " + item2.getSubTotal());
//        System.out.println(order1.getReceiptDetails()); 
        
 
        loadData();
        boolean running = true;
        
        while (running){
            String[] options = {"New Transaction", "Edit Menu", "Transaction History", "Exit"};
            
            int choice = JOptionPane.showOptionDialog(
                null,
                    "Universitas Republik Indonesia's Canteen", "Digital Order Pad",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    options[0]);
            
            switch (choice){
                case 0:
                    newTransaction();
                    break;
                case 1:
                    editMenu();
                    break;
                case 2:
                    transactionHistory();
                    break;
                case 3:
                case JOptionPane.CLOSED_OPTION:
                    running = false;
                    JOptionPane.showMessageDialog(null, "Thank you for using Digital Order Pad!");
                    saveData();
                    break;
            }
        }
        
    }
    
    public static void newTransaction(){
        String customerName = JOptionPane.showInputDialog(null, "Enter Customer Name: ");
        
        if (customerName == null || customerName.trim().isEmpty()){
            customerName = "Guest";
        }
        
        Receipt currentReceipt = new Receipt(customerName);
        boolean ordering = true;
        
        while (ordering){
            ArrayList<Menu> available = catalog.getAvailableItems();
            
            if (available.isEmpty()){
                JOptionPane.showMessageDialog(null, "The canteen catalog is empty.");
                return;
            }
            
            StringBuilder sb = new StringBuilder();
            sb.append("~~~ CANTEEN MENU ~~~\n");
            for (int i = 0; i < available.size(); i++){
                Menu item = available.get(i);
                sb.append(i + 1).append(". ").append(item.getMenuName())
                        .append(" - Rp ").append(item.getPrice()).append("\n");
            }
            sb.append("\n~~~ CART ~~~\n");
            ArrayList<Menu> cartItems = currentReceipt.getItems();
            if (cartItems.isEmpty()){
                sb.append("(Cart is empty.)\n");
            } else{
                for (int i = 0; i < cartItems.size(); i++){
                    Menu item = cartItems.get(i);
                    sb.append(i + 1).append(". ")
                            .append(item.getMenuName()).append("x ")
                            .append(item.getQuantity()).append(" = Rp ")
                            .append(item.getSubTotal()).append("\n");
                }
                sb.append("-------------------------\n");
                sb.append("Current Total: Rp ").append(currentReceipt.calculateTotal()).append("\n");
            }
            
            String[] options = {"Add item", "Check out", "Remove last item", "Back to Main Menu"};
            
            int choice = JOptionPane.showOptionDialog(
                    null, 
                    sb.toString(), 
                    "Choose item", 
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    options[0]);
            if (choice == JOptionPane.CLOSED_OPTION){
                break;
            }
            switch (choice){
                case 0:
                    Menu[] itemArray = available.toArray(new Menu[0]);

                    Menu selected = (Menu) JOptionPane.showInputDialog(
                    null,
                    sb.toString(),                
                    "Choose Item",                
                    JOptionPane.QUESTION_MESSAGE, 
                    null,                    
                    itemArray,                    
                    itemArray[0]);
                
                if (selected != null){
                    String quantityInput = JOptionPane.showInputDialog(null, "Enter quantity for " + selected.getMenuName() + ":");
                if (quantityInput != null && !quantityInput.trim().isEmpty()){
                    
                    try{
                        int quantity = Integer.parseInt(quantityInput.trim()); //converts string input into int
                        if (quantity > 0){
                            currentReceipt.addItem(new Menu(selected.getMenuName(), selected.getPrice(), quantity));
                            JOptionPane.showMessageDialog(null, "Added " + quantity + "x " + selected.getMenuName() + " to order!");
                        } else{
                            JOptionPane.showMessageDialog(null, "Quantity must be greater than .0");
                        }
                    } catch (NumberFormatException e){
                        JOptionPane.showMessageDialog(null, "Invalid quantity number.");
                    }
                } 
                }
                break;
                
                case 1:
                    //check out button
                    if (currentReceipt.getItems().isEmpty()){
                        JOptionPane.showMessageDialog(null, "Cart is empty!");
                        
                    } else{
                        JOptionPane.showMessageDialog(null, currentReceipt.getReceiptDetails());
                        history.addReceipt(currentReceipt);
                        saveData();
                        ordering = false;
                    }
                    break;
                case 2:
                    // remove item button
                    if (currentReceipt.getItems().isEmpty()){
                        JOptionPane.showMessageDialog(null, "Cart is Empty!");
                    } else{
                        if (currentReceipt.removeLastItem()){
                            JOptionPane.showMessageDialog(null, "removed last added item.");
                        }
                    }
                    break;
                case 3:
                    //back to main menu button
                    return;
            
            }
        }
    }
    
    public static void editMenu(){
        //add menu n remove menu
        boolean editing = true;
        while (editing){
            ArrayList<Menu> available = catalog.getAvailableItems();
            
            if (available.isEmpty()){
                JOptionPane.showMessageDialog(null, "The canteen catalog is empty.");
                return;
            }
            
            StringBuilder sb = new StringBuilder();
            sb.append("~~~ CANTEEN MENU ~~~\n");
            for (int i = 0; i < available.size(); i++){
                Menu item = available.get(i);
                sb.append(i + 1).append(". ").append(item.getMenuName())
                        .append(" - Rp ").append(item.getPrice()).append("\n");
            }
            
            
            String[] options = {"Add Menu", "Remove Menu", "Back to Main Menu"};
            
            int choice = JOptionPane.showOptionDialog(
                    null, 
                    sb.toString(), 
                    "Choose item", 
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    options[0]);
            
            switch (choice){
                case 0:
                    //add menu
                    String addMenuName = JOptionPane.showInputDialog(null, "Enter menu name: ");
                    if (addMenuName == null || addMenuName.trim().isEmpty()){return;}
                    
                    String addMenuPrice = JOptionPane.showInputDialog(null, "Enter menu price: ");
                    if (addMenuName == null || addMenuName.trim().isEmpty()){return;}
                    
                    try{
                        int price = Integer.parseInt(addMenuPrice.trim());//change string input into integer
                        if (price>0){
                            catalog.addItem(new Menu(addMenuName.trim(), price,0));
                            saveData();
                            JOptionPane.showMessageDialog(null,addMenuName + " Rp " + addMenuPrice + " added to catalog!");
                        }else{
                            JOptionPane.showMessageDialog(null, "Price must be greater than 0.");
                        }

                    }catch (NumberFormatException e){
                                JOptionPane.showMessageDialog(null, "Invalid price. Please enter numbers only");
                                }
                    break;
                    
                case 1:
                    //remove menu
                    ArrayList<Menu> availableItems = catalog.getAvailableItems();
                    
                    if (availableItems.isEmpty()){
                        JOptionPane.showMessageDialog(null, "Catalog is already empty!");
                        break;
                        
                    }
                    Menu[] itemArray = available.toArray(new Menu[0]);
                    Menu selected = (Menu) JOptionPane.showInputDialog(
                        null,
                            "Select a menu to remove.",
                            "Remove menu",
                            JOptionPane.WARNING_MESSAGE,
                            null,
                            itemArray,
                            itemArray[0]);
                            
                    if (selected != null){
                        int indexToRemove = availableItems.indexOf(selected); //get the index of the selected menu
                        String removedName = selected.getMenuName(); //get menu name for the show message dialog
                        
                        catalog.removeItem(indexToRemove);
                        saveData();
                        JOptionPane.showMessageDialog(null, removedName + " has been removed from catalog!");
                    }
                    break;
                case 2:
                    //back to main menu
                    return;
                    
            }
        }
    }
    
    public static void transactionHistory(){
        String summary = history.getHistorySummary();
        
        if (summary == null || summary.trim().isEmpty()){
            JOptionPane.showMessageDialog(null, "No completed transactions recorded yet.");
            return;
        }
        
        JTextArea textArea = new JTextArea(summary);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(380, 300));
        
        JOptionPane.showMessageDialog(null, scrollPane, "Transaction History", JOptionPane.PLAIN_MESSAGE);

        }
    
    public static void saveData(){
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("canteen_data.dat"))){
            out.writeObject(catalog);
            out.writeObject(history);
        } catch (IOException e){
            JOptionPane.showMessageDialog(null, "Failed to save data: " + e.getMessage());
        }
    }
    
    public static void loadData(){
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("canteen_data.dat"))){
            catalog = (MenuCatalog) in.readObject();
            history = (OrderHistory) in.readObject();
        } catch (Exception e){
            // File doesn't exist yet on first run. keep default catalog & empty history
        }
    }
    
}
