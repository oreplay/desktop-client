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
    private int nRunnerLoop;
    private int nRunnersInLoop;
    private int nClubs;
    private int nRunnerResults;
    private int nSplits;
    private int nTotal;

    public ResponseRunners() {
        nRunnerLoop = 0;
        nRunnersInLoop = 0;
        nClubs = 0;
        nSplits = 0;
        nRunnerResults = 0;
        nTotal = 0;
    }
    public ResponseRunners (int pnRunnerLoop, int pnRunnersInLoop, int pnClubs, 
            int pnRunnerResults, int pnSplits, int pnTotal) {
        nRunnerLoop = pnRunnerLoop;
        nRunnersInLoop = pnRunnersInLoop;
        nClubs = pnClubs;
        nRunnerResults = pnRunnerResults;
        nSplits = pnSplits;
        nTotal = pnTotal;
    }

    @JsonProperty("runnerLoop")
    public int getnRunnerLoop() {
        return nRunnerLoop;
    }
    public void setnRunnerLoop(int pnRunnerLoop) {
        nRunnerLoop = pnRunnerLoop;
    }
    @JsonProperty("runnersInLoop")
    public int getnRunnersInLoop() {
        return nRunnersInLoop;
    }
    public void setnRunnersInLoop(int nRunnersInLoop) {
        this.nRunnersInLoop = nRunnersInLoop;
    }
    @JsonProperty("clubs")
    public int getnClubs() {
        return nClubs;
    }
    public void setnClubs(int pnClubs) {
        nClubs = pnClubs;
    }
    @JsonProperty("runnerResults")
    public int getnRunnerResults() {
        return nRunnerResults;
    }
    public void setnRunnerResults(int nRunnerResults) {
        this.nRunnerResults = nRunnerResults;
    }    
    @JsonProperty("splits")
    public int getnSplits() {
        return nSplits;
    }
    public void setnSplits(int nSplits) {
        this.nSplits = nSplits;
    }
    @JsonProperty("total")
    public int getnTotal() {
        return nTotal;
    }
    public void setnTotal(int nTotal) {
        this.nTotal = nTotal;
    }
    
}
