package sample.bridge;

import javafx.scene.canvas.GraphicsContext;
import sample.myVec;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

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
        //line 0-11 left -> right
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(100,200,1,true));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(150,200,1,false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(200,200,1,false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(250,200,1,false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(300,200,1,false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(350,200,1,false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(450,200,1,false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(500,200,1,false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(550,200,1,false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(600,200,1,false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(650,200,1,false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(700,200,1,true));
        //road
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(0),bridgeSupportAnchorPoints.get(1),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(1),bridgeSupportAnchorPoints.get(2),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(2),bridgeSupportAnchorPoints.get(3),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(3),bridgeSupportAnchorPoints.get(4),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(4),bridgeSupportAnchorPoints.get(5),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(5),bridgeSupportAnchorPoints.get(6),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(6),bridgeSupportAnchorPoints.get(7),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(7),bridgeSupportAnchorPoints.get(8),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(8),bridgeSupportAnchorPoints.get(9),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(9),bridgeSupportAnchorPoints.get(10),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(10),bridgeSupportAnchorPoints.get(11),70));

        //upper upper support points 12 -> 17
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(100,150,1,true));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(250,300,1,false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(350,350,1,false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(450,350,1,false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(550,300,1,false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(700,150,1,true));
        //lower upper support points 18 -> 23
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(100,100,1,true));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(250,250,1,false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(350,300,1,false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(450,300,1,false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(550,250,1,false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(700,100,1,true));
        //upper lower support points 24 -> 26
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(350,250,1,false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(450,250,1,false));

        //upper upper supports
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(12),bridgeSupportAnchorPoints.get(1),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(1),bridgeSupportAnchorPoints.get(13),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(13),bridgeSupportAnchorPoints.get(14),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(14),bridgeSupportAnchorPoints.get(15),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(15),bridgeSupportAnchorPoints.get(16),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(16),bridgeSupportAnchorPoints.get(10),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(10),bridgeSupportAnchorPoints.get(17),70));
        //can be commented out for original bridge (will break though)
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(16),bridgeSupportAnchorPoints.get(11),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(0),bridgeSupportAnchorPoints.get(13),70));


        //middle upper supports
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(12),bridgeSupportAnchorPoints.get(2),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(2),bridgeSupportAnchorPoints.get(13),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(13),bridgeSupportAnchorPoints.get(19),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(13),bridgeSupportAnchorPoints.get(20),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(14),bridgeSupportAnchorPoints.get(20),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(15),bridgeSupportAnchorPoints.get(21),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(14),bridgeSupportAnchorPoints.get(21),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(15),bridgeSupportAnchorPoints.get(20),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(21),bridgeSupportAnchorPoints.get(16),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(16),bridgeSupportAnchorPoints.get(22),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(16),bridgeSupportAnchorPoints.get(9),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(9),bridgeSupportAnchorPoints.get(17),70));


        //lower upper supports/*
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(18),bridgeSupportAnchorPoints.get(2),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(2),bridgeSupportAnchorPoints.get(19),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(19),bridgeSupportAnchorPoints.get(20),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(20),bridgeSupportAnchorPoints.get(21),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(21),bridgeSupportAnchorPoints.get(22),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(22),bridgeSupportAnchorPoints.get(9),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(9),bridgeSupportAnchorPoints.get(23),70));

        //smaller supports
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(4),bridgeSupportAnchorPoints.get(24),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(5),bridgeSupportAnchorPoints.get(24),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(5),bridgeSupportAnchorPoints.get(25),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(6),bridgeSupportAnchorPoints.get(24),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(6),bridgeSupportAnchorPoints.get(25),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(7),bridgeSupportAnchorPoints.get(25),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(24),bridgeSupportAnchorPoints.get(25),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(24),bridgeSupportAnchorPoints.get(20),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(25),bridgeSupportAnchorPoints.get(21),70));

    }

    public void computeTimeStep(double d, double dt){
        ArrayList<BridgeSupportAnchorPoint> tmpNewAnchorPoints = new ArrayList();

        for (BridgeSupportAnchorPoint currAnchorPoint:bridgeSupportAnchorPoints) {

            if(!currAnchorPoint.isFixed()) {

                myVec f = new myVec(0, -9.81 * currAnchorPoint.getWeight()).minus(currAnchorPoint.getVelocity().smult(d));

                myVec p = currAnchorPoint.getPos();

                for (ListIterator<BridgeSupport> iterator = currAnchorPoint.getSupports().listIterator(); iterator.hasNext();) {
                    BridgeSupport currSupport = iterator.next();

                    double k = currSupport.getSpringConstant();
                    myVec springF = currSupport.getNormalizedVec().smult(-k * (currSupport.getCurrentLength() - currSupport.getLength()));

                    if (currAnchorPoint == currSupport.getPointA()) {
                        f = f.plus(springF);
                    } else { // assuming else it's pointB
                        f = f.minus(springF);
                    }
                    if (currSupport.calculateStress() > .9 && !currSupport.isBroken()){
                        //make bridge breakable
                        BridgeSupportAnchorPoint pointA = currSupport.getPointA();
                        BridgeSupportAnchorPoint pointB = currSupport.getPointB();

                        BridgeSupportAnchorPoint newA = new BridgeSupportAnchorPoint(pointA.getxPos(),pointA.getyPos(),pointA.getWeight(),pointA.isFixed());
                        BridgeSupportAnchorPoint newB = new BridgeSupportAnchorPoint(pointB.getxPos(),pointB.getyPos(),pointB.getWeight(),pointB.isFixed());

                        newA.setVelocity(pointA.getVelocity());
                        newB.setVelocity(pointB.getVelocity());

                        currSupport.setLength(newA.getPos().minus(newB.getPos()).length());
                        currSupport.setPointA(newA);
                        currSupport.setPointB(newB);
                        currSupport.setBroken(true);

                        newA.addBridgeSupport(currSupport);
                        newB.addBridgeSupport(currSupport);
                        tmpNewAnchorPoints.add(newA);
                        tmpNewAnchorPoints.add(newB);

                        iterator.remove();
                    }

                }

                /*
                tried with projected force
                myVec a = currAnchorPoint.getVelocity();
                myVec b = f;
                myVec projectedVel = new myVec(0,0);
                if (b.getY() != 0 || b.getX() != 0){projectedVel = b.smult(a.dot(b)/b.dot(b));}

                f = f.plus( new myVec(0, -9.81 * currAnchorPoint.getWeight()));
                f = f.minus(projectedVel);
                */

                // Additional force for collision
                double kr_ = 100;
                if (p.getY() < -1)
                    f = f.minus(new myVec(0, 1 + p.getY()).smult(kr_));

                // Velocity update
                currAnchorPoint.setVelocity(currAnchorPoint.getVelocity().plus(f.smult(dt / currAnchorPoint.getWeight())));

                // Position update
                p = p.plus(currAnchorPoint.getVelocity().smult(dt));
                currAnchorPoint.setPos(p);
            }

        }
        bridgeSupportAnchorPoints.addAll(tmpNewAnchorPoints);
    }

}
