/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eu.oreplay.logic.connection;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author javier.arufe
 */
public class BasicStrings {
    private List<String> lData;

    public BasicStrings() {
        lData = new ArrayList<String>();
    }
    public BasicStrings (List<String> plData) {
        lData = plData;
    }

    @JsonProperty("data")
    public List<String> getlData() {
        return lData;
    }
    public void setlData(List<String> lData) {
        this.lData = lData;
    }
    
    
}
