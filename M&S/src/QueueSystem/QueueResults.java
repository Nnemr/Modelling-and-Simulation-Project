/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QueueSystem;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
public class QueueResults extends javax.swing.JFrame {

    /**
     * Creates new form Result
     */
    Queue queue;
    private DefaultCategoryDataset dataSet[] = new DefaultCategoryDataset[2];
    private JFreeChart chart[] = new JFreeChart[2];
    private File imageFile;
    
    public void createDataSet(int index,HashMap<Integer,Integer> data)
    {
        dataSet[index] = new DefaultCategoryDataset();
        data.entrySet().forEach((d) -> {
            dataSet[index].addValue(d.getValue(), "Number of Occurences",d.getKey());
        });
    }
    
    public void createChart(int index)
    {
        chart[index] = ChartFactory.createBarChart((index == 1? "Average Waiting Time": "Average Service Time"), "Time", "Number of Occurences", dataSet[index]);
    }

    public void saveChart(int index)
    {
        try 
        {
            imageFile = new File((index==0?"Avg Wait Time.jpg":"Avg Service Time.jpg"));
            ChartUtilities.saveChartAsJPEG(imageFile, chart[index], 500, 500);
        } 
        catch (Exception e) 
        {
            System.err.println("Problem in Creating Chart File");
        }
    }
    public QueueResults() {
        initComponents();
    }
    public QueueResults(Queue system){
        initComponents();
        queue = new Queue();
        queue = system;
        ArrayList<Customer> finalTable[] = system.finalTable;
        int [][] idleTimes = system.idleTimes;
        DefaultTableModel model = (DefaultTableModel) resultTable.getModel();
        int n = finalTable.length;
        noOfRuns.setText("Number of Runs: " + system.totalRuns);
        for(int i = 0;i < finalTable[n-1].size(); ++i){
            Customer customer = finalTable[n-1].get(i);
            Object [] newRow = new Object[]{customer.ID, customer.getRandomValueOfArrival(), customer.getInterArrivalTime(), 
                customer.getArrivalTime(), customer.getServer(), customer.getTimeServiceBegin(),
                customer.getRandomValueOfService(), customer.getServiceTime(),
                customer.getWaitingTime(),
                customer.getTimeServiceEnds(),
                customer.getTimeSpentInSystem(),
                idleTimes[n-1][i]
            };
            model.addRow(newRow);
    }
        createDataSet(0,system.serviceTimeCount);
        createDataSet(1, system.interArrivalCount);
        createChart(0);
        createChart(1);
        saveChart(0);
        saveChart(1);
        insertResults();
        
    }
    void insertResults(){
        int size=queue.waitInside.size();
        this.P_wait_inside.setText("P(Wait Inside) = " +( size ==0? 0.0:queue.waitInside.get(size-1)));
        this.maxQueue.setText(maxQueue.getText() + "= " + queue.maxInsideLength);
        size = queue.averageService[0].size();
        this.Avg_ser_inside.setText("Avg. Service Time Inside = " + (size==0? 0:queue.averageService[1].get(size-1)));
        size = queue.averageService[1].size();
        this.avg_ser_drive.setText("Avg. Service Time Drive = " + (size == 0? 0:queue.averageService[0].get(size-1)));
        size = queue.averageWaiting[1].size();
        this.avg_wait_inside.setText("Avg. Waiting Time Inside = " + (size==0? 0:queue.averageWaiting[1].get(size-1)));
        size = queue.averageWaiting[1].size();
        this.Avg_wait_drive.setText("Avg. Waiting Time Drive = " + (size==0? 0:queue.averageWaiting[0].get(size-1)));
        size =queue.insideIdle.size();
        this.idle_inside.setText("Idle Time Inside = " + ((size==0? 0:queue.insideIdle.get(size-1))*100) + "%");
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        returnToMain = new javax.swing.JButton();
        moreResults = new javax.swing.JButton();
        results = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        resultTable = new javax.swing.JTable();
        noOfRuns = new javax.swing.JLabel();
        P_wait_inside = new javax.swing.JLabel();
        Avg_ser_inside = new javax.swing.JLabel();
        avg_ser_drive = new javax.swing.JLabel();
        avg_wait_inside = new javax.swing.JLabel();
        Avg_wait_drive = new javax.swing.JLabel();
        idle_inside = new javax.swing.JLabel();
        maxQueue = new javax.swing.JLabel();
        tab1 = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        interArrivalTable = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        returnToMain.setText("Return");
        returnToMain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                returnToMainActionPerformed(evt);
            }
        });

        moreResults.setText("More Results");
        moreResults.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moreResultsActionPerformed(evt);
            }
        });

        resultTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cust. ID", "IA Random Value", "Inter-arrival Time", "Arrival Time", "Server", "Time Service Begins", "ST Random Value", "Service Time", "Wait. Time in Queue", "Time Service Ends", "Time in System", "Idle Time of Server"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(resultTable);
        if (resultTable.getColumnModel().getColumnCount() > 0) {
            resultTable.getColumnModel().getColumn(0).setResizable(false);
        }

        results.addTab("Run", jScrollPane1);

        noOfRuns.setText("Total Runs:");

        P_wait_inside.setText("P(Waiting Inside) = ");

        Avg_ser_inside.setText("Average Inside Service Time");

        avg_ser_drive.setText("Average Drive Service Time");

        avg_wait_inside.setText("Average W. Time Inside");

        Avg_wait_drive.setText("Average W. Time Drive");

        idle_inside.setText("Inside Teller Idle Time");

        maxQueue.setText("Max. Inside Queue Size");

        interArrivalTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(interArrivalTable);

        tab1.addTab("Inter-Arrival", jScrollPane2);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(jTable1);

        tab1.addTab("Service", jScrollPane3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(251, 251, 251)
                            .addComponent(returnToMain)
                            .addGap(95, 95, 95)
                            .addComponent(moreResults))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(results, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Avg_ser_inside)
                                    .addComponent(P_wait_inside))
                                .addGap(474, 474, 474)))
                        .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(idle_inside)
                                .addComponent(avg_ser_drive)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(maxQueue)
                            .addGap(222, 222, 222)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(295, 295, 295)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(avg_wait_inside)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(Avg_wait_drive)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(72, 72, 72)
                                    .addComponent(noOfRuns))))
                        .addGap(229, 229, 229)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tab1, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(385, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(results, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(tab1, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(noOfRuns)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(P_wait_inside)
                    .addComponent(avg_wait_inside))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(Avg_wait_drive))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(Avg_ser_inside)))
                .addGap(29, 29, 29)
                .addComponent(avg_ser_drive)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idle_inside)
                    .addComponent(maxQueue))
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(returnToMain)
                    .addComponent(moreResults))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void moreResultsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moreResultsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_moreResultsActionPerformed

    private void returnToMainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_returnToMainActionPerformed
      GUI gui = new GUI(queue);
      gui.setVisible(true);
      dispose();      // TODO add your handling code here:
    }//GEN-LAST:event_returnToMainActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Avg_ser_inside;
    private javax.swing.JLabel Avg_wait_drive;
    private javax.swing.JLabel P_wait_inside;
    private javax.swing.JLabel avg_ser_drive;
    private javax.swing.JLabel avg_wait_inside;
    private javax.swing.JLabel idle_inside;
    private javax.swing.JTable interArrivalTable;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel maxQueue;
    private javax.swing.JButton moreResults;
    private javax.swing.JLabel noOfRuns;
    private javax.swing.JTable resultTable;
    private javax.swing.JTabbedPane results;
    private javax.swing.JButton returnToMain;
    private javax.swing.JTabbedPane tab1;
    // End of variables declaration//GEN-END:variables
}
