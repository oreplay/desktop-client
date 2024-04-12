/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eu.oreplay.gui.events;

/**
 * Class that holds the status and values managed by the connection panels
 * @author javier.arufe
 */
public class ConnBackStatus implements java.io.Serializable {
    public static final int LOGIN_NOOK = -2;
    public static final int CONNECTION_NOOK = -1;
    public static final int DISCONNECTED = 0;
    public static final int CONNECTION_OK = 1;
    public static final int LOGIN_OK = 2;
    public static final int STAGE_SELECTED = 3;
    public static final int UPLOAD_OFF = 4;
    public static final int UPLOAD_ON = 5;    
    
    private int nStatus = DISCONNECTED;
    private String cServer = "";
    private String cEveId = "";
    private String cStaId = "";
    private String cEveDesc = "";
    private String cStaDesc = "";
    private String cToken = "";

    public ConnBackStatus() {
        nStatus = DISCONNECTED;
        cServer = "";
        cEveId = "";
        cStaId = "";
        cEveDesc = "";
        cStaDesc = "";
        cToken = "";
    }
    public ConnBackStatus(int pnStatus) {
        nStatus = pnStatus;
        cEveId = "";
        cStaId = "";
        cEveDesc = "";
        cStaDesc = "";
        cToken = "";
    }
    public ConnBackStatus(int pnStatus, String pcServer) {
        nStatus = pnStatus;
        cServer = pcServer;
        cEveId = "";
        cStaId = "";
        cEveDesc = "";
        cStaDesc = "";
        cToken = "";
    }
    public ConnBackStatus(int pnStatus, String pcServer, String pcEveId, String pcStaId) {
        nStatus = pnStatus;
        cServer = pcServer;
        cEveId = pcEveId;
        cStaId = pcStaId;
        cEveDesc = "";
        cStaDesc = "";
        cToken = "";
    }
    public ConnBackStatus(int pnStatus, String pcServer, String pcEveId, String pcStaId, String pcToken) {
        nStatus = pnStatus;
        cServer = pcServer;
        cEveId = pcEveId;
        cStaId = pcStaId;
        cEveDesc = "";
        cStaDesc = "";
        cToken = pcToken;
    }
    public ConnBackStatus(int pnStatus, String pcServer, String pcEveId, 
            String pcStaId, String pcEveDesc, String pcStaDesc) {
        nStatus = pnStatus;
        cServer = pcServer;
        cEveId = pcEveId;
        cStaId = pcStaId;
        cEveDesc = pcEveDesc;
        cStaDesc = pcStaDesc;
        cToken = "";
    }
    public ConnBackStatus(int pnStatus, String pcServer, String pcEveId, 
            String pcStaId, String pcEveDesc, String pcStaDesc, String pcToken) {
        nStatus = pnStatus;
        cServer = pcServer;
        cEveId = pcEveId;
        cStaId = pcStaId;
        cEveDesc = pcEveDesc;
        cStaDesc = pcStaDesc;
        cToken = pcToken;
    }
    

    public int getnStatus() {
        return nStatus;
    }

    public void setnStatus(int nStatus) {
        this.nStatus = nStatus;
    }

    public String getcServer() {
        return cServer;
    }

    public void setcServer(String cServer) {
        this.cServer = cServer;
    }

    public String getcEveId() {
        return cEveId;
    }

    public void setcEveId(String cEveId) {
        this.cEveId = cEveId;
    }

    public String getcStaId() {
        return cStaId;
    }

    public void setcStaId(String cStaId) {
        this.cStaId = cStaId;
    }

    public String getcEveDesc() {
        return cEveDesc;
    }

    public void setcEveDesc(String cEveDesc) {
        this.cEveDesc = cEveDesc;
    }

    public String getcStaDesc() {
        return cStaDesc;
    }

    public void setcStaDesc(String cStaDesc) {
        this.cStaDesc = cStaDesc;
    }

    public String getcToken() {
        return cToken;
    }

    public void setcToken(String cToken) {
        this.cToken = cToken;
    }

    /**
     * The status of the connection with the Backend is ready to make a transfer
     * when there is a server, an event id, a stage id and a token
     * @return 
     */
    public boolean isReadyToSend() {
        boolean vbResul = false;
        if (!cServer.equals("") && !cEveId.equals("") && !cStaId.equals("") && 
                !cToken.equals("")) {
            vbResul = true;
        }
        return vbResul;
    }
    
    @Override
    public String toString() {
        return "ConnBackStatus{" + "nStatus=" + nStatus + ", cServer=" + cServer + ", cEveId=" + cEveId + ", cStaId=" + cStaId + ", cEveDesc=" + cEveDesc + ", cStaDesc=" + cStaDesc + ", cToken=" + cToken + '}';
    }
    
    
}
