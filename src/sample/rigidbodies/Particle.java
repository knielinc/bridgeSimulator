package sample.rigidbodies;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sample.Vec2;

public class Particle {
    //todo make similar implementaion as in BridgeSupportAnchorpoint
    private Vec2 pos;
    private double mass; // weight of a Support is determined by the weight of the 2 anchorpoints combined
    private Vec2 velocity;
    private boolean isFixed;

    Particle(double x, double y, double weight, boolean fixed){
        pos = new Vec2(x,y);
        velocity = new Vec2(0,0);
        this.mass = weight;
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
        //draw velocities
        gc.strokeLine(getxPos(),yMax-getyPos(),getxPos()+(4*velocity.getX()),yMax-(getyPos() + (4*velocity.getY())));
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

    public double getMass() {
        return mass;
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

    public boolean isFixed() {
        return isFixed;
    }
}
