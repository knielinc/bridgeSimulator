package sample.rigidbodies;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sample.Vec2;
import sample.bridge.BridgeSupport;

public class RigidBodyObject extends Particle{
    double angularVel;
    DrawablePolygon polygon;
    double momentOfInertia;
    double torque;
    Vec2 collisionPoint;
    Vec2 collisionCorrectionVec;
    Vec2 restCollisionForce;
    boolean partOfBridge;
    BridgeSupport support;

    public RigidBodyObject(double x, double y, double torque, boolean fixed, DrawablePolygon polygon) {
        super(x, y, polygon.getMass(), fixed);
        partOfBridge = false;
        this.support = null;

        angularVel = 0;
        this.polygon = polygon;
        this.torque = torque;
        this.momentOfInertia = polygon.getMomentOfInertia();
    }

    @Override
    public void draw(GraphicsContext gc) {
        double maxY = gc.getCanvas().getHeight();
        gc.setFill(Color.BLACK);
        //gc.fillOval(getxPos(),maxY-getyPos(),5,5);
        gc.translate(getxPos(),-getyPos() + maxY);
        gc.rotate(-getTorqueInDegrees());
        polygon.draw(gc);
        gc.rotate(getTorqueInDegrees());
        gc.translate(-getxPos(),getyPos()-maxY);
        //gc.fillOval(getxPos(),maxY-getyPos(),5,5);

        //draw collisionPoints
        if (collisionPoint != null && collisionCorrectionVec != null) {
            /*gc.setFill(Color.WHITE);
            gc.fillRect(collisionPoint.getX() - 5, maxY - collisionPoint.getY() - 5, 10, 10);
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(1);
            gc.strokeRect(collisionPoint.getX() - 5, maxY - collisionPoint.getY() - 5, 10, 10);*/

            gc.setStroke(Color.RED);
            gc.setLineWidth(3);
            gc.strokeLine(collisionPoint.getX(), maxY - collisionPoint.getY(), collisionPoint.getX() + collisionCorrectionVec.getX(), maxY - (collisionCorrectionVec.getY() + collisionPoint.getY()));
        }


        if (collisionPoint != null && restCollisionForce != null) {
            gc.setStroke(Color.BLUE);
            gc.setLineWidth(3);
            gc.strokeLine(collisionPoint.getX(), maxY - collisionPoint.getY(), collisionPoint.getX() + restCollisionForce.getX(), maxY - (restCollisionForce.getY() + collisionPoint.getY()));
        }

        gc.setStroke(Color.BLUE);
        gc.setLineWidth(3);

        gc.strokeLine(getxPos(), maxY - getyPos(), getxPos() + getVelocity().getX(), maxY - (getVelocity().getY() + getyPos()));

        collisionPoint = null;


    }

    public double getTorqueInDegrees(){
        return Math.toDegrees(torque);
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

    public void updatePos(double dt){
        setPos(getPos().plus(getVelocity().smult(dt)));
    }

    public void updateTorque(double dt){
        setTorque(getTorque() + getAngularVel() * dt);
    }

    public void setTorque(double torque) {
        this.torque = torque;
    }

    public Vec2 getSupport(Vec2 direction){
        double x,y;
        x = Math.cos(torque) * direction.getX() + Math.sin(torque) * direction.getY();
        y = -1 * Math.sin(torque) * direction.getX() + Math.cos(torque) * direction.getY();
        Vec2 newDirection = new Vec2(x,y);
        Vec2 returnVal = polygon.getSupport(newDirection);

        x = Math.cos(-torque) * returnVal.getX() + Math.sin(-torque) * returnVal.getY();
        y = -1 * Math.sin(-torque) * returnVal.getX() + Math.cos(-torque) * returnVal.getY();

        returnVal.setPos(x,y);

        return returnVal.plus(getPos());
    }

    public Vec2 getCollisionPoint() {
        return collisionPoint;
    }

    public void setCollisionPoint(Vec2 collisionPoint) {
        this.collisionPoint = collisionPoint;
    }

    public void setCollisionCorrectionVec(Vec2 collisionCorrectionVec) {
        this.collisionCorrectionVec = collisionCorrectionVec;
    }

    public void setRestCollisionForce(Vec2 restCollisionForce) {
        this.restCollisionForce = restCollisionForce;
    }

    public void setPartOfBridge(boolean partOfBridge) {
        this.partOfBridge = partOfBridge;
    }

    public boolean isPartOfBridge() {
        return partOfBridge;
    }

    public void setSupport(BridgeSupport support) {
        partOfBridge = true;
        this.support = support;
    }

    public BridgeSupport getSupport() {
        return support;
    }
}
