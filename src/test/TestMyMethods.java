package test;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Ignore;
import sample.Vec2;
import sample.rigidbodies.Simplex;
import sample.utils.HelperClass;

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

        mySimplex.addPoint(new Vec2(-2,0));
        mySimplex.addPoint(new Vec2(1,-1));
        mySimplex.addPoint(new Vec2(1,1));

        assertEquals(mySimplex.containsOrigin(),true);
    }

    @Test
    public void testGJK(){

    }
}
