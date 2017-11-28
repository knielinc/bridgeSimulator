package sample.bridge;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sample.Vec2;

import java.util.ArrayList;

public class BridgeSupportAnchorPoint {
    public static int INDEX = 0;
    private int myIndex;
    private Vec2 pos;
    private double weight; // weight of a Support is determined by the weight of the 2 anchorpoints combined
    private Vec2 velocity;
    private Vec2 force;
    private ArrayList<BridgeSupport> supports;
    private boolean isFixed;

    BridgeSupportAnchorPoint(double x, double y, double weight, boolean fixed){
        force = Vec2.VEC_ZERO;
        pos = new Vec2(x,y);
        velocity = new Vec2(0,0);

        this.weight = weight;
        supports = new ArrayList<>();

        isFixed = fixed;
        this.myIndex = INDEX;
        INDEX++;
    }
    public void draw(GraphicsContext gc){
        gc.setFill(Color.RED);
        if(isFixed){
            gc.setFill(Color.BLUE);
        }
        double yMax = gc.getCanvas().getHeight();
        gc.fillOval(getxPos()-5,yMax-getyPos()-5,10,10);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        //gc.strokeLine(getxPos(),yMax-getyPos(),getxPos()+(4*velocity.getX()),yMax-(getyPos() + (4*velocity.getY())));
        double xForce = force.getX();
        double yForce = force.getY();
        gc.strokeLine(getxPos(),yMax-getyPos(),getxPos()+(xForce),yMax-(getyPos() + (yForce)));
        gc.setFill(Color.CYAN);
        gc.fillText(String.valueOf(getMyIndex()),getxPos(),yMax - getyPos());

    }

    public double getxPos() {
        return pos.getX();
    }

    public double getyPos() {
        return pos.getY();
    }

    public Vec2 getPos() {
        return pos;
    }

    public double getWeight() {
        return weight;
    }

    public Vec2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vec2 velocity) {
        this.velocity = velocity;
    }

    public void setxPos(double xPos) {
        pos.setX(xPos);
    }

    public void setyPos(double yPos) {
        pos.setY(yPos);
    }

    public void setPos(Vec2 newVec){
        pos = newVec;
    }
    public void addBridgeSupport (BridgeSupport in){
        supports.add(in);
    }
    public void removeBridgeSupport (BridgeSupport in){
        supports.remove(in);
    }

    public ArrayList<BridgeSupport> getSupports() {
        return supports;
    }

    public boolean isFixed() {
        return isFixed;
    }

    public static int getPublicMaxIndex() {
        return INDEX;
    }

    public int getMyIndex() {
        return myIndex;
    }

    public Vec2 getForce() {
        return force;
    }

    public void setForce(Vec2 force) {
        this.force = force;
    }
}
