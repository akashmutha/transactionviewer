package com.badoo.transactionviewer.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mutha on 25/06/16.
 */
public class Transaction implements Parcelable{

    private String sku;
    private String currency;
    private double amount;

    public Transaction(final String amount, final String sku, final String currency) {
        this.amount = amount != null || amount != "" ? Double.parseDouble(amount) : 0.0;
        this.sku = sku;
        this.currency = currency;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getSku() {
        return this.sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public static final Creator<Transaction> CREATOR = new Creator<Transaction>() {
        public Transaction createFromParcel(Parcel source) {
            return new Transaction(source);
        }

        public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };

    private Transaction(Parcel in){
        sku = in.readString();
        currency = in.readString();
        amount = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sku);
        dest.writeString(currency);
        dest.writeDouble(amount);
    }
}
