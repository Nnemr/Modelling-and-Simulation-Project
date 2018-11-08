/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m.s;

import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class Teller {
    public int lastServed;
    public int totalIdleTime;
    int extraSpace;
    ArrayList<Integer> idleTimes;
    ArrayList<Customer> servedCustomers;
    public Teller(){
        extraSpace = 2;
        idleTimes = new ArrayList<>();
        lastServed = 0;
        servedCustomers = new ArrayList<>();
    }
    public boolean isBusy(int arrivalTime){
        if(lastServed ==0)
            return false;
        if(arrivalTime < lastServed)
            extraSpace = Math.max(extraSpace-1, 0);
        else{
            extraSpace = Math.min(2, extraSpace+1);
        }
        return extraSpace ==0;
    }
    public void addSpace(){
        ++extraSpace;
    }
    public void addCustomer(Customer customer){
        servedCustomers.add(customer);
    }
    public void addIdleTime(int time){
        idleTimes.add(time);
    }
}
