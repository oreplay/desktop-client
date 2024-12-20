/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eu.oreplay.logic.connection;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

/**
 * Part of the Data Structure that suits the Response from the backend
 * @author javier.arufe
 */
public class ResponseMeta {
    private ResponseUpdated oUpdated;
    private ResponseTimings oTimings;
    private String cHumanColor;
    private List<String> lHuman;

    public ResponseMeta() {
        oUpdated = new ResponseUpdated();
        oTimings = new ResponseTimings();
        cHumanColor = "#000000";
        lHuman = new ArrayList<>();
    }
    public ResponseMeta (ResponseUpdated poUpdated, ResponseTimings poTimings, 
            String pcHumanColor, List<String> plHuman) {
        oUpdated = poUpdated;
        oTimings = poTimings;
        cHumanColor = pcHumanColor;
        lHuman = plHuman;
    }
    @JsonProperty("updated")
    public ResponseUpdated getoUpdated() {
        return oUpdated;
    }
    public void setoUpdated(ResponseUpdated poUpdated) {
        oUpdated = poUpdated;
    }
    @JsonProperty("timings")
    public ResponseTimings getoTimings() {
        return oTimings;
    }
    public void setoTimings(ResponseTimings oTimings) {
        this.oTimings = oTimings;
    }
    @JsonProperty("humanColor")
    public String getcHumanColor() {
        return cHumanColor;
    }
    public void setcHumanColor(String cHumanColor) {
        this.cHumanColor = cHumanColor;
    }
    @JsonProperty("human")
    public List<String> getlHuman() {
        return lHuman;
    }
    public void setlHuman(List<String> plHuman) {
        lHuman = plHuman;
    }

}
