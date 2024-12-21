/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/RunnerLoop/Class.java to edit this template
 */
package eu.oreplay.logic.connection;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Inner class to store some properties
 */
public class ResponseRunners {
    private double nRunnerLoop;
    private double nRunnersInLoop;
    private double nClubs;
    private double nRunnerResults;
    private double nSplits;
    private double nTotal;

    public ResponseRunners() {
        nRunnerLoop = 0.0;
        nRunnersInLoop = 0.0;
        nClubs = 0.0;
        nSplits = 0.0;
        nRunnerResults = 0.0;
        nTotal = 0.0;
    }
    public ResponseRunners (double pnRunnerLoop, double pnRunnersInLoop, double pnClubs, 
            double pnRunnerResults, double pnSplits, double pnTotal) {
        nRunnerLoop = pnRunnerLoop;
        nRunnersInLoop = pnRunnersInLoop;
        nClubs = pnClubs;
        nRunnerResults = pnRunnerResults;
        nSplits = pnSplits;
        nTotal = pnTotal;
    }

    @JsonProperty("runnerLoop")
    public double getnRunnerLoop() {
        return nRunnerLoop;
    }
    public void setnRunnerLoop(double pnRunnerLoop) {
        nRunnerLoop = pnRunnerLoop;
    }
    @JsonProperty("runnersInLoop")
    public double getnRunnersInLoop() {
        return nRunnersInLoop;
    }
    public void setnRunnersInLoop(double nRunnersInLoop) {
        this.nRunnersInLoop = nRunnersInLoop;
    }
    @JsonProperty("clubs")
    public double getnClubs() {
        return nClubs;
    }
    public void setnClubs(double pnClubs) {
        nClubs = pnClubs;
    }
    @JsonProperty("runnerResults")
    public double getnRunnerResults() {
        return nRunnerResults;
    }
    public void setnRunnerResults(double nRunnerResults) {
        this.nRunnerResults = nRunnerResults;
    }    
    @JsonProperty("splits")
    public double getnSplits() {
        return nSplits;
    }
    public void setnSplits(double nSplits) {
        this.nSplits = nSplits;
    }
    @JsonProperty("total")
    public double getnTotal() {
        return nTotal;
    }
    public void setnTotal(double nTotal) {
        this.nTotal = nTotal;
    }
    
}
