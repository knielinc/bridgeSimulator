package sample;

import javafx.scene.canvas.GraphicsContext;
import sample.rigidbodies.DrawablePolygon;
import sample.rigidbodies.RigidBodyObject;

import java.util.ArrayList;

public class GameScene {
    ArrayList<RigidBodyObject> rigidBodyObjects;
    public GameScene(){
        rigidBodyObjects = new ArrayList<>();
        DrawablePolygon car = new DrawablePolygon(new double[]{-10.,10.,10.,5,-5.,-10.},new double[]{0,0,3,7,7,3},6,1);
        rigidBodyObjects.add(new RigidBodyObject(100,100,1,false, car));
    }

    public void draw(GraphicsContext gc){
        for(RigidBodyObject tmpObject:rigidBodyObjects){
            tmpObject.draw(gc);
        }
    }

    public void updateRigidBodies(){
        for(RigidBodyObject tmpObject:rigidBodyObjects){
            myVec force = new myVec(0,.001);
            myVec relForcePos = new myVec(80,0);

            tmpObject.setAngularVel(tmpObject.getAngularVel() + tmpObject.computeAngularAccel(relForcePos,force));
            tmpObject.setVelocity(tmpObject.getVelocity().plus(tmpObject.linearAcceleration(force)));
            tmpObject.setPos(tmpObject.getPos().plus(tmpObject.getVelocity()));
            tmpObject.setTorque(tmpObject.getTorque() + tmpObject.getAngularVel());
            System.out.println(tmpObject.getPos());
        }
    }

}
