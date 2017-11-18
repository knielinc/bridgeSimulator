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
        rigidBodyObjects.add(new RigidBodyObject(200,200,.0001,false, car));
    }

    public void draw(GraphicsContext gc){
        for(RigidBodyObject tmpObject:rigidBodyObjects){
            tmpObject.draw(gc);
        }
    }

    public void updateRigidBodies(double dt){
        for(RigidBodyObject tmpObject:rigidBodyObjects){
            /*
            myVec force = new myVec(0,.001);
            myVec relForcePos = new myVec(80,0);

            tmpObject.setAngularVel(tmpObject.getAngularVel() + tmpObject.computeAngularAccel(relForcePos,force));
            tmpObject.setVelocity(tmpObject.getVelocity().plus(tmpObject.linearAcceleration(force)));
            tmpObject.setPos(tmpObject.getPos().plus(tmpObject.getVelocity()));
            tmpObject.setTorque(tmpObject.getTorque() + tmpObject.getAngularVel());
            System.out.println(tmpObject.getPos());
            */
            double mass = tmpObject.getMass();

            myVec fext = tmpObject.getPos().minus(new myVec(150,200));
            fext.setPos(fext.getY(),-1 * fext.getX());
            fext = fext.normalize().smult(0.0001);

            //testing out simple constraint solver with distance preserving constrained for 1 particle

            myVec p = tmpObject.getPos().minus(new myVec(150,200));
            myVec pdot = tmpObject.getVelocity();
            double lambda = fext.smult(-1).dot(p) - pdot.smult(mass).dot(pdot) / (p.dot(p));
            myVec force = p.smult(lambda);
            myVec relForcePos = new myVec(0,0);

            tmpObject.setVelocity(tmpObject.getVelocity().plus(tmpObject.linearAcceleration(force.plus(fext))));
            tmpObject.setPos(tmpObject.getPos().plus(tmpObject.getVelocity()));
            System.out.println(tmpObject.getPos());


        }
    }

}
