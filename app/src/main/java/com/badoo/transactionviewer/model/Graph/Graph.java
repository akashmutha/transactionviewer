package com.badoo.transactionviewer.model.Graph;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mutha on 21/06/16.
 */
public class Graph {

    private HashMap<String, Vertex> vertices;
    private HashMap<Integer, Edge> edges;

    public int getNoOfVertex() {
        return this.noOfVertex;
    }

    public void setNoOfVertex(int noOfVertex) {
        this.noOfVertex = noOfVertex;
    }

    public HashMap<Integer, Edge> getEdges() {
        return this.edges;
    }

    public void setEdges(HashMap<Integer, Edge> edges) {
        this.edges = edges;
    }

    public HashMap<String, Vertex> getVertices() {
        return this.vertices;
    }

    public void setVertices(HashMap<String, Vertex> vertices) {
        this.vertices = vertices;
    }

    private int noOfVertex;

    public Graph(){
        this.vertices = new HashMap<>();
        this.edges = new HashMap<>();
        this.noOfVertex = 0;
    }

    public Graph(HashMap<String, Vertex> vertices) {
        this.vertices = vertices;
        this.edges = new HashMap<>();
        this.noOfVertex = vertices != null ? vertices.size() : 0;
    }

    public Graph(HashMap<String, Vertex> vertices, HashMap<Integer, Edge> edges, int noOfVertex) {
        this.vertices = vertices;
        this.edges = edges;
        this.noOfVertex = noOfVertex;
    }

    public Graph(ArrayList<Vertex> vertices) {
        this.vertices = new HashMap<>();
        this.edges = new HashMap<>();
        for(Vertex v : vertices){
            this.vertices.put(v.getLabel(), v);
        }
        this.noOfVertex = vertices != null ? vertices.size() : 0;
    }

    public boolean addVertex(Vertex vertex){
       if(vertices.containsKey(vertex.getLabel())){
           return false;
       }
        vertices.put(vertex.getLabel(), vertex);
        return true;
    }

    public boolean addEdge(Vertex one , Vertex two){
        return addEdge(one, two, 1);
    }

    public boolean addEdge(Vertex one, Vertex two, double weight) {
        Edge e = new Edge(one, two, weight);
        if(edges.containsKey(e.hashCode()) || one.containsNeighbour(e) || two.containsNeighbour(e)){
            return false;
        }
        edges.put(e.hashCode(), e);
        this.getVertices().get(one.getLabel()).addNeighbour(e);
        return true;
    }
}
