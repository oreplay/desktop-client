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
    private double nCourses;
    private ResponseRunners oRunners;
    private double nTotal;

    public ResponseProcessing() {
        nCourses = 0.0;
        oRunners = new ResponseRunners();
        nTotal = 0.0;
    }
    public ResponseProcessing (double pnCourses, ResponseRunners poRunners, double pnTotal) {
        nCourses = pnCourses;
        oRunners = poRunners;
        nTotal = pnTotal;
    }
    @JsonProperty("courses")
    public double getnCourses() {
        return nCourses;
    }
    public void setnCourses(double nCourses) {
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
    public double getnTotal() {
        return nTotal;
    }
    public void setnTotal(double nTotal) {
        this.nTotal = nTotal;
    }
    
}
