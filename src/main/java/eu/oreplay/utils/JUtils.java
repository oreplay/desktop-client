/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eu.oreplay.utils;

import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Class that performs basic operations related to Swing
 * @author javier.arufe
 */
public class JUtils {

/**
 * Method used to calculate the position for showing a form
 * @param poParent java.awt.Frame Parent Window
 * @param poChild javax.swing.JDialog Child Window
 */
public static void positioningChild (java.awt.Frame poParent, javax.swing.JDialog poChild) {
    try {
        int vnHalfParentX = (int)(poParent.getBounds().width/2);
        int vnHalfParentY = (int)(poParent.getBounds().height/2);
        int vnHalfChildX = (int)(poChild.getBounds().width/2);
        int vnHalfChildY = (int)(poChild.getBounds().height/2);
        int vnPosX = poParent.getX() + (vnHalfParentX - vnHalfChildX);
        int vnPosY = poParent.getY() + (vnHalfParentY - vnHalfChildY);
        if (vnPosX<0)
            vnPosX = 0;
        if (vnPosY<0)
            vnPosY = 0;
        poChild.setLocation(vnPosX, vnPosY);
    } catch (Exception e) {
        poChild.setLocation(poParent.getX(), poParent.getY());
    }
}

/**
 * Method that allows to select a file from a dialog box
 * @param poParent javax.swing.JDialog Parent Window
 * @return String Path+name of the file, or null if nothing selected
 */
public static String selectFile (javax.swing.JDialog poParent) {
    String vcFile = null;
    int vnRet = javax.swing.JFileChooser.APPROVE_OPTION;
    try {
        final javax.swing.JFileChooser voChooser = new javax.swing.JFileChooser();
        voChooser.setCurrentDirectory(new java.io.File("." + java.io.File.separator));
        vnRet = voChooser.showOpenDialog(poParent);
        if (vnRet == javax.swing.JFileChooser.APPROVE_OPTION) {
            vcFile = voChooser.getSelectedFile().toString();
        }
    } catch (Exception e) {
        vcFile = null;
    }
    return vcFile;
}
/**
 * Method that allows to select a file from a dialog box
 * @param poParent javax.swing.JDialog Parent Window
 * @param pcFolder String Default folder to search
 * @param paExtension String[] Array with allowed file types
 * @param pcTextExt String Text to indicate the type of allowed extensions
 * @param pbOpen boolean Flag to indicate if it's an Open (true) or a Save (false) dialog box
 * @return String Path+name of the file, or null if nothing selected
 */
public static String selectFile (javax.swing.JDialog poParent, String pcFolder, 
        String[] paExtension, String pcTextExt, boolean pbOpen) {
    String vcFile = null;
    int vnRet = javax.swing.JFileChooser.APPROVE_OPTION;
    try {
        final javax.swing.JFileChooser voChooser = new javax.swing.JFileChooser();
        voChooser.setCurrentDirectory(new java.io.File(pcFolder + java.io.File.separator));
        if (pbOpen)
            voChooser.setDialogType(javax.swing.JFileChooser.OPEN_DIALOG);
        else
            voChooser.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
        if (paExtension!=null && paExtension.length>0) {
            voChooser.addChoosableFileFilter(new FileNameExtensionFilter(pcTextExt, paExtension));
            voChooser.setAcceptAllFileFilterUsed(false);
         } else {
            voChooser.setAcceptAllFileFilterUsed(true);
        }
        if (pbOpen)
            vnRet = voChooser.showOpenDialog(poParent);
        else
            vnRet = voChooser.showSaveDialog(poParent);
        if (vnRet == javax.swing.JFileChooser.APPROVE_OPTION) {
            vcFile = voChooser.getSelectedFile().toString();
        }
    } catch (Exception e) {
        vcFile = null;
    }
    return vcFile;
}
    
}
