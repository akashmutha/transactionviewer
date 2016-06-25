package com.badoo.transactionviewer.data.files.service;


import android.util.Log;

import com.badoo.transactionviewer.model.AllTransactions;
import com.badoo.transactionviewer.model.Transaction;
import com.badoo.transactionviewer.util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by mutha on 25/06/16.
 */
public class ReadTransactionsServiceImpl implements ReadTransactionsService {

    /**
     * It reads the file on a background thread and return the transactions
     * @param inputBufferedReader to a file
     * @return all the transaction
     */

    @Override
    public Observable<AllTransactions> readTransactions(final BufferedReader inputBufferedReader) {
        return Observable.just(inputBufferedReader).flatMap(new Func1<BufferedReader, Observable<? extends AllTransactions>>() {
            @Override
            public Observable<? extends AllTransactions> call(BufferedReader inputBufferedReader) {
                return Observable.just(readTransactionFromFile(inputBufferedReader));
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(Schedulers.newThread());
    }

    private AllTransactions readTransactionFromFile(BufferedReader inputBufferedReader){
        try {
            JSONArray appArray = new JSONArray(Utils.readFileDump(inputBufferedReader));
            ArrayList<Transaction> transactions;
            AllTransactions allTransaction;
            HashMap<String, ArrayList<Transaction>> countForTransaction = new HashMap<>();
            String key;

            for(int i = 0; i< appArray.length(); i++){
                key = appArray.getJSONObject(i).getString("sku");
                if(countForTransaction.containsKey(key)){
                    transactions = countForTransaction.get(key);
                    transactions.add(getTransactionFromJsonObject(appArray.getJSONObject(i)));
                    countForTransaction.put(key, transactions);
                } else {
                    transactions = new ArrayList<>();
                    transactions.add(getTransactionFromJsonObject(appArray.getJSONObject(i)));
                    countForTransaction.put(key, transactions);
                }
            }
            allTransaction = new AllTransactions(countForTransaction);
            return allTransaction;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Transaction getTransactionFromJsonObject(JSONObject transactionObject) throws JSONException{
        if(transactionObject == null) {
            return null;
        }
        return new Transaction(transactionObject.getString("amount"), transactionObject.getString("sku"), transactionObject.getString("currency"));
    }
}
