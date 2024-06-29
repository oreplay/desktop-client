/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eu.oreplay.logic.converter;

import eu.oreplay.db.Event;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import eu.oreplay.utils.Utils;
import java.io.File;
import org.apache.logging.log4j.*;


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
    Logger oLog = null;

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

    public Logger getoLog() {
        return oLog;
    }
    public void setoLog(Logger poLog) {
        oLog = poLog;
    }
    
    /**
     * First inspects a file, gets its metadata and then process it to obtain
     * a JSON (or an error message)
     * @param pcFile String File to process
     * @return String JSON or error message
     */
    public String getJsonFromFile (String pcFile) {
        String vcResul = getJsonFromFile(pcFile, "", "", "", "");
        return vcResul;
    }
    /**
     * First inspects a file, gets its metadata and then process it to obtain
     * a JSON (or an error message)
     * @param pcFile String File to process
     * @param pcEveId String Event's Id
     * @param pcEveDesc String Event's description
     * @param pcStaId String Stage's Id
     * @param pcStaDesc String Stage's description
     * @return String JSON or error message
     */
    public String getJsonFromFile (String pcFile, String pcEveId, String pcEveDesc, 
            String pcStaId, String pcStaDesc) {
        String vcResul = processFile(preProcessFile(pcFile), pcEveId, pcEveDesc, 
                pcStaId, pcStaDesc);
        return vcResul;
    }
    /**
     * Inspects a file to decide which kind of data comes in it; this is needed
     * before processing it because fills some metadata
     * @param pcFile String File to inspect
     * @return ConverterToModel Object with the metadata describing the file contents
     */
    public ConverterToModel preProcessFile(String pcFile) {
        oConf = null;
        try {
            //Opens and tries to parse the source file
            File voFile = new File(pcFile);
            oConf = new ConverterIofToModel();
            oConf.setoLog(oLog);
            oConf.inspectFile(voFile);
            voFile = null;
            if (oConf.getcExtension().equals(ConverterToModel.EXT_CSV)) {
                oConf = new ConverterCsvOEToModel(oConf);
            } else if (oConf.getcExtension().equals(ConverterToModel.EXT_XML)) {
                oConf = new ConverterIofToModel(oConf);
            }
        }catch(Exception e) {
            oConf = null;
            if (oLog!=null)
                oLog.error("error_exception", e);
        }
        return oConf;
    }
    /**
     * Given the metadata defining the kind of file with data, this method
     * processes the data and generates a JSON, or returns a message with error
     * @return String JSON or error message
     */
    public String processFile() {
        String vcResul = "";
        vcResul = processFile(oConf, "", "", "", "");
        return vcResul;
    }
    /**
     * Given the metadata defining the kind of file with data, this method
     * processes the data and generates a JSON, or returns a message with error
     * @param poConv ConverterToModel Metadata obtained previously
     * @return String JSON or error message
     */
    public String processFile(ConverterToModel poConv) {
        String vcResul = "";
        vcResul = processFile(poConv, "", "", "", "");
        return vcResul;
    }
    /**
     * Given the metadata defining the kind of file with data, this method
     * processes the data and generates a JSON, or returns a message with error
     * @param pcEveId String Event's Id
     * @param pcEveDesc String Event's description
     * @param pcStaId String Stage's Id
     * @param pcStaDesc String Stage's description
     * @return String JSON or error message
     */
    public String processFile(String pcEveId, String pcEveDesc, 
            String pcStaId, String pcStaDesc) {
        return processFile (oConf, pcEveId, pcEveDesc, pcStaId, pcStaDesc);
    }
    /**
     * Given the metadata defining the kind of file with data, this method
     * processes the data and generates a JSON, or returns a message with error
     * @param poConv ConverterToModel Metadata obtained previously
     * @param pcEveId String Event's Id
     * @param pcEveDesc String Event's description
     * @param pcStaId String Stage's Id
     * @param pcStaDesc String Stage's description
     * @return String JSON or error message
     */
    public String processFile(ConverterToModel poConv, String pcEveId, String pcEveDesc, 
            String pcStaId, String pcStaDesc) {
        String vcResul = "";
        eu.oreplay.db.Event voEve = null;
        try {
            //If a source file has been inspected, process it
            if (poConv!=null) {
                if (poConv.isbExists()) {
                    //If XML, obtain ResultList or StartList
                    if (poConv.getcExtension().equals(ConverterToModel.EXT_XML) && 
                            poConv.getcIofVersion().equals(ConverterToModel.IOF_VERSION_3)) {
                        //Creates a dummy event with one stage
                        eu.oreplay.db.Event voSrcEve = Utils.createDummyEventOneStage(pcEveId, pcEveDesc, pcStaId, pcStaDesc);
                        //Set the specific properties for CSV
                        ((ConverterIofToModel)poConv).setSpecificProperties(voSrcEve);
                        if (poConv.getcContents().equals(ConverterToModel.CONTENTS_RESULT)) {
                            //Parses the contents
                            voEve = poConv.convertResultList(poConv.getcFile());
                        } else if (poConv.getcContents().equals(ConverterToModel.CONTENTS_START)) {
                            //Parses the contents
                            voEve = poConv.convertStartList(poConv.getcFile());
                        } else {
                            vcResul = "error_not_supported_xml_contents";
                        }
                    //If CSV, parse contents
                    } else if (poConv.getcExtension().equals(ConverterToModel.EXT_CSV)) {
                        //Gets an encoding for the text file depending on the UTF mark
                        String vcEncoding = (poConv.isbUtf()?Utils.ENCODING_UTF_8:Utils.ENCODING_ISO_8859_1);
                        //Creates a dummy event with one stage
                        eu.oreplay.db.Event voSrcEve = Utils.createDummyEventOneStage(pcEveId, pcEveDesc, pcStaId, pcStaDesc);
                        //Set the specific properties for CSV
                        ((ConverterCsvOEToModel)poConv).setSpecificProperties(";", vcEncoding, voSrcEve);
                        if (poConv.getcContents().equals(ConverterToModel.CONTENTS_RESULT)) {
                            //Parses the contents
                            voEve = poConv.convertResultList(poConv.getcFile());
                        } else if (poConv.getcContents().equals(ConverterToModel.CONTENTS_START)) {
                            //Parses the contents
                            voEve = poConv.convertStartList(poConv.getcFile());
                        } else {
                            vcResul = "error_not_supported_csv_contents";
                        }
                    } else {
                        vcResul = "error_not_supported_filetype";
                    }
                    //Creates the output in JSON by merging metadata and event
                    if (voEve!=null) {
                        //---- Final steps, write the output ---
                        //Object to group configuration and event
                        OReplayDataTransfer voData = new OReplayDataTransfer(poConv, voEve);
                        //JSON file with Jackson
                        ObjectMapper voMapper = new ObjectMapper();
                        voMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
                        vcResul = voMapper.writerWithDefaultPrettyPrinter().writeValueAsString(voData);
                    } else {
                        vcResul = "error_nothing_to_do_noevent";
                    }
                } else {
                    vcResul = "error_nothing_to_do_nofile";
                }
            } else {
                vcResul = "error_nothing_to_do_noconf";
            }
        } catch(Exception e) {
            vcResul = "error_exception" + ". " + e.getMessage();
        }
        return vcResul;
    }
    
}
