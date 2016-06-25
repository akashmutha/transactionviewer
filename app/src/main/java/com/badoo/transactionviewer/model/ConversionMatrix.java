package com.badoo.transactionviewer.model;

import java.util.HashMap;

/**
 * Created by mutha on 25/06/16.
 */
public class ConversionMatrix {
    private HashMap<HashMap<String,String>, Double> conversionMap;

    public HashMap<HashMap<String, String>, Double> getConversionMap() {
        return this.conversionMap;
    }

    public void setConversionMap(HashMap<HashMap<String, String>, Double> conversionMap) {
        this.conversionMap = conversionMap;
    }

    public ConversionMatrix(HashMap<HashMap<String, String>, Double> conversionMap) {

        this.conversionMap = conversionMap;
    }
}
