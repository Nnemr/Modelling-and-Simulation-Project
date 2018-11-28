/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InventorySystem;

/**
 *
 * @author ASUS
 */
public class Day {
    int dayNumber;
    int toArrive;

    public int getToArrive() {
        return toArrive;
    }

    public void setToArrive(int toArrive) {
        this.toArrive = toArrive;
    }
    int demand;
    int dayInCycle;
    int demandRandomDigit, leadTimeRandomDigit;
    String hasOrdered;
    public int getDemandRandomDigit() {
        return demandRandomDigit;
    }

    public void setDemandRandomDigit(int demandRandomDigit) {
        this.demandRandomDigit = demandRandomDigit;
    }

    public int getLeadTimeRandomDigit() {
        return leadTimeRandomDigit;
    }

    public void setLeadTimeRandomDigit(int leadTimeRandomDigit) {
        this.leadTimeRandomDigit = leadTimeRandomDigit;
    }
    int leadTime;

    public int getDayInCycle() {
        return dayInCycle;
    }

    public void setDayInCycle(int dayInCycle) {
        this.dayInCycle = dayInCycle;
    }

    public int getLeadTime() {
        return leadTime;
    }

    public void setLeadTime(int leadTime) {
        this.leadTime = leadTime;
    }
    int currentInventory;
    int inventoryEnd, showcaseEnd;
    int shortage;
    int quantity;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public Day(int number){
        dayNumber = number;
        currentInventory = 0;
        inventoryEnd = showcaseEnd=0;
        shortage =0;
        quantity=0;
    }

    public int getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(int dayNumber) {
        this.dayNumber = dayNumber;
    }

    public int getDemand() {
        return demand;
    }

    public void setDemand(int demand) {
        this.demand = demand;
    }

    public int getCurrentInventory() {
        return currentInventory;
    }

    public void setCurrentInventory(int currentInventory) {
        this.currentInventory = currentInventory;
    }

    public int getInventoryEnd() {
        return inventoryEnd;
    }

    public void setInventoryEnd(int inventoryEnd) {
        this.inventoryEnd = inventoryEnd;
    }

    public int getShowcaseEnd() {
        return showcaseEnd;
    }

    public void setShowcaseEnd(int showcaseEnd) {
        this.showcaseEnd = showcaseEnd;
    }

    public int getShortage() {
        return shortage;
    }

    public void setShortage(int shortage) {
        this.shortage = shortage;
    }
    @Override
    public String toString(){
        return dayNumber + " " + dayInCycle + " " + demandRandomDigit + " " + demand + " " +currentInventory+ " " +  " " +inventoryEnd + " " + showcaseEnd + " " + shortage +" " + (leadTime==-1? '-': leadTime);
    }
}
