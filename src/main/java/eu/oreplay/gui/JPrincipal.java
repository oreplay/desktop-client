/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.oreplay.gui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import eu.oreplay.controller.EventController;
import javax.xml.bind.JAXBContext;
import java.io.*;
import eu.oreplay.logic.iof.*;
import eu.oreplay.logic.converter.*;
import eu.oreplay.utils.Utils;
import java.util.ArrayList;
import javax.xml.bind.Marshaller;
import org.apache.commons.io.input.BOMInputStream;

/**
 *
 * @author javier.arufe
 */
public class JPrincipal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            System.out.println("Process started.");
            JAXBContext voContext = null;
            ResultList voResults = null;
            StartList voStarts = null;
            //First, testing XML to DB Model conversion, for Results or Starts file
            boolean vbInternalTest = true;
            String vcFicSrc = "IOF_Starts1DayFewData.xml";
            InputStream voIs = JPrincipal.class.getClassLoader().getResourceAsStream("tests/" + vcFicSrc);
            String vcFicDstXml = "00_StartsTest.xml";
            String vcFicDstJson = "00_StartsTest.json";
            String vcFicDstDataStructJson = "00_StartsTest_datastructure.json";
            //Parse arguments
            if (args.length>=2) {
                vbInternalTest = false;
                vcFicSrc = (args.length>0?args[0]:vcFicSrc);
                vcFicDstXml = (args.length>1?args[1] + ".xml":vcFicDstXml);
                vcFicDstJson = (args.length>1?args[1] + ".json":vcFicDstJson);
                vcFicDstDataStructJson = (args.length>1?args[1] + "_datastructure.json":vcFicDstJson);
            }
            File voFic = new File(vcFicSrc);
            if (vbInternalTest) {
                java.nio.file.Files.copy(voIs, voFic.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            }
            ConverterToModel voConv = new ConverterIofToModel();
            voConv.inspectFile(voFic);
            if (voConv.getcExtension().equals(ConverterToModel.EXT_CSV)) {
                voConv = new ConverterCsvOEToModel(voConv);
            } else if (voConv.getcExtension().equals(ConverterToModel.EXT_XML)) {
                voConv = new ConverterIofToModel(voConv);
            }
            System.out.println(vcFicSrc + ". Exists: " + voConv.isbExists() + 
                    "; Known data: " + voConv.isbKnownData() + 
                    "; Extension: " + voConv.getcExtension() + 
                    "; Contents: " + voConv.getcContents() + 
                    "; Source: " + voConv.getcSource() + 
                    "; IOF Version: " + voConv.getcIofVersion() + 
                    "; Results type: " + voConv.getcResultsType()
                    );
            //Object to group configuration and event
            OReplayDataTransfer voData = new OReplayDataTransfer(voConv,
            new eu.oreplay.db.Event());
            //JSON file with Jackson
            ObjectMapper voMapper = new ObjectMapper();
            voMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
            String vcJson = voMapper.writerWithDefaultPrettyPrinter().writeValueAsString(voData);
            BufferedWriter voWriter = new BufferedWriter(new FileWriter(vcFicDstDataStructJson));
            voWriter.write(vcJson);
            voWriter.close();
            
            /**
            //If no args, it performs an internal test using a StartList XML from resources
            if (vbInternalTest) {
                voIs = new BOMInputStream(voIs);
                voContext = JAXBContext.newInstance(StartList.class);
                voStarts = (StartList) voContext.createUnmarshaller()
                        .unmarshal(voIs);
                if (voStarts!=null) {
                    System.out.println(voStarts.getEvent().getName());
                }
            } else {
                //Check whether file exists
                if (voFic.exists())
                    vbUtf = Utils.isFileContainsBOM(voFic);
                if (voFic.exists() && vbSrcXml) {
                    if (vbUtf)
                        voIs = new BOMInputStream(new FileInputStream(vcFicSrc));
                    else
                        voIs = new FileInputStream(vcFicSrc);
                    if (vcType.equals("ResultList")) {
                        voContext = JAXBContext.newInstance(ResultList.class);
                        voResults = (ResultList) voContext.createUnmarshaller()
                                .unmarshal(voIs);
                        if (voResults!=null) {
                            System.out.println(voResults.getEvent().getName());
                        }
                    } else {
                        voContext = JAXBContext.newInstance(StartList.class);
                        voStarts = (StartList) voContext.createUnmarshaller()
                                .unmarshal(voIs);
                        if (voStarts!=null) {
                            System.out.println(voStarts.getEvent().getName());
                        }
                    }
                } else if (!voFic.exists()) {
                    System.out.println("File doesn't exist");
                }
            }
            //Second, XML generation
            if (voResults!=null) {
                Marshaller mar= voContext.createMarshaller();
                mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                mar.marshal(voResults, new File(vcFicDstXml));
                //Also, a JSON file with Jackson
                ObjectMapper voMapper = new ObjectMapper();
                String vcJson = voMapper.writerWithDefaultPrettyPrinter().writeValueAsString(voResults);
                BufferedWriter voWriter = new BufferedWriter(new FileWriter(vcFicDstJson));
                voWriter.write(vcJson);
                voWriter.close();
            } else {
                System.out.println("No XML results to export");
            }
            //or creating a XML for starts
            if (voStarts!=null) {
                //Class and method to process IOF XML and put it into the OReplay data model
                ConverterIofToModel voConv1 = new ConverterIofToModel();
                eu.oreplay.db.Event voEve = voConv1.convertStartListSingleStageClassic(voStarts, "8f3b542c-23b9-4790-a113-b83d476c0ad9", 
                        "51d63e99-5d7c-4382-a541-8567015d8eed");
                //JSON file with Jackson
                ObjectMapper voMapper = new ObjectMapper();
                voMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
                String vcJson = voMapper.writerWithDefaultPrettyPrinter().writeValueAsString(voEve);
                BufferedWriter voWriter = new BufferedWriter(new FileWriter(vcFicDstJson));
                voWriter.write(vcJson);
                voWriter.close();
            } else {
                System.out.println("No XML starts to export");
            }
            //or creating a CSV for starts
            if (voFic.exists() && !vbSrcXml) {
                String vcEncoding = (vbUtf?Utils.ENCODING_UTF_8:Utils.ENCODING_ISO_8859_1);
                ConverterCsvToModel voConv2 = new ConverterCsvToModel();
                //Create a basic event and stage information
                eu.oreplay.db.Event voEveSrc = new eu.oreplay.db.Event();
                voEveSrc.setId("8f3b542c-23b9-4790-a113-b83d476c0ad9");
                voEveSrc.setDescription("First CSV start list, event");
                voEveSrc.setCreated(new java.util.Date());
                //The stage
                eu.oreplay.db.Stage voSta = new eu.oreplay.db.Stage();
                voSta.setId("51d63e99-5d7c-4382-a541-8567015d8eed");
                voSta.setOrderNumber(1);
                voSta.setDescription("First CSV start list, stage");
                voSta.setBaseDate(Utils.parse("01/02/2024", "dd/MM/yyyy"));
                voSta.setBaseTime(Utils.parse("11:00:00", "HH:mm:ss"));
                voSta.setCreated(new java.util.Date());
                //Add the stage to the event
                ArrayList<eu.oreplay.db.Stage> vlSta = new ArrayList<eu.oreplay.db.Stage>();
                vlSta.add(voSta);
                voEveSrc.setStageList(vlSta);
                //Process file
                eu.oreplay.db.Event voEve = voConv2.convertStartListSingleStageClassicOE2010(vcFicSrc, voEveSrc, ";", vcEncoding);
                //JSON file with Jackson
                ObjectMapper voMapper = new ObjectMapper();
                voMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
                String vcJson = voMapper.writerWithDefaultPrettyPrinter().writeValueAsString(voEve);
                BufferedWriter voWriter = new BufferedWriter(new FileWriter(vcFicDstJson));
                voWriter.write(vcJson);
                voWriter.close();
           }
           */
        }catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println("Process finished.");        
    }
    
}
