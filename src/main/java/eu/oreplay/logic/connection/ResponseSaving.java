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
    private double nTotal;

    public ResponseSaving() {
        nTotal = 0.0;
    }
    public ResponseSaving (double pnTotal) {
        nTotal = pnTotal;
    }
    @JsonProperty("total")
    public double getnTotal() {
        return nTotal;
    }
    public void setnTotal(double nTotal) {
        this.nTotal = nTotal;
    }
    
}
