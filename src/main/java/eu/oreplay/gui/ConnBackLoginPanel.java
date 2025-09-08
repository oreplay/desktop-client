/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package eu.oreplay.gui;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.oreplay.gui.events.*;
import eu.oreplay.logic.FormsParameters;
import eu.oreplay.logic.converter.OReplayDataTransfer;
import eu.oreplay.utils.Utils;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 *
 * @author javier.arufe
 */
public class ConnBackLoginPanel extends javax.swing.JPanel {
    private static java.util.ResourceBundle resMessages = java.util.ResourceBundle.getBundle("messages.Messages"); //$NON-NLS-1$;
    private ConnBackStatus oStatus = new ConnBackStatus();
    private java.util.List lListeners = new java.util.ArrayList();
    private String cEveId = "";
    private String cToken = "";
    private String cIdToken = "";
    private String cStaId = "";
    private String cEveDesc = "";
    private String cStaDesc = "";
    private java.util.HashMap<Integer, eu.oreplay.db.Stage> lStages = new java.util.HashMap<>();
    private transient DocumentListenerIdToken oDoc = new DocumentListenerIdToken();
    private static final String MESSAGE_ERROR_1 = "error_exception";    
    private int nPrevListIndex = -1;
    
    /**
     * Creates new form ConnBackLoginPanel
     */
    public ConnBackLoginPanel() {
        initComponents();
        this.setDefaultValues();
        txtIdToken.getDocument().addDocumentListener(oDoc);
    }
    /**
     * Forces app language change
     * @param pcLocale String New language's initials
     */
    public void changeLanguage (String pcLocale) {
        java.util.Locale voLocale = new java.util.Locale(pcLocale);
        java.util.Locale.setDefault(voLocale);
        java.util.ResourceBundle.clearCache();
        resMessages = java.util.ResourceBundle.getBundle("messages.Messages", voLocale);
        //Change the texts
        lblTitle.setText("2. " + resMessages.getString("login_event"));
        btnLogin.setText(resMessages.getString("login"));
        btnWeb.setText(resMessages.getString("gotoweb"));
        lblEveId.setText(resMessages.getString("event_id"));
        lblToken.setText(resMessages.getString("token"));
        lblIdToken.setText(resMessages.getString("idtoken"));
        lblStaId.setText(resMessages.getString("stage"));
        btnLogin.setToolTipText(resMessages.getString("tooltip_login"));
        btnWeb.setToolTipText(resMessages.getString("tooltip_event_web"));
        txtEveId.setToolTipText(resMessages.getString("tooltip_event_id"));
        txtToken.setToolTipText(resMessages.getString("tooltip_token"));
        txtIdToken.setToolTipText(resMessages.getString("tooltip_idtoken"));
        lstStages.setToolTipText(resMessages.getString("tooltip_stage_list"));
    }
    public void initFormParameters(FormsParameters.ParConnBackLoginPanel poParam) {
        try {
            cEveId = poParam.getcEveId();
            cToken = poParam.getcToken();
            cIdToken = poParam.getcIdToken();
            if (cIdToken.equals("")) {
                cEveId = "";
                cToken = "";
            }
            cStaId = poParam.getcStaId();
            cEveDesc = poParam.getcEveDesc();
            cStaDesc = poParam.getcStaDesc();
        }catch (Exception e) {
            if (JClientMain.getoLog()!=null)
                JClientMain.getoLog().error(resMessages.getString(MESSAGE_ERROR_1), e);
        }
    }
    public void saveFormParameters() {
        try {
            //Read the parameters to store
            FormsParameters voPadre = new FormsParameters();
            FormsParameters.ParConnBackLoginPanel voParam = voPadre.new ParConnBackLoginPanel();
            voParam.setcEveId(cEveId);
            voParam.setcToken(cToken);
            voParam.setcIdToken(cIdToken);
            voParam.setcStaId(cStaId);
            voParam.setcEveDesc(cEveDesc);
            voParam.setcStaDesc(cStaDesc);
            //Calls the method in the main form to receive and to store the parameters
            JClientMain.updateFormsParameters("ConnBackLoginPanel", voParam);
        } catch(Exception e) {
            if (JClientMain.getoLog()!=null)
                JClientMain.getoLog().error(resMessages.getString(MESSAGE_ERROR_1), e);
        }                
    }
    public void initialize (ConnBackStatus poStatus) {
        oStatus = poStatus;
        this.setDefaultValues();
    }
    public ConnBackStatus getoStatus() {
        return oStatus;
    }
    public void setoStatus(ConnBackStatus oStatus) {
        this.oStatus = oStatus;
    }
    public synchronized void addEventListener (ConnBackListener poListener) {
        lListeners.add(poListener);
    }
    public synchronized void removeEventListener (ConnBackListener poListener) {
        lListeners.remove(poListener);
    }
    public synchronized void fireEvent() {
        ConnBackEvent oEvt = new ConnBackEvent (this, oStatus);
        java.util.Iterator i = lListeners.iterator();
        while (i.hasNext()) {
            ((ConnBackListener) i.next()).handleConnBackEvent(oEvt);
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

        mnuClipboard = new javax.swing.JPopupMenu();
        mnuCopy = new javax.swing.JMenuItem();
        mnuPaste = new javax.swing.JMenuItem();
        mnuCut = new javax.swing.JMenuItem();
        lblTitle = new javax.swing.JLabel();
        btnLogin = new javax.swing.JButton();
        lblEveId = new javax.swing.JLabel();
        txtEveId = new javax.swing.JTextField();
        lblToken = new javax.swing.JLabel();
        txtToken = new javax.swing.JTextField();
        lblStaId = new javax.swing.JLabel();
        scrStages = new javax.swing.JScrollPane();
        lstStages = new javax.swing.JList<>();
        lblStatus = new javax.swing.JLabel();
        btnWeb = new javax.swing.JButton();
        lblIdToken = new javax.swing.JLabel();
        txtIdToken = new javax.swing.JTextField();

        mnuCopy.setText(resMessages.getString("copy"));
        mnuCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCopyActionPerformed(evt);
            }
        });
        mnuClipboard.add(mnuCopy);

        mnuPaste.setText(resMessages.getString("paste"));
        mnuPaste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuPasteActionPerformed(evt);
            }
        });
        mnuClipboard.add(mnuPaste);

        mnuCut.setText(resMessages.getString("cut"));
        mnuCut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCutActionPerformed(evt);
            }
        });
        mnuClipboard.add(mnuCut);

        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(450, 200));
        setPreferredSize(new java.awt.Dimension(450, 200));

        lblTitle.setBackground(new java.awt.Color(255, 255, 255));
        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTitle.setText("2. " + resMessages.getString("login_event"));

        btnLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_login.png"))); // NOI18N
        btnLogin.setText(resMessages.getString("login"));
        btnLogin.setToolTipText(resMessages.getString("tooltip_login"));
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        lblEveId.setBackground(new java.awt.Color(255, 255, 255));
        lblEveId.setText(resMessages.getString("event_id"));

        txtEveId.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        txtEveId.setToolTipText(resMessages.getString("tooltip_event_id"));

        lblToken.setBackground(new java.awt.Color(255, 255, 255));
        lblToken.setText(resMessages.getString("token"));

        txtToken.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        txtToken.setToolTipText(resMessages.getString("tooltip_token"));

        lblStaId.setBackground(new java.awt.Color(255, 255, 255));
        lblStaId.setText(resMessages.getString("stage"));

        lstStages.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        lstStages.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lstStages.setToolTipText(resMessages.getString("tooltip_stage_list"));
        lstStages.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstStagesValueChanged(evt);
            }
        });
        scrStages.setViewportView(lstStages);

        lblStatus.setBackground(new java.awt.Color(255, 255, 255));
        lblStatus.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N

        btnWeb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_web.png"))); // NOI18N
        btnWeb.setText(resMessages.getString("gotoweb"));
        btnWeb.setToolTipText(resMessages.getString("tooltip_event_web"));
        btnWeb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnWebActionPerformed(evt);
            }
        });

        lblIdToken.setBackground(new java.awt.Color(255, 255, 255));
        lblIdToken.setText(resMessages.getString("idtoken"));

        txtIdToken.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        txtIdToken.setToolTipText(resMessages.getString("tooltip_idtoken"));
        txtIdToken.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtIdTokenMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txtIdTokenMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblStaId, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(scrStages, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnWeb, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblEveId, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtEveId)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblToken, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtToken))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblIdToken, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtIdToken)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblIdToken)
                    .addComponent(txtIdToken, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblToken)
                    .addComponent(txtToken, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEveId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEveId))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnWeb, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblStaId)
                        .addGap(0, 41, Short.MAX_VALUE))
                    .addComponent(scrStages, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        if (oStatus.getnStatus()!=ConnBackStatus.UPLOADING) {
            if (!txtEveId.getText().equals("") && !txtToken.getText().equals("")) {
                this.login();
            }
        } else {
            JOptionPane.showMessageDialog(this , 
                    resMessages.getString("info_upload_inprogress"), 
                    resMessages.getString("warning"),
                    JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnLoginActionPerformed

    private void lstStagesValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstStagesValueChanged
        if (!evt.getValueIsAdjusting()) {
            JList<?> voList = (JList<?>) evt.getSource();
            int vnNewListIndex = voList.getSelectedIndex();
            if (oStatus.getnStatus()!=ConnBackStatus.UPLOADING) {
                //If changing the selection is possible, saves the selected index for future use
                nPrevListIndex = vnNewListIndex;
                this.stageSelected();            
            } else {
                //If changing is not possible, shows a message and selects the previous index again
                JOptionPane.showMessageDialog(this , 
                        resMessages.getString("info_upload_inprogress"), 
                        resMessages.getString("warning"),
                        JOptionPane.WARNING_MESSAGE);
                if (nPrevListIndex>=0)
                    voList.setSelectedIndex(nPrevListIndex);
            }
        }
    }//GEN-LAST:event_lstStagesValueChanged

    private void btnWebActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnWebActionPerformed
        this.gotoWeb();
    }//GEN-LAST:event_btnWebActionPerformed

    private void txtIdTokenMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtIdTokenMousePressed
        // TODO add your handling code here:
        if (evt.isPopupTrigger()) {
            updateContextMenuOptions();
            mnuClipboard.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_txtIdTokenMousePressed

    private void txtIdTokenMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtIdTokenMouseReleased
        // TODO add your handling code here:
        if (evt.isPopupTrigger()) {
            updateContextMenuOptions();
            mnuClipboard.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_txtIdTokenMouseReleased

    private void mnuCopyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCopyActionPerformed
        // TODO add your handling code here:
        txtIdToken.copy();
    }//GEN-LAST:event_mnuCopyActionPerformed

    private void mnuPasteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuPasteActionPerformed
        // TODO add your handling code here:
        txtIdToken.paste();
    }//GEN-LAST:event_mnuPasteActionPerformed

    private void mnuCutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCutActionPerformed
        // TODO add your handling code here:
        txtIdToken.cut();
    }//GEN-LAST:event_mnuCutActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnWeb;
    private javax.swing.JLabel lblEveId;
    private javax.swing.JLabel lblIdToken;
    private javax.swing.JLabel lblStaId;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblToken;
    private javax.swing.JList<String> lstStages;
    private javax.swing.JPopupMenu mnuClipboard;
    private javax.swing.JMenuItem mnuCopy;
    private javax.swing.JMenuItem mnuCut;
    private javax.swing.JMenuItem mnuPaste;
    private javax.swing.JScrollPane scrStages;
    private javax.swing.JTextField txtEveId;
    private javax.swing.JTextField txtIdToken;
    private javax.swing.JTextField txtToken;
    // End of variables declaration//GEN-END:variables

    /**
     * Sets default values
     */
    public void setDefaultValues() {
        txtEveId.setText("");
        txtToken.setText("");
        txtIdToken.setText("");
        lstStages.setModel(new DefaultListModel<>());
        lblEveId.setVisible(false);
        lblToken.setVisible(false);
        txtEveId.setVisible(false);
        txtToken.setVisible(false);
        txtEveId.setEnabled(false);
        txtToken.setEnabled(false);
        txtIdToken.setEnabled(false);
        lstStages.setEnabled(false);
        btnLogin.setEnabled(false);
        btnWeb.setEnabled(false);
        lblStatus.setText("");
    }
    /**
     * Allows to make login
     */
    public void enableForLogin() {
        txtEveId.setText(cEveId);
        txtToken.setText(cToken);
        txtIdToken.setText(cIdToken);
        lstStages.setModel(new DefaultListModel<>());
        lblEveId.setVisible(false);
        lblToken.setVisible(false);
        txtEveId.setVisible(false);
        txtToken.setVisible(false);
        txtEveId.setEnabled(true);
        txtToken.setEnabled(true);
        txtIdToken.setEnabled(true);
        lstStages.setEnabled(false);
        btnLogin.setEnabled(true);
        btnWeb.setEnabled(false);
        lblStatus.setText("");
    }
    /**
     * Populates Event Id and Token from IdToken (concatenated value)
     */
    public void populateIdToken() {
        txtEveId.setText(cEveId);
        txtToken.setText(cToken);
    }
    /**
     * Allows to select a stage
     */
    public void enableForStage() {
        lstStages.setEnabled(true);
        btnWeb.setEnabled(true);
    }
    /**
     * Sets values when login is no ok
     * @param pcMessage String Message for the status bar
     */
    public void loginNoOk(String pcMessage) {
        oStatus.setcEveId("");
        oStatus.setcStaId("");
        oStatus.setcEveDesc("");
        oStatus.setcStaDesc("");
        oStatus.setcToken("");
        oStatus.setcIdToken("");
        oStatus.setnStatus(ConnBackStatus.LOGIN_NOOK);
        lblStatus.setText(pcMessage);
        lstStages.setModel(new DefaultListModel<String>());
        //Shows a message
        JOptionPane.showMessageDialog(this, 
                pcMessage,
                resMessages.getString("info"), 
                JOptionPane.INFORMATION_MESSAGE);
        if (JClientMain.getoLog()!=null)
            JClientMain.getoLog().warn(pcMessage);
        //And fires the event to notify it
        this.fireEvent();
    }
    /**
     * Performs a query to the server to log into an event; if ok, get the
     * list of stages as a result
     */
    private void login() {
        boolean vbOneStage = false;
        int vnStageSel = -1;
        try {
            //Gets an HTTP Client to make a request
            HttpClient voClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();
            //Sets the request to the current server
            HttpRequest voReq = HttpRequest.newBuilder()
                .GET()
                .uri(new URI(oStatus.getcServer() + "/api/v1/events/" + txtEveId.getText()))
                .header("Authorization", "Bearer " + txtToken.getText())
                .build();
            //Sends the request an gets the response
            HttpResponse<String> voResp = voClient.send(voReq, BodyHandlers.ofString());
            //If there is a correct response, fire the event to notify that
            if (voResp.statusCode()==200) {
                try {
                    //First, parse the response (an event and its stages)
                    String vcContents = voResp.body();
                    //JSON file with Jackson
                    ObjectMapper voMapper = new ObjectMapper();
                    OReplayDataTransfer voData = voMapper.readValue(vcContents, OReplayDataTransfer.class);
                    eu.oreplay.db.Event voEve = voData.getoEve();
                    if (voEve!=null) {
                        //Fill the list of stages with values
                        lstStages.setModel(new DefaultListModel<>());
                        lStages = new java.util.HashMap<Integer, eu.oreplay.db.Stage>();
                        DefaultListModel<String> voModel = new DefaultListModel<>();
                        int i = 0;
                        if (voEve.getStageList()!=null) {
                            for (eu.oreplay.db.Stage voSta : voEve.getStageList()) {
                                voModel.addElement(voSta.getDescription());
                                lStages.put(i, voSta);
                                if (voSta.getId().equals(cStaId) || voEve.getStageList().size()==1) {
                                    vnStageSel = i;
                                }
                                i++;
                            }
                        }
                        lstStages.setModel(voModel);
                        if (vnStageSel>=0) {
                            vbOneStage = true;
                            //Get the selected element by index and ensure the scroll is correct
                            lstStages.setSelectedIndex(vnStageSel);
                            lstStages.ensureIndexIsVisible(vnStageSel);
                            cStaId = lStages.get(vnStageSel).getId();
                            cStaDesc = lStages.get(vnStageSel).getDescription();
                        }
                        cEveDesc = voEve.getDescription();
                    }
                    //Show the ok message in the status area
                    lblStatus.setText(resMessages.getString("info_login_ok"));
                    //Fire the event
                    cEveId = txtEveId.getText();
                    cToken = txtToken.getText();
                    cIdToken = txtIdToken.getText();
                    oStatus.setcEveId(cEveId);
                    oStatus.setcEveDesc(cEveDesc);
                    oStatus.setcToken(cToken);
                    oStatus.setcIdToken(cIdToken);
                    if (JClientMain.getoLog()!=null) {
                        JClientMain.getoLog().info(resMessages.getString("info_login_ok"));
                        JClientMain.getoLog().info(resMessages.getString("event") + " " + cEveId + " " + cEveDesc);
                    }
                    //If there are several stages, status is only login ok
                    //But, if only one, select it inmediatly
                    if (!vbOneStage) {
                        oStatus.setnStatus(ConnBackStatus.LOGIN_OK);
                    } else {
                        oStatus.setcStaId(cStaId);
                        oStatus.setcStaDesc(cStaDesc);
                        oStatus.setnStatus(ConnBackStatus.STAGE_SELECTED);
                        if (JClientMain.getoLog()!=null)
                            JClientMain.getoLog().info(resMessages.getString("stage") + " " + cStaId + " " + cStaDesc);
                    }
                    this.fireEvent();
                }catch(Exception eJson) {
                    this.loginNoOk(resMessages.getString("info_data_novalid"));
                }
            } else {
                this.loginNoOk(resMessages.getString("info_login_nook"));
            }
        } catch (Exception eNet) {
            this.loginNoOk(resMessages.getString("info_login_nook_connection"));
        }
    }
    /**
     * When a user selects a stage from the list, set the element as current
     * stage and fire the event to notify the selection
     * @param pnRow int Row selected
     */
    private void stageSelected () {
        int vnRow = lstStages.getSelectedIndex();
        if (vnRow>=0 && vnRow<lStages.size()) {
            //Fire the event
            cEveId = txtEveId.getText();
            cToken = txtToken.getText();
            cIdToken = txtIdToken.getText();
            cStaId = lStages.get(vnRow).getId();
            cStaDesc = lStages.get(vnRow).getDescription();
            if (JClientMain.getoLog()!=null)
                JClientMain.getoLog().info(resMessages.getString("stage") + " " + cStaId + " " + cStaDesc);
            oStatus.setcEveId(cEveId);
            oStatus.setcStaId(cStaId);
            oStatus.setcEveDesc(cEveDesc);
            oStatus.setcStaDesc(cStaDesc);
            oStatus.setcToken(cToken);
            oStatus.setcIdToken(cIdToken);
            oStatus.setnStatus(ConnBackStatus.STAGE_SELECTED);
            this.fireEvent();
        }
    }
    /**
     * If there is an event and stage selected, opens a web explorer to the
     * page of the event and stage. If there is only event, goes to the event page
     */
    private void gotoWeb () {
        try {
            if (!oStatus.getcServer().equals("") && !cEveId.equals("")) {
                //Compose target URL
                String vcUrl = oStatus.getcServer();
                vcUrl += (!oStatus.getcServer().endsWith("/")?"/":"");
                vcUrl += "competitions/";
                vcUrl += cEveId;
                vcUrl += (!cStaId.equals("")?("/"+cStaId+"?menu=results"):"");
                //Launch web explorer
                Utils.openUrlInExplorer(vcUrl, 0);
            }
        } catch (Exception e) {
            if (JClientMain.getoLog()!=null)
                JClientMain.getoLog().error(resMessages.getString(MESSAGE_ERROR_1), e);
        }
    }
    /**
     * When something happens related to the IdToken TextField and the Contextual Menu,
     * it updates the state of the menu options an contents
     */
    private void updateContextMenuOptions() {
        String vcSelectedText = txtIdToken.getSelectedText();
        mnuCopy.setEnabled(vcSelectedText != null && !vcSelectedText.isEmpty());
        mnuCut.setEnabled(vcSelectedText != null && !vcSelectedText.isEmpty());

        try {
            Clipboard voClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            Transferable voContents = voClipboard.getContents(null);
            boolean vbHasText = voContents != null && voContents.isDataFlavorSupported(DataFlavor.stringFlavor);
            mnuPaste.setEnabled(vbHasText);
        } catch (Exception ex) {
            mnuPaste.setEnabled(false);
        }
    }
    
    /**
     * Inner class that is a Listener for the JTextField to know when their value
     * change; used for event id and token when the user pastes a content; 
     * if there comes a concatenation of EventId and Token, this Listener splits
     * the values to fill those two JTextField
     */
    private class DocumentListenerIdToken implements DocumentListener {
        @Override
        public void changedUpdate(DocumentEvent e) { // Noncompliant - method is empty

        }
        @Override
        public void insertUpdate(DocumentEvent e) {
            Document voDoc = e.getDocument();
            try {
                String vcText = voDoc.getText(0, voDoc.getLength());
                //If the text concatenates EventId(36) + Token (40), then split those two values
                if (vcText!=null && vcText.length()>36 && vcText.charAt(8)=='-') {
                    String vcId = vcText.substring(0, 36);
                    String vcToken = vcText.substring(36);
                    SwingUtilities.invokeLater(() -> {
                        //Fire the event
                        cEveId = vcId;
                        cToken = vcToken;
                        cIdToken = vcText;
                        oStatus.setcEveId(cEveId);
                        oStatus.setcToken(cToken);
                        oStatus.setcIdToken(cIdToken);
                        oStatus.setnStatus(ConnBackStatus.PASTE_IDTOKEN);
                        ConnBackLoginPanel.this.fireEvent();
                    });
                }

            } catch (BadLocationException e1) {
                if (JClientMain.getoLog()!=null)
                    JClientMain.getoLog().error(resMessages.getString(MESSAGE_ERROR_1), e1);
            }
        }
        @Override
        public void removeUpdate(DocumentEvent e) { // Noncompliant - method is empty
        }
    }
    
}
