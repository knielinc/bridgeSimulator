package sample;

public class BridgeSupportAnchorPoint {
    private double xPos;
    private double yPos;
    private double weight; // weight of a Support is determined by the weight of the 2 anchorpoints combined
    BridgeSupportAnchorPoint(double x, double y){
        xPos = x;
        yPos = y;
    }
}
