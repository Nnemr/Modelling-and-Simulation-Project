/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QueueSystem;

import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class Teller {
    public int lastServed;
    int extraSpace;
    double averageServiceTime;
    double averageWaitingTime;
    int idleTimes;
    public int getLastServed() {
        return lastServed;
    }

    public void setLastServed(int lastServed) {
        this.lastServed = lastServed;
    }

    public int getExtraSpace() {
        return extraSpace;
    }
    public int getIdleTimes() {
        return idleTimes;
    }

    public void setIdleTimes(int idleTimes) {
        this.idleTimes = idleTimes;
    }

    public ArrayList<Customer> getServedCustomers() {
        return servedCustomers;
    }

    public void setServedCustomers(ArrayList<Customer> servedCustomers) {
        this.servedCustomers = servedCustomers;
    }
    ArrayList<Customer> servedCustomers;
    public Teller(){
        averageServiceTime =0;
        extraSpace = 2;
        idleTimes = 0;
        lastServed = 0;
        servedCustomers = new ArrayList<>();
    }
    public boolean isBusy(int arrivalTime){
        int servedSize =servedCustomers.size(); 
        if(servedSize - extraSpace <0)
            return false;
        for(int i = servedSize -1, j = 0; j < extraSpace; --i, ++j){
            if(arrivalTime >= servedCustomers.get(i).getTimeServiceEnds())
                return false;
        }
        return true;
    }
    public void setSpace(int s){
        extraSpace =s;
    }
    public void addCustomer(Customer customer){
        servedCustomers.add(customer);
    }
}
