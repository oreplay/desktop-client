/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eu.oreplay.logic.connection;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class with data structure of a query to GitHub for custom properties
 * @author javier.arufe
 */
public class CustomProperty {
    private String propertyName;
    private String value;

    public CustomProperty() {
        this.propertyName = "";
        this.value = "";
    }

    public CustomProperty(String propertyName, String value) {
        this.propertyName = propertyName;
        this.value = value;
    }

    @JsonProperty("property_name")
    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    
    
}
