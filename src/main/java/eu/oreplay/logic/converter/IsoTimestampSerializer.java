/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.oreplay.logic.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Custom serializer for timestamps to convert a Date into a Strint that complies with ISO 8601
 * @author javier.arufe
 */
public class IsoTimestampSerializer extends StdSerializer<Date> {
    //OE, OS, SiTiming are not using UTC zones (Z, +01:00, -02:00, etc). Even so, We're going to export it taking from the computer time zone
    private static SimpleDateFormat oFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    private static TimeZone oZone = TimeZone.getDefault();

    public IsoTimestampSerializer() { 
        this(null); 
    } 

    public IsoTimestampSerializer(Class<Date> t) {
        super(t); 
    }

    @Override
    public void serialize(Date poDate, JsonGenerator poGen, SerializerProvider poProv) 
      throws IOException, JsonProcessingException {
        if (poDate!=null) {
            oFormat.setTimeZone(oZone);
            poGen.writeString(oFormat.format(poDate));
        } else {
            poGen.writeString("");
        }
    }
}
