/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InventorySystem;

import QueueSystem.Range;
import java.awt.BorderLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author ASUS
 */
public class Sim_Results extends javax.swing.JFrame {

    Inventory inventory;
    public Sim_Results() {
        initComponents();
    }

    Sim_Results(Inventory inv) {
        initComponents();
        setResults(inv);
        setCDFTable(1,inv.leadTime, inv.leadTimeRange);
        setCDFTable(0,inv.demand, inv.demandRange);
    }
    private DefaultCategoryDataset dataSet[] = new DefaultCategoryDataset[2];
    private JFreeChart chart[] = new JFreeChart[2];
    private File imageFile;
    public void createDataSet(int index,HashMap<Integer,Integer> data)
    {
        dataSet[index] = new DefaultCategoryDataset();
        if(index ==0){
            double t =0.0;
           for(Map.Entry<Integer, Integer>d:data.entrySet()){
               dataSet[index].addValue((double)d.getValue()/inventory.totalLeadUsed, "Experimental Probability", d.getKey());
             double temp = (double)d.getValue()/(double)inventory.totalLeadUsed;
             t = t+ (temp*d.getKey());
             System.out.println(d.getKey()+ "= " + (double)d.getValue()/inventory.totalLeadUsed);
           };
           //this.avg_leadTime.setText("Avg. Lead Time =" + " " + t);
           DefaultTableModel tempTable = (DefaultTableModel)totalResultsTable.getModel();
           tempTable.addRow(new Object[]{"Avg. Lead Time", t});
        }
        else{
        double total =inventory.totalRuns*inventory.totalDays;
            double t =0;
            for(Map.Entry<Integer, Integer> d:data.entrySet()){
            dataSet[index].addValue((double)d.getValue()/total, "Experimental Probability",d.getKey());
            double temp = (double)d.getValue()/total;
            t = t +(temp*d.getKey());
                System.out.println(d.getKey() + "-> " + temp);
            };
            DefaultTableModel tempTable = (DefaultTableModel)totalResultsTable.getModel();
           tempTable.addRow(new Object[]{"Avg. Demand Time", t});
            //this.avg_demand.setText("Avg. Demand = " + t);
        }
    }
    
    public void createChart(int index)
    {
        chart[index] = ChartFactory.createBarChart((index == 1? "Demand Time": "Lead Time"), "Time", "Probability", dataSet[index]);
    }

    public void saveChart(int index)
    {
        try 
        {
            imageFile = new File((index==0?"Lead Time.jpg":"Demand Time.jpg"));
            ChartUtilities.saveChartAsJPEG(imageFile, chart[index], 500, 500);
        } 
        catch (Exception e) 
        {
            System.err.println("Problem in Creating Chart File");
        }
    }
    private void setResults(Inventory inv){
      inventory = new Inventory();
      inventory = inv;
      createDataSet(1, inventory.demandCount);
      createDataSet(0, inventory.leadTimeCount);
      createChart(0);
      createChart(1);
      saveChart(0);
      saveChart(1);
      double []res = new double[3];
      res[0]= (inventory.endingInventory.isEmpty()? 0: inventory.endingInventory.get(inventory.endingInventory.size()-1));
     // this.avg_inv.setText(avg_inv.getText() + " " +averageEnding+"");
      res[2] = (inventory.totalShortage.isEmpty()? 0 : inventory.totalShortage.get(inventory.totalShortage.size()-1));
     // this.shortageDays.setText(shortageDays.getText() + " " + shortage);
     res[1] = (inventory.endingShowroom.isEmpty()? 0: inventory.endingShowroom.get(inventory.endingShowroom.size()-1));
     // this.avg_show.setText(avg_show.getText() + " " +averageEnding+"");
      this.runs.setText(runs.getText() + inventory.totalRuns);
      DefaultTableModel table =(DefaultTableModel)resultTable.getModel();
      ArrayList<Day> finalTable []= inventory.finalTable;
      for(int i = 0; i < finalTable[0].size();++i){
          Day job = finalTable[0].get(i);
          table.addRow(new Object []{
              job.getDayNumber(), job.getDayInCycle(), job.getDemandRandomDigit(),
              job.getDemand(), job.getCurrentInventory(), job.getInventoryEnd(),
              job.getShortage(), (job.getLeadTime()==-1? "-":job.getLeadTime()), (job.getLeadTimeRandomDigit()==-1? "-":job.getLeadTimeRandomDigit()),
              job.getToArrive(), job.getQuantity()
          });
      }
      table = (DefaultTableModel)totalResultsTable.getModel();
      String [] attributes= {"Avg. Inv. End", "Avg. Show. End", "Days of Shortage"};
      for(int i=0; i < 3; ++i){
          table.addRow(new Object[]{
              attributes[i], res[i]
          });
      }
    }
    private void setCDFTable(int index,int[] values, Range [] ranges){
       DefaultTableModel table = (DefaultTableModel)(index==0?demandTable.getModel():leadTable.getModel());
       for(int i =0; i < values.length; ++i){
           table.addRow(new Object[]{
               values[i], ranges[i].first + "-" + ranges[i].second
       });  
    }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        resultTable = new javax.swing.JTable();
        runs = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        avg_demand = new javax.swing.JLabel();
        avg_leadTime = new javax.swing.JLabel();
        CDF1 = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        demandTable = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        leadTable = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        totalResultsTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        resultTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Day", "Day In Cycle", "Demand RN", "Demand", "Current Inv.", "End Inv.", "Shortage", "Lead Time", "Lead RN", "Day of Arrival", "Amount"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(resultTable);

        runs.setText("No. Of Runs :");

        jButton1.setText("Return");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        avg_demand.setText("Average Demand=");

        avg_leadTime.setText("Average Lead Time=");

        demandTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Value", "Range"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(demandTable);

        CDF1.addTab("Demand", jScrollPane2);

        leadTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Value", "Range"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(leadTable);

        CDF1.addTab("Lead Time", jScrollPane3);

        totalResultsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Attribute", "Value"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(totalResultsTable);

        CDF1.addTab("Results", jScrollPane4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(624, 624, 624))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 803, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(CDF1, javax.swing.GroupLayout.PREFERRED_SIZE, 507, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(516, 516, 516)
                        .addComponent(runs))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(484, 484, 484)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(avg_leadTime)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(avg_demand)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CDF1, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addComponent(runs)
                .addGap(16, 16, 16)
                .addComponent(avg_demand)
                .addGap(32, 32, 32)
                .addComponent(avg_leadTime)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 92, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );

        CDF1.getAccessibleContext().setAccessibleName("CDFs");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        InventoryGUI inv_GUI= new InventoryGUI(inventory);
        inv_GUI.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane CDF1;
    private javax.swing.JLabel avg_demand;
    private javax.swing.JLabel avg_leadTime;
    private javax.swing.JTable demandTable;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable leadTable;
    private javax.swing.JTable resultTable;
    private javax.swing.JLabel runs;
    private javax.swing.JTable totalResultsTable;
    // End of variables declaration//GEN-END:variables
}
