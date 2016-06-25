package com.badoo.transactionviewer.model.Graph;

/**
 * Created by mutha on 25/06/16.
 */
public class Edge {

    private Vertex one, two;
    private double weight;

    public Edge(Vertex one, Vertex two) {
        this.one = one;
        this.two = two;
    }

    public Edge(Vertex one, Vertex two, double weight) {
        this.one = one;
        this.two = two;
        this.weight = weight;
    }

    public Vertex getOne() {
        return this.one;
    }

    public void setOne(Vertex one) {
        this.one = one;
    }

    public Vertex getTwo() {
        return this.two;
    }

    public void setTwo(Vertex two) {
        this.two = two;
    }

    public double getWeight() {
        return this.weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    private Vertex getNeighbour(Vertex current){
        if(!(current.equals(one) || current.equals(two))){
            return null;
        }
        return (current.equals(one)) ? two : one;
    }

    public String toString(){
        return "({" + one + ", " + two + "}, " + weight + ")";
    }

    public boolean equals(Object other){
        if(!(other instanceof Edge)){
            return false;
        }
        Edge e = (Edge) other;
        return e.one.equals(this.one) && e.two.equals(this.two);
    }
}
