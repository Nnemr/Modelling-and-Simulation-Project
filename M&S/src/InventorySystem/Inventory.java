/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InventorySystem;

import java.util.ArrayList;
import java.util.Random;
import QueueSystem.Range;
import java.util.HashMap;
/**
 *
 * @author ASUS
 */
public class Inventory {
    int totalRuns;
    int totalDays;
    int cycle, minThreshold;
    HashMap<Integer, Integer> demandCount, leadTimeCount;
    int inventorySize, showroomSize;
    int daysTillArrival;
    int totalLeadUsed;
    int []demand, leadTime;
    Range[] demandRange, leadTimeRange;
    int demand_maxDigits, lead_maxDigits;
    int toOrder;
    ArrayList<Day>finalTable [];
   ArrayList<Double> endingInventory, endingShowroom;
   ArrayList<Double>totalShortage = new ArrayList<>();
    public Inventory(){
       setDemand(new int[]{0,1,2,3,4});
        setLeadtime(new int[]{1,2,3});
        demandCount = new HashMap<>();
        leadTimeCount = new HashMap<>();
        cycle = 2;
        minThreshold = 4;
        endingInventory=new ArrayList<>();
        endingShowroom=new ArrayList<>();
        inventorySize = 8;
        showroomSize = 4;
        checkCumulative(new double[]{0.05, .28,.37, .20,.10}, 2, true);
        checkCumulative(new double[]{0.55, .35, .1}, 2, false);
    }
   public boolean checkCumulative(double []p, int maxLength, boolean forDemand){
         double csum  =0;
        for(double d:p){
            csum+=d;
        }
        csum = Math.round(csum*1e4)/1e4;
        if(csum <1)
            return false;
        int t = 0;
        if(forDemand)
            demandRange = new Range[p.length];
        else
            leadTimeRange = new Range[p.length];
        for(int i = 0; i < p.length; ++i){
           if(forDemand){
               demandRange[i]= new Range();
           
               demandRange[i].first=t;
           }else{
               leadTimeRange[i] = new Range();
               leadTimeRange[i].first= t;           
           }t += (p[i] *Math.pow(10, maxLength)); 
           if(forDemand)
               demandRange[i].second=t-1;
           else
               leadTimeRange[i].second= t-1;
        }
        if(forDemand)
            demand_maxDigits = maxLength;
        else
            lead_maxDigits= maxLength;
        return true;
    }
   public void setDemand(int []p){
       demand=p;
   }
   public void calculateAverageEnding(){
       double result =0;
       result = endingInventory.stream().map((d) -> d).reduce(result, (accumulator, _item) -> accumulator + _item);
       result/=totalRuns;
       endingInventory.add(result);
       result =0;
       result = endingShowroom.stream().map((d) -> d).reduce(result, (accumulator, _item) -> accumulator + _item);
       result /=totalRuns;
       endingShowroom.add(result);
   }
   public void calculateShortage(){
       double result =0;
       result = totalShortage.stream().map((d) -> d).reduce(result, (accumulator, _item) -> accumulator + _item);
       result/=totalRuns;
       totalShortage.add(result);
   }
   public void setLeadtime(int [] p){
       leadTime=p;
   }
   public void setThreshold(int t){
       minThreshold=t;
   }
   public void setCycle(int c){
       cycle = c;
   }
    public void simulateInventory(int leadSeed, int demandSeed, int runs, int weeks){
        totalRuns = runs;
        totalDays = weeks;
        totalLeadUsed=0;
        totalShortage.clear();
        demandCount.clear();
        endingInventory.clear();
        endingShowroom.clear();
        leadTimeCount.clear();
            finalTable = new ArrayList[runs];
            for(int i = 0; i <runs;++i){
                finalTable[i] = new ArrayList<>();
                Random randomDemand= new Random(demandSeed);
                Random randomLead = new Random(leadSeed);
                int dayOfArrival=2;
                int []randomLeadDigits =new int[weeks];
                for(int j = 0; j < weeks; ++j){
                    randomLeadDigits[j] = randomLead.nextInt((int)Math.pow(10, this.lead_maxDigits));
                }
                int leadIndex=0;
                int averageInv_end=0, avg_show_end=0;
                int shortage=0;
                toOrder=5;
                for(int j = 0; j < weeks; ++j){
                    Day newDay = new Day(j+1);
                    int currentInventory =(j==0? 6:finalTable[i].get(j-1).getInventoryEnd());
                    int randomDemandDigit= randomDemand.nextInt((int)Math.pow(10, demand_maxDigits));
                    int demand2 = demandSearch(randomDemandDigit);
                    int temp = demand2;
                    demandCount.put(demand2, demandCount.get(demand2)==null? 1:demandCount.get(demand2)+1);
                    if(dayOfArrival >j){
                        currentInventory +=toOrder;
                        toOrder=0;
                    }
                    newDay.setDemand(demand2);
                    newDay.setDemandRandomDigit(demand_maxDigits);
                    newDay.setDayInCycle((j==0? 0: (j-1)%cycle +1));
                    newDay.setCurrentInventory(currentInventory);
                    newDay.setInventoryEnd(Math.max(currentInventory-demand2, 0));
                    newDay.setShortage(Math.max(demand2- currentInventory, 0));
                    newDay.setToArrive(dayOfArrival);
                   
                    if(newDay.getInventoryEnd() > showroomSize){
                        averageInv_end +=  currentInventory-showroomSize ;
                        avg_show_end +=showroomSize;
                    }
                    else{
                        avg_show_end+=currentInventory;
                    }
                    if(newDay.getShortage()>0)
                        ++shortage;
                    if(j%cycle==0 &&j>0){
                        if(currentInventory <=minThreshold && j >dayOfArrival){
                                int leadDigit = randomLeadDigits[leadIndex++];
                                int leadDay = this.leadTimeSearch(leadDigit);
                                ++totalLeadUsed;
                                leadTimeCount.put(leadDay, leadTimeCount.get(leadDay)==null? 1:leadTimeCount.get(leadDay)+1);
                                toOrder = (inventorySize + showroomSize)-currentInventory;
                                dayOfArrival = j + leadDay;
                                newDay.setLeadTime(leadDay);
                                newDay.setLeadTimeRandomDigit(leadDigit);
                                
                            }
                            else{
                                newDay.setLeadTimeRandomDigit(-1);
                                newDay.setLeadTime(-1);
                                newDay.setToArrive(-1);
                            }
                        
                    }
                    else{
                                newDay.setLeadTimeRandomDigit(-1);
                                newDay.setLeadTime(-1);
                                newDay.setToArrive(-1);
                    }
                     newDay.setQuantity(toOrder);
                      finalTable[i].add(newDay);
                }
                System.out.println(shortage);
                ++leadSeed;
                ++demandSeed;
                this.endingInventory.add((double)averageInv_end/(double)weeks);
                this.endingShowroom.add((double)avg_show_end/(double)weeks);
                if(shortage>0)
                    this.totalShortage.add((double)shortage);
            }
            this.calculateAverageEnding();
            this.calculateShortage();
    }
    /*searches the demand CDF table for the given value
    returns -1 in case the value is not found
    */
    private int demandSearch(int randomDemandDigit) {
       for(int i=0; i < demandRange.length; ++i){
           if(randomDemandDigit>=demandRange[i].first &&randomDemandDigit <=demandRange[i].second )
               return demand[i];
       }
        return -1;
    }
    /*searches the lead time CDF table for the given value
    returns -1 in case the value is not found
    */
    private int leadTimeSearch(int leadDayRandom) {
        for(int i=0;i<leadTimeRange.length;++i){
            if(leadTimeRange[i].first <=leadDayRandom && leadDayRandom <=leadTimeRange[i].second)
                return leadTime[i];
        }
        return -1;
    }
    //Console Testing
    public static void main(String[] args){
        Inventory I = new Inventory();

        I.simulateInventory(0, 1, 1, 10);
    }
}
