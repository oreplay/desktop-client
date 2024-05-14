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
public class Meta {
    private Updated oUpdated;
    private List<String> lHuman;

    public Meta() {
        oUpdated = new Updated();
        lHuman = new ArrayList<String>();
    }
    public Meta (Updated poUpdated, List<String> plHuman) {
        oUpdated = poUpdated;
        lHuman = plHuman;
    }
    @JsonProperty("updated")
    public Updated getoUpdated() {
        return oUpdated;
    }
    public void setoUpdated(Updated poUpdated) {
        oUpdated = poUpdated;
    }
    @JsonProperty("human")
    public List<String> getlHuman() {
        return lHuman;
    }
    public void setlHuman(List<String> plHuman) {
        lHuman = plHuman;
    }

}
