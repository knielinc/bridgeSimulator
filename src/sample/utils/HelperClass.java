package sample.utils;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import sample.Vec2;
import sample.rigidbodies.DrawablePolygon;
import sample.rigidbodies.Simplex;

public class HelperClass {

    public static RealVector gaussSeidel(RealMatrix M, RealVector b){
        int dimension = b.getDimension();
        RealVector oldvec = new ArrayRealVector(dimension);
        RealVector out = new ArrayRealVector(dimension);
        int cutoff = 100;
        double threshold = 0.0000001;
        for (int iterationstep = 0; iterationstep < cutoff; iterationstep++) {
            for (int k = 0; k < dimension; k++) {
                double lhs = 0, rhs = 0;

                for (int i = 0; i < k; i++) {
                    lhs += M.getEntry(k, i) * out.getEntry(i);
                }

                for (int i = k + 1; i < dimension; i++) {
                    lhs += M.getEntry(k, i) * out.getEntry(i);
                }
                double entry = M.getEntry(k,k);
                double x_k = 1.0 / entry * (b.getEntry(k) - lhs - rhs);

                out.setEntry(k, x_k);
            }
            RealVector residual = oldvec.subtract(out);
            double myError = residual.getNorm();

            //System.out.println("it: " + iterationstep + " Error: " + myError);

            if(myError < threshold){
                break;
            }
            oldvec = out.copy();

            //System.out.println(iterationstep + " error: " +error);

        }
        return out;
    }

    public static Vec2 getMinkovskiPointForDirection(Vec2 direction, DrawablePolygon polygon1, DrawablePolygon polygon2){
        return polygon1.getSupport(direction).minus(polygon2.getSupport(direction.smult(-1)));
    }

    public static Vec2 tripleProduct(Vec2 a, Vec2 b, Vec2 c){
        //(a x b) x c
        return a.smult(-1 * c.dot(b)).plus(b.smult(c.dot(a)));
    }

    public static Vec2 getNextSupportDirection(Vec2 pointA, Vec2 pointB){
        Vec2 AB = pointB.minus(pointA);
        Vec2 AO = pointA.smult(-1);

        // old code AO.smult(AB.dot(AB)).minus(AB.smult(AB.dot(AO)));

        return tripleProduct(AB,AO,AB);
    }


    public static boolean gjk(DrawablePolygon polygon1, DrawablePolygon polygon2){
        /*
        // get the first Minkowski Difference point
        Simplex.add(support(A, B, d));
        // negate d for the next point
        d.negate();
        // start looping
        while (true) {
            // add a new point to the simplex because we haven't terminated yet
            Simplex.add(support(A, B, d));
            // make sure that the last point we added actually passed the origin
            if (Simplex.getLast().dot(d) <= 0) {
                // if the point added last was not past the origin in the direction of d
                // then the Minkowski Sum cannot possibly contain the origin since
                // the last point added is on the edge of the Minkowski Difference
                return false;
            } else {
                // otherwise we need to determine if the origin is in
                // the current simplex
                if (Simplex.contains(ORIGIN)) {
                    // if it does then we know there is a collision
                    return true;
                } else {
                    // otherwise we cannot be certain so find the edge who is
                    // closest to the origin and use its normal (in the direction
                    // of the origin) as the new d and continue the loop
                    d = getDirection(Simplex);
                }
            }
        }
        */
        Simplex mySimplex = new Simplex();
        Vec2 direction = new Vec2(1,1);
        mySimplex.addPoint(getMinkovskiPointForDirection(direction,polygon1,polygon2));

        direction = direction.smult(-1);

        while (true){
            mySimplex.addPoint(getMinkovskiPointForDirection(direction,polygon1,polygon2));

            if (mySimplex.getLast().dot(direction) <= 0){
                return false;
            } else {
                if (mySimplex.containsOriginForGJK(direction)){
                    return true;
                }
            }
        }
    }

    public static Vec2 epa(){
        //TODO IMPLEMENT

        return Vec2.VEC_ZERO;
    }
}
