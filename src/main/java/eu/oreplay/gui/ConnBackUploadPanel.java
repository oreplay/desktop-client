/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package eu.oreplay.gui;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.oreplay.gui.events.*;
import eu.oreplay.logic.FormsParameters;
import eu.oreplay.logic.connection.UploadResponse;
import eu.oreplay.logic.converter.ConverterToModel;
import eu.oreplay.logic.converter.OReplayDataTransfer;
import eu.oreplay.utils.JUtils;
import eu.oreplay.utils.Utils;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.Date;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

/**
 *
 * @author javier.arufe
 */
public class ConnBackUploadPanel extends javax.swing.JPanel {
    private static java.util.ResourceBundle resMessages = java.util.ResourceBundle.getBundle("messages.Messages"); //$NON-NLS-1$;
    private static java.util.ResourceBundle resGlobal = java.util.ResourceBundle.getBundle("messages.Global"); //$NON-NLS-1$;
    private static java.util.ResourceBundle resDates = java.util.ResourceBundle.getBundle("messages.Dates"); //$NON-NLS-1$;
    private ConnBackStatus oStatus = new ConnBackStatus();
    private java.util.List lListeners = new java.util.ArrayList();
    private String cFolder = "";
    private String cExtension = "";
    private boolean bSplit = false;
    private boolean bRun = false;
    private StringBuilder oSB = new StringBuilder();
    private String cStart = "<html><head></head><body>";
    private String cEnd = "</body></html>";

    
    /**
     * Creates new form ConnBackUploadPanel
     */
    public ConnBackUploadPanel() {
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
        resMessages = java.util.ResourceBundle.getBundle("messages.Messages", voLocale);
        resDates = java.util.ResourceBundle.getBundle("messages.Dates", voLocale);
        //Change the texts
        lblTitle.setText("3. " + resMessages.getString("upload_data"));
        if (!bRun)
            btnUpload.setText(resMessages.getString("run"));
        else
            btnUpload.setText(resMessages.getString("stop"));
        lblFolder.setText(resMessages.getString("folder"));
        btnFolder.setText(resMessages.getString("browse"));
        lblExtension.setText(resMessages.getString("extension"));
        chkSplit.setText(resMessages.getString("split_class"));
        txtFolder.setToolTipText(resMessages.getString("tooltip_upload_folder"));
        lstExtensions.setToolTipText(resMessages.getString("tooltip_extensions"));
        chkSplit.setToolTipText(resMessages.getString("tooltip_split_cat"));
        btnUpload.setToolTipText(resMessages.getString("tooltip_start_stop"));
    }
    public void initFormParameters(FormsParameters.ParConnBackUploadPanel poParam) {
        try {
            cFolder = poParam.getcFolder();
            cExtension = poParam.getcExtension();
            bSplit = poParam.isbSplit();
            //Fire the event to notify changing the extension
            if (cExtension.toLowerCase().equals("csv"))
                oStatus.setnStatus(ConnBackStatus.EXT_CSV);
            else if (cExtension.toLowerCase().equals("xml"))
                oStatus.setnStatus(ConnBackStatus.EXT_XML);
            fireEvent();
        }catch (Exception e) {
            if (JClientMain.getoLog()!=null)
                JClientMain.getoLog().error(resMessages.getString("error_exception"), e);
        }
    }
    public void saveFormParameters() {
        try {
            //Read the parameters to store
            FormsParameters voPadre = new FormsParameters();
            FormsParameters.ParConnBackUploadPanel voParam = voPadre.new ParConnBackUploadPanel();
            voParam.setcFolder(cFolder);
            voParam.setcExtension(cExtension);
            voParam.setbSplit(chkSplit.isSelected());
            //Calls the method in the main form to receive and to store the parameters
            JClientMain.updateFormsParameters("ConnBackUploadPanel", voParam);
        } catch(Exception e) {
            if (JClientMain.getoLog()!=null)
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
        btnUpload = new javax.swing.JButton();
        scrStatus = new javax.swing.JScrollPane();
        txtStatus = new javax.swing.JEditorPane();
        lblFolder = new javax.swing.JLabel();
        txtFolder = new javax.swing.JTextField();
        btnFolder = new javax.swing.JButton();
        lblExtension = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstExtensions = new javax.swing.JList<>();
        chkSplit = new javax.swing.JCheckBox();

        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(500, 250));
        setPreferredSize(new java.awt.Dimension(500, 250));

        lblTitle.setBackground(new java.awt.Color(255, 255, 255));
        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTitle.setText("3. " + resMessages.getString("upload_data"));

        btnUpload.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_start.png"))); // NOI18N
        btnUpload.setText(resMessages.getString("run"));
        btnUpload.setToolTipText(resMessages.getString("tooltip_start_stop"));
        btnUpload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUploadActionPerformed(evt);
            }
        });

        txtStatus.setContentType("text/html"); // NOI18N
        txtStatus.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        scrStatus.setViewportView(txtStatus);

        lblFolder.setBackground(new java.awt.Color(255, 255, 255));
        lblFolder.setText(resMessages.getString("folder"));

        txtFolder.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        txtFolder.setToolTipText(resMessages.getString("tooltip_upload_folder"));

        btnFolder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_browse.png"))); // NOI18N
        btnFolder.setText(resMessages.getString("browse"));
        btnFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFolderActionPerformed(evt);
            }
        });

        lblExtension.setBackground(new java.awt.Color(255, 255, 255));
        lblExtension.setText(resMessages.getString("extension"));

        lstExtensions.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        lstExtensions.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "xml", "csv" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        lstExtensions.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lstExtensions.setToolTipText(resMessages.getString("tooltip_extensions"));
        lstExtensions.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstExtensionsValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(lstExtensions);

        chkSplit.setText(resMessages.getString("split_class"));
        chkSplit.setToolTipText(resMessages.getString("tooltip_split_cat"));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scrStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblFolder, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtFolder))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblExtension, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(chkSplit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnFolder, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUpload, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnFolder, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnUpload, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblFolder, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtFolder, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblExtension)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(chkSplit))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnUploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUploadActionPerformed
        this.startStopUpload();
    }//GEN-LAST:event_btnUploadActionPerformed

    private void btnFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFolderActionPerformed
        this.selectFileForReading();
    }//GEN-LAST:event_btnFolderActionPerformed

    private void lstExtensionsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstExtensionsValueChanged
        if (!evt.getValueIsAdjusting())
            this.extensionSelected();
    }//GEN-LAST:event_lstExtensionsValueChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFolder;
    private javax.swing.JButton btnUpload;
    private javax.swing.JCheckBox chkSplit;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblExtension;
    private javax.swing.JLabel lblFolder;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JList<String> lstExtensions;
    private javax.swing.JScrollPane scrStatus;
    private javax.swing.JTextField txtFolder;
    private javax.swing.JEditorPane txtStatus;
    // End of variables declaration//GEN-END:variables

    /**
     * Sets default values
     */
    public void setDefaultValues() {
        oSB = new StringBuilder();
        txtFolder.setText("");
        lstExtensions.clearSelection();
        txtFolder.setEnabled(false);
        lstExtensions.setEnabled(false);
        btnFolder.setEnabled(false);
        btnUpload.setEnabled(false);
        txtStatus.setText(cStart + appendString(resMessages.getString("waiting"), "#000000") + cEnd);
        btnUpload.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_start.png"))); // NOI18N
        btnUpload.setText(resMessages.getString("run"));
        chkSplit.setEnabled(false);
        bRun = false;
    }
    /**
     * Allows to start/stop upload
     */
    public void enableForUpload() {
        txtFolder.setText(cFolder);
        if (!cExtension.equals(""))
            lstExtensions.setSelectedValue(cExtension, true);
        else {
            lstExtensions.setSelectedIndex(0);
            this.extensionSelected();
        }
        txtFolder.setEnabled(true);
        lstExtensions.setEnabled(true);
        btnFolder.setEnabled(true);
        btnUpload.setEnabled(true);
        btnUpload.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_start.png"))); // NOI18N
        btnUpload.setText(resMessages.getString("run"));
        chkSplit.setEnabled(true);
        chkSplit.setSelected(bSplit);
        bRun = false;
    }
    /**
     * Toogles between uploading or not
     */
    private void startStopUpload() {
        try {
            if (bRun) {
                //If there is no file currently uploading, changes the status to stop
                if (oStatus.getnStatus()!=ConnBackStatus.UPLOADING) {
                    btnUpload.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_start.png"))); // NOI18N
                    btnUpload.setText(resMessages.getString("run"));
                    txtStatus.setText(cStart + appendString(resMessages.getString("info_upload_stopped"), "#000000") + cEnd);
                    if (JClientMain.getoLog()!=null)
                        JClientMain.getoLog().info(resMessages.getString("info_upload_stopped"));
                    bRun = !bRun;
                    //Fire the event to notify stopping the upload
                    oStatus.setnStatus(ConnBackStatus.UPLOAD_OFF);
                    oStatus.setnStatusOld(ConnBackStatus.NO_STATUS);
                    oStatus.setnStatusNext(ConnBackStatus.NO_STATUS);
                    fireEvent();
                } else {
                    JOptionPane.showMessageDialog(this , 
                            resMessages.getString("info_upload_inprogress_request_stop"), 
                            resMessages.getString("warning"),
                            JOptionPane.WARNING_MESSAGE);
                    //Sets the future status to stop
                    oStatus.setnStatusNext(ConnBackStatus.UPLOAD_OFF);
                    fireEvent();
                }                
            } else {
                boolean vbExtAndDate = this.checkExtensionAndDate();
                if (vbExtAndDate) {
                    if (oStatus.isReadyToSend() && Utils.folderExists(cFolder) && 
                            !cExtension.equals("")) {
                        btnUpload.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_stop.png"))); // NOI18N
                        btnUpload.setText(resMessages.getString("stop"));
                        txtStatus.setText(cStart + appendString(resMessages.getString("info_upload_started"), "#000000") + cEnd);
                        if (JClientMain.getoLog()!=null)
                            JClientMain.getoLog().info(resMessages.getString("info_upload_started"));
                        //Fire the event to notify starting the upload
                        oStatus.setnStatus(ConnBackStatus.UPLOAD_ON);
                        oStatus.setnStatusOld(ConnBackStatus.NO_STATUS);
                        oStatus.setnStatusNext(ConnBackStatus.NO_STATUS);
                        fireEvent();
                        //Change the running flag
                        bRun = !bRun;
                        //Starts the upload process in a separated thread using a SwingWorker
                        this.uploadThread();
                    } else {
                        txtStatus.setText(cStart + appendString(resMessages.getString("info_upload_notready"), "#000000") + cEnd);
                        if (JClientMain.getoLog()!=null)
                            JClientMain.getoLog().info(resMessages.getString("info_upload_notready"));
                    }
                }
            }
        }catch(Exception e) {
            if (JClientMain.getoLog()!=null)
                JClientMain.getoLog().error(resMessages.getString("error_exception"), e);
        }
    }
    /**
     * When the user clics the button, it allows to select a source folder for processing files
     */
    public void selectFileForReading() {
        try {
            String vcFile = JUtils.selectDirectory(null, cFolder);
            if (vcFile!=null) {
                cFolder = JUtils.getcCurDir();
                txtFolder.setText(cFolder);
            } else {
                txtFolder.setText("");
            }
        } catch (Exception e) {
            if (JClientMain.getoLog()!=null)
                JClientMain.getoLog().error(resMessages.getString("error_exception"), e);
        }
    }
    /**
     * When a user selects an extension type from the list, set the current value
     */
    private void extensionSelected () {
        int vnRow = lstExtensions.getSelectedIndex();
        if (vnRow>=0 && vnRow<2) {
            //Save current status to be able to come back to it after this selection
            oStatus.setnStatusOld(oStatus.getnStatus());
            //Get new extension name
            cExtension = lstExtensions.getModel().getElementAt(vnRow);
            //Fire the event to notify changing the extension
            if (cExtension.toLowerCase().equals("csv"))
                oStatus.setnStatus(ConnBackStatus.EXT_CSV);
            else if (cExtension.toLowerCase().equals("xml"))
                oStatus.setnStatus(ConnBackStatus.EXT_XML);
            fireEvent();
        }
    }
 
    /**
     * Performs a simple query to the server to know if it's ready to receive communications
     * There can be several servers; the execution is inside a SwingWorker to be able
     * to refresh status messages in the GUI as the requests are done
     */
    private void uploadThread() 
    { 
        SwingWorker voSw = new SwingWorker() { 
            // Method to perform tasks in background and free the GUI to refresh data
            @Override
            protected String doInBackground() 
                throws Exception { 
                boolean vbSearchNew = false;
                boolean vbFound = false;
                Date voStart = new Date();
                Date voFinish = new Date();
                Date voNow = new Date();
                String vcNow = "";
                String vcFile = "";
                boolean vbDeleteFile = true;
                HttpClient voClient = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(5))
                    .build();

                //Iterate until the thread is stopped
                while (bRun) {
                    //Before checking for new contents, check if the user has requested to stop
                    if (oStatus.getnStatusNext()==ConnBackStatus.UPLOAD_OFF) {
                        JOptionPane.showMessageDialog(ConnBackUploadPanel.this , 
                            resMessages.getString("info_upload_aborted"), 
                            resMessages.getString("warning"),
                            JOptionPane.WARNING_MESSAGE);
                        if (JClientMain.getoLog()!=null)
                            JClientMain.getoLog().warn(resMessages.getString("info_upload_aborted"));
                        //Launches the method to effectively stop
                        oStatus.setnStatus(ConnBackStatus.UPLOAD_OFF);
                        ConnBackUploadPanel.this.startStopUpload();
                    } else {                   
                        //First, find a file with the given extension located at the given folder
                        //If it's not the first time, and the last file was unsuccessful and needs to be uploaded again, don't try to find another file, keep on with the same
                        if (!(vbDeleteFile==false && vcFile!=null && !vcFile.equals(""))) {
                            vcFile = Utils.findFirstFileInDir(cFolder, cExtension, false);
                            vbSearchNew = true;
                        } else {
                            vbSearchNew = false;
                        }
                        if (vcFile!=null && !vcFile.equals("")) {
                            //Clean the status list (for performance testing)
                            if (vbSearchNew)
                                oSB = new StringBuilder();
                            //Fire the event to notify starting uploading a new found file
                            if (oStatus.getnStatusNext()!=ConnBackStatus.UPLOAD_OFF) {
                                oStatus.setnStatus(ConnBackStatus.UPLOADING);
                                oStatus.setnStatusOld(ConnBackStatus.NO_STATUS);
                                oStatus.setnStatusNext(ConnBackStatus.NO_STATUS);
                                fireEvent();
                            }
                            //Sets the flag value by default
                            vbDeleteFile = true;
                            //Get a Timestamp when starting process
                            voStart = new Date();
                            vcNow = Utils.format(voStart, resDates.getString("format_datetime_milli_dash"));
                            publish("#000000<JARUTAG>" + resMessages.getString("info_fileprocess_started") + " - " + vcNow);
                            if (JClientMain.getoLog()!=null)
                                JClientMain.getoLog().info("File: " + vcFile + "\n" +
                                    "Eve Id: " + oStatus.getcEveId() +  "\n" +
                                    "Eve Desc: " + oStatus.getcEveDesc() + "\n" +
                                    "Sta Id: " + oStatus.getcStaId() +  "\n" +
                                    "Sta Desc: " + oStatus.getcStaDesc());
                            //Second, parse the contents to generate a JSON
                            OReplayDataTransfer voTransf = new OReplayDataTransfer();
                            voTransf.setoLog(JClientMain.getoLog());
                            ConverterToModel voConv = voTransf.preProcessFile(vcFile);
                            //Sets stage date and zero time from Main Form
                            try {
                                voConv.setcStageDate(JClientMain.getDateForTransfer());
                                voConv.setcStageZeroTime(JClientMain.getZeroTimeForTransfer());
                                voConv.setbForce(JClientMain.isbForce());
                                voConv.setcDateFormat(resDates.getString("format_date"));
                            }catch(Exception eDate) {
                            }
                            HashMap<String, String> vaJson = null;
                            if (chkSplit.isSelected()) {
                                vaJson = (oStatus==null?voTransf.processFileAndSplit(voConv):voTransf.processFileAndSplit(voConv, oStatus.getcEveId(), oStatus.getcEveDesc(), oStatus.getcStaId(), oStatus.getcStaDesc()));
                            } else {
                                String vcJson = (oStatus==null?voTransf.processFile(voConv):voTransf.processFile(voConv, oStatus.getcEveId(), oStatus.getcEveDesc(), oStatus.getcStaId(), oStatus.getcStaDesc()));
                                vaJson = new HashMap<>();
                                vaJson.put("ALL", vcJson);
                            }
                            if (JClientMain.getoLog()!=null)
                                JClientMain.getoLog().info("JSON parts: " + vaJson.size());
                            try {
                                //Iterate over keys of the Hashmap
                                for (String vcClass : vaJson.keySet()) {
                                    //Check again for bRun because it could be changed in any step
                                    if (bRun) {
                                        //Before sending new contents, check if the user has requested to stop
                                        if (oStatus.getnStatusNext()==ConnBackStatus.UPLOAD_OFF) {
                                            JOptionPane.showMessageDialog(ConnBackUploadPanel.this , 
                                                resMessages.getString("info_upload_aborted"), 
                                                resMessages.getString("warning"),
                                                JOptionPane.WARNING_MESSAGE);
                                            if (JClientMain.getoLog()!=null)
                                                JClientMain.getoLog().warn(resMessages.getString("info_upload_aborted"));
                                            //Launches the method to effectively stop
                                            oStatus.setnStatus(ConnBackStatus.UPLOAD_OFF);
                                            ConnBackUploadPanel.this.startStopUpload();
                                        } else {
                                            //Extract the JSON of the class using the Hashmap and the key
                                            String vcJson = vaJson.get(vcClass);
                                            //Communication with backend
                                            if (!vcJson.toLowerCase().startsWith("error")) {
                                                //Get a Timestamp when starting process for a part of the file
                                                Date voStartPart = new Date();
                                                vcNow = Utils.format(voStartPart, resDates.getString("format_datetime_milli_dash"));
                                                publish("#000000<JARUTAG>" + resMessages.getString("info_classprocess_started") + " - " + vcClass + " - " + vcNow);
                                                //Get a version number from version resource text
                                                long vnVersion = Utils.getVersionNumberFromText(resGlobal.getString("version"));
                                                //Next, send the contents to the backend
                                                vbFound = false;
                                                //Sets the request to the current server
                                                HttpRequest voReq = HttpRequest.newBuilder()
                                                    .POST(HttpRequest.BodyPublishers.ofString(vcJson))
                                                    .uri(new URI(oStatus.getcServer() + 
                                                            "/api/v1/events/" + oStatus.getcEveId() + "/uploads?version=" + vnVersion))
                                                    .header("Authorization", "Bearer " + oStatus.getcToken())
                                                    .header("Content-Type", "application/json")
                                                    .header("Accept", "application/json")
                                                    .build();
                                                //Sends the request an gets the response
                                                HttpResponse<String> voResp = voClient.send(voReq, BodyHandlers.ofString());
                                                //If there is a correct response, finish the process to fire the event
                                                if (voResp.statusCode()==200 || voResp.statusCode() == 202) {
                                                    try {
                                                        //First, parse the response (a list of strings with data)
                                                        String vcContents = voResp.body();
                                                        //JSON file with Jackson
                                                        ObjectMapper voMapper = new ObjectMapper();
                                                        UploadResponse voData = voMapper.readValue(vcContents, UploadResponse.class);
                                                        //If there are data, get the human readable strings
                                                        if (voData!=null && voData.getoMeta()!=null && 
                                                                voData.getoMeta().getlHuman()!=null && 
                                                                !voData.getoMeta().getlHuman().isEmpty()) {
                                                            String vcColor = "#000000";
                                                            try {
                                                                vcColor = voData.getoMeta().getcHumanColor();
                                                            }catch(Exception eColor) {
                                                                vcColor = "#000000";
                                                            }
                                                            for (int i=0; i<voData.getoMeta().getlHuman().size(); i++) {
                                                                String vcData = voData.getoMeta().getlHuman().get(i);
                                                                //Check if the backend sends a message indicating that the process has been aborted due a long time
                                                                //In this case, don't delete de file to continue uploading it again
                                                                if (vcData.toLowerCase().contains("it is taking too long")) {
                                                                    vbDeleteFile = false;
                                                                }
                                                                //This calls the method "process" in the SwingWorker, to set a status text in the panel
                                                                publish(vcColor + "<JARUTAG>" + vcData);
                                                            }
                                                            vbFound = true;
                                                        }
                                                    }catch (Exception eParseResp) {
                                                        if (JClientMain.getoLog()!=null)
                                                            JClientMain.getoLog().error(resMessages.getString("error_exception"), eParseResp);
                                                        vbFound = false;
                                                    }
                                                    if (!vbFound) {
                                                        voFinish = new java.util.Date();
                                                        vcNow = Utils.format(voFinish, resDates.getString("format_datetime_milli_dash"));
                                                        //This calls the method "process" in the SwingWorker, to set a status text in the panel
                                                        publish("#000000<JARUTAG>" + resMessages.getString("info_data_saved_error") + " - " + vcNow);
                                                    } else {
                                                        //Get a Timestamp when stopping process
                                                        voFinish = new java.util.Date();
                                                        vcNow = Utils.format(voFinish, resDates.getString("format_datetime_milli_dash"));
                                                        long vnDiff = Math.abs(voFinish.getTime() - voStartPart.getTime());
                                                        long vnDiffSec = TimeUnit.SECONDS.convert(vnDiff, TimeUnit.MILLISECONDS);
                                                        publish("#000000<JARUTAG>" + resMessages.getString("info_classprocess_finished") + " - " + vcNow + 
                                                                " - " + vnDiffSec + " " + resMessages.getString("second_mid"));
                                                    }
                                                } else {
                                                    voFinish = new java.util.Date();
                                                    vcNow = Utils.format(voFinish, resDates.getString("format_datetime_milli_dash"));
                                                    //This calls the method "process" in the SwingWorker, to set a status text in the panel
                                                    publish("#000000<JARUTAG>" + resMessages.getString("info_connection_nook") + " - " + voResp.statusCode() + " - " + vcNow);
                                                    //Temporal, for testing when big files returning connection errors. Set split check to true
                                                    if ((voResp.statusCode()==500 || voResp.statusCode() == 502 || voResp.statusCode() == 504) &&
                                                            vaJson.size()==1 && !chkSplit.isSelected()) {
                                                        vbDeleteFile = false;
                                                        chkSplit.setSelected(true);
                                                    }
                                                }
                                                //Set connection objects to null
                                                try {
                                                    voResp = null;
                                                    voReq = null;
                                                }catch (Exception eCloseConn) {
                                                    if (JClientMain.getoLog()!=null)
                                                        JClientMain.getoLog().error(resMessages.getString("error_exception"), eCloseConn);
                                                }
                                            } else {
                                                voFinish = new java.util.Date();
                                                vcNow = Utils.format(voFinish, resDates.getString("format_datetime_milli_dash"));
                                                //This calls the method "process" in the SwingWorker, to set a status text in the panel
                                                if (vcJson.startsWith("error_exception")) {
                                                    publish("#FF0000<JARUTAG>" + vcJson + " - " + vcNow);
                                                } else {
                                                    publish("#FF0000<JARUTAG>" + resMessages.getString(vcJson) + " - " + vcNow);
                                                }
                                            }
                                            //JSON process finished
                                            //Garbage collection. Be carefull !!!!!
                                            System.gc();
                                        }
                                    }
                                }
                                //Get a Timestamp when stopping process
                                voFinish = new java.util.Date();
                                vcNow = Utils.format(voFinish, resDates.getString("format_datetime_milli_dash"));
                                long vnDiff = Math.abs(voFinish.getTime() - voStart.getTime());
                                long vnDiffSec = TimeUnit.SECONDS.convert(vnDiff, TimeUnit.MILLISECONDS);
                                publish("#000000<JARUTAG>" + resMessages.getString("info_fileprocess_finished") + " - " + vcNow + 
                                        " - " + vnDiffSec + " " + resMessages.getString("second_mid"));
                            } catch (java.net.http.HttpConnectTimeoutException eTimeout) {
                                //If any of the connections fails, set the flag not to delete the file
                                vbDeleteFile = false;
                                voFinish = new java.util.Date();
                                vcNow = Utils.format(voFinish, resDates.getString("format_datetime_milli_dash"));
                                if (JClientMain.getoLog()!=null)
                                    JClientMain.getoLog().error(resMessages.getString("error_exception") + " - " + vcNow, eTimeout);
                                //This calls the method "process" in the SwingWorker, to set a status text in the panel
                                publish("#000000<JARUTAG>" + resMessages.getString("info_connection_timeout") + " - " + vcNow);
                            } catch (java.io.IOException | java.lang.InterruptedException eInterrupt) {
                                vbDeleteFile = false;
                                voFinish = new java.util.Date();
                                vcNow = Utils.format(voFinish, resDates.getString("format_datetime_milli_dash"));
                                if (JClientMain.getoLog()!=null)
                                    JClientMain.getoLog().error(resMessages.getString("error_exception") + " - " + vcNow, eInterrupt);
                                //This calls the method "process" in the SwingWorker, to set a status text in the panel
                                publish("#000000<JARUTAG>" + resMessages.getString("info_connection_break") + " - " + vcNow);
                            } catch (Exception eNet) {
                                vbDeleteFile = false;
                                voFinish = new java.util.Date();
                                vcNow = Utils.format(voFinish, resDates.getString("format_datetime_milli_dash"));
                                if (JClientMain.getoLog()!=null)
                                    JClientMain.getoLog().error(resMessages.getString("error_exception") + " - " + vcNow, eNet);
                                //This calls the method "process" in the SwingWorker, to set a status text in the panel
                                publish("#000000<JARUTAG>" + resMessages.getString("info_connection_nook") + " - " + vcNow);
                            }
                            //Set Objects to null
                            try {
                                voConv = null;
                                voTransf = null;
                                vaJson.clear();
                                vaJson = null;
                            }catch(Exception eNull) {
                                if (JClientMain.getoLog()!=null)
                                    JClientMain.getoLog().error(resMessages.getString("error_exception"), eNull);
                            }
                            //Delete the file if the flag didn't change to false due to connection timeouts
                            if (vbDeleteFile) {
                                Utils.deleteFile(vcFile);
                            }
                            //Update the status to inform that is ready to upload again
                            if (oStatus.getnStatusNext()!=ConnBackStatus.UPLOAD_OFF) {
                                oStatus.setnStatus(ConnBackStatus.UPLOAD_ON);
                                fireEvent();                        
                            }
                        }
                    }
                    //Waits 0.5 seconds to let the system breathe
                    Thread.sleep(Duration.ofMillis(500));
                }
                try {
                    voClient.close();
                    voClient.shutdownNow();
                    voClient = null;
                }catch (Exception eCloseConn) {
                    if (JClientMain.getoLog()!=null)
                        JClientMain.getoLog().error(resMessages.getString("error_exception"), eCloseConn);
                }
                return "done"; 
            } 
            // Method called when using "publish" in the doInBackground, to refresh the GUI with new data
            @Override protected void process(java.util.List plChunks) 
            { 
                String vcColor = "#000000";
                if (plChunks!=null) {
                    for (int i=0; i<plChunks.size(); i++) {
                        String vcValue = (String)plChunks.get(i);
                        String vaValues[] = vcValue.split("<JARUTAG>");
                        if (vaValues.length>1) {
                            vcColor = vaValues[0];
                            vcValue = vaValues[1];
                        } else if (vaValues.length>0) {
                            vcValue = vaValues[0];
                        }
                        txtStatus.setText(cStart + appendString(vcValue, vcColor) + cEnd);
                        if (JClientMain.getoLog()!=null)
                            JClientMain.getoLog().info(vcValue);
                    }
                }
            }   
            // Method called when doInBackground finishes
            @Override protected void done() 
            { 
                try { 
                    //Get the message from doInBackground. In this case, it`s a String
                    String vcMsg = (String)get(); 
                    if (!vcMsg.equals("")) {
                        //Fire the event
                        oStatus.setnStatus(ConnBackStatus.UPLOAD_OFF);
                        fireEvent();
                    }
                } 
                catch (InterruptedException e) { 
                    if (JClientMain.getoLog()!=null)
                        JClientMain.getoLog().error(resMessages.getString("error_exception"), e);
                } 
                catch (ExecutionException e) { 
                    if (JClientMain.getoLog()!=null)
                        JClientMain.getoLog().error(resMessages.getString("error_exception"), e);
                } 
            } 
        }; 
        // Executes the swingworker on worker thread 
        voSw.execute(); 
    }     
    
    /**
     * Adds a new line of text, formatted with some color, to the editor pane
     * @param pcLine String Text for a new line
     * @param pcColor String Hexadecimal value for the color
     * @return String New content for the editor pane
     */
    private String appendString (String pcLine, String pcColor) {
        String vcResul = "";
        String vcLine = "";
        try {
            vcLine = "<span style=\"color:" + pcColor + "\">" + pcLine + "</span><br>";
        }catch(Exception e) {
            vcLine = "";
            if (JClientMain.getoLog()!=null)
                JClientMain.getoLog().error(resMessages.getString("error_exception"), e);
        }
        vcResul = oSB.append(vcLine).toString() + "\n";
        return vcResul;
    }
    /**
     * Checks the selected type of extension and the value of the date and time value,
     * in case of CSV
     * @return boolean Flag to indicate if everything is ok
     */
    private boolean checkExtensionAndDate() {
        boolean vbResul = true;
        try {
            if (cExtension.toLowerCase().equals("csv")) {
                String vcStageDate = JClientMain.getcStageDate();
                Date voStageDate = Utils.parse(vcStageDate, resDates.getString("format_date"));
                String vcStageZeroTime = JClientMain.getcStageZeroTime();
                Date voStageZeroTime = Utils.parse(vcStageZeroTime, resDates.getString("format_time"));
                if (voStageDate==null || voStageZeroTime==null) {
                    JOptionPane.showMessageDialog(this , 
                            resMessages.getString("info_date_time_needed"), 
                            resMessages.getString("info"),
                            JOptionPane.ERROR_MESSAGE);
                    if (JClientMain.getoLog()!=null)
                        JClientMain.getoLog().warn(resMessages.getString("info_date_time_needed"));
                    vbResul = false;
                }
            }
        }catch(Exception e) {
            vbResul = false;
        }
        return vbResul;
    }
}
