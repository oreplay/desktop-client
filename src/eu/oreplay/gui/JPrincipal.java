/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.oreplay.gui;

import javax.xml.bind.JAXBContext;
import java.io.*;
import eu.oreplay.logic.iof.*;
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
            ResultList voResultados = null;
            //Primero, test de conversión XML a Clases de un fichero de resultados
            String vcFic = "";
            if (args.length>0) {
                vcFic = args[0];
            }
            File voFic = new File(vcFic);
            if (voFic.exists()) {
                InputStream voIs = new BOMInputStream(new FileInputStream(vcFic));
                voContext = JAXBContext.newInstance(ResultList.class);
                voResultados = (ResultList) voContext.createUnmarshaller()
                        .unmarshal(voIs);
                if (voResultados!=null) {
                    System.out.println(voResultados.getEvent().getName());
                }
            } else {
                System.out.println("El fichero no existe");
            }
            //Segundo, creación de un XML con resultados
            if (voResultados!=null) {
                Marshaller mar= voContext.createMarshaller();
                mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                mar.marshal(voResultados, new File("./00_PruebaResultados.xml"));
            } else {
                System.out.println("No tengo resultados para exportar");
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        
    }
    
}
