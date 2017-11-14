package sample;

import com.sun.javafx.geom.Vec2d;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Iterator;

public class Bridge {
    private ArrayList<BridgeSupport> supports;
    private ArrayList<BridgeSupportAnchorPoint> bridgeSupportAnchorPoints;

    Bridge(){
        //constuctor to be implemented
        bridgeSupportAnchorPoints = new ArrayList();
        supports = new ArrayList();
    }

    public void draw(GraphicsContext gc){
        Iterator<BridgeSupport> supportIterator;
        for(supportIterator = supports.iterator();supportIterator.hasNext();){
            supportIterator.next().draw(gc);
        }
    }

    public void addSupport(BridgeSupport inSupport){
        supports.add(inSupport);
    }

    public ArrayList<BridgeSupport> getSupports() {
        return supports;
    }

    public ArrayList<BridgeSupportAnchorPoint> getBridgeSupportAnchorPoints() {
        return bridgeSupportAnchorPoints;
    }


    public void createTestBridge(){
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(300,300,.1));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(50,100,.1));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(150,100,.1));

        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(0),bridgeSupportAnchorPoints.get(1), 10));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(1),bridgeSupportAnchorPoints.get(2), 10));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(2),bridgeSupportAnchorPoints.get(0), 10));
    }

    public void computeTimeStep(double d, double dt){
        // Compute gravitational and damping forces for all mass points
        //TODO
        /*
        Vec2 f1 = Vec2(0, -g * m) - d * v1;
        Vec2 f2 = Vec2(0, -g * m) - d * v2;
        Vec2 f3 = Vec2(0, -g * m) - d * v3;
        */

        BridgeSupportAnchorPoint ap1 = bridgeSupportAnchorPoints.get(0);
        BridgeSupportAnchorPoint ap2 = bridgeSupportAnchorPoints.get(1);
        BridgeSupportAnchorPoint ap3 = bridgeSupportAnchorPoints.get(2);

        BridgeSupport bs1 = supports.get(0);
        BridgeSupport bs2 = supports.get(1);
        BridgeSupport bs3 = supports.get(2);

        myVec f1 = new myVec(0, -9.81 * ap1.getWeight()).minus(ap1.getVelocity().smult(d));
        myVec f2 = new myVec(0, -9.81 * ap2.getWeight()).minus(ap2.getVelocity().smult(d));
        myVec f3 = new myVec(0, -9.81 * ap3.getWeight()).minus(ap3.getVelocity().smult(d));


        // Compute spring forces
        /*
        myVec springF1 = -k * ((p1 - p2).length() - L) * (p1 - p2).getNormalizedCopy();
        myVec springF2 = -k * ((p2 - p3).length() - L) * (p2 - p3).getNormalizedCopy();
        myVec springF3 = -k * ((p3 - p1).length() - L) * (p3 - p1).getNormalizedCopy();*/

        myVec p1 = ap1.getPos();
        myVec p2 = ap2.getPos();
        myVec p3 = ap3.getPos();

        double k1 = bs1.getSpringConstant();
        double k2 = bs2.getSpringConstant();
        double k3 = bs3.getSpringConstant();

        myVec springF1 = bs1.getNormalizedVec().smult(-k1 * (bs1.getCurrentLength() - bs1.getLength()));
        myVec springF2 = bs2.getNormalizedVec().smult(-k2 * (bs2.getCurrentLength() - bs2.getLength()));
        myVec springF3 = bs3.getNormalizedVec().smult(-k3 * (bs3.getCurrentLength() - bs3.getLength()));

        //System.out.println((bs1.getCurrentLength() - bs1.getLength()));

        // Add spring forces to vertices
        f1 = f1.plus(springF1.minus(springF3));
        f2 = f2.plus(springF2.minus(springF1));
        f3 = f3.plus(springF3.minus(springF2));

        // Additional force for collision
	    double kr = 100;
        if (p1.getY() < -1)
            f1 = f1.minus(new myVec(0,1 + p1.getY()).smult(kr));
        if (p2.getY() < -1)
            f2 = f2.minus(new myVec(0,1 + p2.getY()).smult(kr));
        if (p3.getY() < -1)
            f3 = f3.minus(new myVec(0,1 + p3.getY()).smult(kr));

        // Velocity update
        ap1.setVelocity(ap1.getVelocity().plus(f1.smult(dt/ap1.getWeight())));
        ap2.setVelocity(ap2.getVelocity().plus(f2.smult(dt/ap2.getWeight())));
        ap3.setVelocity(ap3.getVelocity().plus(f3.smult(dt/ap3.getWeight())));

        // Position update
        /*
        p1 += dt * v1;
        p2 += dt * v2;
        p3 += dt * v3;*/
        //p1 = ap1.getVelocity().smult(dt);
        p2 = ap2.getVelocity().smult(dt);
        p3 = ap3.getVelocity().smult(dt);

        //ap1.setPos(ap1.getPos().plus(p1));
        ap2.setPos(ap2.getPos().plus(p2));
        ap3.setPos(ap3.getPos().plus(p3));

        //System.out.println(p1.getY());

    }

}
