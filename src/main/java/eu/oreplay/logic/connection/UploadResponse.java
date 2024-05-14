/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eu.oreplay.logic.connection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author javier.arufe
 */
public class UploadResponse {
    private List<String> data;
    private Meta oMeta;

    public UploadResponse() {
        data = new ArrayList<String>();
        oMeta = new Meta();
    }
    public UploadResponse (List<String> plData, Meta poMeta) {
        data = plData;
        oMeta = poMeta;
    }

    @JsonIgnore
    public List<String> getData() {
        return data;
    }
    public void setlData(List<String> lData) {
        this.data = lData;
    }
    @JsonProperty("meta")
    public Meta getoMeta() {
        return oMeta;
    }
    public void setoMeta(Meta poMeta) {
        oMeta = poMeta;
    }
}
