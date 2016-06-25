package com.badoo.transactionviewer.model.Graph;

import java.util.ArrayList;

/**
 * Created by mutha on 25/06/16.
 */
public class Vertex {

    public String label;
    public ArrayList<Edge> neighbours;

    /**
     *  Construct the vertexes
     * @param label
     */

    public Vertex(String label) {
        this.label = label;
        this.neighbours = new ArrayList<>();
    }

    public Vertex(String label, ArrayList<Edge> neighbours) {
        this.label = label;
        this.neighbours = neighbours;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setNeighbours(ArrayList<Edge> neighbours) {
        this.neighbours = neighbours;
    }

    public void addNeighbour(Edge edge){
        if(this.neighbours.contains(edge)){
            return;
        }
        this.neighbours.add(edge);
    }

    public boolean containsNeighbour(Edge edge){
        return this.neighbours.contains(edge);
    }

    public Edge getNeighbour(int index){
        return this.neighbours.get(index);
    }

    public Edge removeNeighbour(int index){
        return this.neighbours.remove(index);
    }

    public int getNeighbourCount(){
        return this.neighbours.size();
    }

    public String getLabel(){
        return this.label;
    }

    public String toString(){
        return "Vertex " + label;
    }

    public int hashCode(){
        return this.label.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if(!(other instanceof Vertex)) {
            return false;
        }

        Vertex v = (Vertex) other;
        return this.label.equals(v.label);
    }

    public ArrayList<Edge> getNeighbours(){
        return new ArrayList<Edge>(this.neighbours);
    }
}
