package sample;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class BridgeSupportAnchorPoint {
    private myVec pos;
    private double weight; // weight of a Support is determined by the weight of the 2 anchorpoints combined
    private myVec velocity;
    private ArrayList<BridgeSupport> supports;
    private boolean isFixed;

    BridgeSupportAnchorPoint(double x, double y, double weight, boolean fixed){
        pos = new myVec(x,y);
        velocity = new myVec(0,0);

        this.weight = weight;
        supports = new ArrayList<>();

        isFixed = fixed;
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
        gc.strokeLine(getxPos(),yMax-getyPos(),getxPos()+(4*velocity.getX()),yMax-(getyPos() + (4*velocity.getY())));
    }

    public double getxPos() {
        return pos.getX();
    }

    public double getyPos() {
        return pos.getY();
    }

    public myVec getPos() {
        return pos;
    }

    public double getWeight() {
        return weight;
    }

    public myVec getVelocity() {
        return velocity;
    }

    public void setVelocity(myVec velocity) {
        this.velocity = velocity;
    }

    public void setxPos(double xPos) {
        pos.setX(xPos);
    }

    public void setyPos(double yPos) {
        pos.setY(yPos);
    }

    public void setPos(myVec newVec){
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
}
