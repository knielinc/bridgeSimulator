package sample;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BridgeSupportAnchorPoint {
    private myVec pos;
    private double weight; // weight of a Support is determined by the weight of the 2 anchorpoints combined
    private myVec velocity;


    BridgeSupportAnchorPoint(double x, double y, double weight){
        pos = new myVec(x,y);
        velocity = new myVec(0,0);

        this.weight = weight;

    }
    public void draw(GraphicsContext gc){
        gc.setFill(Color.RED);
        double yMax = gc.getCanvas().getHeight();
        gc.fillOval(getxPos()-5,yMax-getyPos()-5,10,10);
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

}
