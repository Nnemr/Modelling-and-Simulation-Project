/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m.s;

/**
 *
 * @author ASUS
 */
public class Customer {
    int ID;
    int serviceTime;
    int arrivalTime;
    int interArrivalTime;
    int timeSpentInSystem;
    
    int timeServiceBegin;
    public Customer(int id){
        this.ID = id;
    }
    public int getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getInterArrivalTime() {
        return interArrivalTime;
    }

    public void setInterArrivalTime(int interArrivalTime) {
        this.interArrivalTime = interArrivalTime;
    }

    public int getTimeServiceBegin() {
        return timeServiceBegin;
    }

    public void setTimeServiceBegin(int timeServiceBegin) {
        this.timeServiceBegin = timeServiceBegin;
    }
    public int getID(){
        return ID;
    }
    public void setID(int id){
        ID = id;
    }
    public int getWaitingTime(){
        return timeServiceBegin - arrivalTime;
    }
    public int getTimeServiceEnds(){
        return timeServiceBegin +serviceTime;
    }
    int getTimeSpentInSystem(){
        return getTimeServiceEnds() - arrivalTime;
    }
    @Override
    public String toString(){
        return  interArrivalTime + " " + arrivalTime + " "+timeServiceBegin +" "+ getWaitingTime() + " "+ serviceTime + " " + getTimeServiceEnds() + " " + getTimeSpentInSystem();
    }
}
