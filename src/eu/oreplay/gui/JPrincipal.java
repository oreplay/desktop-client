/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.oreplay.gui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import javax.xml.bind.JAXBContext;
import java.io.*;
import eu.oreplay.logic.iof.*;
import eu.oreplay.logic.converter.*;
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
            JAXBContext voContext = null;
            ResultList voResults = null;
            StartList voStarts = null;
            //First, testing XML to DB Model conversion, for Results or Starts file
            String vcType = "";
            String vcFicSrc = "";
            String vcFicDstXml = "./00_ResultsTest.xml";
            String vcFicDstJson = "./00_ResultsTest.json";
            if (args.length>=3) {
                vcType = args[0];
                vcFicSrc = args[1];
                vcFicDstXml = args[2] + ".xml";
                vcFicDstJson = args[2] + ".json";
            }
            File voFic = new File(vcFicSrc);
            if (voFic.exists()) {
                InputStream voIs = new BOMInputStream(new FileInputStream(vcFicSrc));
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
            } else {
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
                System.out.println("No results to export");
            }
            //or creating a XML for starts
            if (voStarts!=null) {
                //Class and method to process IOF XML and put it into the OReplay data model
                ConverterIofToModel voConv1 = new ConverterIofToModel();
                eu.oreplay.db.Event voEve = voConv1.convertStartListSingleStageClassic(voStarts, "8f3b542c-23b9-4790-a113-b83d476c0ad9", 
                        "8f3b542c-23b9-4790-a113-b83d476c0ad9");
                //Conversion to XML
                //Marshaller mar= voContext.createMarshaller();
                //mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                //mar.marshal(voEve, new File(vcFicDstXml));
                //Also, a JSON file with Jackson
                ObjectMapper voMapper = new ObjectMapper();
                voMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
                String vcJson = voMapper.writerWithDefaultPrettyPrinter().writeValueAsString(voEve);
                BufferedWriter voWriter = new BufferedWriter(new FileWriter(vcFicDstJson));
                voWriter.write(vcJson);
                voWriter.close();
            } else {
                System.out.println("No starts to export");
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        
    }
    
}
