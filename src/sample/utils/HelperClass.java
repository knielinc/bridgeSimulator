package sample.utils;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import sample.Vec2;
import sample.rigidbodies.PolytopeEdge;
import sample.rigidbodies.RigidBodyObject;
import sample.rigidbodies.Simplex;

import java.awt.*;
import java.util.ArrayList;

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

    public static Vec2 getMinkovskiPointForDirection(Vec2 direction, RigidBodyObject polygon1, RigidBodyObject polygon2){
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


    public static boolean gjk(RigidBodyObject polygon1, RigidBodyObject polygon2){

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

    public static Simplex gjkForEPA(RigidBodyObject polygon1, RigidBodyObject polygon2){

        Simplex mySimplex = new Simplex();
        Vec2 direction = new Vec2(1,1);
        mySimplex.addPoint(getMinkovskiPointForDirection(direction,polygon1,polygon2));

        direction = direction.smult(-1);

        while (true){
            mySimplex.addPoint(getMinkovskiPointForDirection(direction,polygon1,polygon2));

            if (mySimplex.getLast().dot(direction) <= 0){
                return mySimplex;
            } else {
                if (mySimplex.containsOriginForGJK(direction)){
                    return mySimplex;
                }
            }
        }
    }

    public static Vec2 EPA(RigidBodyObject polygon1, RigidBodyObject polygon2){
        //polytope = last iteration of GJK

        Simplex mySimplex = gjkForEPA(polygon1, polygon2);

        boolean collides = mySimplex.containsOrigin();

        ArrayList<Vec2>  polytope =  mySimplex.getPoints();

        ArrayList<PolytopeEdge> edges = new ArrayList();

        for(int i = 0; i < polytope.size(); i++){
            int curr = i;

            int next = Math.floorMod(i + 1, polytope.size());

            edges.add(new PolytopeEdge(polytope.get(curr),polytope.get(next)));
        }
        while(true){
            double minDistance = Double.MAX_VALUE;

            PolytopeEdge closestEdge = edges.get(0);

            for (PolytopeEdge currEdge: edges){
                if (currEdge.getDistanceToCenter() < minDistance){
                    closestEdge = currEdge;
                    minDistance = currEdge.getDistanceToCenter();
                }
            }

            Vec2 translationVec = closestEdge.getOuterNormalVecFromOrigin();
            Vec2 nextSuppdirection = translationVec;
            //if origin is inside go away from center to get next point and vice versa if origin is outside

            if (collides){
                nextSuppdirection = nextSuppdirection.smult(-1);
            }

            Vec2 nextPoint = getMinkovskiPointForDirection(nextSuppdirection,polygon1,polygon2);

            if (closestEdge.containsPoint(nextPoint)){
                return translationVec;
            } else {
                edges.add(new PolytopeEdge(closestEdge.geta(),nextPoint));
                edges.add(new PolytopeEdge(nextPoint,closestEdge.getb()));

                edges.remove(closestEdge);
            }
        }
    }

}
