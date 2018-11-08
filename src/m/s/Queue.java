/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m.s;

import java.util.ArrayList;
import java.util.Scanner;
/**
 *
 * @author ASUS
 */
public class Queue {
    public Teller [] tellers = new Teller[2];
    public Range []range1, range2;
    int [] serviceTime, interArrival;
    public int [][] prob;
    static String [] types= {"Good", "Fair", "Poor"};
    public boolean checkCumulative(double []p, int maxLength){
         double csum   =0;
        for(double d:p){
            csum+=d;
        }
        if(csum <1)
            return false;
        int t = 0;
        for(int i = 0; i < p.length; ++i){
            range1[i].first=t;
            t += (p[i] *Math.pow(10, maxLength+1));
            range1[i].second = t -1;
            System.out.println(range1[i].first + " " +range1[i].second);
        }
        return true;
    }
    public int serviceTimeSearch(int value){
        for(int i =0; i < range1.length; ++i){
            if(value >= range1[i].first && value <= range1[i].second)
                return serviceTime[i];
        }
        return -1;
    }
    public int interArrivalSearch(int value){
        for(int i =0; i < range2.length; ++i){
            if(value >= range2[i].first && value <= range2[i].second)
                return interArrival[i];
        }
        return -1; 
    }
    public static ArrayList<Customer> finalTable = new ArrayList<Customer>();
    public static void main(String[] args) {
        Queue system= new Queue();
         system.tellers[0] = new Teller();
        system.tellers[1] = new Teller();
        Queue q = new Queue();
        int n;
//        int numberOfJobs;
          Scanner in = new Scanner(System.in);
         n = in.nextInt();
         for(int i = 0;i < n;++i){
             int servedInside = 0;
             Customer newJob = new Customer(i+1);
             int serviceRandomValue = (int)(Math.random()*100);
             int interArrivalRandomValue = (int)(Math.random()*100);
           int randomValue = q.serviceTimeSearch(serviceRandomValue);
           newJob.setInterArrivalTime(i==0? 0:q.interArrivalSearch(interArrivalRandomValue));
           newJob.setArrivalTime(i==0? 0: finalTable.get(i-1).getArrivalTime() + randomValue);
           if(system.tellers[0].isBusy(newJob.getArrivalTime())){
               servedInside=1;
           }
            newJob.setServiceTime(randomValue);
            //newJob.setTimeServiceBegin(i==0? 0:Math.max(newJob.getArrivalTime(),finalTable.get(i-1).getTimeServiceEnds()));
            newJob.setTimeServiceBegin(Math.max(newJob.getArrivalTime(), system.tellers[servedInside].lastServed));
            system.tellers[servedInside].lastServed =newJob.getTimeServiceEnds();
            system.tellers[servedInside].addCustomer(newJob);
            system.tellers[servedInside].idleTimes.add(i==0? 0: Math.max(0, newJob.getArrivalTime() - system.tellers[servedInside].lastServed));
             System.out.println(newJob +" "+ servedInside + " " + system.tellers[servedInside].lastServed);
       // newJob.setWaitingTime(newJob.getTimeServiceBegin()- newJob.getArrivalTime());
            //newJob.setTimeServiceEnds(newJob.timeServiceBegin + newJob.serviceTime);
           // newJob.idleTime = (i==0? 0: Math.max(newJob.arrivalTime - finalTable.get(i-1).getTimeServiceEnds(),0));
            system.tellers[servedInside].addCustomer(newJob);
            finalTable.add(newJob);
         }
    }
    public Queue() {
        
    }
    
}
