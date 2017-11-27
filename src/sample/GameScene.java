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
        DrawablePolygon car = new DrawablePolygon(new double[]{-10.,10.,10.,5,-5.,-10.},new double[]{0,0,3,7,7,3},6,1);
        rigidBodyObjects.add(new RigidBodyObject(100,300,Math.toRadians(0),1,false, car));
        rigidBodyObjects.add(new RigidBodyObject(150,300,Math.toRadians(0),1,false, car));
        rigidBodyObjects.add(new RigidBodyObject(200,300,Math.toRadians(0),1,false, car));
        rigidBodyObjects.add(new RigidBodyObject(250,300,Math.toRadians(0),1,false, car));


        myBridge = new Bridge();
        myBridge.createTestBridge();



    }

    public void draw(GraphicsContext gc){
        for(RigidBodyObject tmpObject:rigidBodyObjects){
            tmpObject.draw(gc);
        }
        myBridge.draw(gc);
    }

    public void update(double dt){
        updateRigidBodies(dt);
        myBridge.computeTimeStepImplicit(0.5,dt);
        myBridge.collapseBridge();
    }

    public void updateRigidBodies(double dt){
        for(RigidBodyObject tmpObject:rigidBodyObjects){

            Vec2 force;
            Vec2 relForcePos;

            boolean collides = false;

            for (BridgeSupport currSupport:myBridge.getSupports()){
                if (currSupport.isRoad()){
                    if(HelperClass.gjk(tmpObject,currSupport.getStreetRB())){
                        collides = true;
                        currSupport.getPointA().setVelocity(currSupport.getPointA().getVelocity().plus(new Vec2(0,-.5)));
                        currSupport.getPointB().setVelocity(currSupport.getPointB().getVelocity().plus(new Vec2(0,-.5)));
                    }
                }

            }

            if(collides){
                force = new Vec2(0.001,.1);
                relForcePos = new Vec2(0,0);

                tmpObject.setAngularVel(tmpObject.getAngularVel() + tmpObject.computeAngularAccel(relForcePos,force));
                tmpObject.setVelocity(tmpObject.getVelocity().plus(tmpObject.linearAcceleration(force)));
                tmpObject.setVelocity(tmpObject.getVelocity().mul(new Vec2(1,0)).plus(new Vec2(0,1)));
                tmpObject.setPos(tmpObject.getPos().plus(tmpObject.getVelocity()));
                tmpObject.setTorque(tmpObject.getTorque() + tmpObject.getAngularVel());
                //System.out.println(tmpObject.getPos());

            } else {
                force = new Vec2(0.001,-.1);
                relForcePos = new Vec2(0,0);

                tmpObject.setAngularVel(tmpObject.getAngularVel() + tmpObject.computeAngularAccel(relForcePos,force));
                tmpObject.setVelocity(tmpObject.getVelocity().plus(tmpObject.linearAcceleration(force)));
                tmpObject.setPos(tmpObject.getPos().plus(tmpObject.getVelocity()));
                tmpObject.setTorque(tmpObject.getTorque() + tmpObject.getAngularVel());
                //System.out.println(tmpObject.getPos());

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
