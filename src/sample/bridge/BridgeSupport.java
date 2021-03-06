package sample.bridge;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.apache.commons.math3.linear.RealMatrix;
import sample.Main;
import sample.Vec2;
import sample.rigidbodies.DrawablePolygon;
import sample.rigidbodies.RigidBodyObject;

public class BridgeSupport {
    private double xPos;
    private double yPos;
    private double length;
    private double tension;
    private RealMatrix Jx,Jv;
    private BridgeSupportAnchorPoint pointA,pointB;
    private double springConstant; //might make final
    private boolean isBroken = false;
    private boolean isRoad;
    private RigidBodyObject streetRB;
    private double weight;

    BridgeSupport(BridgeSupportAnchorPoint first, BridgeSupportAnchorPoint second, double inSpringConstant, boolean isRoad,double weight){
        this.weight = weight;
        pointA = first;
        pointA.addWeight(weight/2);
        first.addBridgeSupport(this);
        pointB = second;
        pointB.addWeight(weight/2);
        second.addBridgeSupport(this);
        springConstant = inSpringConstant;
        length = first.getPos().minus(second.getPos()).length();
        this.isRoad = isRoad;
        if (this.isRoad){
            DrawablePolygon street = new DrawablePolygon(new double[]{-length/2.0,-length/4.0,length/4.0,length/2.0,length/4.0,-length/4.0},new double[]{0,-5,-5,0,5,5},6,(weight) * 10);
            streetRB = new RigidBodyObject(getPos().getX(),getPos().getY(),getAngle(), false, street);
            streetRB.setSupport(this);
        }
    }

    public void draw(GraphicsContext gc) {
        double yMax = gc.getCanvas().getHeight();
        int f = (int) Math.floor(Math.abs((length - getCurrentLength()) / length) * 40 * 255);
        f = Math.min(255, f);


        if (isRoad){
            streetRB.draw(gc);
            if (Main.DEBUG_MODE_ENABLED){
                gc.setStroke(Color.rgb(f, 255 - f, 0));
                gc.setLineWidth(5);
                gc.strokeLine(pointA.getxPos(), yMax-pointA.getyPos(), pointB.getxPos(), yMax-pointB.getyPos());
            }
        } else {
            gc.setStroke(Color.rgb(f, 255 - f, 0));
            gc.setLineWidth(5);
            gc.strokeLine(pointA.getxPos(), yMax-pointA.getyPos(), pointB.getxPos(), yMax-pointB.getyPos());
        }

        pointA.draw(gc);
        pointB.draw(gc);

    }

    public double calculateStress(){
        return (Math.abs((length-getCurrentLength())/length) * 50); //high stress : 1 and above
    }

    public double getLength(){return length;}

    public void setLength(double length) {
        this.length = length;
    }

    public double getCurrentLength(){
        return pointA.getPos().minus(pointB.getPos()).length();
    }

    public double getSpringConstant() {
        return springConstant;
    }

    public double getTension() {
        return tension;
    }

    public BridgeSupportAnchorPoint getPointA() {
        return pointA;
    }

    public BridgeSupportAnchorPoint getPointB() {
        return pointB;
    }

    public void setPointA(BridgeSupportAnchorPoint pointA) {
        this.pointA = pointA;
    }

    public void setPointB(BridgeSupportAnchorPoint pointB) {
        this.pointB = pointB;
    }

    public boolean isBroken() {
        return isBroken;
    }

    public void setBroken(boolean bool){
        isBroken = bool;
    }

    public boolean isRoad() {
        return isRoad;
    }

    public RigidBodyObject getStreetRB() {
        if(isRoad){
            return streetRB;
        } else {
            return null;
        }
    }

    public Vec2 getPos(){
        return pointB.getPos().plus(pointA.getPos()).smult(0.5);
    }

    public double getAngle(){
        return Math.atan2(getVec().getY(),getVec().getX());
    }

    public Vec2 getVec(){
        Vec2 pos1 = pointA.getPos();
        Vec2 pos2 = pointB.getPos();
        return pos2.minus(pos1);
    }

    public Vec2 getNormalizedVec(){
        Vec2 pos1 = pointA.getPos();
        Vec2 pos2 = pointB.getPos();
        return pos1.minus(pos2).normalize();
    }

    public void setJv(RealMatrix jv) {
        Jv = jv;
    }

    public void setJx(RealMatrix jx) {
        Jx = jx;
    }


    public RealMatrix getJv() {
        return Jv;
    }

    public RealMatrix getJx() {
        return Jx;
    }

    public void  updateRoadRB (double dt){
        if (isRoad){
            //System.out.println(getAngle());

            streetRB.setAngularVel((getAngle() - streetRB.getTorque()) / dt);
            streetRB.setVelocity(getPos().minus(streetRB.getPos()).smult(1/dt));
            streetRB.setTorque(getAngle());
            streetRB.setPos(getPos());
            streetRB.setPrevAngularVel(streetRB.getAngularVel());
            streetRB.setPrevPos(streetRB.getPos());
            streetRB.setPrevTorque(streetRB.getTorque());
            streetRB.setPrevVel(streetRB.getPrevVel());
        }
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
