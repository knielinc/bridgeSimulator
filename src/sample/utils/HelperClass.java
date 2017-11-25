package sample.utils;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import sample.Vec2;
import sample.rigidbodies.DrawablePolygon;

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

    private Vec2 getNextSupportDirection(Vec2 pointA, Vec2 pointB){
        Vec2 AB = pointA.minus(pointB);
        Vec2 AO = pointA.smult(-1);

        AO.smult(AB.dot(AB)).minus(AB.smult(AB.dot(AO)));

        return AO.smult(AB.dot(AB)).minus(AB.smult(AB.dot(AO)));
    }

    public static boolean gjk(){

        return true;
    }

    public static Vec2 epa(){
        //TODO IMPLEMENT

        return Vec2.VEC_ZERO;
    }
}
