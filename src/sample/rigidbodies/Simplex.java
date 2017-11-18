package sample.rigidbodies;

import sample.myVec;

public class Simplex {
    myVec pointA,pointB,pointC;
    public Simplex(myVec a,myVec b,myVec c){
        pointA = a;
        pointB = b;
        pointC = c;
    }

    public boolean isInside(myVec point){
        return true;
    }
}
