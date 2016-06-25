package com.badoo.transactionviewer.data.files.service;


import android.util.Log;

import com.badoo.transactionviewer.model.ConversionMatrix;
import com.badoo.transactionviewer.model.Graph.Graph;
import com.badoo.transactionviewer.model.Graph.ShortestWayForConversionDjikshtraBfs;
import com.badoo.transactionviewer.model.Graph.Vertex;
import com.badoo.transactionviewer.util.Utils;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.util.HashMap;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by mutha on 25/06/16.
 */
public class ReadRatesServiceImpl implements ReadRatesService {

    /**
     *  read the rates from the file generate graph and apply djikshtra's Shortest path algorithm
     * @param bufferedReader
     * @return
     */

    @Override
    public Observable<ConversionMatrix> getConversionMatrix(BufferedReader bufferedReader) {
        return Observable.just(bufferedReader).flatMap(new Func1<BufferedReader, Observable<? extends ConversionMatrix>>() {
            @Override
            public Observable<? extends ConversionMatrix> call(BufferedReader inputBufferedReader) {
                return Observable.just(readRatesFromFile(inputBufferedReader));
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(Schedulers.newThread());
    }

    private ConversionMatrix readRatesFromFile(BufferedReader inputBufferedReader){

        try {
            JSONArray appArray = new JSONArray(Utils.readFileDump(inputBufferedReader));
            Graph graph = generateGraph(appArray);
            ShortestWayForConversionDjikshtraBfs shortestWayForConversionDjikshtraBfs = new ShortestWayForConversionDjikshtraBfs(graph);
            return shortestWayForConversionDjikshtraBfs.getConversionMatrix();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Graph generateGraph(JSONArray appArray) throws JSONException {
        Graph graph = new Graph();
        double reverseRate;
        double rate;
        HashMap<String, Integer> alreadyAdded = new HashMap<>();

        for(int i = 0; i< appArray.length(); i++){
            Vertex one = new Vertex(appArray.getJSONObject(i).getString("from"));
            Vertex two = new Vertex(appArray.getJSONObject(i).getString("to"));
            // it will add only if vertex is not already added
            if(!alreadyAdded.containsKey(one.getLabel())) {
                graph.addVertex(one);
                alreadyAdded.put(one.getLabel(), 1);
            }
            if(!alreadyAdded.containsKey(two.getLabel())) {
                graph.addVertex(two);
                alreadyAdded.put(two.getLabel(), 1);
            }
            rate = appArray.getJSONObject(i).getDouble("rate");
            graph.addEdge(one, two, rate);

            // self conversion will be useful while finding shortest distance using
            // Djikshtra's algorithm or Bfs

            graph.addEdge(one, one, 1.0);
            graph.addEdge(two, two, 1.0);
            reverseRate = rate != 0 ? (1/rate) : 0;
            graph.addEdge(two, one, reverseRate);
        }
        return graph;
    }
}
