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
    public static final int NO_STATUS = -999;
    public static final int LOGIN_NOOK = -2;
    public static final int CONNECTION_NOOK = -1;
    public static final int DISCONNECTED = 0;
    public static final int CONNECTION_OK = 1;
    public static final int LOGIN_OK = 2;
    public static final int STAGE_SELECTED = 3;
    public static final int UPLOAD_OFF = 4;
    public static final int UPLOAD_ON = 5;    
    public static final int EXT_CSV = 6;
    public static final int EXT_XML = 7;
    public static final int PASTE_IDTOKEN = 8;
    public static final int UPLOADING = 9;    
    
    private int nStatus = DISCONNECTED; //Current status
    private int nStatusOld = NO_STATUS; //Stores previous status before changing some things
    private int nStatusNext = NO_STATUS;//Stores next requested status if something is still performing which prevents status to change
    private String cServer = "";
    private String cEveId = "";
    private String cStaId = "";
    private String cEveDesc = "";
    private String cStaDesc = "";
    private String cToken = "";
    private String cIdToken = "";

    public ConnBackStatus() {
        nStatus = DISCONNECTED;
        nStatusOld = NO_STATUS;
        nStatusNext = NO_STATUS;
        cServer = "";
        cEveId = "";
        cStaId = "";
        cEveDesc = "";
        cStaDesc = "";
        cToken = "";
        cIdToken = "";
    }
    public ConnBackStatus(int pnStatus) {
        nStatus = pnStatus;
        nStatusOld = NO_STATUS;
        nStatusNext = NO_STATUS;
        cEveId = "";
        cStaId = "";
        cEveDesc = "";
        cStaDesc = "";
        cToken = "";
        cIdToken = "";
    }
    public ConnBackStatus(int pnStatus, String pcServer) {
        nStatus = pnStatus;
        nStatusOld = NO_STATUS;
        nStatusNext = NO_STATUS;
        cServer = pcServer;
        cEveId = "";
        cStaId = "";
        cEveDesc = "";
        cStaDesc = "";
        cToken = "";
        cIdToken = "";
    }
    public ConnBackStatus(int pnStatus, String pcServer, String pcEveId, String pcStaId) {
        nStatus = pnStatus;
        nStatusOld = NO_STATUS;
        nStatusNext = NO_STATUS;
        cServer = pcServer;
        cEveId = pcEveId;
        cStaId = pcStaId;
        cEveDesc = "";
        cStaDesc = "";
        cToken = "";
        cIdToken = "";
    }
    public ConnBackStatus(int pnStatus, String pcServer, String pcEveId, String pcStaId, String pcToken) {
        nStatus = pnStatus;
        nStatusOld = NO_STATUS;
        nStatusNext = NO_STATUS;
        cServer = pcServer;
        cEveId = pcEveId;
        cStaId = pcStaId;
        cEveDesc = "";
        cStaDesc = "";
        cToken = pcToken;
        cIdToken = "";
    }
    public ConnBackStatus(int pnStatus, String pcServer, String pcEveId, 
            String pcStaId, String pcEveDesc, String pcStaDesc) {
        nStatus = pnStatus;
        nStatusOld = NO_STATUS;
        nStatusNext = NO_STATUS;
        cServer = pcServer;
        cEveId = pcEveId;
        cStaId = pcStaId;
        cEveDesc = pcEveDesc;
        cStaDesc = pcStaDesc;
        cToken = "";
        cIdToken = "";
    }
    public ConnBackStatus(int pnStatus, String pcServer, String pcEveId, 
            String pcStaId, String pcEveDesc, String pcStaDesc, String pcToken) {
        nStatus = pnStatus;
        nStatusOld = NO_STATUS;
        nStatusNext = NO_STATUS;
        cServer = pcServer;
        cEveId = pcEveId;
        cStaId = pcStaId;
        cEveDesc = pcEveDesc;
        cStaDesc = pcStaDesc;
        cToken = pcToken;
        cIdToken = "";
    }
    public ConnBackStatus(int pnStatus, String pcServer, String pcEveId, 
            String pcStaId, String pcEveDesc, String pcStaDesc, String pcToken,
            String pcIdToken) {
        nStatus = pnStatus;
        nStatusOld = NO_STATUS;
        nStatusNext = NO_STATUS;
        cServer = pcServer;
        cEveId = pcEveId;
        cStaId = pcStaId;
        cEveDesc = pcEveDesc;
        cStaDesc = pcStaDesc;
        cToken = pcToken;
        cIdToken = pcIdToken;
    }
    

    public int getnStatus() {
        return nStatus;
    }

    public void setnStatus(int nStatus) {
        this.nStatus = nStatus;
    }

    public int getnStatusOld() {
        return nStatusOld;
    }

    public void setnStatusOld(int nStatusOld) {
        this.nStatusOld = nStatusOld;
    }

    public int getnStatusNext() {
        return nStatusNext;
    }

    public void setnStatusNext(int nStatusNext) {
        this.nStatusNext = nStatusNext;
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

    public String getcIdToken() {
        return cIdToken;
    }

    public void setcIdToken(String cIdToken) {
        this.cIdToken = cIdToken;
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
