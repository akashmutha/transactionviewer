package com.badoo.transactionviewer.model.Graph;

import android.util.Log;

import com.badoo.transactionviewer.model.ConversionMatrix;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mutha on 25/06/16.
 */
public class ShortestWayForConversionDjikshtraBfs {

    private Graph graph;
    private ConversionMatrix conversionMatrix;

    public ShortestWayForConversionDjikshtraBfs(Graph graph) {
        this.graph = graph;
    }

    /**
     * This function use BFS to find the minimum number of conversion to be done
     * for converting one currency to other
     * @return
     */
    public ConversionMatrix getConversionMatrix(){

        Vertex v = graph.getVertices().get("GBP");
        if(v == null){
            throw new IllegalArgumentException("Source(GBP) node not found, can't convert to GBp");
        }
        // final result will be stored in the map
        HashMap<HashMap<String, String>, Double> conversionMap = new HashMap<>();
        // to Detect whether we have traverse that node or not
        HashMap<String, Integer> traversalState = new HashMap<>();
        // to get the content from map
        HashMap<String, String> key;
        HashMap<String, String> oldKey;

        // Finding the shortest conversion using same as BFS(Because edges weight does not matter)
        // each conversion will have only one multiplication
        ArrayList<Vertex> queue = new ArrayList<>();
        queue.add(v);
        traversalState.put(v.getLabel(), 1);

        // Simple BFS Algo for shortest path as weight of edge does not matter
        // every conversion from one node to adjacent node will take only one multiplication
        // and Store the conversion once it occurs

        while(!queue.isEmpty()){
            v = queue.remove(0);
            v = graph.getVertices().get(v.getLabel());

            // traverse all the adjacent node of the
            // current vertex

            for(Edge e : v.getNeighbours()){
                if(traversalState.containsKey(e.getTwo().getLabel()) && traversalState.containsKey(e.getOne().getLabel())){
                    continue;
                }
                key = new HashMap<>();

                // check if we have already visited that node than don't add that node
                if(!traversalState.containsKey(e.getTwo().getLabel())) {
                    queue.add(e.getTwo());
                    traversalState.put(e.getTwo().getLabel(), 1);
                    key.put("GBP", e.getTwo().getLabel());
                } else {
                    queue.add(e.getOne());
                    traversalState.put(e.getOne().getLabel(), 1);
                    key.put("GBP", e.getOne().getLabel());
                }
                oldKey = new HashMap<>();
                oldKey.put("GBP", v.getLabel());
                if(conversionMap.containsKey(oldKey)) {
                    conversionMap.put(key, conversionMap.get(oldKey) * e.getWeight());
                } else {
                    conversionMap.put(key, e.getWeight());
                }
            }
        }
        conversionMatrix = new ConversionMatrix(conversionMap);
        return conversionMatrix;
    }
}
