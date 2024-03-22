/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package eu.oreplay.gui;

/**
 *
 * @author javier.arufe
 */
public class JClientMain extends javax.swing.JFrame {
    private java.util.ResourceBundle resMessages = java.util.ResourceBundle.getBundle("eu.oreplay.library.messages.Messages"); //$NON-NLS-1$;

    /**
     * Creates new form JClientMain
     */
    public JClientMain() {
        initComponents();
        this.getContentPane().setBackground(new java.awt.Color(255, 255, 255));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mnuMain = new javax.swing.JMenuBar();
        mnuFile = new javax.swing.JMenu();
        mnuTest = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        mnuExit = new javax.swing.JMenuItem();
        mnuHelp = new javax.swing.JMenu();
        mnuAbout = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        mnuLanguage = new javax.swing.JMenu();
        mnuEnglish = new javax.swing.JMenuItem();
        mnuSpanish = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setBounds(new java.awt.Rectangle(200, 200, 650, 400));
        setResizable(false);

        mnuFile.setText(resMessages.getString("file"));
        mnuFile.setFont(new java.awt.Font("Garamond", 0, 12)); // NOI18N

        mnuTest.setFont(new java.awt.Font("Garamond", 0, 12)); // NOI18N
        mnuTest.setText(resMessages.getString("test"));
        mnuTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuTestActionPerformed(evt);
            }
        });
        mnuFile.add(mnuTest);
        mnuFile.add(jSeparator2);

        mnuExit.setFont(new java.awt.Font("Garamond", 0, 12)); // NOI18N
        mnuExit.setText(resMessages.getString("exit"));
        mnuExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuExitActionPerformed(evt);
            }
        });
        mnuFile.add(mnuExit);

        mnuMain.add(mnuFile);

        mnuHelp.setText(resMessages.getString("help"));
        mnuHelp.setFont(new java.awt.Font("Garamond", 0, 12)); // NOI18N

        mnuAbout.setFont(new java.awt.Font("Garamond", 0, 12)); // NOI18N
        mnuAbout.setText(resMessages.getString("about"));
        mnuAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAboutActionPerformed(evt);
            }
        });
        mnuHelp.add(mnuAbout);
        mnuHelp.add(jSeparator1);

        mnuLanguage.setText(resMessages.getString("language"));
        mnuLanguage.setFont(new java.awt.Font("Garamond", 0, 12)); // NOI18N

        mnuEnglish.setFont(new java.awt.Font("Garamond", 0, 12)); // NOI18N
        mnuEnglish.setText(resMessages.getString("english"));
        mnuEnglish.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuEnglishActionPerformed(evt);
            }
        });
        mnuLanguage.add(mnuEnglish);

        mnuSpanish.setFont(new java.awt.Font("Garamond", 0, 12)); // NOI18N
        mnuSpanish.setText(resMessages.getString("spanish"));
        mnuSpanish.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuSpanishActionPerformed(evt);
            }
        });
        mnuLanguage.add(mnuSpanish);

        mnuHelp.add(mnuLanguage);

        mnuMain.add(mnuHelp);

        setJMenuBar(mnuMain);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 665, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 315, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mnuExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuExitActionPerformed
        // TODO add your handling code here:
        this.exitApp();
    }//GEN-LAST:event_mnuExitActionPerformed

    private void mnuEnglishActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuEnglishActionPerformed
        // TODO add your handling code here:
        this.changeLanguage("en");
    }//GEN-LAST:event_mnuEnglishActionPerformed

    private void mnuSpanishActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuSpanishActionPerformed
        // TODO add your handling code here:
        this.changeLanguage("es");
    }//GEN-LAST:event_mnuSpanishActionPerformed

    private void mnuAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAboutActionPerformed
        // TODO add your handling code here:
        this.manageAbout();
    }//GEN-LAST:event_mnuAboutActionPerformed

    private void mnuTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuTestActionPerformed
        // TODO add your handling code here:
        this.manageTest();
    }//GEN-LAST:event_mnuTestActionPerformed

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
            java.util.logging.Logger.getLogger(JClientMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JClientMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JClientMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JClientMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JClientMain().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JMenuItem mnuAbout;
    private javax.swing.JMenuItem mnuEnglish;
    private javax.swing.JMenuItem mnuExit;
    private javax.swing.JMenu mnuFile;
    private javax.swing.JMenu mnuHelp;
    private javax.swing.JMenu mnuLanguage;
    private javax.swing.JMenuBar mnuMain;
    private javax.swing.JMenuItem mnuSpanish;
    private javax.swing.JMenuItem mnuTest;
    // End of variables declaration//GEN-END:variables

    public void exitApp() {
        System.exit(0);
    }
    /**
     * Forces app language change
     * @param pcLocale String New language's initials
     */
    private void changeLanguage (String pcLocale) {
        try {
            java.util.Locale voLocale = new java.util.Locale(pcLocale);
            java.util.Locale.setDefault(voLocale);
            java.util.ResourceBundle.clearCache();
            resMessages = java.util.ResourceBundle.getBundle("eu.oreplay.library.messages.Messages", voLocale);
            this.initComponents();
        }catch(Exception e) {}
    }
    /**
     * Shows a dialog with data about the app
     */
    private void manageAbout() {
        try {
            //Instances the component, pass parameters and shows it in screen
            java.awt.Frame voFrame = new java.awt.Frame();
            voFrame.setBounds(this.getBounds().x, this.getBounds().y, 
                    this.getBounds().width, this.getBounds().height);
            JAbout viForm = new JAbout(voFrame, true);
            viForm.setModal(true);
            viForm.setVisible(true);
        } catch (Exception e) {
            //oLog.error(resMessages.getString("error"), e);
        }
    }
    /**
     * Shows a dialog to test source files with data
     */
    private void manageTest() {
        try {
            //Instances the component, pass parameters and shows it in screen
            java.awt.Frame voFrame = new java.awt.Frame();
            voFrame.setBounds(this.getBounds().x, this.getBounds().y, 
                    this.getBounds().width, this.getBounds().height);
            JTest viForm = new JTest(voFrame, true);
            viForm.setModal(true);
            viForm.setVisible(true);
        } catch (Exception e) {
            //oLog.error(resMessages.getString("error"), e);
        }
    }

}
