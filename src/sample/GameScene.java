package sample;

import javafx.scene.canvas.GraphicsContext;
import sample.bridge.Bridge;
import sample.bridge.BridgeSupport;
import sample.rigidbodies.DrawablePolygon;
import sample.rigidbodies.RigidBodyObject;
import sample.utils.HelperClass;

import java.util.ArrayList;

public class GameScene {

    private final double PENETRATION_THRESHOLD = .5;
    ArrayList<RigidBodyObject> rigidBodyObjects;
    Bridge myBridge;
    public GameScene(){
        rigidBodyObjects = new ArrayList<>();
        /*
        DrawablePolygon car = new DrawablePolygon(new double[]{-10.,10.,10.,5,-5.,-10.},new double[]{0,0,3,7,7,3},6,1);
        DrawablePolygon rectangle = new DrawablePolygon(new double[]{-10.,10.,10.,-10.},new double[]{-10.,-10.,10.,10.},4,1);

        rigidBodyObjects.add(new RigidBodyObject(100,300,Math.toRadians(0),1,false, car));
        rigidBodyObjects.add(new RigidBodyObject(150,300,Math.toRadians(0),1,false, car));
        rigidBodyObjects.add(new RigidBodyObject(200,300,Math.toRadians(0),1,false, car));
        rigidBodyObjects.add(new RigidBodyObject(250,300,Math.toRadians(0),1,false, car));
        rigidBodyObjects.add(new RigidBodyObject(300,300,Math.toRadians(0),1,false, rectangle));
        */


        DrawablePolygon car = new DrawablePolygon(new double[]{-40.,-30,30,40.,40.,20,-20.,-40.},new double[]{0,-5,-5,0,12,28,28,12},8,10);
        rigidBodyObjects.add(new RigidBodyObject(150,300,Math.toRadians(0),false, car));
        rigidBodyObjects.get(0).setVelocity(new Vec2(8,0));

        rigidBodyObjects.add(new RigidBodyObject(600,300,Math.toRadians(0),false, car));
        rigidBodyObjects.get(1).setVelocity(new Vec2(-8,0));


        //DrawablePolygon rectangle = new DrawablePolygon(new double[]{-1000.,1000.,1000.,-1000.},new double[]{-100.,-100.,100.,100.},4,100);

        DrawablePolygon rectangle = new DrawablePolygon(new double[]{-10.,10.,10.,-10.},new double[]{-10.,-10.,10.,10.},4,100);

        //rigidBodyObjects.add(new RigidBodyObject(0,150,Math.toRadians(0),true, rectangle));


        //rigidBodyObjects.add(new RigidBodyObject(350,380,Math.toRadians(0),false, rectangle));
        //rigidBodyObjects.add(new RigidBodyObject(350,300,Math.toRadians(0),false, rectangle));
        //rigidBodyObjects.add(new RigidBodyObject(350,320,Math.toRadians(0),false, rectangle));
        //rigidBodyObjects.add(new RigidBodyObject(350,340,Math.toRadians(0),false, rectangle));
        //rigidBodyObjects.add(new RigidBodyObject(350,360,Math.toRadians(0),false, rectangle));


        myBridge = new Bridge();
        myBridge.createTestBridge();



    }
    private int counter = 0;
    public void draw(GraphicsContext gc){
        myBridge.draw(gc);

        for(RigidBodyObject tmpObject:rigidBodyObjects){
            tmpObject.draw(gc);
        }

        /*
        counter++;
        if (rigidBodyObjects.size() < 5 && counter > 100){
            DrawablePolygon rectangle = new DrawablePolygon(new double[]{-10.,10.,10.,-10.},new double[]{-10.,-10.,10.,10.},4,100);
            rigidBodyObjects.add(new RigidBodyObject(350,300,Math.toRadians(0), false, rectangle));
            counter = 0;
        }*/
    }

    public void update(double dt){
        updateRigidBodies(dt,.1);
        myBridge.computeTimeStepImplicit(0.1,dt);
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

                for (BridgeSupport currSupport : myBridge.getSupports()) {
                    if (currSupport.isRoad()) {
                        if (HelperClass.gjk(tmpObject, currSupport.getStreetRB())) {
                            //otherSupport = currSupport;
                            otherObjects.add(currSupport.getStreetRB());
                            collides = true;
                            //currSupport.getPointA().setVelocity(currSupport.getPointA().getVelocity().plus(new Vec2(0,-.5)));
                            //currSupport.getPointB().setVelocity(currSupport.getPointB().getVelocity().plus(new Vec2(0,-.5)));
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
                        if (!Double.isNaN(tmpObject.getPos().length()) && !Double.isNaN(otherObject.getPos().length())) {
                            Vec2[] epaResults = HelperClass.EPA2(tmpObject, otherObject);
                            //Vec2[] epaResults2 = HelperClass.EPA2(tmpObject, otherObject);


                            translationVec = epaResults[0];
                            rAP = epaResults[1].minus(tmpObject.getPos());
                            rBP = epaResults[1].minus(otherObject.getPos());
                            translationNormal = epaResults[3];

                            double penetration = translationVec.length();

                            if (penetration > PENETRATION_THRESHOLD) {


                                Vec2 vA1 = tmpObject.getVelocity();
                                Vec2 vB1 = otherObject.getVelocity();

                                double m1 = tmpObject.getMass();
                                double m2 = otherObject.getMass(); // check not null

                                double wA1 = tmpObject.getAngularVel();
                                double wB1 = otherObject.getAngularVel();

                                Vec2 vAP1 = vA1.plus(new Vec2(-wA1 * rAP.getY(), wA1 * rAP.getX()));
                                Vec2 vBP1 = vB1.plus(new Vec2(-wB1 * rBP.getY(), wB1 * rBP.getX()));

                                double Ia = tmpObject.getMomentOfInertia();
                                double Ib = otherObject.getMomentOfInertia();

                                Vec2 vAB1 = vAP1.minus(vBP1);

                                //relative normal velocity

                                // r x J = (Jx ray - Jy rax)


                                Vec2 n = translationNormal.normalize().smult(-1);

                                double j = (1.0 + e) * vAB1.dot(n) * (-1.0);

                                double denominator;
                                if (collidesWithStatic) {
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

                                //Vec2 vAB2 = vAP2.minus(vB2);
                                //double lhs = translationNormal.dot(vAB2);
                                double rhs = translationNormal.dot(vAB1) * (-e);


                                tmpObject.setVelocity(vA2);
                                tmpObject.setAngularVel(wA2);

                                if (tmpObject.getPos().minus(tmpObject.getPos().plus(tmpObject.getVelocity()).plus(translationVec)).length() > 50) {
                                    System.out.println("CAAARE");
                                }

                                tmpObject.updatePos(dt);
                                if(translationVec.length() > 0) {
                                    tmpObject.setPos(tmpObject.getPos().plus(translationVec.normalize().smult(translationVec.length() - PENETRATION_THRESHOLD)));
                                }
                                //System.out.println(translationVec);
                                tmpObject.updateTorque(dt);

                                if (!collidesWithStatic && !otherObject.isFixed()) {
                                    otherObject.setVelocity(vB2);
                                    otherObject.setAngularVel(wB2);
                                    //otherSupport.getPointA().setVelocity(vB2);
                                    //otherSupport.getPointB().setVelocity(vB2);


                                    otherObject.updatePos(dt);
                                    otherObject.updateTorque(dt);
                                }
                            } else {


                                double VEL_THRESHOLD = 3;

                                Vec2 vA1 = tmpObject.getVelocity();
                                Vec2 vB1 = otherObject.getVelocity();

                                double wA1 = tmpObject.getAngularVel();
                                double wB1 = otherObject.getAngularVel();

                                Vec2 vAP1 = vA1.plus(new Vec2(-wA1 * rAP.getY(), wA1 * rAP.getX()));
                                Vec2 vBP1 = vB1.plus(new Vec2(-wB1 * rBP.getY(), wB1 * rBP.getX()));

                                Vec2 vAB1 = vAP1.minus(vBP1);

                                //force applied
                                Vec2 appliedGravityForce = new Vec2(0, -tmpObject.getMass() * 9.81 * dt);
                                Vec2 gravityForce = new Vec2(0, -tmpObject.getMass() * 9.81 * dt);


                                translationNormal = translationNormal.normalize();

                                forceOnObject = new Vec2(0,0);// = new Vec2(0, -tmpObject.getMass() * 9.81 * dt);
                                Vec2 forceY = new Vec2(0,0);
                                Vec2 forceX = new Vec2(0,0);


                                Vec2 correctionNormal = new Vec2(translationNormal.getY() * -1,translationNormal.getX());
                                if(0 < (correctionNormal.smult(correctionNormal.dot(gravityForce)).length())){
                                    forceY = translationNormal.smult(translationNormal.dot(gravityForce));

                                    if (gravityForce.dot(translationNormal) < 0) {
                                        forceY = forceY.smult(-1);
                                    }

                                    forceX = gravityForce.plus(forceY);

                                    //System.out.println("forceY = " + forceY + " gravityforce= " +gravityForce + " forcex = " + forceX);

                                    forceOnObject.set(forceY.plus(forceX));

                                } else {
                                    //acceleration = Vec2.VEC_ZERO;
                                }

                                if(penetration < PENETRATION_THRESHOLD){
                                    forceY = forceY.smult(penetration/PENETRATION_THRESHOLD);
                                }

                                //forcey = normalkraft forcex = gravity + normalkraft

                                tmpObject.setVelocity(tmpObject.getVelocity().plus(tmpObject.linearAcceleration(forceX).smult(dt)));
                                tmpObject.setAngularVel(tmpObject.getAngularVel() + tmpObject.computeAngularAccel(rAP, forceY) * dt);

                                //apply y force if velocities towards collision not slow enough yet
                                if(vAB1.length() > VEL_THRESHOLD) {
                                    tmpObject.setVelocity(tmpObject.getVelocity().plus(tmpObject.linearAcceleration(forceY).smult(dt)));
                                }



                                tmpObject.updatePos(dt);
                                tmpObject.updateTorque(dt);

                                tmpObject.setRestCollisionForce(forceX.smult(50));
                            }

                        }
                    }

//                force = new Vec2(0.001,.002);
//                if(0 != translationNormal.length()) {
//                    force = translationNormal.normalize().smult(tmpObject.getMass() * 9.81 * dt);
//                }
//                 /* for the lulz
//                double torque = Math.random() * Math.PI;
//
//                double x = Math.cos(torque) * relForcePos.getX() + Math.sin(torque) * relForcePos.getY();
//                double y = -1 * Math.sin(torque) * relForcePos.getX() + Math.cos(torque) * relForcePos.getY();
//
//                relForcePos = new Vec2(x,y);
//                 end of lulz */
//
//                tmpObject.setAngularVel(tmpObject.getAngularVel() + tmpObject.computeAngularAccel(rAP,force) * dt);
//                tmpObject.setVelocity(tmpObject.getVelocity().plus(tmpObject.linearAcceleration(force).smult(dt)));
//                //tmpObject.setVelocity(tmpObject.getVelocity().mul(new Vec2(1,0)).plus(new Vec2(0,.1)));
//                //tmpObject.setVelocity(tmpObject.getVelocity().mul(new Vec2(1,0)).plus(new Vec2(0,.1)));
//                Vec2 positionBeforeCollision = tmpObject.getPos();
//
//
//
//                Vec2 correctionNormal = new Vec2(translationNormal.getY(),translationNormal.getX() * -1);
//                if(0 != (correctionNormal.smult(correctionNormal.dot(tmpObject.getVelocity())).length())){
//                    correctionNormal = correctionNormal.normalize();
//                    tmpObject.setVelocity(correctionNormal.smult(correctionNormal.dot(tmpObject.getVelocity())));
//                }
//                tmpObject.setPos(tmpObject.getPos().plus(tmpObject.getVelocity().smult(dt)).plus(translationNormal));
//
//                tmpObject.setTorque(tmpObject.getTorque() + tmpObject.getAngularVel() * dt);
//                //System.out.println(tmpObject.getPos());


                } else {
                    //force = new Vec2(0.001,-.01);
                    forceOnObject = new Vec2(0, -tmpObject.getMass() * 9.81 * dt);

                    tmpObject.setAngularVel(tmpObject.getAngularVel() + tmpObject.computeAngularAccel(rAP, forceOnObject) * dt);
                    tmpObject.setVelocity(tmpObject.getVelocity().plus(tmpObject.linearAcceleration(forceOnObject).smult(dt)));
                    tmpObject.setPos(tmpObject.getPos().plus(tmpObject.getVelocity().smult(dt)));
                    tmpObject.setTorque(tmpObject.getTorque() + tmpObject.getAngularVel() * dt);
                    //System.out.println(tmpObject.getPos());

                }

                if (tmpObject.getxPos() > 700 || tmpObject.getyPos() < 0 || tmpObject.getxPos() < 0) {
                    tmpObject.setPos(new Vec2(200, 300));
                    tmpObject.setVelocity(new Vec2(10, tmpObject.getVelocity().getY()));
                }







            /*
            Vec2 p = tmpObject.getPos().minus(new Vec2(150,200));
            Vec2 correctedPosition = p.normalize().smult(50).minus(p).plus(tmpObject.getPos());

            tmpObject.setPos(correctedPosition);

            double mass = tmpObject.getMass();

            Vec2 fext = tmpObject.getPos().minus(new Vec2(150,200));
            fext.setPos(fext.getY(),-1 * fext.getX());
            fext = fext.normalize().smult(0.00001);

            //testing out simple constraint solver with distance preserving constrained for 1 particle

            Vec2 pdot = tmpObject.getVelocity();
            double lambda = fext.smult(-1).dot(p) - pdot.smult(mass).dot(pdot) / (p.dot(p));
            Vec2 fc = p.smult(lambda);
            Vec2 relForcePos = new Vec2(0,0);

            //fc = Vec2.VEC_ZERO;

            //testing custom 2.nd constraint rule
            tmpObject.setVelocity(tmpObject.getVelocity().plus(tmpObject.linearAcceleration(fc.plus(fext))));
            tmpObject.setPos(tmpObject.getPos().plus(tmpObject.getVelocity()));
            System.out.println(tmpObject.getPos());
            */


            }
        }
    }
}
