/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eu.oreplay.logic.connection;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class to store some properties and some strings
 */
public class ResponseSaving {
    private int nTotal;

    public ResponseSaving() {
        nTotal = 0;
    }
    public ResponseSaving (int pnTotal) {
        nTotal = pnTotal;
    }
    @JsonProperty("total")
    public int getnTotal() {
        return nTotal;
    }
    public void setnTotal(int nTotal) {
        this.nTotal = nTotal;
    }
    
}
