package sample.rigidbodies;

import sample.Vec2;

public class PolytopeEdge {
    private Vec2 a,b;

    private double distanceToCenter = Double.NaN;

    private Vec2 outerNormalVecFromOrigin = new Vec2(Double.NaN,Double.NaN);

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

    public Vec2 getClosestPointToOrigin(){
        return getPointWithT(getT());
    }

    public double getDistanceToCenter(){

        if(Double.isNaN(distanceToCenter)) {
            return getDistanceToPoint(new Vec2(0, 0));
        } else {
            return distanceToCenter;
        }

    }

    public Vec2 getNormal(){
        Vec2 n = b.minus(a);
        return  new Vec2(n.getY(),-n.getX());

    }

    public Vec2 getOuterNormalVecFromOrigin(){
        if (Double.isNaN(outerNormalVecFromOrigin.getX()) || Double.isNaN(outerNormalVecFromOrigin.getY())) {
            Vec2 n = b.minus(a);

            Vec2 returnVec;

            t = (a.smult(-1)).mul(n).dot(new Vec2(1, 1)) / n.dot(n);

            if (t < 0) {

                returnVec = (a.smult(-1));

            } else if (t > 1) {

                returnVec = (b.smult(-1));

            } else {
                n = n.normalize();

                Vec2 projectionVec = a.minus(n.smult(a.dot(n)));

                returnVec = projectionVec.smult(-1);
            }

            // make returnvec point away from center
            return returnVec;
        } else {
            return outerNormalVecFromOrigin;
        }

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
