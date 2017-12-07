package sample.utils;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import sample.Vec2;
import sample.rigidbodies.EPAEdge;
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
        mySimplex.addPoint(direction,polygon1,polygon2);

        direction = direction.smult(-1);

        while (true){
            mySimplex.addPoint(direction,polygon1,polygon2);

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

        if(polygon1.getxPos() == 323){
            System.out.println("edgecase found!");
        }
        Simplex mySimplex = new Simplex();
        Vec2 direction = new Vec2(1,1);

        mySimplex.addPoint(direction,polygon1,polygon2);


        direction = direction.smult(-1);

        while (true){
            mySimplex.addPoint(direction,polygon1,polygon2);


            if (mySimplex.getLast().dot(direction) <= 0){
                mySimplex.setCollides(false);
                return mySimplex;
            } else {
                if (mySimplex.containsOriginForGJK(direction)){
                    mySimplex.setCollides(true);
                    return mySimplex;
                }
            }
        }
    }

    public static Vec2[] EPA(RigidBodyObject rb1, RigidBodyObject rb2){
        //polytope = last iteration of GJK

        Simplex mySimplex = gjkForEPA(rb1, rb2);

        boolean collides = mySimplex.collides();

        ArrayList<Vec2>  polytope =  mySimplex.getPoints();
        ArrayList<Vec2>  supportPointsA =  mySimplex.getSupportPointsA();
        ArrayList<Vec2>  supportPointsB =  mySimplex.getSupportPointsB();

        ArrayList<PolytopeEdge> edges = new ArrayList();
        ArrayList<PolytopeEdge> supportEdgesA = new ArrayList();
        ArrayList<PolytopeEdge> supportEdgesB = new ArrayList();

        Vec2 pointA,pointB;
        pointA = Vec2.VEC_ZERO;
        pointB = Vec2.VEC_ZERO;

        for(int i = 0; i < polytope.size(); i++){
            int curr = i;

            int next = Math.floorMod(i + 1, polytope.size());

            edges.add(new PolytopeEdge(polytope.get(curr),polytope.get(next)));

            supportEdgesA.add(new PolytopeEdge(supportPointsA.get(curr),supportPointsA.get(next)));
            supportEdgesB.add(new PolytopeEdge(supportPointsB.get(curr),supportPointsB.get(next)));

        }
        while(true){
            double minDistance = Double.MAX_VALUE;

            PolytopeEdge closestEdge = edges.get(0);
            int index = 0;
            int indexClosestEdge = 0;
            for (PolytopeEdge currEdge: edges){
                if (currEdge.getDistanceToCenter() < minDistance){
                    closestEdge = currEdge;
                    minDistance = currEdge.getDistanceToCenter();

                    indexClosestEdge = index;
                }
                index++;
            }

            Vec2 translationVec = closestEdge.getOuterNormalVecFromOrigin();

            pointA = supportEdgesA.get(indexClosestEdge).getPointWithT(closestEdge.getT());
            pointB = supportEdgesB.get(indexClosestEdge).getPointWithT(closestEdge.getT());


            Vec2 nextSuppdirection;
            if (translationVec.length() != 0) {
                nextSuppdirection = translationVec;
                //if origin is inside go away from center to get next point and vice versa if origin is outside

            } else {
                Vec2 normal = closestEdge.getNormal(collides).normalize();

                //abusing the fact, that the shape is convex i project the vector from any point in the Polytope not on the Edge to the closestPoint on the Edge onto the normal of the closestEdge
                int nextEdge = Math.floorMod(indexClosestEdge + 1 ,edges.size());

                Vec2 otherPoint;

                if (closestEdge.containsPoint(edges.get(nextEdge).geta())){
                    otherPoint = edges.get(nextEdge).getb();
                } else {
                    otherPoint = edges.get(nextEdge).geta();
                }
                otherPoint = otherPoint.minus(closestEdge.getClosestPointToOrigin());
                otherPoint.smult(-1);
                nextSuppdirection = normal.smult(normal.dot(otherPoint));

            }

            if (collides) {
                nextSuppdirection = nextSuppdirection.smult(-1);
            }

            Vec2 nextPoint = getMinkovskiPointForDirection(nextSuppdirection,rb1,rb2);
            Vec2 nextSupportPointA = rb1.getSupport(nextSuppdirection);
            Vec2 nextSupportPointB = rb2.getSupport(nextSuppdirection.smult(-1));


            if (closestEdge.containsPoint(nextPoint)){
                rb1.setCollisionPoint(pointA);
                rb1.setCollisionCorrectionVec(translationVec);
                rb2.setCollisionPoint(pointB);
                if(nextSuppdirection.length() == 0){
                    System.out.println("0vec for nextSuppDirection");
                }
                return new Vec2[]{translationVec,pointA,pointB,nextSuppdirection.normalize().smult(-1)};
            } else {
                edges.add(new PolytopeEdge(closestEdge.geta(),nextPoint));
                edges.add(new PolytopeEdge(nextPoint,closestEdge.getb()));

                supportEdgesA.add(new PolytopeEdge(supportEdgesA.get(indexClosestEdge).geta(),nextSupportPointA));
                supportEdgesA.add(new PolytopeEdge(nextSupportPointA,supportEdgesA.get(indexClosestEdge).getb()));

                supportEdgesB.add(new PolytopeEdge(supportEdgesB.get(indexClosestEdge).geta(),nextSupportPointB));
                supportEdgesB.add(new PolytopeEdge(nextSupportPointB,supportEdgesB.get(indexClosestEdge).getb()));

                edges.remove(closestEdge);

                supportEdgesA.remove(indexClosestEdge);
                supportEdgesB.remove(indexClosestEdge);
            }
        }
    }


    public static Vec2[] EPA2(RigidBodyObject rb1, RigidBodyObject rb2){
        //polytope = last iteration of GJK

        Simplex mySimplex = gjkForEPA(rb1, rb2);

        boolean collides = mySimplex.collides();

        ArrayList<Vec2>  s =  mySimplex.getPoints();
        ArrayList<Vec2>  supportPointsA =  mySimplex.getSupportPointsA();
        ArrayList<Vec2>  supportPointsB =  mySimplex.getSupportPointsB();

        while(true){
            //find closest edge
            EPAEdge e = findClosestEdge(s,collides);

            Vec2 p = getMinkovskiPointForDirection(e.getNormal(),rb1,rb2);

            double d = p.dot(e.getNormal());

            Vec2 nextSupportPointA = rb1.getSupport(e.getNormal());
            Vec2 nextSupportPointB = rb2.getSupport(e.getNormal().smult(-1));

            if(d - e.getDistance() < 0.00001){
                Vec2 normal = e.getNormal();
                double depth = d;

                int indexBefore = e.getIndex() - 1 >= 0 ? e.getIndex() - 1:s.size()-1;
                int nextIndex = e.getIndex() + 1 < s.size() ? e.getIndex() + 1:0;

                PolytopeEdge closestEdge = new PolytopeEdge(s.get(indexBefore),s.get(e.getIndex()));
                PolytopeEdge EdgeA = new PolytopeEdge(supportPointsA.get(indexBefore),supportPointsA.get(e.getIndex()));
                PolytopeEdge EdgeB = new PolytopeEdge(supportPointsB.get(indexBefore),supportPointsB.get(e.getIndex()));

                Vec2 pointA = EdgeA.getPointWithT(closestEdge.getT());
                Vec2 pointB = EdgeB.getPointWithT(closestEdge.getT());

                Vec2 translationVec = normal.smult(-d);

                rb1.setCollisionPoint(pointA);
                rb1.setCollisionCorrectionVec(translationVec);
                rb2.setCollisionPoint(pointB);

                return new Vec2[]{translationVec,pointA,pointB,normal.smult(-1)};
            } else {
                s.add(e.getIndex(),p);
                supportPointsA.add(e.getIndex(),nextSupportPointA);
                supportPointsB.add(e.getIndex(),nextSupportPointB);
            }
        }

    }

    public static EPAEdge findClosestEdge(ArrayList<Vec2> s,boolean orientationIsClockwise){
        EPAEdge closest = new EPAEdge();

        closest.setDistance(Double.MAX_VALUE);


        for(int i = 0; i < s.size(); i++){

            int j = i + 1 == s.size() ? 0 : i + 1;

            Vec2 a = s.get(i);

            Vec2 b = s.get(j);

            Vec2 e = b.minus(a);

            Vec2 n;// = tripleProduct(e,oa,e);

            if(orientationIsClockwise){
                n = new Vec2(e.getY(),-e.getX());
            } else {
                n = new Vec2(-e.getY(),e.getX());
            }

            if(n.length() > 0){
                n = n.normalize();
            } else {
                n = new Vec2(0,1);
            }

            double d = n.dot(a);

            if(d < closest.getDistance()){
                closest.setDistance(d);
                closest.setNormal(n);
                closest.setIndex(j);
            }

        }
        return closest;

    }

}
