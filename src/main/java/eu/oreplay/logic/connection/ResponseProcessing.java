/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eu.oreplay.logic.connection;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class to store some properties and some strings
 */
public class ResponseProcessing {
    private int nCourses;
    private ResponseRunners oRunners;
    private int nTotal;

    public ResponseProcessing() {
        nCourses = 0;
        oRunners = new ResponseRunners();
        nTotal = 0;
    }
    public ResponseProcessing (int pnCourses, ResponseRunners poRunners, int pnTotal) {
        nCourses = pnCourses;
        oRunners = poRunners;
        nTotal = pnTotal;
    }
    @JsonProperty("courses")
    public int getnCourses() {
        return nCourses;
    }
    public void setnCourses(int nCourses) {
        this.nCourses = nCourses;
    }
    @JsonProperty("runners")
    public ResponseRunners getoRunners() {
        return oRunners;
    }
    public void setoRunners(ResponseRunners poRunners) {
        oRunners = poRunners;
    }
    @JsonProperty("total")
    public int getnTotal() {
        return nTotal;
    }
    public void setnTotal(int nTotal) {
        this.nTotal = nTotal;
    }
    
}
