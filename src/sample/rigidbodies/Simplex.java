package sample.rigidbodies;

import com.sun.tools.corba.se.idl.toJavaPortable.Helper;
import sample.Vec2;
import sample.utils.HelperClass;

import java.util.ArrayList;

public class Simplex {
    //Vec2[] points = new Vec2[3];
    ArrayList<Vec2> points = new ArrayList<>();

    ArrayList<Vec2> supportPointsA = new ArrayList<>();
    ArrayList<Vec2> supportPointsB = new ArrayList<>();
    private boolean collides = false;

    int oldestVal = 0;
    boolean isFull = false;

    public Simplex(){

    }

    public void addPoint(Vec2 direction, RigidBodyObject ro1,RigidBodyObject ro2){

        Vec2 inPoint = HelperClass.getMinkovskiPointForDirection(direction, ro1, ro2);
        Vec2 suppA = ro1.getSupport(direction);
        Vec2 suppB = ro2.getSupport(direction.smult(-1));


        if (!isFull){
            if (points.size() == 2){
                isFull = true;
            }
            points.add(inPoint);
            supportPointsA.add(suppA);
            supportPointsB.add(suppB);
        } else {
            points.remove(0);
            supportPointsA.remove(0);
            supportPointsB.remove(0);
        }
    }
    /*
    public void addPoint(Vec2 inPoint){

        if (!isFull){
            if (points.size() == 2){
                isFull = true;
            }
            points.add(inPoint);
        } else {
            points.remove(0);
        }
    }
    */

    public boolean isInside(Vec2 point){
        return true;
    }

    public Vec2 getLast(){
        return points.get(points.size()-1);
    }

    public Vec2 getB(){
        return points.get(points.size()-2);
    }

    public void setB(Vec2 newB){
        points.set(1,newB);
    }

    public Vec2 getC(){
        return points.get(0);
    }

    public void setC(Vec2 newC){
        points.set(0,newC);
    }

    public boolean containsOriginForGJK(Vec2 nextDirection){
        /*
        For each edge (each side) of a triangle you need two things:
        a normal vector (a perpendicular) to that side towards the Origin and a vector from the Origin to opposite vertex of the triangle.
        Then you check if dot product of the two vectors is positive (greater than zero)...
         */
        Vec2 a = getLast();

        Vec2 ao = a.smult(-1);

        if (isFull){
            Vec2 b = getB();
            Vec2 c = getC();

            Vec2 ab = b.minus(a);
            Vec2 ac = c.minus(a);

            Vec2 abPerp = HelperClass.tripleProduct(ac,ab,ab);
            Vec2 acPerp = HelperClass.tripleProduct(ab,ac,ac);

            if (abPerp.dot(ao) > 0){
                points.remove(c);

                supportPointsA.remove(0);
                supportPointsB.remove(0);

                isFull = false;

                if(abPerp.length() > 0) {
                    nextDirection.set(abPerp.normalize());
                } else {
                    nextDirection.set(new Vec2(ab.getY(),-ab.getX()));//bullshitcode
                }

            } else if(acPerp.dot(ao) > 0){
                points.remove(b);

                supportPointsA.remove(1);
                supportPointsB.remove(1);

                isFull = false;

                if(acPerp.length() > 0) {
                    nextDirection.set(acPerp.normalize());
                } else {
                    nextDirection.set(new Vec2(ac.getY(),-ac.getX()));
                }

            } else {
                return true;
            }
        } else {
            //only 2 points
            Vec2 b = getB();

            Vec2 ab = b.minus(a);

            Vec2 abPerp = HelperClass.tripleProduct(ab, ao, ab);
            if(abPerp.length() > 0) {
                nextDirection.set(abPerp.normalize());
            } else {
                nextDirection.set(new Vec2(ab.getY(),-ab.getX()));
            }
        }
        return false;
    }

    public boolean containsOrigin(){
        if (isFull){
            for (int i = 0; i < 3;i++){
                int i1,i2,i3;
                i1 = Math.floorMod(i,3);
                i2 = Math.floorMod(i + 1,3);
                i3 = Math.floorMod(i + 2,3);

                //getNextSupportDirection yields a perpendicular vector to the line created from 2 points towards the origin
                Vec2 direction = HelperClass.getNextSupportDirection(points.get(i1),points.get(i2));

                if (direction.dot(points.get(i3)) <= 0){
                    return false;
                }
            }
        } else{
            return false;
        }

        return true;
    }

    public ArrayList<Vec2> getPoints() {
        return points;
    }

    public ArrayList<Vec2> getSupportPointsA() {
        return supportPointsA;
    }

    public ArrayList<Vec2> getSupportPointsB() {
        return supportPointsB;
    }

    public void setCollides(boolean collides) {
        this.collides = collides;
    }

    public boolean collides() {
        return collides;
    }
}
