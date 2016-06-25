package com.badoo.transactionviewer;

import android.app.Application;
import android.util.Log;

import com.badoo.transactionviewer.data.files.service.ReadRatesService;
import com.badoo.transactionviewer.data.files.service.ReadRatesServiceImpl;
import com.badoo.transactionviewer.model.ConversionMatrix;
import com.badoo.transactionviewer.util.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by mutha on 25/06/16.
 */
public class TransactionViewerApp extends Application {

    private static ConversionMatrix conversionMatrix;
    private static ReadRatesService readRatesService;

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            // it executes the task on the background thread
            readConversionMatrix();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ConversionMatrix getConversionMatrix(){
        return conversionMatrix;
    }

    private void readConversionMatrix() throws IOException {
        InputStream fileInputStream = getAssets().open(Constants.RATES_FILE_NAME);
        if(readRatesService == null) {
            readRatesService = new ReadRatesServiceImpl();
        }
        readRatesService.getConversionMatrix(new BufferedReader(new InputStreamReader(fileInputStream, "UTF-8")))
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Action1<ConversionMatrix>() {
                    @Override
                    public void call(ConversionMatrix conversionMatrix) {
                        setConversionMatrix(conversionMatrix);
                        Log.e("conversion matrix", conversionMatrix.toString());
                    }
                });
    }

    private void setConversionMatrix(ConversionMatrix conversionMatrix){
        this.conversionMatrix = conversionMatrix;
    }
}
