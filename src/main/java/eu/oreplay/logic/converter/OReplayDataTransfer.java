/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eu.oreplay.logic.converter;

import eu.oreplay.db.Event;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


/**
 *
 * @author javier.arufe
 */
@JsonRootName(value = "oreplay_data_transfer")
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({
   "configuration",
   "event"
})
public class OReplayDataTransfer {
    Event oEve = null;
    ConverterToModel oConf = null;

    public OReplayDataTransfer() {
        super();
    }

    public OReplayDataTransfer(ConverterToModel poConf, Event poEve) {
        oConf = poConf;
        oEve = poEve;
    }
    
    @JsonProperty("event")
    public Event getoEve() {
        return oEve;
    }

    public void setoEve(Event oEve) {
        this.oEve = oEve;
    }

    @JsonProperty("configuration")
    public ConverterToModel getoConf() {
        return oConf;
    }

    public void setoConf(ConverterToModel oConf) {
        this.oConf = oConf;
    }
    
    
}
