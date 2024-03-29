/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m.s;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author ASUS
 */
public class ServiceTimeTable extends javax.swing.JFrame {
    Queue system;
    public ServiceTimeTable() {
        initComponents();
    }
    public ServiceTimeTable(Queue queue, int tableSize){
        initComponents();
        system = queue;
        DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
        for(int i = 0; i < tableSize; ++i){
            tableModel.addRow(new Object[] {0,0.0});
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

        jScrollPane2 = new javax.swing.JScrollPane();
        jButton1 = new javax.swing.JButton();
        newCell = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Submit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        newCell.setText("Return");
        newCell.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newCellActionPerformed(evt);
            }
        });

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Time", "P(Time)"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setResizable(false);
            table.getColumnModel().getColumn(1).setResizable(false);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(115, 115, 115)
                        .addComponent(jButton1)
                        .addGap(58, 58, 58)
                        .addComponent(newCell)))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(newCell))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
            DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
            try{
            int tableSize=  tableModel.getRowCount();
            int []interval = new int[tableSize];
            double [] probabilities = new double[tableSize];
            boolean 
            int previousSize= 0;
            for(int i = 0; i < tableSize; ++i){ 
                double temp = (double)tableModel.getValueAt(i, 1);
                interval[i] = (int)tableModel.getValueAt(i, 0);
                probabilities[i] = (double)tableModel.getValueAt(i, 1);
                String []valueSizes = Double.toString(temp).split("\\.");
                if(i>0){
                    if(valueSizes[1].length() != previousSize){
                              JOptionPane.showMessageDialog(this, "Error", "Probabilities should be of same digit length", JOptionPane.ERROR_MESSAGE);
                    }
                }
                previousSize = valueSizes[1].length();
                
            }
            if(totalProbability < 1.0){    
                 JOptionPane.showMessageDialog(this, "Error", "Total sum is not 1", JOptionPane.ERROR_MESSAGE);
           }
            else{
                GUI gui = new GUI(system, interval, probabilities);
            }
            }
            notEqual:
            catch(Exception e){
                JOptionPane.showMessageDialog(this, "Error", "Invalid value", JOptionPane.ERROR_MESSAGE);
            }
      
    }//GEN-LAST:event_jButton1ActionPerformed

    private void newCellActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newCellActionPerformed
      GUI gui = new GUI();
      gui.setVisible(true);
      dispose();
    }//GEN-LAST:event_newCellActionPerformed

    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton newCell;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
