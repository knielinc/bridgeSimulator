package sample;

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
    }

    public void draw(){
        //TO BE IMPLEMENTED
    }
}
