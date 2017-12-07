package sample.rigidbodies;

import sample.Vec2;

public class EPAEdge {
    private double distance;
    private Vec2 normal;
    private int index;

    public EPAEdge(){

    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setNormal(Vec2 normal) {
        this.normal = normal;
    }

    public double getDistance() {
        return distance;
    }

    public int getIndex() {
        return index;
    }

    public Vec2 getNormal() {
        return normal;
    }
}
