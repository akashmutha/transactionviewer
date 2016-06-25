package com.badoo.transactionviewer.data.files.service;


import com.badoo.transactionviewer.model.AllTransactions;
import com.badoo.transactionviewer.model.Transaction;

import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;

import rx.Observable;

/**
 * Created by mutha on 25/06/16.
 */
public interface ReadTransactionsService {
    Observable<AllTransactions> readTransactions(BufferedReader bufferedReader);
}
