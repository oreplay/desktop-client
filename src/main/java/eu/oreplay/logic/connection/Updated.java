/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eu.oreplay.logic.connection;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Inner class to store some properties
 */
public class Updated {
    private String cClasses;
    private String cRunners;

    public Updated() {
        cClasses = "";
        cRunners = "";
    }
    public Updated (String pcClasses, String pcRunners) {
        cClasses = pcClasses;
        cRunners = pcRunners;
    }

    @JsonProperty("classes")
    public String getcClasses() {
        return cClasses;
    }
    public void setcClasses(String pcClasses) {
        cClasses = pcClasses;
    }
    @JsonProperty("runners")
    public String getcRunners() {
        return cRunners;
    }
    public void setcRunners(String pcRunners) {
        cRunners = pcRunners;
    }
}
