/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QueueSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
/**
 *
 * @author ASUS
 */
public class Queue {
     
    public Teller [] tellers = new Teller[2];
    Range []range1, range2;
    int [][] idleTimes;
    long totalRuns=0, totalCustomers=0, totalTime=0;
    long totalSimulationTime=0;
    double maxInsideLength, insideWaitingTime;
    int []customers = new int[2];
    int [] serviceTime, interArrival;
    HashMap<Integer, Integer> serviceTimeCount, interArrivalCount;
    int maxLength_service, maxLength_interArrival;
    ArrayList<Double> insideIdle = new ArrayList<>();
    ArrayList<Double> averageService[]= new ArrayList[2];
    ArrayList<Double>waitInside = new ArrayList<>();
    ArrayList<Double> averageWaiting[] = new ArrayList[2];
    ArrayList<Double>insideTotalIdle=new ArrayList<>();
    /* Checks if the probabilities cumulate to 1*/
    public boolean checkCumulative(double []p, int maxLength, boolean forServiceTime){
         double csum  =0;
        for(double d:p){
            csum+=d;
        }
        if(csum <1)
            return false;
        int t = 0;
        if(forServiceTime)
            range1 = new Range[p.length];
        else
            range2 = new Range[p.length];
        for(int i = 0; i < p.length; ++i){
           if(forServiceTime){
               range1[i]= new Range();
           
               range1[i].first=t;
           }else{
               range2[i] = new Range();
               range2[i].first= t;           
           }t += (p[i] *Math.pow(10, maxLength)); 
           if(forServiceTime)
               range1[i].second=t-1;
           else
               range2[i].second= t-1;
        }
        if(forServiceTime)
            maxLength_service = maxLength;
        else
            maxLength_interArrival= maxLength;
        return true;
    }
    public void setServiceTime(int [] values){
        serviceTime =values;
    }
    public void setInterArrivalTime(int [] values){
        interArrival = values;
    }
    //Searches the service time table for the given value
    public int serviceTimeSearch(int value){
        for(int i =0; i < range1.length; ++i){
            if(value >= range1[i].first && value <= range1[i].second)
                return serviceTime[i];
        }
        return -1;
    }
    //Searches the inter-arrival CDF Table for the given value
    public int interArrivalSearch(int value){
        for(int i =0; i < range2.length; ++i){
            if(value >= range2[i].first && value <= range2[i].second)
                return interArrival[i];
        }
        return -1; 
    }
    public ArrayList<Customer> finalTable [];
    //This function gets the maximum queue length inside the bank
    public void getMaxQueueLength(int cur){
        boolean isFirst = true;
        int maxCount= 0, currentCount = 0;
        for(int i = 0; i < finalTable[cur].size(); ++i){
            if(finalTable[cur].get(i).getServer() == 1){
                if(finalTable[cur].get(i).getWaitingTime() >0){
                    ++currentCount;
                }
                else{
                    currentCount=0;
                }
            }
            else{
                currentCount=0;
            }
             maxCount = Math.max(maxCount, currentCount);
        }
        maxInsideLength +=maxCount;
    }
    /*This function calculates the service time
    for both tellers
    and returns it as the last element in the array lists*/
    void calcuateServiceTime(){
        double result=0;
        result = averageService[0].stream().map((d) -> d).reduce(result, (accumulator, _item) -> accumulator + _item);
        averageService[0].add(result/totalRuns);
        result=0;
        result = averageService[1].stream().map((d) -> d).reduce(result, (accumulator, _item) -> accumulator + _item);
        averageService[1].add(result/totalRuns);
    }
    void calculateWaitingTime(){
        double result=0;
        result = averageWaiting[0].stream().map((d) -> d).reduce(result, (accumulator, _item) -> accumulator + _item);
        averageWaiting[0].add(result/(totalRuns));
        result=0;
        result = averageWaiting[1].stream().map((d) -> d).reduce(result, (accumulator, _item) -> accumulator + _item);
        averageWaiting[1].add(result/totalRuns);
    }
    void calculateChances(){
        double result=0;
        result = waitInside.stream().map((d) -> d).reduce(result, (accumulator, _item) -> accumulator + _item);
        waitInside.add(result/(totalRuns));
    }
    void getIdleTimeInside(){
       double result =0;
       result = insideIdle.stream().map((d) -> d).reduce(result, (accumulator, _item) -> accumulator + _item); 
       insideIdle.add(result/(totalSimulationTime));
    }
    public void simulateSystem(int arrivalSeed, int serviceSeed, long numberOfCustomers, long numberOfRuns, int tellerCapacity) {
        totalSimulationTime=0;
        finalTable = new ArrayList[(int)numberOfRuns];
        averageWaiting[0].clear();
        averageWaiting[1].clear();
        serviceTimeCount.clear();
        interArrivalCount.clear();
        averageService[0].clear();
        averageService[1].clear();
        waitInside.clear();
        insideIdle.clear();
        this.maxInsideLength=0;
        totalRuns=numberOfRuns;
        totalCustomers=numberOfCustomers;
        idleTimes= new int[(int)numberOfCustomers][(int)numberOfCustomers];
        for(int trials = 0; trials<numberOfRuns; ++trials){
            Random randomArrival = new Random(arrivalSeed);
             Random serviceRandom = new Random(serviceSeed);
            finalTable[trials]=new ArrayList<>();
         tellers[0] = new Teller();
            tellers[1] = new Teller();
            tellers[0].setSpace(tellerCapacity);
        int waitingCount =0;
         for(int i = 0;i < numberOfCustomers;++i){
           int servedInside = 0;
           Customer newJob = new Customer(i+1);
           int serviceRandomValue = serviceRandom.nextInt((int)Math.pow(10,this.maxLength_service));
           int interArrivalRandomValue = randomArrival.nextInt((int)Math.pow(10,this.maxLength_interArrival));
           newJob.setRandomValueOfArrival(interArrivalRandomValue);
           newJob.setRandomValueOfService(serviceRandomValue);
           int randomValue = serviceTimeSearch(serviceRandomValue);
           int randomValue2= interArrivalSearch(interArrivalRandomValue);
           newJob.setInterArrivalTime(i==0? 0:randomValue2);
           newJob.setArrivalTime(i==0? 0: finalTable[trials].get(i-1).getArrivalTime() + randomValue2);
           if(tellers[0].isBusy(newJob.getArrivalTime())){
               servedInside=1;
           }
           serviceTimeCount.put(randomValue, serviceTimeCount.get(randomValue) == null?1 : serviceTimeCount.get(randomValue)+1); //Frequency array
           interArrivalCount.put(randomValue2, interArrivalCount.get(randomValue2) == null? 1:interArrivalCount.get(randomValue2)+1);//Frequency array
           newJob.setServer(servedInside);
            newJob.setServiceTime(randomValue); 
            newJob.setTimeServiceBegin(Math.max(newJob.getArrivalTime(), tellers[servedInside].lastServed));
            idleTimes[trials][i] = (i==0? 0: Math.max(0,  newJob.getArrivalTime() - tellers[servedInside].lastServed));
            tellers[servedInside].lastServed =newJob.getTimeServiceEnds();
            System.out.println(newJob +" "+ servedInside + " " + tellers[servedInside].lastServed + " " + idleTimes[trials][i]);
            tellers[servedInside].addCustomer(newJob);
             if(servedInside==1){
                 tellers[1].idleTimes +=newJob.getServiceTime();
             }
            if(servedInside ==1 && newJob.getWaitingTime() >0)
                ++waitingCount;
            tellers[servedInside].averageServiceTime+=newJob.getServiceTime();
            tellers[servedInside].averageWaitingTime+=newJob.getWaitingTime();
            finalTable[trials].add(newJob);
         }
         totalTime = finalTable[trials].get(finalTable[trials].size()-1).getTimeServiceEnds();
         insideIdle.add(totalTime-(double)tellers[1].idleTimes);
         getMaxQueueLength(trials);
         totalSimulationTime +=finalTable[trials].get(finalTable[trials].size()-1).getTimeServiceEnds();
         averageService[0].add((double)tellers[0].averageServiceTime/ (double)tellers[0].servedCustomers.size());
         averageService[1].add((double)tellers[1].averageServiceTime/ (double)tellers[1].servedCustomers.size());    
         this.averageWaiting[0].add((double)tellers[0].averageWaitingTime/(double)tellers[0].servedCustomers.size());
         averageWaiting[1].add((double)tellers[1].averageWaitingTime/(double)tellers[1].servedCustomers.size());
         waitInside.add((double)waitingCount/ (double)tellers[1].servedCustomers.size());
         ++arrivalSeed;
         ++serviceSeed;
        }
        getIdleTimeInside();
        insideTotalIdle.add((double)tellers[1].idleTimes);
         calcuateServiceTime();
         calculateChances();
         calculateWaitingTime();
        maxInsideLength/=(numberOfRuns);
    }
    
    /*Method for testing simulation of some specific values*/
    public void sm2(int n,int a, int b){
        Random r = new Random(a);
        Random r2 = new Random(b);
        finalTable = new ArrayList[1];
        totalRuns=1;
        totalCustomers=10;
        this.checkCumulative(new double[]{0.09, .17, .27, .2, .15, .12}, 2, false);
        this.checkCumulative(new double[]{0.2, .4,.28, .12}, 2, true);
        this.setInterArrivalTime(new int[] {0,1,2,3,4,5});
        this.setServiceTime(new int[]{1,2,3,4});
        finalTable[0] = new ArrayList<>();
        tellers[0] = new Teller();
        tellers[1] = new Teller();
        idleTimes= new int[1][n];
        int waitingCount=0;
        //int [] v1 = {0,0,0,0,0,0,0,0,0,0}, v2 = {  2,2,2,3,3,2,2,2,3,1};
         for(int i = 0;i <n;++i){
           int servedInside = 0;
           int a1 = r.nextInt((int)Math.pow(10,2));
           int b1=r2.nextInt((int)Math.pow(10,2));
           Customer newJob = new Customer(i+1);
           int randomValue = this.serviceTimeSearch(a1);
           int randomValue2=  this.interArrivalSearch(b1);
           newJob.setRandomValueOfArrival(a1);
           newJob.setRandomValueOfService(b1);
           newJob.setInterArrivalTime(i==0? 0:randomValue2);
           newJob.setArrivalTime(i==0? 0: finalTable[0].get(i-1).getArrivalTime() + randomValue2);
           if(tellers[0].isBusy(newJob.getArrivalTime())){
               servedInside=1;
           }
           newJob.setServer(servedInside);
            newJob.setServiceTime(randomValue);
            newJob.setTimeServiceBegin(Math.max(newJob.getArrivalTime(),tellers[servedInside].lastServed));
            if(tellers[servedInside].lastServed == 0 && i>0){
                idleTimes[0][i] = newJob.getArrivalTime();
            }
            else
                 idleTimes[0][i] = (i==0? 0: Math.max(0, newJob.getArrivalTime()-finalTable[0].get(i-1).getTimeServiceEnds()));
            System.out.println(newJob +" "+ servedInside + " " + tellers[servedInside].lastServed + " " + idleTimes[0][i]);
            tellers[servedInside].lastServed =newJob.getTimeServiceEnds();
            tellers[servedInside].addCustomer(newJob);
            finalTable[0].add(newJob);
            int waitTime = newJob.getWaitingTime();
            if(servedInside==1){
                 tellers[1].idleTimes +=newJob.getServiceTime();
             }
            if(servedInside ==1 && waitTime >0)
                ++waitingCount;
            tellers[servedInside].averageServiceTime+=newJob.getServiceTime();
            tellers[servedInside].averageWaitingTime+=waitTime;
         }
         totalTime = finalTable[0].get(finalTable[0].size()-1).getTimeServiceEnds();
         if(tellers[1].idleTimes >=0)
             insideIdle.add(totalTime-(double)tellers[1].idleTimes);
         getMaxQueueLength(0);
         this.averageWaiting[0].add(tellers[0].averageWaitingTime);
         averageWaiting[1].add(tellers[1].averageWaitingTime);
         averageService[0].add(tellers[0].averageServiceTime);
         averageService[1].add(tellers[1].averageServiceTime);    
         waitInside.add((double)waitingCount/n);
         calcuateServiceTime();
         calculateChances();
         this.getIdleTimeInside();
         calculateWaitingTime();
    }
    public Queue() {
        interArrivalCount = new HashMap<>();
        serviceTimeCount = new HashMap<>();
        for(int i=0;i<2; ++i){
            averageService[i] = new ArrayList<>();
            averageWaiting[i]= new ArrayList<>();
        }
         this.checkCumulative(new double[]{0.09, .17, .27, .2, .15, .12}, 2, false);
        this.checkCumulative(new double[]{0.2, .4,.28, .12}, 2, true);
         this.setInterArrivalTime(new int[] {0,1,2,3,4,5});
        this.setServiceTime(new int[]{1,2,3,4});
    }
    
}
