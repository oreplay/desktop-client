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

    public FormsParameters() {
        oJClientMain = new ParJClientMain();
        oJAbout = new ParJAbout();
        oJTest = new ParJTest();
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
        /**
         * Constructor by default
         */
        public ParJClientMain() {
            oPos = new ParPosition (200, 200, 650, 400);
        }
        /**
         * Constructor with parameters
         * @param oPos ParPosition
         */
        public ParJClientMain(ParPosition oPos) {
            this.oPos = oPos;
        }
    }
    /**
     * Internal class that manages the parameters of the JTest
     */
    public class ParJTest extends ParBase{
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
    
}
