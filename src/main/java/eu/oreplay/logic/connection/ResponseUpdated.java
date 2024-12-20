/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eu.oreplay.logic.connection;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Inner class to store some properties
 */
public class ResponseUpdated {
    private int nClases;
    private int nClasses;
    private int nCourses;
    private int nRunners;
    private int nSplits;
    private int nRunnerResults;

    public ResponseUpdated() {
        nClases = 0;
        nClasses = 0;
        nCourses = 0;
        nRunners = 0;
        nSplits = 0;
        nRunnerResults = 0;
    }
    public ResponseUpdated (int pnClasses, int pnCourses, int pnRunners, int pnSplits, int pnRunnerResults) {
        nClasses = pnClasses;
        nCourses = pnCourses;
        nRunners = pnRunners;
        nSplits = pnSplits;
        nRunnerResults = pnRunnerResults;
    }

    @JsonProperty("clases")
    public int getnClases() {
        return nClases;
    }
    public void setnClases(int pnClases) {
        nClases = pnClases;
    }
    @JsonProperty("classes")
    public int getnClasses() {
        return nClasses;
    }
    public void setnClasses(int pnClasses) {
        nClasses = pnClasses;
    }
    @JsonProperty("courses")
    public int getnCourses() {
        return nCourses;
    }
    public void setnCourses(int nCourses) {
        this.nCourses = nCourses;
    }
    @JsonProperty("runners")
    public int getnRunners() {
        return nRunners;
    }
    public void setnRunners(int pnRunners) {
        nRunners = pnRunners;
    }
    @JsonProperty("splits")
    public int getnSplits() {
        return nSplits;
    }
    public void setnSplits(int nSplits) {
        this.nSplits = nSplits;
    }
    @JsonProperty("runnerResults")
    public int getnRunnerResults() {
        return nRunnerResults;
    }
    public void setnRunnerResults(int nRunnerResults) {
        this.nRunnerResults = nRunnerResults;
    }    
}
