package sample.rigidbodies;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Polygon;
import sample.myVec;

import static com.sun.tools.doclint.Entity.theta;

public class RigidBodyObject extends Particle{
    double angularVel;
    DrawablePolygon polygon;
    double momentOfInertia;
    double torque;
    RigidBodyObject(double x, double y, double mass, boolean fixed, DrawablePolygon polygon) {
        super(x, y, mass, fixed);
        angularVel = 0;
        this.polygon = polygon;
        this.momentOfInertia = polygon.getMomentOfInertia(getMass());
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.translate(-getxPos(),-getyPos());
        gc.fillPolygon(polygon.getxVals(),polygon.getyVals(),polygon.getNumberOfPoints());
        gc.translate(getxPos(),getyPos());
    }

    public double getAngularVel() {
        return angularVel;
    }

    public void setAngularVel(double angularVel) {
        this.angularVel = angularVel;
    }

    public DrawablePolygon getPolygon() {
        return polygon;
    }

    public double getTorqueForForce(myVec relForcePos, myVec force){
        double tau = relForcePos.getX() * force.getY() - relForcePos.getY() * force.getX();
        return tau;
    }

    public double computeAngularAccel(myVec relForcePos, myVec force){
        double tau = getTorqueForForce(relForcePos,force);

        return tau/momentOfInertia;
    }

    public myVec getRelativePos(double x, double y){
        return new myVec(x - getxPos(),y - getyPos());
    }

    public myVec getRelativePos(myVec pos){
        return new myVec(pos.getX() - getxPos(),pos.getY() - getyPos());
    }

    public double getMomentOfInertia() {
        return momentOfInertia;
    }

    public myVec linearAcceleration(myVec force){
      return  force.smult(1.0/getMass());
    }

}
