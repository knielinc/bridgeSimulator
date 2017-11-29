package sample.rigidbodies;

import sample.Vec2;

public class PolytopeEdge {
    private Vec2 a,b;

    private double t = Double.NaN;

    public PolytopeEdge(Vec2 a, Vec2 b){
        this.a = a;
        this.b = b;
    }

    public Vec2 geta() {
        return a;
    }

    public Vec2 getb() {
        return b;
    }

    public double getDistanceToPoint(Vec2 point){

        Vec2 n = b.minus(a);

        double distance;

        double t = point.minus(a).mul(n).dot(new Vec2(1,1)) / n.dot(n);

        if (t < 0){

            distance = point.minus(a).length();

        } else if (t > 1){

            distance = point.minus(b).length();

        } else {
            n = n.normalize();

            Vec2 projectionVec = a.minus(point).minus(n.smult(a.minus(point).dot(n)));

            distance = projectionVec.length();
        }

        return distance;
    }

    public double getDistanceToCenter(){

        return getDistanceToPoint(new Vec2(0,0));

    }

    public Vec2 getOuterNormalVecFromOrigin(){

        Vec2 n = b.minus(a);

        Vec2 returnVec;

        t = (a.smult(-1)).mul(n).dot(new Vec2(1,1)) / n.dot(n);

        if (t < 0){

            returnVec = (a.smult(-1));

        } else if (t > 1){

            returnVec = (b.smult(-1));

        } else {
            n = n.normalize();

            Vec2 projectionVec = a.minus(n.smult(a.dot(n)));

            returnVec = projectionVec.smult(-1);
        }

        // make returnvec point away from center

        returnVec = returnVec;
        return returnVec;

    }

    public boolean containsPoint(Vec2 point){
        return a.equals(point) || b.equals(point);
    }

    public double getT() {
        if(!Double.isNaN(t)){
            return t;
        } else {
            Vec2 n = b.minus(a);
            t = (a.smult(-1)).mul(n).dot(new Vec2(1,1)) / n.dot(n);
            return t;
        }
    }

    public Vec2 getPointWithT(double inT){
        Vec2 n = b.minus(a);
        return n.smult(inT).plus(a);
    }
}
