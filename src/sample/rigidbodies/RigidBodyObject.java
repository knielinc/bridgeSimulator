package sample.rigidbodies;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import sample.Vec2;
import sample.bridge.BridgeSupport;

public class RigidBodyObject extends Particle{
    double angularVel;
    DrawablePolygon polygon;
    double momentOfInertia;
    double torque;

    Image imgDrawable = null;
    double minX,maxX,minY,maxY;

    double prevTorque;
    Vec2 prevPos;
    Vec2 prevVel;

    double prevAngularVel;


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
        minX = polygon.getSupport(new Vec2(-1.,0.)).getX();
        maxX = polygon.getSupport(new Vec2(1.,0.)).getX();
        minY = polygon.getSupport(new Vec2(0.,-1.)).getY();
        maxY = polygon.getSupport(new Vec2(0.,1.)).getY();


        prevAngularVel = getAngularVel();
        prevPos = getPos();
        prevTorque = getTorque();
        prevVel = getVelocity();
    }

    public RigidBodyObject(double x, double y, double torque, boolean fixed, DrawablePolygon polygon,Image img) {
        super(x, y, polygon.getMass(), fixed);
        partOfBridge = false;
        this.support = null;

        angularVel = 0;
        this.polygon = polygon;
        this.torque = torque;
        this.momentOfInertia = polygon.getMomentOfInertia();

        double boundingBox[] = polygon.getBoundingBox();

        minX = boundingBox[0];
        minY = boundingBox[1];
        maxX = boundingBox[2];
        maxY = boundingBox[3];


        prevAngularVel = getAngularVel();
        prevPos = getPos();
        prevTorque = getTorque();
        prevVel = getVelocity();
        this.imgDrawable = img;
    }

    @Override
    public void draw(GraphicsContext gc) {
        double maxY = gc.getCanvas().getHeight();
        gc.setFill(Color.BLACK);
        //gc.fillOval(getxPos(),maxY-getyPos(),5,5);
        gc.translate(getxPos(),-getyPos() + maxY);
        gc.rotate(-getTorqueInDegrees());
        if(imgDrawable != null){
            //polygon.draw(gc,false);
            //gc.fillRect(this.minX,this.minY,this.maxX-this.minX,this.maxY-this.minY);
            gc.drawImage(imgDrawable,this.minX,this.minY,this.maxX-this.minX,this.maxY-this.minY);
        } else {
            polygon.draw(gc,true);
        }
        gc.rotate(getTorqueInDegrees());
        gc.translate(-getxPos(),getyPos()-maxY);
        //gc.fillOval(getxPos(),maxY-getyPos(),5,5);
        /*
        //draw collisionPoints
        if (collisionPoint != null && collisionCorrectionVec != null) {
            gc.setFill(Color.WHITE);
            gc.fillRect(collisionPoint.getX() - 5, maxY - collisionPoint.getY() - 5, 10, 10);
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(1);
            gc.strokeRect(collisionPoint.getX() - 5, maxY - collisionPoint.getY() - 5, 10, 10);

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
        */
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

    public double getPrevTorque() {
        return prevTorque;
    }


    public void setPrevTorque(double prevTorque) {
        this.prevTorque = prevTorque;
    }

    public Vec2 getPrevPos() {
        return prevPos;
    }

    public void setPrevPos(Vec2 prevPos) {
        this.prevPos = prevPos;
    }

    public Vec2 getPrevVel() {
        return prevVel;
    }

    public void setPrevVel(Vec2 prevVel) {
        this.prevVel = prevVel;
    }

    public double getPrevAngularVel() {
        return prevAngularVel;
    }

    public void setPrevAngularVel(double prevAngularVel) {
        this.prevAngularVel = prevAngularVel;
    }

    public Image getImgDrawable() { return imgDrawable; }

    public void setImgDrawable(Image imgDrawable) { this.imgDrawable = imgDrawable; }


}
