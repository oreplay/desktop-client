/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eu.oreplay.logic.xml;

import org.xml.sax.*;
import org.xml.sax.helpers.*;
import javax.xml.parsers.*;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.List;
import java.util.ArrayList;

import eu.oreplay.logic.*;
import java.nio.charset.StandardCharsets;

/**
 * SAX2 manager to transform an XML file
 * Data are transformed and incorporated into the structure of this app
 * 
 * @author javier.arufe
 */
public class FormsParametersXMLHandler extends DefaultHandler {
    private FormsParameters oRec = new FormsParameters();
    private int nType = 0;
    /** Buffer for data read */
    protected StringBuffer vcBuffer = new StringBuffer();
    private List<String> lServers;

    public FormsParameters getoRec() {
        return oRec;
    }
    public void setoRec(FormsParameters poRec) {
        this.oRec = poRec;
    }
    public void startElement(String uri, String lname, String qname,
            Attributes attributes) {
        vcBuffer.setLength(0);
        if (lname.toLowerCase().equals("formsparameters")) {
            oRec = new FormsParameters();
            nType = 0;
        } else if (lname.toLowerCase().equals("jclientmain")) {
            nType = 1;
        } else if (lname.toLowerCase().equals("jabout")) {
            nType = 2;
        } else if (lname.toLowerCase().equals("jtest")) {
            nType = 3;
        } else if (lname.toLowerCase().equals("connbackcheckpanel")) {
            nType = 4;
        } else if (lname.toLowerCase().equals("connbackloginpanel")) {
            nType = 5;
        } else if (lname.toLowerCase().equals("connbackuploadpanel")) {
            nType = 6;
        } else if (lname.toLowerCase().equals("servers")) {
            if (nType==4) {
                lServers = new ArrayList<String>();
            }
        }
    }

    public void characters(char[] chars, int start, int length) {
            vcBuffer.append(chars, start, length);
    }

    public void endElement(String uri, String lname, String qname) {
        if (lname.toLowerCase().equals("formsparameters")) {
        } else {
            if (oRec!=null) {
                String content = vcBuffer.toString().trim();
                if (lname.toLowerCase().equals("nposx")) {
                    try {
                        switch (nType) {
                            case 1:
                                oRec.getoJClientMain().getoPos().setnPosX(Integer.parseInt(content));
                                break;
                            case 2:
                                oRec.getoJAbout().getoPos().setnPosX(Integer.parseInt(content));
                                break;
                            case 3:
                                oRec.getoJTest().getoPos().setnPosX(Integer.parseInt(content));
                                break;
                        }
                    }catch(Exception e1) {}
                } else if (lname.toLowerCase().equals("nposy")) {
                    try {
                        switch (nType) {
                            case 1:
                                oRec.getoJClientMain().getoPos().setnPosY(Integer.parseInt(content));
                                break;
                            case 2:
                                oRec.getoJAbout().getoPos().setnPosY(Integer.parseInt(content));
                                break;
                            case 3:
                                oRec.getoJTest().getoPos().setnPosY(Integer.parseInt(content));
                                break;
                        }
                    }catch(Exception e2) {}
                } else if (lname.toLowerCase().equals("nsizex")) {
                    try {
                        switch (nType) {
                            case 1:
                                oRec.getoJClientMain().getoPos().setnSizeX(Integer.parseInt(content));
                                break;
                            case 2:
                                oRec.getoJAbout().getoPos().setnSizeX(Integer.parseInt(content));
                                break;
                            case 3:
                                oRec.getoJTest().getoPos().setnSizeX(Integer.parseInt(content));
                                break;
                        }
                    }catch(Exception e3) {}
                } else if (lname.toLowerCase().equals("nsizey")) {
                    try {
                        switch (nType) {
                            case 1:
                                oRec.getoJClientMain().getoPos().setnSizeY(Integer.parseInt(content));
                                break;
                            case 2:
                                oRec.getoJAbout().getoPos().setnSizeY(Integer.parseInt(content));
                                break;
                            case 3:
                                oRec.getoJTest().getoPos().setnSizeY(Integer.parseInt(content));
                                break;
                        }
                    }catch(Exception e4) {}
                } else if (lname.toLowerCase().equals("cversion")) {
                    try {
                        switch (nType) {
                            case 2:
                                oRec.getoJAbout().setcVersion(content);
                                break;
                        }
                    }catch(Exception e5) {}
                } else if (lname.toLowerCase().equals("cpath")) {
                    try {
                        switch (nType) {
                            case 3:
                                oRec.getoJTest().setcPath(content);
                                break;
                        }
                    }catch(Exception e6) {}
                } else if (lname.toLowerCase().equals("servers")) {
                    try {
                        switch (nType) {
                            case 4:
                                oRec.getoConnBackCheckPanel().setlServers(lServers);
                                break;
                        }
                    }catch(Exception e7) {}
                } else if (lname.toLowerCase().equals("server")) {
                    try {
                        switch (nType) {
                            case 4:
                                lServers.add(content);
                                break;
                        }
                    }catch(Exception e8) {}
                } else if (lname.toLowerCase().equals("ceveid")) {
                    try {
                        switch (nType) {
                            case 5:
                                oRec.getoConnBackLoginPanel().setcEveId(content);
                                break;
                        }
                    }catch(Exception e9) {}
                } else if (lname.toLowerCase().equals("ctoken")) {
                    try {
                        switch (nType) {
                            case 5:
                                oRec.getoConnBackLoginPanel().setcToken(content);
                                break;
                        }
                    }catch(Exception e10) {}
                } else if (lname.toLowerCase().equals("cidtoken")) {
                    try {
                        switch (nType) {
                            case 5:
                                oRec.getoConnBackLoginPanel().setcIdToken(content);
                                break;
                        }
                    }catch(Exception e10) {}
                } else if (lname.toLowerCase().equals("cstaid")) {
                    try {
                        switch (nType) {
                            case 5:
                                oRec.getoConnBackLoginPanel().setcStaId(content);
                                break;
                        }
                    }catch(Exception e11) {}
                } else if (lname.toLowerCase().equals("cevedesc")) {
                    try {
                        switch (nType) {
                            case 5:
                                oRec.getoConnBackLoginPanel().setcEveDesc(content);
                                break;
                        }
                    }catch(Exception e9) {}
                } else if (lname.toLowerCase().equals("cstadesc")) {
                    try {
                        switch (nType) {
                            case 5:
                                oRec.getoConnBackLoginPanel().setcStaDesc(content);
                                break;
                        }
                    }catch(Exception e9) {}
                } else if (lname.toLowerCase().equals("cfolder")) {
                    try {
                        switch (nType) {
                            case 6:
                                oRec.getoConnBackUploadPanel().setcFolder(content);
                                break;
                        }
                    }catch(Exception e12) {}
                } else if (lname.toLowerCase().equals("cextension")) {
                    try {
                        switch (nType) {
                            case 6:
                                oRec.getoConnBackUploadPanel().setcExtension(content);
                                break;
                        }
                    }catch(Exception e13) {}
                } else if (lname.toLowerCase().equals("bsplit")) {
                    try {
                        switch (nType) {
                            case 6:
                                if (content.toUpperCase().equals("T") || content.toUpperCase().equals("V") ||
                                        content.toUpperCase().equals("1") || content.toUpperCase().equals("S"))
                                    oRec.getoConnBackUploadPanel().setbSplit(true);
                                else
                                    oRec.getoConnBackUploadPanel().setbSplit(false);
                                break;
                        }
                    }catch(Exception e14) {}
                } else if (lname.toLowerCase().equals("cstagedate")) {
                    try {
                        switch (nType) {
                            case 1:
                                oRec.getoJClientMain().setcStageDate(content);
                                break;
                        }
                    }catch(Exception e15) {}
                } else if (lname.toLowerCase().equals("cstagezerotime")) {
                    try {
                        switch (nType) {
                            case 1:
                                oRec.getoJClientMain().setcStageZeroTime(content);
                                break;
                        }
                    }catch(Exception e16) {}
                }
            }
        }
    }

    /**
     * Method that is in charge of recuperating data from the XML file; 
     * Data is inserted in an object of FormsParameters;
     * 
     * @param pcFile String. Complete Path of the XML file
     * @return FormsParameters. Element of the class containing the data from the XML file
     */
    public static FormsParameters getXmlData(String pcFile) {
        FormsParameters voResul = new FormsParameters();
        try {
            SAXParserFactory voFactory = SAXParserFactory.newInstance();
            voFactory.setValidating(false);
            voFactory.setNamespaceAware(true);
            // parse the document
            SAXParser parser = voFactory.newSAXParser();                        
            FormsParametersXMLHandler voHandler = new FormsParametersXMLHandler();
            InputSource voSrc = new InputSource(new FileReader(pcFile));
            // explicitly set a encoding
            voSrc.setEncoding(StandardCharsets.UTF_8.displayName());
            parser.parse(voSrc, (DefaultHandler)voHandler);
            //After working with the file, data is in the form of a unique object
            voResul = voHandler.getoRec();
        }
        catch (Exception e) {
            voResul = new FormsParameters();
        }
        return voResul;
    }
    /**
     * Sends the data to an XML file, whose name is in the second argument
     * @param poRec FormsParameters Object of the class
     * @param pcFile String Path+name of the XML file
     */
    public static void writeXmlData (FormsParameters poRec, String pcFile) {
            PrintStream voStream = null;		
            try {
                //Gets an object that represents the file that is about to write
                voStream = new PrintStream(new BufferedOutputStream(new FileOutputStream(pcFile)), true, "UTF-8");
                //Starts writing the XML header
                voStream.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                voStream.println("<!--<!DOCTYPE FormsParameters SYSTEM \"FormsParameters.dtd\">-->");
                voStream.println("<FormsParameters>");
                //Form JClientMain
                voStream.println("  <JClientMain>");
                voStream.println("    <nPosX>" + poRec.getoJClientMain().getoPos().getnPosX()+ "</nPosX>");
                voStream.println("    <nPosY>" + poRec.getoJClientMain().getoPos().getnPosY()+ "</nPosY>");
                voStream.println("    <nSizeX>" + poRec.getoJClientMain().getoPos().getnSizeX()+ "</nSizeX>");
                voStream.println("    <nSizeY>" + poRec.getoJClientMain().getoPos().getnSizeY()+ "</nSizeY>");
                voStream.println("    <cStageDate>" + poRec.getoJClientMain().getcStageDate()+ "</cStageDate>");
                voStream.println("    <cStageZeroTime>" + poRec.getoJClientMain().getcStageZeroTime()+ "</cStageZeroTime>");
                voStream.println("  </JClientMain>");
                //Form JAbout
                voStream.println("  <JAbout>");
                voStream.println("    <nPosX>" + poRec.getoJAbout().getoPos().getnPosX()+ "</nPosX>");
                voStream.println("    <nPosY>" + poRec.getoJAbout().getoPos().getnPosY()+ "</nPosY>");
                voStream.println("    <nSizeX>" + poRec.getoJAbout().getoPos().getnSizeX()+ "</nSizeX>");
                voStream.println("    <nSizeY>" + poRec.getoJAbout().getoPos().getnSizeY()+ "</nSizeY>");
                voStream.println("    <cVersion>" + poRec.getoJAbout().getcVersion()+ "</cVersion>");
                voStream.println("  </JAbout>");
                //Form JTest
                voStream.println("  <JTest>");
                voStream.println("    <nPosX>" + poRec.getoJTest().getoPos().getnPosX()+ "</nPosX>");
                voStream.println("    <nPosY>" + poRec.getoJTest().getoPos().getnPosY()+ "</nPosY>");
                voStream.println("    <nSizeX>" + poRec.getoJTest().getoPos().getnSizeX()+ "</nSizeX>");
                voStream.println("    <nSizeY>" + poRec.getoJTest().getoPos().getnSizeY()+ "</nSizeY>");
                voStream.println("    <cPath>" + poRec.getoJTest().getcPath()+ "</cPath>");
                voStream.println("  </JTest>");
                //Panel ConnBackCheckPanel
                voStream.println("  <ConnBackCheckPanel>");
                voStream.println("    <nPosX>" + poRec.getoConnBackCheckPanel().getoPos().getnPosX()+ "</nPosX>");
                voStream.println("    <nPosY>" + poRec.getoConnBackCheckPanel().getoPos().getnPosY()+ "</nPosY>");
                voStream.println("    <nSizeX>" + poRec.getoConnBackCheckPanel().getoPos().getnSizeX()+ "</nSizeX>");
                voStream.println("    <nSizeY>" + poRec.getoConnBackCheckPanel().getoPos().getnSizeY()+ "</nSizeY>");
                if (poRec.getoConnBackCheckPanel().getlServers()!=null) {
                    voStream.println("    <servers>");
                    for (String vcServer : poRec.getoConnBackCheckPanel().getlServers()) {
                        voStream.println("      <server>" + vcServer + "</server>");
                    }
                    voStream.println("    </servers>");
                }
                voStream.println("  </ConnBackCheckPanel>");
                //Panel ConnBackLoginPanel
                voStream.println("  <ConnBackLoginPanel>");
                voStream.println("    <nPosX>" + poRec.getoConnBackLoginPanel().getoPos().getnPosX()+ "</nPosX>");
                voStream.println("    <nPosY>" + poRec.getoConnBackLoginPanel().getoPos().getnPosY()+ "</nPosY>");
                voStream.println("    <nSizeX>" + poRec.getoConnBackLoginPanel().getoPos().getnSizeX()+ "</nSizeX>");
                voStream.println("    <nSizeY>" + poRec.getoConnBackLoginPanel().getoPos().getnSizeY()+ "</nSizeY>");
                voStream.println("    <cEveId>" + poRec.getoConnBackLoginPanel().getcEveId()+ "</cEveId>");
                voStream.println("    <cToken>" + poRec.getoConnBackLoginPanel().getcToken()+ "</cToken>");
                voStream.println("    <cIdToken>" + poRec.getoConnBackLoginPanel().getcIdToken()+ "</cIdToken>");
                voStream.println("    <cStaId>" + poRec.getoConnBackLoginPanel().getcStaId()+ "</cStaId>");
                voStream.println("    <cEveDesc>" + poRec.getoConnBackLoginPanel().getcEveDesc()+ "</cEveDesc>");
                voStream.println("    <cStaDesc>" + poRec.getoConnBackLoginPanel().getcStaDesc()+ "</cStaDesc>");
                voStream.println("  </ConnBackLoginPanel>");
                //Panel ConnBackUploadPanel
                voStream.println("  <ConnBackUploadPanel>");
                voStream.println("    <nPosX>" + poRec.getoConnBackUploadPanel().getoPos().getnPosX()+ "</nPosX>");
                voStream.println("    <nPosY>" + poRec.getoConnBackUploadPanel().getoPos().getnPosY()+ "</nPosY>");
                voStream.println("    <nSizeX>" + poRec.getoConnBackUploadPanel().getoPos().getnSizeX()+ "</nSizeX>");
                voStream.println("    <nSizeY>" + poRec.getoConnBackUploadPanel().getoPos().getnSizeY()+ "</nSizeY>");
                voStream.println("    <cFolder>" + poRec.getoConnBackUploadPanel().getcFolder()+ "</cFolder>");
                voStream.println("    <cExtension>" + poRec.getoConnBackUploadPanel().getcExtension()+ "</cExtension>");
                voStream.println("    <bSplit>" + (poRec.getoConnBackUploadPanel().isbSplit()?"T":"F")+ "</bSplit>");
                voStream.println("  </ConnBackUploadPanel>");
                //End of the main tag
                voStream.println("</FormsParameters>");
                voStream.close();
            } catch (Exception e) {
                    if (voStream!=null)
                            voStream.close();
            }
    }
    
}
