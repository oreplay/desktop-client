/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package eu.oreplay.gui;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.oreplay.gui.events.*;
import eu.oreplay.logic.FormsParameters;
import eu.oreplay.logic.converter.OReplayDataTransfer;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;

/**
 *
 * @author javier.arufe
 */
public class ConnBackLoginPanel extends javax.swing.JPanel {
    private static java.util.ResourceBundle resMessages = java.util.ResourceBundle.getBundle("eu.oreplay.library.messages.Messages"); //$NON-NLS-1$;
    private ConnBackStatus oStatus = new ConnBackStatus();
    private java.util.List lListeners = new java.util.ArrayList();
    private String cEveId = "";
    private String cToken = "";
    private String cStaId = "";
    private String cEveDesc = "";
    private String cStaDesc = "";
    private java.util.HashMap<Integer, eu.oreplay.db.Stage> lStages = new java.util.HashMap<>();
    
    /**
     * Creates new form ConnBackLoginPanel
     */
    public ConnBackLoginPanel() {
        initComponents();
        this.setDefaultValues();
    }
    /**
     * Forces app language change
     * @param pcLocale String New language's initials
     */
    public void changeLanguage (String pcLocale) {
        java.util.Locale voLocale = new java.util.Locale(pcLocale);
        java.util.Locale.setDefault(voLocale);
        java.util.ResourceBundle.clearCache();
        resMessages = java.util.ResourceBundle.getBundle("eu.oreplay.library.messages.Messages", voLocale);
        //Change the texts
        lblTitle.setText("2. " + resMessages.getString("login_event"));
        btnLogin.setText(resMessages.getString("login"));
        lblEveId.setText(resMessages.getString("event_id"));
        lblToken.setText(resMessages.getString("token"));
        lblStaId.setText(resMessages.getString("stage"));
    }
    public void initFormParameters(FormsParameters.ParConnBackLoginPanel poParam) {
        try {
            /*
            this.setBounds(poParam.getoPos().getnPosX(), 
                poParam.getoPos().getnPosY(), 
                poParam.getoPos().getnSizeX(),
                poParam.getoPos().getnSizeY());
            */
            cEveId = poParam.getcEveId();
            cToken = poParam.getcToken();
            cStaId = poParam.getcStaId();
            cEveDesc = poParam.getcEveDesc();
            cStaDesc = poParam.getcStaDesc();
        }catch (Exception e) {
            JClientMain.getoLog().error(resMessages.getString("error_exception"), e);
        }
    }
    public void saveFormParameters() {
        try {
            //Read the parameters to store
            FormsParameters voPadre = new FormsParameters();
            FormsParameters.ParConnBackLoginPanel voParam = voPadre.new ParConnBackLoginPanel();
            /*
            voParam.getoPos().setnPosX(this.getX());
            voParam.getoPos().setnPosY(this.getY());
            voParam.getoPos().setnSizeX(this.getWidth());
            voParam.getoPos().setnSizeY(this.getHeight());
            */
            voParam.setcEveId(cEveId);
            voParam.setcToken(cToken);
            voParam.setcStaId(cStaId);
            voParam.setcEveDesc(cEveDesc);
            voParam.setcStaDesc(cStaDesc);
            //Calls the method in the main form to receive and to store the parameters
            JClientMain.updateFormsParameters("ConnBackLoginPanel", voParam);
        } catch(Exception e) {
            JClientMain.getoLog().error(resMessages.getString("error_exception"), e);
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

        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(300, 200));
        setPreferredSize(new java.awt.Dimension(300, 200));

        lblTitle.setBackground(new java.awt.Color(255, 255, 255));
        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTitle.setText("2. " + resMessages.getString("login_event"));

        btnLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_login.png"))); // NOI18N
        btnLogin.setText(resMessages.getString("login"));
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        lblEveId.setBackground(new java.awt.Color(255, 255, 255));
        lblEveId.setText(resMessages.getString("event_id"));

        txtEveId.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N

        lblToken.setBackground(new java.awt.Color(255, 255, 255));
        lblToken.setText(resMessages.getString("token"));

        txtToken.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N

        lblStaId.setBackground(new java.awt.Color(255, 255, 255));
        lblStaId.setText(resMessages.getString("stage"));

        lstStages.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        lstStages.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lstStages.setToolTipText("");
        lstStages.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstStagesValueChanged(evt);
            }
        });
        scrStages.setViewportView(lstStages);

        lblStatus.setBackground(new java.awt.Color(255, 255, 255));
        lblStatus.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblStaId, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(scrStages, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblToken, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtToken))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblEveId, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtEveId)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEveId)
                    .addComponent(txtEveId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblToken)
                    .addComponent(txtToken, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        // TODO add your handling code here:
        if (!txtEveId.equals("") && !txtToken.equals("")) {
            this.login();
        }
    }//GEN-LAST:event_btnLoginActionPerformed

    private void lstStagesValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstStagesValueChanged
        // TODO add your handling code here:
        if (!evt.getValueIsAdjusting())
            this.stageSelected();
    }//GEN-LAST:event_lstStagesValueChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private javax.swing.JLabel lblEveId;
    private javax.swing.JLabel lblStaId;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblToken;
    private javax.swing.JList<String> lstStages;
    private javax.swing.JScrollPane scrStages;
    private javax.swing.JTextField txtEveId;
    private javax.swing.JTextField txtToken;
    // End of variables declaration//GEN-END:variables

    /**
     * Sets default values
     */
    public void setDefaultValues() {
        txtEveId.setText("");
        txtToken.setText("");
        lstStages.setModel(new DefaultListModel<>());
        txtEveId.setEnabled(false);
        txtToken.setEnabled(false);
        lstStages.setEnabled(false);
        btnLogin.setEnabled(false);
        lblStatus.setText("");
    }
    /**
     * Allows to make login
     */
    public void enableForLogin() {
        txtEveId.setText(cEveId);
        txtToken.setText(cToken);
        lstStages.setModel(new DefaultListModel<>());
        txtEveId.setEnabled(true);
        txtToken.setEnabled(true);
        lstStages.setEnabled(false);
        btnLogin.setEnabled(true);
        lblStatus.setText("");
    }
    /**
     * Allows to select a stage
     */
    public void enableForStage() {
        lstStages.setEnabled(true);
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
        oStatus.setnStatus(ConnBackStatus.LOGIN_NOOK);
        lblStatus.setText(pcMessage);
        lstStages.setModel(new DefaultListModel<String>());
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
                    oStatus.setcEveId(cEveId);
                    oStatus.setcEveDesc(cEveDesc);
                    oStatus.setcToken(cToken);
                    //If there are several stages, status is only login ok
                    //But, if only one, select it inmediatly
                    if (!vbOneStage) {
                        oStatus.setnStatus(ConnBackStatus.LOGIN_OK);
                    } else {
                        oStatus.setcStaId(cStaId);
                        oStatus.setcStaDesc(cStaDesc);
                        oStatus.setnStatus(ConnBackStatus.STAGE_SELECTED);
                    }
                    this.fireEvent();
                }catch(Exception eJson) {
                    eJson.printStackTrace();
                    this.loginNoOk(resMessages.getString("info_data_novalid"));
                }
            } else {
                this.loginNoOk(resMessages.getString("info_login_nook"));
            }
        } catch (Exception eNet) {
            this.loginNoOk(resMessages.getString("info_login_nook"));
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
            cStaId = lStages.get(vnRow).getId();
            cStaDesc = lStages.get(vnRow).getDescription();
            oStatus.setcEveId(cEveId);
            oStatus.setcStaId(cStaId);
            oStatus.setcEveDesc(cEveDesc);
            oStatus.setcStaDesc(cStaDesc);
            oStatus.setcToken(cToken);
            oStatus.setnStatus(ConnBackStatus.STAGE_SELECTED);
            this.fireEvent();
        }
    }
}
