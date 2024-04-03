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
                    }catch(Exception e4) {}
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
                voStream.println("  </JClientMain>");
                //Pantalla JAbout
                voStream.println("  <JAbout>");
                voStream.println("    <nPosX>" + poRec.getoJAbout().getoPos().getnPosX()+ "</nPosX>");
                voStream.println("    <nPosY>" + poRec.getoJAbout().getoPos().getnPosY()+ "</nPosY>");
                voStream.println("    <nSizeX>" + poRec.getoJAbout().getoPos().getnSizeX()+ "</nSizeX>");
                voStream.println("    <nSizeY>" + poRec.getoJAbout().getoPos().getnSizeY()+ "</nSizeY>");
                voStream.println("    <cVersion>" + poRec.getoJAbout().getcVersion()+ "</cVersion>");
                voStream.println("  </JAbout>");
                //Pantalla JTest
                voStream.println("  <JTest>");
                voStream.println("    <nPosX>" + poRec.getoJTest().getoPos().getnPosX()+ "</nPosX>");
                voStream.println("    <nPosY>" + poRec.getoJTest().getoPos().getnPosY()+ "</nPosY>");
                voStream.println("    <nSizeX>" + poRec.getoJTest().getoPos().getnSizeX()+ "</nSizeX>");
                voStream.println("    <nSizeY>" + poRec.getoJTest().getoPos().getnSizeY()+ "</nSizeY>");
                voStream.println("  </JTest>");
                //End of the main tag
                voStream.println("</FormsParameters>");
                voStream.close();
            } catch (Exception e) {
                    if (voStream!=null)
                            voStream.close();
            }
    }
    
}
