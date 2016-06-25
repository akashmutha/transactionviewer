package com.badoo.transactionviewer.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mutha on 25/06/16.
 */
public class AllTransactions {

    private HashMap<String, ArrayList<Transaction>> countForTransaction;

    public HashMap<String, ArrayList<Transaction>> getCountForTransaction() {
        return this.countForTransaction;
    }

    public void setCountForTransaction(HashMap<String, ArrayList<Transaction>> countForTransaction) {
        this.countForTransaction = countForTransaction;
    }

    public AllTransactions(HashMap<String, ArrayList<Transaction>> countForTransaction) {

        this.countForTransaction = countForTransaction;
    }
}
