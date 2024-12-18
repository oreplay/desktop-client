/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package eu.oreplay.gui;

import eu.oreplay.gui.events.*;
import eu.oreplay.logic.FormsParameters;
import eu.oreplay.logic.xml.FormsParametersXMLHandler;
import eu.oreplay.utils.Utils;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import javax.swing.JOptionPane;
import org.apache.logging.log4j.*;

/**
 *
 * @author javier.arufe
 */
public class JClientMain extends javax.swing.JFrame implements ConnBackListener {
    private static java.util.ResourceBundle resMessages = java.util.ResourceBundle.getBundle("eu.oreplay.library.messages.Messages"); //$NON-NLS-1$;
    private boolean bFirstOpen = true;
    private static String cPathApp = "." + java.io.File.separator;
    private static FormsParameters oForms = null;
    private static Logger oLog = LogManager.getLogger(JClientMain.class.getName());
    private ConnBackStatus oStatus = new ConnBackStatus();

    /**
     * Creates new form JClientMain
     */
    public JClientMain() {
        initComponents();
        this.getContentPane().setBackground(new java.awt.Color(255, 255, 255));
    }
    public static String getcPathApp() {
        return cPathApp;
    }
    public static void setcPathApp(String cPathApp) {
        JClientMain.cPathApp = cPathApp;
    }
    public static FormsParameters getoForms() {
        return oForms;
    }
    public static void setoForms(FormsParameters oForms) {
        JClientMain.oForms = oForms;
    }
    public static Logger getoLog() {
        return oLog;
    }
    public static void setoLog(Logger oLog) {
        JClientMain.oLog = oLog;
    }
    public ConnBackStatus getoStatus() {
        return oStatus;
    }
    public void setoStatus(ConnBackStatus oStatus) {
        this.oStatus = oStatus;
    }
    public static void updateFormsParameters (String pcName, Object poParam) {
        try {
            if (poParam!=null && oForms!=null) {
                if (pcName.equals("JClientMain")) {
                    oForms.setoJClientMain((FormsParameters.ParJClientMain)poParam);
                } else if (pcName.equals("JAbout")) {
                    oForms.setoJAbout((FormsParameters.ParJAbout)poParam);
                } else if (pcName.equals("JTest")) {
                    oForms.setoJTest((FormsParameters.ParJTest)poParam);
                } else if (pcName.equals("ConnBackCheckPanel")) {
                    oForms.setoConnBackCheckPanel((FormsParameters.ParConnBackCheckPanel)poParam);
                } else if (pcName.equals("ConnBackLoginPanel")) {
                    oForms.setoConnBackLoginPanel((FormsParameters.ParConnBackLoginPanel)poParam);
                } else if (pcName.equals("ConnBackUploadPanel")) {
                    oForms.setoConnBackUploadPanel((FormsParameters.ParConnBackUploadPanel)poParam);
                }
            }                        
        } catch (Exception e) {
            oLog.error(resMessages.getString("error_exception"), e);
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

        pnlCheck = new eu.oreplay.gui.ConnBackCheckPanel();
        pnlLogin = new eu.oreplay.gui.ConnBackLoginPanel();
        pnlUpload = new eu.oreplay.gui.ConnBackUploadPanel();
        txtOffset = new javax.swing.JTextField();
        lblOffset = new javax.swing.JLabel();
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
        setTitle(resMessages.getString("oreplay"));
        setBackground(new java.awt.Color(255, 255, 255));
        setBounds(new java.awt.Rectangle(200, 200, 785, 590));
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/images/LogoOReplay_32.png")).getImage());
        setMinimumSize(new java.awt.Dimension(785, 590));
        setPreferredSize(new java.awt.Dimension(785, 590));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        pnlCheck.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        pnlLogin.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        pnlUpload.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        txtOffset.setEditable(false);
        txtOffset.setText("+01:00");
        txtOffset.setMaximumSize(new java.awt.Dimension(64, 22));

        lblOffset.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblOffset.setText(resMessages.getString("timezone_offset"));

        mnuFile.setText(resMessages.getString("file"));

        mnuTest.setText(resMessages.getString("test"));
        mnuTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuTestActionPerformed(evt);
            }
        });
        mnuFile.add(mnuTest);
        mnuFile.add(jSeparator2);

        mnuExit.setText(resMessages.getString("exit"));
        mnuExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuExitActionPerformed(evt);
            }
        });
        mnuFile.add(mnuExit);

        mnuMain.add(mnuFile);

        mnuHelp.setText(resMessages.getString("help"));

        mnuAbout.setText(resMessages.getString("about"));
        mnuAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAboutActionPerformed(evt);
            }
        });
        mnuHelp.add(mnuAbout);
        mnuHelp.add(jSeparator1);

        mnuLanguage.setText(resMessages.getString("language"));

        mnuEnglish.setText(resMessages.getString("english"));
        mnuEnglish.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuEnglishActionPerformed(evt);
            }
        });
        mnuLanguage.add(mnuEnglish);

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
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlCheck, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(24, 24, 24)
                        .addComponent(pnlLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlUpload, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblOffset, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtOffset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlLogin, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                    .addComponent(pnlCheck, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlUpload, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtOffset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblOffset))))
                .addGap(12, 12, 12))
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

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        if (bFirstOpen) {
            oLog.info(resMessages.getString("info_enter_app"));
            Utils.setoLog(oLog);  //Sets log file in the Utils static class for using it during the app execution
            //Get data from XML files
            getXmlData();
            //Get Timezone offset from computer
            getTimezoneOffset();
            //For each Connection panel, add managing events, set status and pass default values
            pnlCheck.addEventListener(this);
            pnlCheck.initialize(oStatus);
            if (oForms!=null)
                pnlCheck.initFormParameters(oForms.getoConnBackCheckPanel());
            pnlLogin.addEventListener(this);
            pnlLogin.initialize(oStatus);
            if (oForms!=null)
                pnlLogin.initFormParameters(oForms.getoConnBackLoginPanel());
            pnlUpload.addEventListener(this);
            pnlUpload.initialize(oStatus);
            if (oForms!=null)
                pnlUpload.initFormParameters(oForms.getoConnBackUploadPanel());
            //
            bFirstOpen = false;
        }
    }//GEN-LAST:event_formWindowOpened

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        this.stopListenersBeforeExit();
    }//GEN-LAST:event_formWindowClosing

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
                JClientMain voPrincipal = new JClientMain();
                try {
                    String vcFile = "";
                    //Configuration parameters
                    vcFile = cPathApp+"FormsParameters.xml";
                    if (Utils.fileExists(vcFile)) {
                        oForms = FormsParametersXMLHandler.getXmlData(vcFile);
                    } else {
                        oForms = new FormsParameters();
                    }
                    //Set position and size of this form
                    voPrincipal.setBounds(oForms.getoJClientMain().getoPos().getnPosX(), 
                            oForms.getoJClientMain().getoPos().getnPosY(), 
                            oForms.getoJClientMain().getoPos().getnSizeX(),
                            oForms.getoJClientMain().getoPos().getnSizeY());                
                }catch (Exception e) {
                    oLog.error(resMessages.getString("error_exception"), e);
                }
                voPrincipal.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JLabel lblOffset;
    private javax.swing.JMenuItem mnuAbout;
    private javax.swing.JMenuItem mnuEnglish;
    private javax.swing.JMenuItem mnuExit;
    private javax.swing.JMenu mnuFile;
    private javax.swing.JMenu mnuHelp;
    private javax.swing.JMenu mnuLanguage;
    private javax.swing.JMenuBar mnuMain;
    private javax.swing.JMenuItem mnuSpanish;
    private javax.swing.JMenuItem mnuTest;
    private eu.oreplay.gui.ConnBackCheckPanel pnlCheck;
    private eu.oreplay.gui.ConnBackLoginPanel pnlLogin;
    private eu.oreplay.gui.ConnBackUploadPanel pnlUpload;
    private javax.swing.JTextField txtOffset;
    // End of variables declaration//GEN-END:variables

    private void stopListenersBeforeExit() {
        if (pnlCheck!=null) {
            pnlCheck.saveFormParameters();
            //Remove listener
            pnlCheck.removeEventListener(this);
        }
        if (pnlLogin!=null) {
            pnlLogin.saveFormParameters();
            //Remove listener
            pnlLogin.removeEventListener(this);
        }
        if (pnlUpload!=null) {
            pnlUpload.saveFormParameters();
            //Remove listener
            pnlUpload.removeEventListener(this);
        }
        this.saveFormsParameters();
        oLog.info(resMessages.getString("info_exit_app"));        
    }
    public void exitApp() {
        this.stopListenersBeforeExit();
        System.exit(0);
    }
    /**
     * Forces app language change
     * @param pcLocale String New language's initials
     */
    private void changeLanguage (String pcLocale) {
        try {
            //Change the resources locale
            java.util.Locale voLocale = new java.util.Locale(pcLocale);
            java.util.Locale.setDefault(voLocale);
            java.util.ResourceBundle.clearCache();
            resMessages = java.util.ResourceBundle.getBundle("eu.oreplay.library.messages.Messages", voLocale);
            //Set the texts again
            setTitle(resMessages.getString("oreplay"));
            mnuFile.setText(resMessages.getString("file"));
            mnuTest.setText(resMessages.getString("test"));
            mnuExit.setText(resMessages.getString("exit"));
            mnuHelp.setText(resMessages.getString("help"));
            mnuAbout.setText(resMessages.getString("about"));
            mnuLanguage.setText(resMessages.getString("language"));
            mnuEnglish.setText(resMessages.getString("english"));
            mnuSpanish.setText(resMessages.getString("spanish"));
            //Force the panels to do the same
            pnlCheck.changeLanguage(pcLocale);
            pnlLogin.changeLanguage(pcLocale);
            pnlUpload.changeLanguage(pcLocale);
        }catch(Exception e) {
            oLog.error(resMessages.getString("error_exception"), e);
        }
    }
    /**
     * When app starts, read XML configuration files
     */
    private void getXmlData() {
        try {
            String vcFile = "";
            //Forms parameters file
            vcFile = cPathApp+"FormsParameters.xml";
            if (Utils.fileExists(vcFile)) {
                oForms = FormsParametersXMLHandler.getXmlData(vcFile);
                //Sets position and size of the form
                JClientMain.this.setBounds(oForms.getoJClientMain().getoPos().getnPosX(), 
                        oForms.getoJClientMain().getoPos().getnPosY(), 
                        oForms.getoJClientMain().getoPos().getnSizeX(),
                        oForms.getoJClientMain().getoPos().getnSizeY());
            } else {
                oForms = new FormsParameters();
            }
        } catch (Exception e) {
            oLog.error(resMessages.getString("error_exception"), e);
        }
    }
    /**
     * Saves an XML file with the parameters of the forms
     */
    private void saveFormsParameters () {
        try {
            if (oForms!=null) {
                //Tries to save the position and size of the main form
                try {
                    int vnPosX = JClientMain.this.getX();
                    int vnPosY = JClientMain.this.getY();
                    int vnSizeX = JClientMain.this.getWidth();
                    int vnSizeY = JClientMain.this.getHeight();
                    oForms.getoJClientMain().getoPos().setnPosX(vnPosX);
                    oForms.getoJClientMain().getoPos().setnPosY(vnPosY);
                    oForms.getoJClientMain().getoPos().setnSizeX(vnSizeX);
                    oForms.getoJClientMain().getoPos().setnSizeY(vnSizeY);
                }catch (Exception ePos) {
                    
                }
                String vcFile = cPathApp+"FormsParameters.xml";
                FormsParametersXMLHandler.writeXmlData(oForms, vcFile);
            }
        } catch (Exception eLocal) {
            oLog.error(resMessages.getString("error_exception"), eLocal);
        }
    }
    /**
     * Inspects the computer to get the timezone offset; this value is used
     * to compose datetime values (start lists, results, splits, etc)
     */
    private void getTimezoneOffset() {
        try {
            txtOffset.setText(Utils.getTimezoneOffset());
        }catch(Exception e) {
            oLog.error(resMessages.getString("error_exception"), e);
        }
    }
    /**
     * Method that receives the event occurred in one of the connection panels;
     * It's in charge of activating or deactivating panels as things happen,
     * letting the user interacte with the panels
     * @param e ConnBackEvent Event that occurred
     */
    @Override
    public void handleConnBackEvent(ConnBackEvent e) {
        if (e!=null) {
            //Set the new values for the status
            this.setoStatus(e.getoStatus());
            //If there is no connection, disable the other panels
            if (e.getoStatus().getnStatus()==ConnBackStatus.CONNECTION_NOOK ||
                    e.getoStatus().getnStatus()==ConnBackStatus.DISCONNECTED) {
                pnlLogin.setDefaultValues();
                pnlUpload.setDefaultValues();
            //If it wasn't connected and now it's connected, activate second panel for login
            } else if (e.getoStatus().getnStatus()==ConnBackStatus.CONNECTION_OK) {
                pnlLogin.setoStatus(oStatus);
                pnlLogin.enableForLogin();
                pnlUpload.setDefaultValues();
            //If login fails, enable for login again
            } else if (e.getoStatus().getnStatus()==ConnBackStatus.LOGIN_NOOK) {
                pnlLogin.setoStatus(oStatus);
                pnlLogin.enableForLogin();
                pnlUpload.setDefaultValues();
            //If login ok, enable for stage selection
            } else if (e.getoStatus().getnStatus()==ConnBackStatus.LOGIN_OK) {
                pnlLogin.setoStatus(oStatus);
                pnlLogin.enableForStage();
                pnlUpload.setDefaultValues();
            //If a stage is selected, enable for upload
            } else if (e.getoStatus().getnStatus()==ConnBackStatus.STAGE_SELECTED) {
                pnlLogin.enableForStage();
                pnlUpload.setoStatus(oStatus);
                pnlUpload.enableForUpload();
            }

        }
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
            if (oForms!=null)
                viForm.initFormParameters(oForms.getoJAbout());
            viForm.setModal(true);
            viForm.setVisible(true);
        } catch (Exception e) {
            oLog.error(resMessages.getString("error_exception"), e);
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
            if (oForms!=null)
                viForm.initFormParameters(oForms.getoJTest());
            viForm.setModal(true);
            viForm.setVisible(true);
        } catch (Exception e) {
            oLog.error(resMessages.getString("error_exception"), e);
        }
    }

    /**
     * Checks for a version change; If so, show a message to recommend an update
     */
    private void checkForNewVersion() {
        boolean vbChanged = false;
        try {
            vbChanged = Utils.checkForNewVersion(resMessages.getString("version"));
            if (vbChanged) {
                JOptionPane.showMessageDialog(JClientMain.this, 
                        resMessages.getString("info_new_version"),
                        resMessages.getString("info"), 
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }catch(Exception e) {
            oLog.error(resMessages.getString("error_exception"), e);
        }
    }    
    
}
