/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eu.oreplay.logic.connection;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to store some properties and some strings
 */
public class ResponseTimings {
    private ResponseProcessing oProcessing;
    private ResponseSaving oSaving;
    private int nTotal;

    public ResponseTimings() {
        oProcessing = new ResponseProcessing();
        oSaving = new ResponseSaving();
        nTotal = 0;
    }
    public ResponseTimings (ResponseProcessing poProcessing, ResponseSaving poSaving, int pnTotal) {
        oProcessing = poProcessing;
        oSaving = poSaving;
        nTotal = pnTotal;
    }
    @JsonProperty("processing")
    public ResponseProcessing getoProcessing() {
        return oProcessing;
    }
    public void setoProcessing(ResponseProcessing poProcessing) {
        oProcessing = poProcessing;
    }
    @JsonProperty("saving")
    public ResponseSaving getoSaving() {
        return oSaving;
    }
    public void setoSaving(ResponseSaving oSaving) {
        this.oSaving = oSaving;
    }
    @JsonProperty("total")
    public int getnTotal() {
        return nTotal;
    }
    public void setnTotal(int nTotal) {
        this.nTotal = nTotal;
    }
    
}
