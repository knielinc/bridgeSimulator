package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BridgeSupport {
    private double xPos;
    private double yPos;
    private double length;
    private double tension;
    private BridgeSupportAnchorPoint pointA,pointB;
    private double springConstant; //might make final

    BridgeSupport(BridgeSupportAnchorPoint first, BridgeSupportAnchorPoint second, double inSpringConstant){
        pointA = first;
        pointB = second;
        springConstant = inSpringConstant;
        length = first.getPos().minus(second.getPos()).length();
    }

    public void draw(GraphicsContext gc){
        pointA.draw(gc);
        pointB.draw(gc);
        double yMax = gc.getCanvas().getHeight();
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(5);
        gc.strokeLine(pointA.getxPos(), yMax-pointA.getyPos(), pointB.getxPos(), yMax-pointB.getyPos());
    }

    public double getLength(){return length;}

    public double getCurrentLength(){
        return pointA.getPos().minus(pointB.getPos()).length();
    }

    public double getSpringConstant() {
        return springConstant;
    }

    public double getTension() {
        return tension;
    }

    public myVec getNormalizedVec(){
        myVec pos1 = pointA.getPos();
        myVec pos2 = pointB.getPos();
        return pos1.minus(pos2).normalize();
    }
}
