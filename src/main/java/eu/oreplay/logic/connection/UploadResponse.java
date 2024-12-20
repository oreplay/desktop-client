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
 * Data Structure that suits the Response from the backend
 * @author javier.arufe
 */
public class UploadResponse {
    private ResponseMeta oMeta;
    private List<String> lData;

    public UploadResponse() {
        oMeta = new ResponseMeta();
        lData = new ArrayList<>();
    }
    public UploadResponse (ResponseMeta poMeta, List<String> plData) {
        oMeta = poMeta;
        lData = plData;
    }

    @JsonProperty("meta")
    public ResponseMeta getoMeta() {
        return oMeta;
    }
    public void setoMeta(ResponseMeta poMeta) {
        oMeta = poMeta;
    }
    @JsonProperty("data")
    public List<String> getlData() {
        return lData;
    }
    public void setlData(List<String> lData) {
        this.lData = lData;
    }
}
