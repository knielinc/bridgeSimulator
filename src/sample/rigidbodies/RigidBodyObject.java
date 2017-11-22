package sample.rigidbodies;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sample.Vec2;

public class RigidBodyObject extends Particle{
    double angularVel;
    DrawablePolygon polygon;
    double momentOfInertia;
    double torque;
    public RigidBodyObject(double x, double y, double mass, boolean fixed, DrawablePolygon polygon) {
        super(x, y, mass, fixed);
        angularVel = 0;
        this.polygon = polygon;
        this.momentOfInertia = polygon.getMomentOfInertia();
    }

    @Override
    public void draw(GraphicsContext gc) {
        double maxY = gc.getCanvas().getHeight();
        gc.setFill(Color.BLACK);
        //gc.fillOval(getxPos(),maxY-getyPos(),5,5);
        gc.translate(getxPos(),-getyPos() + maxY);
        gc.rotate(-torque);
        polygon.draw(gc);
        gc.rotate(torque);
        gc.translate(-getxPos(),getyPos()-maxY);
        //gc.fillOval(getxPos(),maxY-getyPos(),5,5);


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

    public double getTorqueForForce(Vec2 relForcePos, Vec2 force){
        double tau = relForcePos.getX() * force.getY() - relForcePos.getY() * force.getX();
        return tau;
    }

    public double computeAngularAccel(Vec2 relForcePos, Vec2 force){
        double tau = getTorqueForForce(relForcePos,force);

        return tau/momentOfInertia;
    }

    public Vec2 getRelativePos(double x, double y){
        return new Vec2(x - getxPos(),y - getyPos());
    }

    public Vec2 getRelativePos(Vec2 pos){
        return new Vec2(pos.getX() - getxPos(),pos.getY() - getyPos());
    }

    public double getMomentOfInertia() {
        return momentOfInertia;
    }

    public Vec2 linearAcceleration(Vec2 force){
      return  force.smult(1.0/getMass());
    }

    public double getTorque() {
        return torque;
    }

    public void setTorque(double torque) {
        this.torque = torque;
    }
}
