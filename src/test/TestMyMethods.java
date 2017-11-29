package test;

import static org.junit.Assert.*;

import com.sun.tools.corba.se.idl.toJavaPortable.Helper;
import org.junit.Test;
import org.junit.Ignore;
import sample.Vec2;
import sample.rigidbodies.DrawablePolygon;
import sample.rigidbodies.PolytopeEdge;
import sample.rigidbodies.RigidBodyObject;
import sample.rigidbodies.Simplex;
import sample.utils.HelperClass;

import java.util.ArrayList;

public class TestMyMethods {
    @Test
    public void rightOrientationOfNextDirectionInGJK(){

        Vec2 direction = HelperClass.getNextSupportDirection(new Vec2(-2,0),new Vec2(0,-2));
        direction = direction.normalize();
        Vec2 expectedDirection = new Vec2(1,1).normalize();
        assertEquals(direction,expectedDirection);
    }

    @Test
    public void testSimplex(){

        Simplex mySimplex = new Simplex();
        /*
        mySimplex.addPoint(new Vec2(-2,0));
        mySimplex.addPoint(new Vec2(1,-1));
        mySimplex.addPoint(new Vec2(1,1));
        */
        assertEquals(mySimplex.containsOrigin(),true);
    }

    @Test
    public void testGJK(){
        DrawablePolygon rectangle = new DrawablePolygon(new double[]{-10.,10.,10.,-10.},new double[]{-10.,-10.,10.,10.},4,1);
        RigidBodyObject rigidBody1 = new RigidBodyObject(15,0,0,1,false, rectangle);
        RigidBodyObject rigidBody2 = new RigidBodyObject(-15,0,0,1,false, rectangle);
        RigidBodyObject rigidBody3 = new RigidBodyObject(-6,0,0,1,false, rectangle);

        RigidBodyObject rigidBody4 = new RigidBodyObject(0,0,0,1,false, rectangle);
        RigidBodyObject rigidBody5 = new RigidBodyObject(0,15,0,1,false, rectangle);


        Vec2 xDirection = new Vec2(1,0);

        boolean test1 = HelperClass.gjk(rigidBody1,rigidBody2);
        boolean test2 = HelperClass.gjk(rigidBody1,rigidBody2);
        boolean test3 = HelperClass.gjk(rigidBody1,rigidBody3);
        Vec2 newSupport = rigidBody3.getSupport(xDirection);
        rigidBody3.setTorque(Math.PI / 4.0);
        Vec2 newSuppor2 = rigidBody3.getSupport(xDirection);
        boolean test4 = HelperClass.gjk(rigidBody1,rigidBody3);

        Vec2[] translation = HelperClass.EPA(rigidBody1,rigidBody3);
        Vec2[] translation2 = HelperClass.EPA(rigidBody4,rigidBody5);

        assertEquals(test1,false);
        assertEquals(test2,false);
        assertEquals(test3,false);
        assertEquals(test4,true);

    }

    @Test

    public void testDistanceToSegment(){

        PolytopeEdge edge = new PolytopeEdge(new Vec2(-1,0),new Vec2(1,0));

        double result1 = edge.getDistanceToCenter();
        double result2 = edge.getDistanceToPoint(new Vec2(2,0));
        double result3 = edge.getDistanceToPoint(new Vec2(0,1));

        double result4 = edge.getDistanceToPoint(new Vec2(2,1));

        double result5 = edge.getDistanceToPoint(new Vec2(-2,1));

    }
}
