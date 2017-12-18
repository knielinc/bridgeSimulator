package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import sample.bridge.Bridge;
import sample.bridge.BridgeSupport;
import sample.bridge.BridgeSupportAnchorPoint;
import sample.rigidbodies.DrawablePolygon;
import sample.rigidbodies.RigidBodyObject;
import sample.utils.HelperClass;

import java.util.ArrayList;

public class GameScene {

    private Image carImage = new Image("file:res/Car.png");
    private Image truckImage = new Image("file:res/Truck.png");

    private DrawablePolygon carCollBox = new DrawablePolygon(new double[]{-20,-15,15,20,17,3,-10,-15,-18},new double[]{0,-5,-5,0,4,12,12,10,5},9,2);
    private DrawablePolygon createCarCollBoxWithSizeAndWeight(double size, double weight){
        return new DrawablePolygon(new double[]{-20*size,-15*size,15*size,20*size,17*size,3*size,-10*size,-15*size,-18*size},new double[]{0*size,-5*size,-5*size,0*size,4*size,12*size,12*size,10*size,5*size},9,weight);
    }

    private DrawablePolygon truckCollBox = new DrawablePolygon(new double[]{-25,-10,15,25,25,10,-25},new double[]{0,-5,-5,0,10,20,20},7,3.9);

    private DrawablePolygon createTruckCollBoxWithSizeAndWeight(double size, double weight){
        return new DrawablePolygon(new double[]{-25*size,-10*size,15*size,25*size,25*size,10*size,-25*size},new double[]{0*size,-5*size,-5*size,0*size,10*size,20*size,20*size},7,weight);
    }

    private final double PENETRATION_THRESHOLD = 1;

    ArrayList<RigidBodyObject> rigidBodyObjects;
    Bridge myBridge;

    double GLOBAL_BOUNCINESS = 0.2;
    Vec2 GLOBAL_GRAVITY = new Vec2(1, - 9.81);
    boolean GLOBAL_BREAKABLE_BRIDGE = false;
    double GLOBAL_COLLAPSING_THRESHOLD = 1.2;


    public GameScene(){
        rigidBodyObjects = new ArrayList<>();
        rigidBodyObjects.add(new RigidBodyObject(450,-50,Math.toRadians(0),true, new DrawablePolygon(new double[]{0,900,900,0},new double[]{0,0,100,100},4, 500)));

        initEric2();
    }

    public GameScene(int i){
        rigidBodyObjects = new ArrayList<>();
        rigidBodyObjects.add(new RigidBodyObject(450,-50,Math.toRadians(0),true, new DrawablePolygon(new double[]{0,900,900,0},new double[]{0,0,100,100},4, 500)));

        switch (i){
            case 1:
                init0();
                break;
            case 2:
                init1();
                break;
            case 3:
                init2();
                break;
            case 4:
                init3();
                break;
            case 5:
                init4();
                break;
            case 6:
                initEric1();
                break;
            case 7:
                initEric2();
                break;
            default:
                init0();
                break;
        }
    }

    //2 cars colliding
    public void init0(){

        RigidBodyObject truck1 = new RigidBodyObject(150,300, Math.toRadians(0),false, truckCollBox);
        truck1.setImgDrawable(truckImage);

        rigidBodyObjects.add(truck1);
        rigidBodyObjects.get(1).setVelocity(new Vec2(12,0));

        RigidBodyObject truck2 = new RigidBodyObject(600,300, Math.toRadians(0),false, carCollBox);
        truck2.setImgDrawable(carImage);

        rigidBodyObjects.add(truck2);
        rigidBodyObjects.get(2).setVelocity(new Vec2(-12,0));

        GLOBAL_GRAVITY = new Vec2(0,0);
        GLOBAL_BOUNCINESS = .9;
    }

    //samplebridge without breaking
    public void init1(){
        myBridge = new Bridge();
        myBridge.createTestBridge2();
    }

    //samplebridge with breaking
    public void init2(){
        myBridge = new Bridge();
        myBridge.createTestBridge2();
        GLOBAL_BREAKABLE_BRIDGE = true;
    }

    //fancyBridge with Cars with Breaking
    public void init3(){
        /*
        DrawablePolygon smallcar = new DrawablePolygon(new double[]{-10.,10.,10.,5,-5.,-10.},new double[]{0,0,3,7,7,3},6,1);
        DrawablePolygon rectangle = new DrawablePolygon(new double[]{-10.,10.,10.,-10.},new double[]{-10.,-10.,10.,10.},4,1);

        rigidBodyObjects.add(new RigidBodyObject(100,300,Math.toRadians(0),1,false, car));
        rigidBodyObjects.add(new RigidBodyObject(150,300,Math.toRadians(0),1,false, car));
        rigidBodyObjects.add(new RigidBodyObject(200,300,Math.toRadians(0),1,false, car));
        rigidBodyObjects.add(new RigidBodyObject(250,300,Math.toRadians(0),1,false, car));
        rigidBodyObjects.add(new RigidBodyObject(300,300,Math.toRadians(0),1,false, rectangle));
        */


        rigidBodyObjects.add(new RigidBodyObject(100,250,Math.toRadians(0),false, carCollBox,carImage));
        rigidBodyObjects.get(1).setVelocity(new Vec2(5,0));


        myBridge = new Bridge();
        myBridge.createTestBridge1();
        GLOBAL_BREAKABLE_BRIDGE = true;
        GLOBAL_GRAVITY = new Vec2(2,-9.81);
    }

    //fancyBridge with Trucks with Breaking
    public void init4(){
        /*
        DrawablePolygon smallcar = new DrawablePolygon(new double[]{-10.,10.,10.,5,-5.,-10.},new double[]{0,0,3,7,7,3},6,1);
        DrawablePolygon rectangle = new DrawablePolygon(new double[]{-10.,10.,10.,-10.},new double[]{-10.,-10.,10.,10.},4,1);

        rigidBodyObjects.add(new RigidBodyObject(100,300,Math.toRadians(0),1,false, car));
        rigidBodyObjects.add(new RigidBodyObject(150,300,Math.toRadians(0),1,false, car));
        rigidBodyObjects.add(new RigidBodyObject(200,300,Math.toRadians(0),1,false, car));
        rigidBodyObjects.add(new RigidBodyObject(250,300,Math.toRadians(0),1,false, car));
        rigidBodyObjects.add(new RigidBodyObject(300,300,Math.toRadians(0),1,false, rectangle));
        */


        rigidBodyObjects.add(new RigidBodyObject(130,230,Math.toRadians(0),false, truckCollBox,truckImage));
        rigidBodyObjects.get(1).setVelocity(new Vec2(5,0));

        rigidBodyObjects.add(new RigidBodyObject(300,230,Math.toRadians(0),false, truckCollBox,truckImage));
        rigidBodyObjects.get(2).setVelocity(new Vec2(10,0));

        myBridge = new Bridge();
        myBridge.createTestBridge1();
        GLOBAL_BREAKABLE_BRIDGE = true;
    }

    public void initEric1(){

        //DrawablePolygon car = new DrawablePolygon(new double[]{-40.,-30,30,40.,40.,20,-20.,-40.},new double[]{0,-5,-5,0,12,28,28,12},8,10);
        DrawablePolygon car = createTruckCollBoxWithSizeAndWeight(1.5, 10);
        rigidBodyObjects.add(new RigidBodyObject(150,250,Math.toRadians(0),false, car, truckImage));
        rigidBodyObjects.get(1).setVelocity(new Vec2(15 ,-25));

        DrawablePolygon car2 = createCarCollBoxWithSizeAndWeight(1.5, 5);
        rigidBodyObjects.add(new RigidBodyObject(50,500,Math.toRadians(0),false, car2, carImage));
        rigidBodyObjects.get(2).setVelocity(new Vec2(10,0));
        GLOBAL_GRAVITY = new Vec2(1,-9.81);


        myBridge = new Bridge();
        myBridge.createWeirdBridge();
        GLOBAL_BREAKABLE_BRIDGE = false;
    }

    public void initEric2(){
        int i = 1; // easier to comment these in/out. Don't have to change index anymore.

        /*DrawablePolygon car = createCarCollBoxWithSizeAndWeight(1.5, 5);
        rigidBodyObjects.add(new RigidBodyObject(220,0,Math.toRadians(0),false, car, carImage));
        rigidBodyObjects.get(i).setVelocity(new Vec2(100,0));
        i++;*/

        DrawablePolygon car2 = createCarCollBoxWithSizeAndWeight(1.5, 5);
        rigidBodyObjects.add(new RigidBodyObject(0,0,Math.toRadians(0),false, car2, carImage));
        rigidBodyObjects.get(i).setVelocity(new Vec2(100,0));
        i++;

        GLOBAL_GRAVITY = new Vec2(50,-9.81);


        myBridge = new Bridge();
        myBridge.createWaveformBridge();
        GLOBAL_BREAKABLE_BRIDGE = true;
    }

    public void initRampComparison(){
        DrawablePolygon car = createCarCollBoxWithSizeAndWeight(1.5, 5);
        rigidBodyObjects.add(new RigidBodyObject(220,0,Math.toRadians(0),false, car, carImage));
        rigidBodyObjects.get(1).setVelocity(new Vec2(100,0));

        DrawablePolygon car2 = createCarCollBoxWithSizeAndWeight(1.5, 5);
        rigidBodyObjects.add(new RigidBodyObject(0,0,Math.toRadians(0),false, car2, carImage));
        rigidBodyObjects.get(2).setVelocity(new Vec2(100,0));

        GLOBAL_GRAVITY = new Vec2(0,-9.81);


        myBridge = new Bridge();
        myBridge.createRampComparisonBridges();
        GLOBAL_BREAKABLE_BRIDGE = true;
    }


    private int counter = 0;
    public void draw(GraphicsContext gc){
        if(myBridge != null) {
            myBridge.draw(gc);
        }

        for(RigidBodyObject tmpObject:rigidBodyObjects){
            tmpObject.draw(gc);
        }
    }

    public void update(double dt){
        updateRigidBodies(dt,GLOBAL_BOUNCINESS);
        if(myBridge != null) {
            myBridge.computeTimeStepImplicit(0.1, dt);
            for (BridgeSupport currSupport : myBridge.getSupports()) {
                if (currSupport.isRoad()) {
                    currSupport.updateRoadRB(dt);
                }
            }

            if(GLOBAL_BREAKABLE_BRIDGE){
                myBridge.collapseBridge(GLOBAL_COLLAPSING_THRESHOLD);
            }
        }
        //myBridge.collapseBridge(.9);
    }

    public void updateRigidBodies(double dt, double collisionResponse) {

        double e = collisionResponse;

        for (RigidBodyObject tmpObject : rigidBodyObjects) {

            if (!tmpObject.isFixed()) {

                Vec2 forceOnObject;
                Vec2 rAP = new Vec2(0, 0);
                Vec2 rBP = new Vec2(0, 0);


                Vec2 translationVec = new Vec2(0, 0);
                Vec2 translationNormal = new Vec2(0, 0);


                boolean collides = false; //only handle 1 collision atm
                ArrayList<RigidBodyObject> otherObjects = new ArrayList<>();
                BridgeSupport otherSupport = null;

                boolean collidesWithStatic = true;

                if(myBridge!= null) {
                    for (BridgeSupport currSupport : myBridge.getSupports()) {
                        if (currSupport.isRoad()) {
                            //currSupport.updateRoadRB(dt);
                            if (HelperClass.gjk(tmpObject, currSupport.getStreetRB())) {
                                //otherSupport = currSupport;
                                otherObjects.add(currSupport.getStreetRB());
                                collides = true;
                                //currSupport.getPointA().setVelocity(currSupport.getPointA().getVelocity().plus(new Vec2(0,-.5)));
                                //currSupport.getPointB().setVelocity(currSupport.getPointB().getVelocity().plus(new Vec2(0,-.5)));
                            }
                        }
                    }
                }

                for (RigidBodyObject currObject : rigidBodyObjects) {

                    if (!currObject.equals(tmpObject) && HelperClass.gjk(tmpObject, currObject)) {
                        otherObjects.add(currObject);
                        collides = true;
                        collidesWithStatic = false;
                    }
                }


                if (collides) {

                    for (RigidBodyObject otherObject : otherObjects) {
                        if (!Double.isNaN(tmpObject.getPrevPos().length()) && !Double.isNaN(otherObject.getPrevPos().length())) {
                            Vec2[] epaResults = HelperClass.EPA2(tmpObject, otherObject);
                            //Vec2[] epaResults2 = HelperClass.EPA2(tmpObject, otherObject);


                            translationVec = epaResults[0];
                            rAP = epaResults[1].minus(tmpObject.getPrevPos());
                            rBP = epaResults[1].minus(otherObject.getPrevPos());
                            translationNormal = epaResults[3];

                            double penetration = translationVec.length();

                            if (penetration > PENETRATION_THRESHOLD) {


                                Vec2 vA1 = tmpObject.getPrevVel();
                                Vec2 vB1 = otherObject.getPrevVel();

                                double m1 = tmpObject.getMass();
                                double m2 = otherObject.getMass(); // check not null

                                double wA1 = tmpObject.getPrevAngularVel();
                                double wB1 = otherObject.getPrevAngularVel();

                                Vec2 vAP1 = vA1.plus(new Vec2(-wA1 * rAP.getY(), wA1 * rAP.getX()));
                                Vec2 vBP1 = vB1.plus(new Vec2(-wB1 * rBP.getY(), wB1 * rBP.getX()));

                                double Ia = tmpObject.getMomentOfInertia();
                                double Ib = otherObject.getMomentOfInertia();

                                Vec2 vAB1 = vAP1.minus(vBP1);

                                //relative normal velocity

                                // r x J = (Jx ray - Jy rax)


                                Vec2 n = translationNormal.normalize();

                                double j = (1.0 + e) * vAB1.dot(n) * (-1.0);

                                double denominator;
                                if (otherObject.isFixed()) {
                                    denominator = 1.0 / m1 + (rAP.cross(n) * rAP.cross(n)) / Ia;
                                    //denominator = 1.0 / m1 + 1.0 / m2 + (rAP.cross(n) * rAP.cross(n)) / Ia + (rBP.cross(n) * rBP.cross(n)) / Ib;


                                } else {
                                    denominator = 1.0 / m1 + 1.0 / m2 + (rAP.cross(n) * rAP.cross(n)) / Ia + (rBP.cross(n) * rBP.cross(n)) / Ib;
                                }

                                j /= denominator;

                                Vec2 jn = n.smult(j);

                                Vec2 vA2 = vA1.plus(jn.smult(1.0 / m1));
                                Vec2 vB2 = vB1.minus(jn.smult(1.0 / m2));

                                double wA2 = wA1 + tmpObject.getTorqueForForce(rAP, jn) / Ia;//rAP.cross(jn)/Ia;
                                double wB2 = wB1 - rBP.cross(jn) / Ib;

                                Vec2 vAP2 = vA2.plus(new Vec2(-wA2 * rAP.getY(), wA2 * rAP.getX()));
                                Vec2 vBP2 = vB2.plus(new Vec2(-wB2 * rBP.getY(), wB2 * rBP.getX()));

                                Vec2 vAB2 = vAP2.minus(vBP2);

                                //System.out.println("vAB2.dot(n)= " + vAB2.dot(n) + " , vAB1.dot(n) * -e = " + vAB1.dot(n) * -e);
                                //double lhs = translationNormal.dot(vAB2);
                                double rhs = translationNormal.dot(vAB1) * (-e);


                                //prev + delta = nacher
                                //delta = nacher - prev

                                tmpObject.setVelocity(tmpObject.getVelocity().plus(vA2.minus(tmpObject.getPrevVel())));
                                tmpObject.setAngularVel(tmpObject.getAngularVel() + wA2-tmpObject.getPrevAngularVel());

                                //tmpObject.updatePos(dt);

                                if(translationVec.length() > 0) {
                                    tmpObject.setPos(tmpObject.getPos().plus(translationVec.normalize().smult(translationVec.length() - PENETRATION_THRESHOLD/4.0)));
                                }
                                //System.out.println(translationVec);
                                //tmpObject.updateTorque(dt);

                                if (!otherObject.isFixed() && !otherObject.isPartOfBridge()) {
                                    otherObject.setVelocity(vB2);
                                    otherObject.setAngularVel(wB2);
                                }


                                //tmpObject.setRestCollisionForce(translationNormal.smult(50));

                                if(otherObject.isPartOfBridge()){
                                    //apply impulse on BridgeSupport, by computing the velocity changes of the BridgeSupportAnchorPoints
                                    BridgeSupportAnchorPoint pointA = otherObject.getSupport().getPointA();
                                    BridgeSupportAnchorPoint pointB = otherObject.getSupport().getPointB();

                                    Vec2 velA = pointA.getVelocity();
                                    Vec2 velB = pointB.getVelocity();

                                    //vec from origin to B
                                    Vec2 oa = otherObject.getPos().minus(pointA.getPos()).smult(-1);
                                    Vec2 ob = otherObject.getPos().minus(pointB.getPos()).smult(-1);


                                    Vec2 vAPA = vB2.plus(new Vec2(-wB2 * oa.getY(), wB2 * oa.getX()));
                                    Vec2 vAPB = vB2.plus(new Vec2(-wB2 * ob.getY(), wB2 * ob.getX()));

                                    pointA.setVelocity(vAPA);
                                    pointB.setVelocity(vAPB);

                                    //use vB2 and wB2
                                }


                            } else {

                                double VEL_THRESHOLD = 10;

                                Vec2 vA1 = tmpObject.getPrevVel();
                                Vec2 vB1 = otherObject.getPrevVel();

                                double wA1 = tmpObject.getPrevAngularVel();
                                double wB1 = otherObject.getPrevAngularVel();

                                Vec2 vAP1 = vA1.plus(new Vec2(-wA1 * rAP.getY(), wA1 * rAP.getX()));
                                Vec2 vBP1 = vB1.plus(new Vec2(-wB1 * rBP.getY(), wB1 * rBP.getX()));

                                Vec2 vAB1 = vAP1.minus(vBP1);

                                //force applied
                                Vec2 gravityForce = GLOBAL_GRAVITY.smult(tmpObject.getMass());

                                translationNormal = translationNormal.normalize();

                                forceOnObject = new Vec2(0,0);// = new Vec2(0, -tmpObject.getMass() * 9.81 * dt);
                                Vec2 forceY = new Vec2(0,0);
                                Vec2 forceX = new Vec2(0,0);

                                Vec2 correctionNormal = new Vec2(translationNormal.getY() * -1,translationNormal.getX());
                                if(true){
                                    translationNormal = translationNormal.normalize();
                                    correctionNormal = correctionNormal.normalize();

                                    forceY = translationNormal.smult(translationNormal.dot(gravityForce));

                                    if (gravityForce.dot(translationNormal) < 0) {
                                        forceY = forceY.smult(-1);
                                    }

                                    forceX = correctionNormal.smult(correctionNormal.dot(gravityForce));

                                    if (gravityForce.dot(correctionNormal) < 0) {
                                        forceX = forceX.smult(-1);
                                    }

                                    //System.out.println("forceY = " + forceY + " gravityforce= " +gravityForce + " forcex = " + forceX);

                                    forceOnObject.set(forceY.plus(forceX));

                                } else {
                                    //acceleration = Vec2.VEC_ZERO;
                                }

                                if(penetration < PENETRATION_THRESHOLD){
                                    //forceY = forceY.smult(penetration/PENETRATION_THRESHOLD);
                                }

                                //forcey = normalkraft forcex = gravity + normalkraft

                                tmpObject.setAngularVel(tmpObject.getAngularVel() + tmpObject.computeAngularAccel(rAP, forceY) * dt);
                                tmpObject.setVelocity(tmpObject.getVelocity().plus(tmpObject.linearAcceleration(forceX).smult(dt * 0)));


                                //apply y force if velocities towards collision not slow enough yet
                                if(vAB1.length() > VEL_THRESHOLD) {
                                    tmpObject.setVelocity(tmpObject.getVelocity().plus(tmpObject.linearAcceleration(forceY).smult(dt)));
                                }

                                if(otherObject.isPartOfBridge()){
                                    //TODO LOCAL COORDINATES INSTEAD OF MIDDLE
                                    otherObject.getSupport().getPointA().setAppliedForces(forceY.smult(-.5));
                                    otherObject.getSupport().getPointB().setAppliedForces(forceY.smult(-.5));
                                    /*if(0.5 < forceY.smult(-1).getY())
                                        System.out.println(forceY.smult(-1).getY());*/
                                }

                                //tmpObject.updatePos(dt);
                                //tmpObject.updateTorque(dt);

                                //tmpObject.setRestCollisionForce(forceY.smult(50));
                            }

                        }
                    }

                } else {
                    //force = new Vec2(0.001,-.01);
                    forceOnObject = GLOBAL_GRAVITY.smult(tmpObject.getMass());

                    tmpObject.setAngularVel(tmpObject.getAngularVel() + tmpObject.computeAngularAccel(rAP, forceOnObject) * dt);
                    tmpObject.setVelocity(tmpObject.getVelocity().plus(tmpObject.linearAcceleration(forceOnObject).smult(dt)));
                    //tmpObject.setPos(tmpObject.getPos().plus(tmpObject.getVelocity().smult(dt)));
                    //tmpObject.setTorque(tmpObject.getTorque() + tmpObject.getAngularVel() * dt);
                    //System.out.println(tmpObject.getPos());

                }

                if (tmpObject.getxPos() > 700 || tmpObject.getyPos() < -100 || tmpObject.getxPos() < -100) {
                    tmpObject.setPos(new Vec2(100, 250));
                    tmpObject.setVelocity(new Vec2(10, tmpObject.getVelocity().getY()));
                }


                for(RigidBodyObject currBody:rigidBodyObjects){
                    if(!currBody.isFixed()) {
                        currBody.updatePos(dt);
                        currBody.updateTorque(dt);

                        currBody.setPrevPos(currBody.getPos());
                        currBody.setPrevTorque(currBody.getTorque());
                        currBody.setPrevVel(currBody.getVelocity());
                        currBody.setPrevAngularVel(currBody.getAngularVel());
                    }
                }

            }
        }
    }
}
