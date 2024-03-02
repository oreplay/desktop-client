/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.oreplay.gui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.xml.bind.JAXBContext;
import java.io.*;
import eu.oreplay.logic.iof.*;
import eu.oreplay.logic.converter.*;
import eu.oreplay.utils.Utils;
import java.util.ArrayList;
import jakarta.xml.bind.Marshaller;
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
            String vcType = "StartList";
            boolean vbSrcXml = true;
            boolean vbUtf = true;
            String vcFicSrc = "./00_Salidas1DiaPocosDatos.xml";
            String vcFicDstXml = "./00_StartsTest.xml";
            String vcFicDstJson = "./00_StartsTest.json";
            if (args.length>=3) {
                vcType = args[0];
                vbSrcXml = (args[1].endsWith("xml")?true:false);
                vcFicSrc = args[1];
                vcFicDstXml = args[2] + ".xml";
                vcFicDstJson = args[2] + ".json";
            }
            File voFic = new File(vcFicSrc);
            if (voFic.exists())
                vbUtf = Utils.isFileContainsBOM(voFic);
            if (voFic.exists() && vbSrcXml) {
                InputStream voIs = null;
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
                String vcEncoding = (vbUtf?"UTF-8":"ISO-8859-1");
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
        }catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println("Process finished.");        
    }
    
}
