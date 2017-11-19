package sample.rigidbodies;

import sample.Vec2;

public class Simplex {
    Vec2 pointA,pointB,pointC;
    public Simplex(Vec2 a, Vec2 b, Vec2 c){
        pointA = a;
        pointB = b;
        pointC = c;
    }

    public boolean isInside(Vec2 point){
        return true;
    }
}
