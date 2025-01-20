/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package eu.oreplay.gui;

import eu.oreplay.logic.FormsParameters;
import eu.oreplay.logic.converter.ConverterToModel;
import eu.oreplay.logic.converter.OReplayDataTransfer;
import eu.oreplay.utils.JUtils;
import eu.oreplay.utils.Utils;
import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * Basic test form to inspect files, their contents and getting JSON exchange data
 * @author javier.arufe
 */
public class JTest extends javax.swing.JDialog {
    private java.util.ResourceBundle resMessages = java.util.ResourceBundle.getBundle("eu.oreplay.library.messages.Messages"); //$NON-NLS-1$;
    private ConverterToModel oConv = null;
    private String cPath = "." + java.io.File.separator;
    
    /**
     * Creates new form JAbout
     */
    public JTest(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.getContentPane().setBackground(new java.awt.Color(255, 255, 255));
        setBounds(new java.awt.Rectangle(parent.getX()+50, parent.getY()+50, 
                (int)this.getPreferredSize().getWidth(), (int)this.getPreferredSize().getHeight()));
        setResizable(false);
        //Cleans and set editabilty of metadata fields
        cleanMetadata();
        setMetadataEditable(false);
    }
    public void initFormParameters(FormsParameters.ParJTest poParam) {
        try {
            this.setBounds(poParam.getoPos().getnPosX(), 
                poParam.getoPos().getnPosY(), 
                poParam.getoPos().getnSizeX(),
                poParam.getoPos().getnSizeY());
            if (Utils.folderExists(poParam.getcPath()))                
                cPath = poParam.getcPath();
        }catch (Exception e) {
            JClientMain.getoLog().error(resMessages.getString("error_exception"), e);
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

        pnlSource = new javax.swing.JPanel();
        lblSrcFile = new javax.swing.JLabel();
        txtSrcFile = new javax.swing.JTextField();
        btnSrcFile = new javax.swing.JButton();
        lblDstFile = new javax.swing.JLabel();
        txtDstFile = new javax.swing.JTextField();
        btnDstFile = new javax.swing.JButton();
        pnlInfo = new javax.swing.JPanel();
        lblExtension = new javax.swing.JLabel();
        chkExists = new javax.swing.JCheckBox();
        chkUtf = new javax.swing.JCheckBox();
        chkKnown = new javax.swing.JCheckBox();
        txtExtension = new javax.swing.JTextField();
        lblContents = new javax.swing.JLabel();
        txtContents = new javax.swing.JTextField();
        lblSource = new javax.swing.JLabel();
        txtSource = new javax.swing.JTextField();
        lblIof = new javax.swing.JLabel();
        txtIof = new javax.swing.JTextField();
        lblResults = new javax.swing.JLabel();
        txtResults = new javax.swing.JTextField();
        lblStatus = new javax.swing.JLabel();
        pnlButtons = new javax.swing.JPanel();
        btnAccept = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnProcess = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(800, 500));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        pnlSource.setBackground(new java.awt.Color(255, 255, 255));

        lblSrcFile.setBackground(new java.awt.Color(255, 255, 255));
        lblSrcFile.setText(resMessages.getString("src_file"));

        btnSrcFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_browse.png"))); // NOI18N
        btnSrcFile.setText(resMessages.getString("browse"));
        btnSrcFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSrcFileActionPerformed(evt);
            }
        });

        lblDstFile.setBackground(new java.awt.Color(255, 255, 255));
        lblDstFile.setText(resMessages.getString("dst_file"));

        btnDstFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_browse.png"))); // NOI18N
        btnDstFile.setText(resMessages.getString("browse"));
        btnDstFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDstFileActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlSourceLayout = new javax.swing.GroupLayout(pnlSource);
        pnlSource.setLayout(pnlSourceLayout);
        pnlSourceLayout.setHorizontalGroup(
            pnlSourceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSourceLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSourceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlSourceLayout.createSequentialGroup()
                        .addComponent(lblSrcFile, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtSrcFile)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSrcFile))
                    .addGroup(pnlSourceLayout.createSequentialGroup()
                        .addComponent(lblDstFile, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtDstFile)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnDstFile)))
                .addContainerGap())
        );
        pnlSourceLayout.setVerticalGroup(
            pnlSourceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSourceLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSourceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnSrcFile, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSrcFile, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtSrcFile))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlSourceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnDstFile, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDstFile, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDstFile))
                .addGap(41, 41, 41))
        );

        pnlInfo.setBackground(new java.awt.Color(255, 255, 255));

        lblExtension.setBackground(new java.awt.Color(255, 255, 255));
        lblExtension.setText(resMessages.getString("extension"));
        lblExtension.setMaximumSize(new java.awt.Dimension(70, 25));
        lblExtension.setMinimumSize(new java.awt.Dimension(70, 25));
        lblExtension.setPreferredSize(new java.awt.Dimension(70, 25));

        chkExists.setBackground(new java.awt.Color(255, 255, 255));
        chkExists.setText(resMessages.getString("exists"));

        chkUtf.setBackground(new java.awt.Color(255, 255, 255));
        chkUtf.setText(resMessages.getString("utf"));

        chkKnown.setBackground(new java.awt.Color(255, 255, 255));
        chkKnown.setText(resMessages.getString("known_data"));

        txtExtension.setMinimumSize(new java.awt.Dimension(64, 25));
        txtExtension.setPreferredSize(new java.awt.Dimension(64, 25));

        lblContents.setBackground(new java.awt.Color(255, 255, 255));
        lblContents.setText(resMessages.getString("contents"));
        lblContents.setMaximumSize(new java.awt.Dimension(70, 25));
        lblContents.setMinimumSize(new java.awt.Dimension(70, 25));
        lblContents.setPreferredSize(new java.awt.Dimension(70, 25));

        txtContents.setMinimumSize(new java.awt.Dimension(64, 25));
        txtContents.setPreferredSize(new java.awt.Dimension(64, 25));

        lblSource.setBackground(new java.awt.Color(255, 255, 255));
        lblSource.setText(resMessages.getString("source"));
        lblSource.setMaximumSize(new java.awt.Dimension(70, 25));
        lblSource.setMinimumSize(new java.awt.Dimension(70, 25));
        lblSource.setPreferredSize(new java.awt.Dimension(70, 25));

        txtSource.setMinimumSize(new java.awt.Dimension(64, 25));
        txtSource.setPreferredSize(new java.awt.Dimension(64, 25));

        lblIof.setBackground(new java.awt.Color(255, 255, 255));
        lblIof.setText(resMessages.getString("iof_version"));
        lblIof.setMaximumSize(new java.awt.Dimension(70, 25));
        lblIof.setMinimumSize(new java.awt.Dimension(70, 25));
        lblIof.setPreferredSize(new java.awt.Dimension(70, 25));

        txtIof.setMinimumSize(new java.awt.Dimension(64, 25));
        txtIof.setPreferredSize(new java.awt.Dimension(64, 25));

        lblResults.setBackground(new java.awt.Color(255, 255, 255));
        lblResults.setText(resMessages.getString("results_type"));
        lblResults.setMaximumSize(new java.awt.Dimension(70, 25));
        lblResults.setMinimumSize(new java.awt.Dimension(70, 25));
        lblResults.setPreferredSize(new java.awt.Dimension(70, 25));

        txtResults.setMinimumSize(new java.awt.Dimension(64, 25));
        txtResults.setPreferredSize(new java.awt.Dimension(64, 25));

        lblStatus.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout pnlInfoLayout = new javax.swing.GroupLayout(pnlInfo);
        pnlInfo.setLayout(pnlInfoLayout);
        pnlInfoLayout.setHorizontalGroup(
            pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlInfoLayout.createSequentialGroup()
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlInfoLayout.createSequentialGroup()
                                .addComponent(chkExists, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(chkUtf, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addComponent(chkKnown, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlInfoLayout.createSequentialGroup()
                                .addComponent(lblSource, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtSource, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(pnlInfoLayout.createSequentialGroup()
                                    .addComponent(lblContents, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtContents, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblResults, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtResults, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(pnlInfoLayout.createSequentialGroup()
                                    .addComponent(lblExtension, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtExtension, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(35, 35, 35)
                                    .addComponent(lblIof, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtIof, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 13, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlInfoLayout.setVerticalGroup(
            pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkExists)
                    .addComponent(chkUtf)
                    .addComponent(chkKnown))
                .addGap(21, 21, 21)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtIof, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtExtension, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblExtension, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblIof, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtResults, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblContents, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtContents, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblResults, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblSource, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSource, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lblStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlButtons.setBackground(new java.awt.Color(255, 255, 255));

        btnAccept.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_yes.png"))); // NOI18N
        btnAccept.setText(resMessages.getString("accept"));
        btnAccept.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAcceptActionPerformed(evt);
            }
        });

        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_no.png"))); // NOI18N
        btnCancel.setText(resMessages.getString("cancel"));
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnProcess.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_gear.png"))); // NOI18N
        btnProcess.setText(resMessages.getString("process"));
        btnProcess.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProcessActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlButtonsLayout = new javax.swing.GroupLayout(pnlButtons);
        pnlButtons.setLayout(pnlButtonsLayout);
        pnlButtonsLayout.setHorizontalGroup(
            pnlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlButtonsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAccept)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCancel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnProcess)
                .addContainerGap())
        );
        pnlButtonsLayout.setVerticalGroup(
            pnlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlButtonsLayout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(pnlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(pnlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnProcess, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(btnAccept, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnlButtons, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlSource, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(pnlInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlSource, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(pnlButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAcceptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAcceptActionPerformed
        // TODO add your handling code here:
        this.saveFormParameters();
        this.dispose();
    }//GEN-LAST:event_btnAcceptActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnSrcFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSrcFileActionPerformed
        // TODO add your handling code here:
        this.selectFileForReading();
    }//GEN-LAST:event_btnSrcFileActionPerformed

    private void btnDstFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDstFileActionPerformed
        // TODO add your handling code here:
        this.selectFileForWriting();
    }//GEN-LAST:event_btnDstFileActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        this.saveFormParameters();
    }//GEN-LAST:event_formWindowClosing

    private void btnProcessActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProcessActionPerformed
        // TODO add your handling code here:
        this.processFile();
    }//GEN-LAST:event_btnProcessActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JTest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JTest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JTest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JTest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JTest dialog = new JTest(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAccept;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDstFile;
    private javax.swing.JButton btnProcess;
    private javax.swing.JButton btnSrcFile;
    private javax.swing.JCheckBox chkExists;
    private javax.swing.JCheckBox chkKnown;
    private javax.swing.JCheckBox chkUtf;
    private javax.swing.JLabel lblContents;
    private javax.swing.JLabel lblDstFile;
    private javax.swing.JLabel lblExtension;
    private javax.swing.JLabel lblIof;
    private javax.swing.JLabel lblResults;
    private javax.swing.JLabel lblSource;
    private javax.swing.JLabel lblSrcFile;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JPanel pnlButtons;
    private javax.swing.JPanel pnlInfo;
    private javax.swing.JPanel pnlSource;
    private javax.swing.JTextField txtContents;
    private javax.swing.JTextField txtDstFile;
    private javax.swing.JTextField txtExtension;
    private javax.swing.JTextField txtIof;
    private javax.swing.JTextField txtResults;
    private javax.swing.JTextField txtSource;
    private javax.swing.JTextField txtSrcFile;
    // End of variables declaration//GEN-END:variables

    /**
     * Sets field values by default
     */
    public void cleanMetadata () {
        chkExists.setSelected(false);
        chkKnown.setSelected(false);
        chkUtf.setSelected(false);
        txtExtension.setText("");
        txtContents.setText("");
        txtResults.setText("");
        txtSource.setText("");
        txtIof.setText("");
    }
    /**
     * Sets editable property to fields
     * @param pbFlag boolean Flag to enable or disable metadata fields
     */
    public void setMetadataEditable (boolean pbFlag) {
        chkExists.setEnabled(pbFlag);
        chkKnown.setEnabled(pbFlag);
        chkUtf.setEnabled(pbFlag);
        txtExtension.setEditable(pbFlag);
        txtContents.setEditable(pbFlag);
        txtResults.setEditable(pbFlag);
        txtSource.setEditable(pbFlag);
        txtIof.setEditable(pbFlag);
    }
    public void saveFormParameters() {
        try {
            //Read the parameters to store
            FormsParameters voParent = new FormsParameters();
            FormsParameters.ParJTest voParam = voParent.new ParJTest();
            voParam.getoPos().setnPosX(this.getX());
            voParam.getoPos().setnPosY(this.getY());
            voParam.getoPos().setnSizeX(this.getWidth());
            voParam.getoPos().setnSizeY(this.getHeight());
            voParam.setcPath(cPath);
            //Calls the method in the main form to receive and to store the parameters
            JClientMain.updateFormsParameters("JTest", voParam);
        } catch(Exception e) {
            JClientMain.getoLog().error(resMessages.getString("error_exception"), e);
        }                
    }
    /**
     * When the user clics the button, it allows to select a source file for reading
     */
    public void selectFileForReading() {
        try {
            String vcFile = JUtils.selectFile(this, cPath, 
                    new String[] {"csv", "xml", "htm", "html"}, ".csv, .xml, .htm, .html", true);
            if (vcFile!=null) {
                txtSrcFile.setText(vcFile);
                cPath = JUtils.getcCurDir();
            } else {
                txtSrcFile.setText("");
            }
            //Pre-process the type of file to show its metadata
            if (!txtSrcFile.getText().equals("")) {
                this.preProcessFile(vcFile);
            }
            lblStatus.setText("");
        } catch (Exception e) {
            JClientMain.getoLog().error(resMessages.getString("error_exception"), e);
        }
    }
    /**
     * When the user clics the button, it allows to select a destination file for writing
     */
    public void selectFileForWriting() {
        try {
            String vcFile = JUtils.selectFile(this, cPath, 
                    new String[] {"json"}, ".json", false);
            if (vcFile!=null) {
                if (!vcFile.toLowerCase().endsWith(".json"))
                    vcFile = vcFile + ".json";
                txtDstFile.setText(vcFile);
                cPath = JUtils.getcCurDir();
            } else {
                txtDstFile.setText("");
            }
            lblStatus.setText("");
        } catch (Exception e) {
            JClientMain.getoLog().error(resMessages.getString("error_exception"), e);
        }
    }
    /**
     * Performs the previous tasks to parse the source file and fill its metadata
     * @param pcFile String path+name of the source file
     */
    public void preProcessFile(String pcFile) {
        //Clean previous values of the metadata
        this.cleanMetadata();
        //And sets the editability
        this.setMetadataEditable(false);
        //Opens and tries to parse the source file
        OReplayDataTransfer voTransf = new OReplayDataTransfer();
        oConv = voTransf.preProcessFile(pcFile);
        //Show the results
        chkExists.setSelected(oConv.isbExists());
        chkKnown.setSelected(oConv.isbKnownData());
        chkUtf.setSelected(oConv.isbUtf());
        txtExtension.setText(oConv.getcExtension());
        txtContents.setText(resMessages.getString(oConv.getcContents().toLowerCase()));
        if (oConv.getcContents().equals(ConverterToModel.CONTENTS_RESULT))
            txtResults.setText(resMessages.getString(oConv.getcResultsType().toLowerCase()));
        else
            txtResults.setText("");
        txtSource.setText(oConv.getcSource());
        if (oConv.getcExtension().equals(ConverterToModel.EXT_XML))
            txtIof.setText(oConv.getcIofVersion());
        else
            txtIof.setText("");
    }
    /**
     * Performs the tasks to generate the output JSON
     */
    public void processFile() {
        eu.oreplay.db.Event voEve = null;
        try {
            //If a source file has been inspected and a destination file has value, process it
            if (oConv!=null && !txtDstFile.getText().equals("")) {
                //Sets stage date and zero time from Main Form
                try {
                    oConv.setcStageDate(JClientMain.getDateForTransfer());
                    oConv.setcStageZeroTime(JClientMain.getZeroTimeForTransfer());
                }catch(Exception eDate) {
                }
                OReplayDataTransfer voTransf = new OReplayDataTransfer();
                String vcJson = voTransf.processFile(oConv);
                if (!vcJson.toLowerCase().startsWith("error")) {
                    BufferedWriter voWriter = new BufferedWriter(new FileWriter(txtDstFile.getText()));
                    voWriter.write(vcJson);
                    voWriter.close();
                    //Write a text in the status label
                    lblStatus.setText(resMessages.getString("info_process_finished"));
                } else {
                    if (!vcJson.toLowerCase().startsWith("error_exception")) {
                        lblStatus.setText(resMessages.getString(vcJson));
                    } else {
                        lblStatus.setText(resMessages.getString("error_exception"));
                        JClientMain.getoLog().error(vcJson);
                    }
                }
            } else {
                lblStatus.setText(resMessages.getString("error_nothing_to_do"));
            }
        } catch(Exception e) {
            JClientMain.getoLog().error(resMessages.getString("error_exception"), e);
        }
    }
}
