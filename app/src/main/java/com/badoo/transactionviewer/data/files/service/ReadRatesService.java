package com.badoo.transactionviewer.data.files.service;


import com.badoo.transactionviewer.model.ConversionMatrix;

import java.io.BufferedReader;

import rx.Observable;

/**
 * Created by mutha on 25/06/16.
 */
public interface ReadRatesService {
    Observable<ConversionMatrix> getConversionMatrix(BufferedReader bufferedReader);
}
