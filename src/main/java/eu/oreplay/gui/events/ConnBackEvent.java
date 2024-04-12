/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eu.oreplay.gui.events;

/**
 * Specific event to manage the things that happen in the connetion panels
 * @author javier.arufe
 */
public class ConnBackEvent extends java.util.EventObject {
    private ConnBackStatus oStatus;
    
    public ConnBackEvent(Object poSource, ConnBackStatus poStatus) {
        super(poSource);
        oStatus = poStatus;
    }

    public ConnBackStatus getoStatus() {
        return oStatus;
    }
    
}
