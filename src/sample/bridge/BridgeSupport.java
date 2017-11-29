package sample.bridge;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
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

    BridgeSupport(BridgeSupportAnchorPoint first, BridgeSupportAnchorPoint second, double inSpringConstant, boolean isRoad){
        pointA = first;
        first.addBridgeSupport(this);
        pointB = second;
        second.addBridgeSupport(this);
        springConstant = inSpringConstant;
        length = first.getPos().minus(second.getPos()).length();
        this.isRoad = isRoad;
        if (this.isRoad){
            DrawablePolygon street = new DrawablePolygon(new double[]{-length/2.0,length/2.0,length/2.0,-length/2.0},new double[]{-5,-5,5,5},4,pointA.getWeight() + pointB.getWeight());
            streetRB = new RigidBodyObject(getPos().getX(),getPos().getY(),getAngle(),pointA.getWeight() + pointB.getWeight(), false, street);
        }
    }

    public void draw(GraphicsContext gc){
        double yMax = gc.getCanvas().getHeight();
        int f = (int) Math.floor(Math.abs((length-getCurrentLength())/length) * 40 * 255);
        f = Math.min(255,f);
        gc.setStroke(Color.rgb(f,255-f,0));
        gc.setLineWidth(5);
        if (isRoad){
            //System.out.println(getAngle());
            streetRB.setTorque(getAngle());
            streetRB.setPos(getPos());
            streetRB.draw(gc);
        }
        gc.strokeLine(pointA.getxPos(), yMax-pointA.getyPos(), pointB.getxPos(), yMax-pointB.getyPos());
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
}
