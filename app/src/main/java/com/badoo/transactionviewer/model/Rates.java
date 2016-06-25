package com.badoo.transactionviewer.model;

/**
 * Created by mutha on 25/06/16.
 */
public class Rates {

    private String from;
    private String to;
    private double rate;

    public Rates(final String from, final String to, final String rate) {
        this.from = from;
        this.to = to;
        this.rate = rate != null || rate != "" ? Double.parseDouble(rate) : 0.0;
    }
}
