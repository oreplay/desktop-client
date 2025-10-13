/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eu.oreplay.logic;

/**
 * Stores parameters for all of the forms in the application: size, position 
 * and default values
 * @author javier.arufe
 */
public class FormsParameters {
    private ParJClientMain oJClientMain;
    private ParJAbout oJAbout;
    private ParJTest oJTest;
    private ParConnBackCheckPanel oConnBackCheckPanel;
    private ParConnBackLoginPanel oConnBackLoginPanel;
    private ParConnBackUploadPanel oConnBackUploadPanel;

    public FormsParameters() {
        oJClientMain = new ParJClientMain();
        oJAbout = new ParJAbout();
        oJTest = new ParJTest();
        oConnBackCheckPanel = new ParConnBackCheckPanel();
        oConnBackLoginPanel = new ParConnBackLoginPanel();
        oConnBackUploadPanel = new ParConnBackUploadPanel();
    }

    public ParJClientMain getoJClientMain() {
        return oJClientMain;
    }

    public void setoJClientMain(ParJClientMain oJClientMain) {
        this.oJClientMain = oJClientMain;
    }

    public ParJAbout getoJAbout() {
        return oJAbout;
    }

    public void setoJAbout(ParJAbout oJAbout) {
        this.oJAbout = oJAbout;
    }

    public ParJTest getoJTest() {
        return oJTest;
    }

    public void setoJTest(ParJTest oJTest) {
        this.oJTest = oJTest;
    }

    public ParConnBackCheckPanel getoConnBackCheckPanel() {
        return oConnBackCheckPanel;
    }

    public void setoConnBackCheckPanel(ParConnBackCheckPanel oConnBackCheckPanel) {
        this.oConnBackCheckPanel = oConnBackCheckPanel;
    }

    public ParConnBackLoginPanel getoConnBackLoginPanel() {
        return oConnBackLoginPanel;
    }

    public void setoConnBackLoginPanel(ParConnBackLoginPanel oConnBackLoginPanel) {
        this.oConnBackLoginPanel = oConnBackLoginPanel;
    }

    public ParConnBackUploadPanel getoConnBackUploadPanel() {
        return oConnBackUploadPanel;
    }

    public void setoConnBackUploadPanel(ParConnBackUploadPanel oConnBackUploadPanel) {
        this.oConnBackUploadPanel = oConnBackUploadPanel;
    }
    
    /**
     * Internal class to store position and size of a form
     */
    public class ParPosition {
        private int nPosX;
        private int nPosY;
        private int nSizeX;
        private int nSizeY;
        /**
         * Constructor by default
         */
        public ParPosition() {
            this.nPosX = 100;
            this.nPosY = 100;
            this.nSizeX = 200;
            this.nSizeY = 200;
        }
        /**
         * Constructor with parameters
         * @param nPosX int Position X
         * @param nPosY int Position Y
         * @param nSizeX int Size X
         * @param nSizeY int Size Y
         */
        public ParPosition(int nPosX, int nPosY, int nSizeX, int nSizeY) {
            this.nPosX = nPosX;
            this.nPosY = nPosY;
            this.nSizeX = nSizeX;
            this.nSizeY = nSizeY;
        }
        /**
         * Returns the horizontal position of the form
         * @return int
         */
        public int getnPosX() {
            return nPosX;
        }
        /**
         * Sets the horizontal position of the form
         * @param nPosX int
         */
        public void setnPosX(int nPosX) {
            this.nPosX = nPosX;
        }
        /**
         * Returns the vertical position of the form
         * @return int
         */
        public int getnPosY() {
            return nPosY;
        }
        /**
         * Sets the vertical position of the form
         * @param nPosY int
         */
        public void setnPosY(int nPosY) {
            this.nPosY = nPosY;
        }
        /**
         * Returns the horizontal size of the form
         * @return int
         */
        public int getnSizeX() {
            return nSizeX;
        }
        /**
         * Sets the horizontal size of the form
         * @param nSizeX int
         */
        public void setnSizeX(int nSizeX) {
            this.nSizeX = nSizeX;
        }
        /**
         * Returns the vertical size of the form
         * @return int
         */
        public int getnSizeY() {
            return nSizeY;
        }
        /**
         * Sets the vertical size of the form
         * @param nSizeY int
         */
        public void setnSizeY(int nSizeY) {
            this.nSizeY = nSizeY;
        }
    }
    /**
     * Internal class to manage the parameters of a basic form,
     * only position and size
     */
    public class ParBase {
        protected ParPosition oPos;
        /**
         * Constructor by default
         */
        public ParBase() {
            oPos = new ParPosition (100, 100, 566, 282);
        }
        /**
         * Constructor with parameters
         * @param oPos ParPosition
         */
        public ParBase(ParPosition oPos) {
            this.oPos = oPos;
        }
        /**
         * Returns the position and size of the form
         * @return ParPosition
         */
        public ParPosition getoPos() {
            return oPos;
        }
        /**
         * Sets the position and size of the form
         * @param oPos ParPosition
         */
        public void setoPos(ParPosition oPos) {
            this.oPos = oPos;
        }        
    }
    

    /**
     * Internal class that manages the parameters of the main form, JClientMain
     */
    public class ParJClientMain extends ParBase{
        private String cStageDate = ""; //Uses yyyy-MM-dd format
        private String cStageZeroTime = ""; //Uses HH:mm:ss format
        private String cLang = "";
        /**
         * Constructor by default
         */
        public ParJClientMain() {
            oPos = new ParPosition (200, 200, 785, 590);
        }
        /**
         * Constructor with parameters
         * @param oPos ParPosition
         */
        public ParJClientMain(ParPosition oPos) {
            this.oPos = oPos;
        }
        /**
         * Constructor with parameters
         * @param oPos ParPosition
         * @param pcDate String Stage Date (yyyy-MM-dd)
         * @param pcTime String Stage Zero Time (HH:mm:ss)
         */
        public ParJClientMain(ParPosition oPos, String pcDate, String pcTime) {
            this.oPos = oPos;
            this.cStageDate = pcDate;
            this.cStageZeroTime = pcTime;
        }
        /**
         * Constructor with parameters
         * @param oPos ParPosition
         * @param pcDate String Stage Date (yyyy-MM-dd)
         * @param pcTime String Stage Zero Time (HH:mm:ss)
         * @param pcLang String Language
         */
        public ParJClientMain(ParPosition oPos, String pcDate, String pcTime, String pcLang) {
            this.oPos = oPos;
            this.cStageDate = pcDate;
            this.cStageZeroTime = pcTime;
            this.cLang = pcLang;
        }
        
        public String getcStageDate() {
            return cStageDate;
        }
        public void setcStageDate(String cStageDate) {
            this.cStageDate = cStageDate;
        }
        public String getcStageZeroTime() {
            return cStageZeroTime;
        }
        public void setcStageZeroTime(String cStageZeroTime) {
            this.cStageZeroTime = cStageZeroTime;
        }
        public String getcLang() {
            return cLang;
        }
        public void setcLang(String cLang) {
            this.cLang = cLang;
        }
    }
    /**
     * Internal class that manages the parameters of the JTest
     */
    public class ParJTest extends ParBase{
        private String cPath = "." + java.io.File.separator;
        /**
         * Constructor by default
         */
        public ParJTest() {
            oPos = new ParPosition (200, 200, 800, 500);
        }
        /**
         * Constructor with parameters
         * @param oPos ParPosition
         */
        public ParJTest(ParPosition oPos) {
            this.oPos = oPos;
        }
        /**
         * Constructor with parameters
         * @param oPos ParPosition
         * @param pcPath String Additional data
         */
        public ParJTest(ParPosition oPos, String pcPath) {
            this.oPos = oPos;
            cPath = pcPath;
        }

        public String getcPath() {
            return cPath;
        }
        public void setcPath(String cPath) {
            this.cPath = cPath;
        }
    }
    /**
     * Internal class that manages the parameters of the JAbout
     */
    public class ParJAbout extends ParBase{
        private String cVersion = "";
        /**
         * Constructor by default
         */
        public ParJAbout() {
            oPos = new ParPosition (200, 200, 700, 450);
            cVersion = "";
        }
        /**
         * Constructor with parameters
         * @param oPos ParPosition
         */
        public ParJAbout(ParPosition oPos) {
            this.oPos = oPos;
            cVersion = "";
        }
        /**
         * Constructor with parameters
         * @param oPos ParPosition
         * @param pcVersion String Additional data
         */
        public ParJAbout(ParPosition oPos, String pcVersion) {
            this.oPos = oPos;
            cVersion = pcVersion;
        }

        public String getcVersion() {
            return cVersion;
        }
        public void setcVersion(String cVersion) {
            this.cVersion = cVersion;
        }
    }
    /**
     * Internal class that manages the parameters of the ConnBackCheckPanel panel
     */
    public class ParConnBackCheckPanel extends ParBase{
        private java.util.List<String> lServers = new java.util.ArrayList<String>();
        /**
         * Constructor by default
         */
        public ParConnBackCheckPanel() {
            oPos = new ParPosition (200, 200, 450, 200);
            lServers.add("http://127.0.0.1");
            lServers.add("https://www.oreplay.es");
        }
        /**
         * Constructor with parameters
         * @param oPos ParPosition
         */
        public ParConnBackCheckPanel(ParPosition oPos) {
            this.oPos = oPos;
            lServers.add("http://127.0.0.1");
            lServers.add("https://www.oreplay.es");
        }
        /**
         * Constructor with parameters
         * @param oPos ParPosition
         * @param plServers java.util.List<String> List of servers
         */
        public ParConnBackCheckPanel(ParPosition oPos, java.util.List<String> plServers) {
            this.oPos = oPos;
            lServers = plServers;
        }

        public java.util.List<String> getlServers() {
            return lServers;
        }
        public void setlServers(java.util.List<String> plServers) {
            this.lServers = plServers;
        }
    }
    /**
     * Internal class that manages the parameters of the ConnBackLoginPanel panel
     */
    public class ParConnBackLoginPanel extends ParBase{
        private String cEveId = "";
        private String cToken = "";
        private String cIdToken = "";
        private String cStaId = "";
        private String cEveDesc = "";
        private String cStaDesc = "";
        /**
         * Constructor by default
         */
        public ParConnBackLoginPanel() {
            oPos = new ParPosition (200, 200, 450, 200);
            cEveId = "";
            cToken = "";
            cIdToken = "";
            cStaId = "";
            cEveDesc = "";
            cStaDesc = "";
        }
        /**
         * Constructor with parameters
         * @param oPos ParPosition
         */
        public ParConnBackLoginPanel(ParPosition oPos) {
            this.oPos = oPos;
            cEveId = "";
            cToken = "";
            cIdToken = "";
            cStaId = "";
            cEveDesc = "";
            cStaDesc = "";
        }
        /**
         * Constructor with parameters
         * @param oPos ParPosition
         * @param pcEveId String Event's id
         * @param pcToken String Event's secret
         * @param pcStaId String Stage's id
         */
        public ParConnBackLoginPanel(ParPosition oPos, String pcEveId, String pcToken, String pcStaId) {
            this.oPos = oPos;
            cEveId = pcEveId;
            cToken = pcToken;
            cIdToken = "";
            cStaId = pcStaId;
            cEveDesc = "";
            cStaDesc = "";
        }
        /**
         * Constructor with parameters
         * @param oPos ParPosition
         * @param pcEveId String Event's id
         * @param pcToken String Event's secret
         * @param pcStaId String Stage's id
         * @param pcEveDesc String Event's description
         * @param pcStaDesc String Stage's description
         */
        public ParConnBackLoginPanel(ParPosition oPos, String pcEveId, String pcToken, 
                String pcStaId, String pcEveDesc, String pcStaDesc) {
            this.oPos = oPos;
            cEveId = pcEveId;
            cToken = pcToken;
            cIdToken = "";
            cStaId = pcStaId;
            cEveDesc = pcEveDesc;
            cStaDesc = pcStaDesc;
        }
        /**
         * Constructor with parameters
         * @param oPos ParPosition
         * @param pcEveId String Event's id
         * @param pcToken String Event's secret
         * @param pcIdToken String Event's Id + Token
         * @param pcStaId String Stage's id
         * @param pcEveDesc String Event's description
         * @param pcStaDesc String Stage's description
         */
        public ParConnBackLoginPanel(ParPosition oPos, String pcEveId, String pcToken, String pcIdToken,
                String pcStaId, String pcEveDesc, String pcStaDesc) {
            this.oPos = oPos;
            cEveId = pcEveId;
            cToken = pcToken;
            cIdToken = pcIdToken;
            cStaId = pcStaId;
            cEveDesc = pcEveDesc;
            cStaDesc = pcStaDesc;
        }

        public String getcEveId() {
            return cEveId;
        }

        public void setcEveId(String cEveId) {
            this.cEveId = cEveId;
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

        
    }
    /**
     * Internal class that manages the parameters of the ConnBackUploadPanel panel
     */
    public class ParConnBackUploadPanel extends ParBase{
        private String cFolder = "";
        private String cExtension = "";
        private boolean bSplit = false;
        /**
         * Constructor by default
         */
        public ParConnBackUploadPanel() {
            oPos = new ParPosition (200, 200, 300, 200);
            cFolder = "";
            cExtension = "";
            bSplit = false;
        }
        /**
         * Constructor with parameters
         * @param oPos ParPosition
         */
        public ParConnBackUploadPanel(ParPosition oPos) {
            this.oPos = oPos;
            cFolder = "";
            cExtension = "";
            bSplit = false;
        }
        /**
         * Constructor with parameters
         * @param oPos ParPosition
         * @param pcFolder String Folder to search files
         * @param pcExtension String File extension to search
         */
        public ParConnBackUploadPanel(ParPosition oPos, String pcFolder, String pcExtension) {
            this.oPos = oPos;
            cFolder = pcFolder;
            cExtension = pcExtension;
            bSplit = false;
        }
        /**
         * Constructor with parameters
         * @param oPos ParPosition
         * @param pcFolder String Folder to search files
         * @param pcExtension String File extension to search
         * @param pbSplit boolean Flag to separate data and perform uploads by classes
         */
        public ParConnBackUploadPanel(ParPosition oPos, String pcFolder, String pcExtension, boolean pbSplit) {
            this.oPos = oPos;
            cFolder = pcFolder;
            cExtension = pcExtension;
            bSplit = pbSplit;
        }

        public String getcFolder() {
            return cFolder;
        }

        public void setcFolder(String cFolder) {
            this.cFolder = cFolder;
        }

        public String getcExtension() {
            return cExtension;
        }

        public void setcExtension(String cExtension) {
            this.cExtension = cExtension;
        }

        public boolean isbSplit() {
            return bSplit;
        }

        public void setbSplit(boolean bSplit) {
            this.bSplit = bSplit;
        }
        
    }
    
}
