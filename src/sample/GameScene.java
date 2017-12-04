package sample;

import javafx.scene.canvas.GraphicsContext;
import sample.bridge.Bridge;
import sample.bridge.BridgeSupport;
import sample.rigidbodies.DrawablePolygon;
import sample.rigidbodies.RigidBodyObject;
import sample.utils.HelperClass;

import java.util.ArrayList;

public class GameScene {
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

        DrawablePolygon car = new DrawablePolygon(new double[]{-40.,40.,40.,20,-20.,-40.},new double[]{0,0,12,28,28,12},6,1);
        rigidBodyObjects.add(new RigidBodyObject(100,210,Math.toRadians(0),10,false, car));
        rigidBodyObjects.get(0).setVelocity(new Vec2(10,0));


        DrawablePolygon rectangle = new DrawablePolygon(new double[]{-10.,10.,10.,-10.},new double[]{-10.,-10.,10.,10.},4,1);

        /*
        rigidBodyObjects.add(new RigidBodyObject(350,380,Math.toRadians(0),3,false, rectangle));
        rigidBodyObjects.add(new RigidBodyObject(350,300,Math.toRadians(0),3,false, rectangle));
        rigidBodyObjects.add(new RigidBodyObject(350,320,Math.toRadians(0),3,false, rectangle));
        rigidBodyObjects.add(new RigidBodyObject(350,340,Math.toRadians(0),3,false, rectangle));
        rigidBodyObjects.add(new RigidBodyObject(350,360,Math.toRadians(0),3,false, rectangle));
        */

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
        if (counter > 100){
            DrawablePolygon car = new DrawablePolygon(new double[]{-10.,10.,10.,5,-5.,-10.},new double[]{0,0,3,7,7,3},6,1);
            rigidBodyObjects.add(new RigidBodyObject(350,300,Math.toRadians(0),1,false, car));
            counter = 0;
        }*/
    }

    public void update(double dt){
        updateRigidBodies(dt);
        myBridge.computeTimeStepImplicit(0.1,dt);
        //myBridge.collapseBridge(.9);
    }

    public void updateRigidBodies(double dt){
        for(RigidBodyObject tmpObject:rigidBodyObjects){

            Vec2 force;
            Vec2 relForcePos = new Vec2(0,0);
            Vec2 translation = new Vec2(0,0);
            double angle;

            boolean collides = false;

            boolean collidesWithStatic = true;

            for (BridgeSupport currSupport:myBridge.getSupports()){
                if (currSupport.isRoad()){
                    if(!collides && HelperClass.gjk(tmpObject,currSupport.getStreetRB())){
                        collides = true;
                        //currSupport.getPointA().setVelocity(currSupport.getPointA().getVelocity().plus(new Vec2(0,-.5)));
                        //currSupport.getPointB().setVelocity(currSupport.getPointB().getVelocity().plus(new Vec2(0,-.5)));
                        angle = currSupport.getStreetRB().getTorque() - Math.PI / 2;
                        if (!Double.isNaN(tmpObject.getPos().length()) && !Double.isNaN(currSupport.getStreetRB().getPos().length())){
                            Vec2[] epaResults = HelperClass.EPA(tmpObject, currSupport.getStreetRB());

                            translation = epaResults[0];
                            relForcePos = epaResults[1].minus(tmpObject.getPos());
                        }
                        break;
                    }
                }

            }


            for (RigidBodyObject otherObject: rigidBodyObjects){

                if(!collides && !otherObject.equals(tmpObject) && HelperClass.gjk(tmpObject,otherObject)){
                    collides = true;

                    if (!Double.isNaN(tmpObject.getPos().length()) && !Double.isNaN(otherObject.getPos().length())){
                        Vec2[] epaResults = HelperClass.EPA(tmpObject, otherObject);

                        translation = epaResults[0];
                        relForcePos = epaResults[1].minus(tmpObject.getPos());
                        collidesWithStatic = false;
                    }
                    break;

                }
            }

            if(collides){
                force = new Vec2(0.001,.002);
                if(0 != translation.length()) {
                    force = translation.normalize().smult(tmpObject.getMass() * 9.81 * dt);
                }
                 /* for the lulz
                double torque = Math.random() * Math.PI;

                double x = Math.cos(torque) * relForcePos.getX() + Math.sin(torque) * relForcePos.getY();
                double y = -1 * Math.sin(torque) * relForcePos.getX() + Math.cos(torque) * relForcePos.getY();

                relForcePos = new Vec2(x,y);
                 end of lulz */

                tmpObject.setAngularVel(tmpObject.getAngularVel() + tmpObject.computeAngularAccel(relForcePos,force) * dt);
                tmpObject.setVelocity(tmpObject.getVelocity().plus(tmpObject.linearAcceleration(force).smult(dt)));
                //tmpObject.setVelocity(tmpObject.getVelocity().mul(new Vec2(1,0)).plus(new Vec2(0,.1)));
                //tmpObject.setVelocity(tmpObject.getVelocity().mul(new Vec2(1,0)).plus(new Vec2(0,.1)));
                Vec2 positionBeforeCollision = tmpObject.getPos();



                Vec2 correctionNormal = new Vec2(translation.getY(),translation.getX() * -1);
                if(0 != (correctionNormal.smult(correctionNormal.dot(tmpObject.getVelocity())).length())){
                    correctionNormal = correctionNormal.normalize();
                    tmpObject.setVelocity(correctionNormal.smult(correctionNormal.dot(tmpObject.getVelocity())));
                }
                tmpObject.setPos(tmpObject.getPos().plus(tmpObject.getVelocity().smult(dt)).plus(translation));

                tmpObject.setTorque(tmpObject.getTorque() + tmpObject.getAngularVel() * dt);
                //System.out.println(tmpObject.getPos());

            } else {
                //force = new Vec2(0.001,-.01);
                force = new Vec2(0,-tmpObject.getMass() * 9.81 * dt);

                tmpObject.setAngularVel(tmpObject.getAngularVel() + tmpObject.computeAngularAccel(relForcePos,force) * dt);
                tmpObject.setVelocity(tmpObject.getVelocity().plus(tmpObject.linearAcceleration(force).smult(dt)));
                tmpObject.setPos(tmpObject.getPos().plus(tmpObject.getVelocity().smult(dt)));
                tmpObject.setTorque(tmpObject.getTorque() + tmpObject.getAngularVel() * dt);
                //System.out.println(tmpObject.getPos());

            }

            if(tmpObject.getxPos() > 700 || tmpObject.getyPos() < 0){
                tmpObject.setPos(new Vec2(150,210));
                tmpObject.setVelocity(new Vec2(10,0));
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
