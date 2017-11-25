package sample;

import javafx.scene.canvas.GraphicsContext;
import sample.rigidbodies.DrawablePolygon;
import sample.rigidbodies.RigidBodyObject;

import java.util.ArrayList;

public class GameScene {
    ArrayList<RigidBodyObject> rigidBodyObjects;
    public GameScene(){
        rigidBodyObjects = new ArrayList<>();
        DrawablePolygon car = new DrawablePolygon(new double[]{-10.,10.,10.,5,-5.,-10.},new double[]{0,0,3,7,7,3},6,0.01);
        rigidBodyObjects.add(new RigidBodyObject(200,200,.01,false, car));
    }

    public void draw(GraphicsContext gc){
        for(RigidBodyObject tmpObject:rigidBodyObjects){
            tmpObject.draw(gc);
        }
    }

    public void updateRigidBodies(double dt){
        for(RigidBodyObject tmpObject:rigidBodyObjects){

            Vec2 force = new Vec2(0,.00001);
            Vec2 relForcePos = new Vec2(800,0);

            tmpObject.setAngularVel(tmpObject.getAngularVel() + tmpObject.computeAngularAccel(relForcePos,force));
            tmpObject.setVelocity(tmpObject.getVelocity().plus(tmpObject.linearAcceleration(force)));
            tmpObject.setPos(tmpObject.getPos().plus(tmpObject.getVelocity()));
            tmpObject.setTorque(tmpObject.getTorque() + tmpObject.getAngularVel());
            System.out.println(tmpObject.getPos());

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
