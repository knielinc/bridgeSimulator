package sample.bridge;

import com.sun.org.apache.regexp.internal.RE;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.MatrixType;
import org.apache.commons.math3.geometry.Vector;
import org.apache.commons.math3.geometry.euclidean.oned.Vector1D;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.linear.*;
import sample.Vec2;
import sample.utils.HelperClass;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class Bridge {
    private ArrayList<BridgeSupport> supports;
    private ArrayList<BridgeSupportAnchorPoint> bridgeSupportAnchorPoints;
    private boolean bridgeIsBreakable = true;

    public Bridge() {
        //constuctor to be implemented
        bridgeSupportAnchorPoints = new ArrayList();
        supports = new ArrayList();
    }

    public void draw(GraphicsContext gc) {
        Iterator<BridgeSupport> supportIterator;
        for (supportIterator = supports.iterator(); supportIterator.hasNext(); ) {
            supportIterator.next().draw(gc);
        }
    }

    public void addSupport(BridgeSupport inSupport) {
        supports.add(inSupport);
    }

    public ArrayList<BridgeSupport> getSupports() {
        return supports;
    }

    public ArrayList<BridgeSupportAnchorPoint> getBridgeSupportAnchorPoints() {
        return bridgeSupportAnchorPoints;
    }

    public void createTestRoad(){
        //line 0-11 left -> right
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(100, 200, true));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(150, 200, false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(200, 200, false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(250, 200, false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(300, 200, false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(350, 200, false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(450, 200, false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(500, 200, false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(550, 200, false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(600, 200, false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(650, 200, false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(700, 200, true));
        //road
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(0), bridgeSupportAnchorPoints.get(1), 70, true,0.5));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(1), bridgeSupportAnchorPoints.get(2), 70, true,0.5));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(2), bridgeSupportAnchorPoints.get(3), 70, true,0.5));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(3), bridgeSupportAnchorPoints.get(4), 70, true,0.5));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(4), bridgeSupportAnchorPoints.get(5), 70, true,0.5));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(5), bridgeSupportAnchorPoints.get(6), 70, true,0.5));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(6), bridgeSupportAnchorPoints.get(7), 70, true,0.5));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(7), bridgeSupportAnchorPoints.get(8), 70, true,0.5));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(8), bridgeSupportAnchorPoints.get(9), 70, true,0.5));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(9), bridgeSupportAnchorPoints.get(10), 70, true,0.5));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(10), bridgeSupportAnchorPoints.get(11), 70, true,0.5));

    }

    public void createTestBridge1() {
        //line 0-11 left -> right
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(100, 200, true));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(150, 200, false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(200, 200, false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(250, 200, false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(300, 200, false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(350, 200, false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(450, 200, false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(500, 200, false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(550, 200, false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(600, 200, false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(650, 200, false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(700, 200, true));
        //road
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(0), bridgeSupportAnchorPoints.get(1), 70, true,0.5));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(1), bridgeSupportAnchorPoints.get(2), 70, true,0.5));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(2), bridgeSupportAnchorPoints.get(3), 70, true,0.5));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(3), bridgeSupportAnchorPoints.get(4), 70, true,0.5));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(4), bridgeSupportAnchorPoints.get(5), 70, true,0.5));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(5), bridgeSupportAnchorPoints.get(6), 70, true,0.5));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(6), bridgeSupportAnchorPoints.get(7), 70, true,0.5));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(7), bridgeSupportAnchorPoints.get(8), 70, true,0.5));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(8), bridgeSupportAnchorPoints.get(9), 70, true,0.5));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(9), bridgeSupportAnchorPoints.get(10), 70, true,0.5));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(10), bridgeSupportAnchorPoints.get(11), 70, true,0.5));

        //upper upper support points 12 -> 17
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(100, 150, true));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(250, 300, false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(350, 350, false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(450, 350, false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(550, 300, false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(700, 150, true));
        //lower upper support points 18 -> 23
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(100, 100, true));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(250, 250, false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(350, 300, false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(450, 300, false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(550, 250, false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(700, 100, true));
        //upper lower support points 24 -> 26
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(350, 250, false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(450, 250, false));

        //upper upper supports
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(12), bridgeSupportAnchorPoints.get(1) , 70, false,0.5));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(1), bridgeSupportAnchorPoints.get(13) , 70, false,0.5));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(13), bridgeSupportAnchorPoints.get(14), 70, false,0.5));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(14), bridgeSupportAnchorPoints.get(15), 70, false,0.5));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(15), bridgeSupportAnchorPoints.get(16), 70, false,0.5));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(16), bridgeSupportAnchorPoints.get(10), 70, false,0.5));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(10), bridgeSupportAnchorPoints.get(17), 70, false,0.5));
        //can be commented out for original bridge (will break though)
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(16), bridgeSupportAnchorPoints.get(11), 70, false,0.5));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(0), bridgeSupportAnchorPoints.get(13) , 70, false,0.5));


        //middle upper supports
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(12), bridgeSupportAnchorPoints.get(2) , 70, false,0.5));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(2), bridgeSupportAnchorPoints.get(13) , 70, false,0.5));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(13), bridgeSupportAnchorPoints.get(19), 70, false,0.5));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(13), bridgeSupportAnchorPoints.get(20), 70, false,0.5));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(14), bridgeSupportAnchorPoints.get(20), 70, false,0.5));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(15), bridgeSupportAnchorPoints.get(21), 70, false,0.5));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(14), bridgeSupportAnchorPoints.get(21), 70, false,0.5));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(15), bridgeSupportAnchorPoints.get(20), 70, false,0.5));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(21), bridgeSupportAnchorPoints.get(16), 70, false,0.5));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(16), bridgeSupportAnchorPoints.get(22), 70, false,0.5));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(16), bridgeSupportAnchorPoints.get(9) , 70, false,0.5));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(9), bridgeSupportAnchorPoints.get(17) , 70, false,0.5));


        //lower upper supports/*
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(18), bridgeSupportAnchorPoints.get(2) , 70, false,0.5));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(2), bridgeSupportAnchorPoints.get(19) , 70, false,0.5));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(19), bridgeSupportAnchorPoints.get(20), 70, false,0.5));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(20), bridgeSupportAnchorPoints.get(21), 70, false,0.5));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(21), bridgeSupportAnchorPoints.get(22), 70, false,0.5));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(22), bridgeSupportAnchorPoints.get(9) , 70, false,0.5));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(9), bridgeSupportAnchorPoints.get(23) , 70, false,0.5));

        //smaller supports

        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(4), bridgeSupportAnchorPoints.get(24) , 70, false,0.5));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(5), bridgeSupportAnchorPoints.get(24) , 70, false,0.5));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(5), bridgeSupportAnchorPoints.get(25) , 70, false,0.5));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(6), bridgeSupportAnchorPoints.get(24) , 70, false,0.5));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(6), bridgeSupportAnchorPoints.get(25) , 70, false,0.5));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(7), bridgeSupportAnchorPoints.get(25) , 70, false,0.5));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(24), bridgeSupportAnchorPoints.get(25), 70, false,0.5));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(24), bridgeSupportAnchorPoints.get(20), 70, false,0.5));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(25), bridgeSupportAnchorPoints.get(21), 70, false,0.5));

        //middleSupports

        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(3), bridgeSupportAnchorPoints.get(19) , 70, false,0.5));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(8), bridgeSupportAnchorPoints.get(22) , 70, false,0.5));
    }

    public void createTestBridge2(){
        //Points for Road 0 - 11
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(100, 200, true));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(150, 200, false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(200, 200, false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(250, 200, false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(300, 200, false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(350, 200, false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(450, 200, false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(500, 200, false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(550, 200, false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(600, 200, false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(650, 200, false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(700, 200, true));
        //Points for upper supports 12 - 21
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(150, 250, false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(200, 250, false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(250, 250, false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(300, 250, false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(350, 250, false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(450, 250, false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(500, 250, false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(550, 250, false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(600, 250, false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(650, 250, false));


        //road
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(0), bridgeSupportAnchorPoints.get(1), 70, true,0.1));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(1), bridgeSupportAnchorPoints.get(2), 70, true,0.1));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(2), bridgeSupportAnchorPoints.get(3), 70, true,0.1));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(3), bridgeSupportAnchorPoints.get(4), 70, true,0.1));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(4), bridgeSupportAnchorPoints.get(5), 70, true,0.1));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(5), bridgeSupportAnchorPoints.get(6), 70, true,0.1));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(6), bridgeSupportAnchorPoints.get(7), 70, true,0.1));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(7), bridgeSupportAnchorPoints.get(8), 70, true,0.1));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(8), bridgeSupportAnchorPoints.get(9), 70, true,0.1));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(9), bridgeSupportAnchorPoints.get(10), 70, true,0.1));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(10), bridgeSupportAnchorPoints.get(11), 70, true,0.1));

        //upper supports
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(0), bridgeSupportAnchorPoints.get(12), 70, false,0.1));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(12), bridgeSupportAnchorPoints.get(13), 70, false,0.1));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(13), bridgeSupportAnchorPoints.get(14), 70, false,0.1));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(14), bridgeSupportAnchorPoints.get(15), 70, false,0.1));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(15), bridgeSupportAnchorPoints.get(16), 70, false,0.1));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(16), bridgeSupportAnchorPoints.get(17), 70, false,0.1));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(17), bridgeSupportAnchorPoints.get(18), 70, false,0.1));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(18), bridgeSupportAnchorPoints.get(19), 70, false,0.1));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(19), bridgeSupportAnchorPoints.get(20), 70, false,0.1));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(20), bridgeSupportAnchorPoints.get(21), 70, false,0.1));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(21), bridgeSupportAnchorPoints.get(11), 70, false,0.1));

        //lower supports
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(1), bridgeSupportAnchorPoints.get(12), 70, false,0.1));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(1), bridgeSupportAnchorPoints.get(13), 70, false,0.1));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(2), bridgeSupportAnchorPoints.get(13), 70, false,0.1));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(2), bridgeSupportAnchorPoints.get(14), 70, false,0.1));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(3), bridgeSupportAnchorPoints.get(14), 70, false,0.1));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(3), bridgeSupportAnchorPoints.get(15), 70, false,0.1));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(4), bridgeSupportAnchorPoints.get(15), 70, false,0.1));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(4), bridgeSupportAnchorPoints.get(16), 70, false,0.1));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(5), bridgeSupportAnchorPoints.get(16), 70, false,0.1));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(5), bridgeSupportAnchorPoints.get(17), 70, false,0.1));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(6), bridgeSupportAnchorPoints.get(17), 70, false,0.1));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(6), bridgeSupportAnchorPoints.get(18), 70, false,0.1));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(7), bridgeSupportAnchorPoints.get(18), 70, false,0.1));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(7), bridgeSupportAnchorPoints.get(19), 70, false,0.1));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(8), bridgeSupportAnchorPoints.get(19), 70, false,0.1));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(8), bridgeSupportAnchorPoints.get(20), 70, false,0.1));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(9), bridgeSupportAnchorPoints.get(20), 70, false,0.1));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(9), bridgeSupportAnchorPoints.get(21), 70, false,0.1));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(10), bridgeSupportAnchorPoints.get(21), 70, false,0.1));

    }

    public void createWeirdBridge(){

        // middle object
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(350+1, 125, false)); // 0
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(450+1, 125, true)); // 1
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(550+1, 125, false)); // 2
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(400+1, 100, false)); // 3
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(500+1, 100, false));  // 4

        // right object


        // reusable
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(500, -10, true));  // 5
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(600, -10, true)); // 6

        // left object
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(200, 300, true)); // 7
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(100, 200, false)); // 8
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(200, 175, false)); // 9
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(300, 200, false)); // 10
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(150, 150, false)); // 11
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(250, 150, false)); // 12

        // left catcher
        // bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(0, 0, true)); // 13
        // bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(500, 0, true)); // 14

        // left road
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(8), bridgeSupportAnchorPoints.get(9), 70, true, 1));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(9), bridgeSupportAnchorPoints.get(10), 70, true, 1));

        // middle road
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(0), bridgeSupportAnchorPoints.get(1), 70, true, 1));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(1), bridgeSupportAnchorPoints.get(2), 70, true, 1));

        // right road
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(5), bridgeSupportAnchorPoints.get(6), 70, false, 1));

        // left supports
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(7), bridgeSupportAnchorPoints.get(8), 70, false,1));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(7), bridgeSupportAnchorPoints.get(10), 70, false,1));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(8), bridgeSupportAnchorPoints.get(9), 70, false,1));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(9), bridgeSupportAnchorPoints.get(10), 70, false,1));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(8), bridgeSupportAnchorPoints.get(11), 70, false,1));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(9), bridgeSupportAnchorPoints.get(11), 70, false,1));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(9), bridgeSupportAnchorPoints.get(12), 70, false,1));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(10), bridgeSupportAnchorPoints.get(12), 70, false,1));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(11), bridgeSupportAnchorPoints.get(12), 70, false,1));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(7), bridgeSupportAnchorPoints.get(9), 70, false,1));

        // middle supports
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(0), bridgeSupportAnchorPoints.get(3), 70, false,1));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(1), bridgeSupportAnchorPoints.get(4), 70, false,1));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(3), bridgeSupportAnchorPoints.get(1), 70, false,1));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(4), bridgeSupportAnchorPoints.get(2), 70, false,1));
        supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(3), bridgeSupportAnchorPoints.get(4), 70, false,1));

        // right supports: none

        // left catcher
        // supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(13), bridgeSupportAnchorPoints.get(14), 70, true,1));

    }

    /**
     * Shortcut for adding the previously stored (in {@link #bridgeSupportAnchorPoints} points to a support in {@link #supports}
     * */
    private void addSupport(int a, int b, boolean road){supports.add(new BridgeSupport(bridgeSupportAnchorPoints.get(a), bridgeSupportAnchorPoints.get(b), 70, road,1));}

    public void createRampComparisonBridges(){

        // ramp
        int rampOffset1 = 0; int xOffset1=200;
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(200+xOffset1, 0, true)); // 0 -
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(240+xOffset1, 0, true)); // 1
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(240+xOffset1, 20, false)); // 2
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(280+xOffset1, 0, true)); // 3
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(280+xOffset1, 20, false));  // 4
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(280+xOffset1, 60, false));  // 5
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(290+xOffset1, 0, true));  // 6 floor
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(300+xOffset1, 0, true));  // 7 -
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(300+xOffset1, 60, false));  // 8
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(300+xOffset1, 100, false));  // 9 -
        // add the supports
        addSupport(rampOffset1+0, rampOffset1+1, false);
        addSupport(rampOffset1+0, rampOffset1+2, true);
        addSupport(rampOffset1+1,rampOffset1+2, false);
        addSupport(rampOffset1+2, rampOffset1+5, true);
        addSupport(rampOffset1+5,rampOffset1+9,true);
        addSupport(rampOffset1+9,rampOffset1+8,true);
        addSupport(rampOffset1+8,rampOffset1+7, true);
        addSupport(rampOffset1+5,rampOffset1+8, false);
        addSupport(rampOffset1+2,rampOffset1+4, false);
        addSupport(rampOffset1+4,rampOffset1+5, false);
        addSupport(rampOffset1+1,rampOffset1+4, false);
        addSupport(rampOffset1+2,rampOffset1+3,false);
        addSupport(rampOffset1+3,rampOffset1+4, false);
        addSupport(rampOffset1+5,rampOffset1+7, false);
        addSupport(rampOffset1+3,rampOffset1+6, false);
        addSupport(rampOffset1+6,rampOffset1+7, false);
        addSupport(rampOffset1+3,rampOffset1+5, false);
        addSupport(rampOffset1+5,rampOffset1+7, false);
        addSupport(rampOffset1+6, rampOffset1+2, false);

        // ramp 2
        int rampOffset2 = 10; int xOffset2=100;
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(xOffset2+0, 0, true));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(xOffset2+100, 0, true));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(xOffset2+100, 100, false));
        addSupport(rampOffset2+0, rampOffset2+1, false);
        addSupport(rampOffset2+0, rampOffset2+2, true);
        addSupport(rampOffset2+1, rampOffset2+2, false);
    }

    public void createWaveformBridge(){
        // left ramp
        int rampOffset1 = 0; int xOffset1=0;
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(200+xOffset1, 0, true)); // 0 -
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(240+xOffset1, 0, true)); // 1
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(240+xOffset1, 20, false)); // 2
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(280+xOffset1, 0, true)); // 3
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(280+xOffset1, 20, false));  // 4
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(280+xOffset1, 60, false));  // 5
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(290+xOffset1, 0, true));  // 6 floor
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(300+xOffset1, 0, true));  // 7 -
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(300+xOffset1, 60, false));  // 8
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(300+xOffset1, 100, false));  // 9 -
        // add the supports
        addSupport(rampOffset1+0, rampOffset1+1, false);
        addSupport(rampOffset1+0, rampOffset1+2, true);
        addSupport(rampOffset1+1,rampOffset1+2, false);
        addSupport(rampOffset1+2, rampOffset1+5, true);
        addSupport(rampOffset1+5,rampOffset1+9,true);
        addSupport(rampOffset1+9,rampOffset1+8,true);
        addSupport(rampOffset1+8,rampOffset1+7, true);
        addSupport(rampOffset1+5,rampOffset1+8, false);
        addSupport(rampOffset1+2,rampOffset1+4, false);
        addSupport(rampOffset1+4,rampOffset1+5, false);
        addSupport(rampOffset1+1,rampOffset1+4, false);
        addSupport(rampOffset1+2,rampOffset1+3,false);
        addSupport(rampOffset1+3,rampOffset1+4, false);
        addSupport(rampOffset1+5,rampOffset1+7, false);
        addSupport(rampOffset1+3,rampOffset1+6, false);
        addSupport(rampOffset1+6,rampOffset1+7, false);
        addSupport(rampOffset1+3,rampOffset1+5, false);
        addSupport(rampOffset1+5,rampOffset1+7, false);
        addSupport(rampOffset1+6, rampOffset1+2, false);
    }

    public void computeTimeStepExplicit(double d, double dt) {
        ArrayList<BridgeSupportAnchorPoint> tmpNewAnchorPoints = new ArrayList();

        for (BridgeSupportAnchorPoint currAnchorPoint : bridgeSupportAnchorPoints) {

            if (!currAnchorPoint.isFixed()) {

                Vec2 f = new Vec2(0, -9.81 * currAnchorPoint.getWeight()).minus(currAnchorPoint.getVelocity().smult(d));

                Vec2 p = currAnchorPoint.getPos();

                for (ListIterator<BridgeSupport> iterator = currAnchorPoint.getSupports().listIterator(); iterator.hasNext(); ) {
                    BridgeSupport currSupport = iterator.next();

                    double k = currSupport.getSpringConstant();
                    Vec2 springF = currSupport.getNormalizedVec().smult(-k * (currSupport.getCurrentLength() - currSupport.getLength()));

                    if (currAnchorPoint == currSupport.getPointA()) {
                        f = f.plus(springF);
                    } else { // assuming else it's pointB
                        f = f.minus(springF);
                    }
                    if (bridgeIsBreakable && currSupport.calculateStress() > .9 && !currSupport.isBroken()) {
                        //make bridge breakable
                        BridgeSupportAnchorPoint pointA = currSupport.getPointA();
                        BridgeSupportAnchorPoint pointB = currSupport.getPointB();

                        BridgeSupportAnchorPoint newA = new BridgeSupportAnchorPoint(pointA.getxPos(), pointA.getyPos(), pointA.getWeight(), pointA.isFixed());
                        BridgeSupportAnchorPoint newB = new BridgeSupportAnchorPoint(pointB.getxPos(), pointB.getyPos(), pointB.getWeight(), pointB.isFixed());

                        newA.setVelocity(pointA.getVelocity());
                        newB.setVelocity(pointB.getVelocity());

                        currSupport.setLength(newA.getPos().minus(newB.getPos()).length());
                        currSupport.setPointA(newA);
                        currSupport.setPointB(newB);
                        currSupport.setBroken(true);

                        newA.addBridgeSupport(currSupport);
                        newB.addBridgeSupport(currSupport);
                        tmpNewAnchorPoints.add(newA);
                        tmpNewAnchorPoints.add(newB);

                        iterator.remove();
                    }

                }

                /*
                tried with projected force
                Vec2 a = currAnchorPoint.getVelocity();
                Vec2 b = f;
                Vec2 projectedVel = new Vec2(0,0);
                if (b.getY() != 0 || b.getX() != 0){projectedVel = b.smult(a.dot(b)/b.dot(b));}

                f = f.plus( new Vec2(0, -9.81 * currAnchorPoint.getWeight()));
                f = f.minus(projectedVel);
                */

                // Additional force for collisionasd
                double kr_ = 100;
                if (p.getY() < -1)
                    f = f.minus(new Vec2(0, 1 + p.getY()).smult(kr_));

                // Velocity update
                currAnchorPoint.setVelocity(currAnchorPoint.getVelocity().plus(f.smult(dt / currAnchorPoint.getWeight())));

                // Position update
                p = p.plus(currAnchorPoint.getVelocity().smult(dt));
                currAnchorPoint.setPos(p);
            }

        }
        bridgeSupportAnchorPoints.addAll(tmpNewAnchorPoints);
    }

    public void computeTimeStepImplicit(double d, double dt) {
        //setup jacobian matrices

        int dimension = bridgeSupportAnchorPoints.size() * 2;

        SparseRealMatrix DfDx, DfDv, M;

        DfDx = new OpenMapRealMatrix(dimension, dimension);
        DfDv = new OpenMapRealMatrix(dimension, dimension);
        M = new OpenMapRealMatrix(dimension, dimension);
        /* set all masses to 1
        for (int i = 0; i < dimension; i++){
            DfDv.addToEntry(i,i,d);
            M.addToEntry(i,i,1);
        }
        */
        //set MassMatrix with weights
        for (BridgeSupportAnchorPoint currPoint : bridgeSupportAnchorPoints) {
            int index = currPoint.getMyIndex() * 2;
            M.setEntry(index, index, currPoint.getWeight());
            M.setEntry(index + 1, index + 1, currPoint.getWeight());
        }

        //Compute df/dx,df/dy with trick from Choi, K 2002
        for (BridgeSupport tmpSupport : supports) {
            BridgeSupportAnchorPoint a = tmpSupport.getPointA();
            BridgeSupportAnchorPoint b = tmpSupport.getPointB();
            int i = a.getMyIndex();
            int j = b.getMyIndex();
            RealVector posA, posB, aMinusB;
            posA = new ArrayRealVector(new double[]{a.getxPos(), a.getyPos()});
            posB = new ArrayRealVector(new double[]{b.getxPos(), b.getyPos()});
            aMinusB = posA.subtract(posB);
            double innerDotAB = aMinusB.dotProduct(aMinusB);
            double oneOverInnerDotAB = 1.0 / innerDotAB;
            RealMatrix outerDotAB = aMinusB.outerProduct(aMinusB);
            RealMatrix twoByTwoI = MatrixUtils.createRealIdentityMatrix(2);
            double currLength = aMinusB.getNorm();
            double length = tmpSupport.getLength();
            double k = tmpSupport.getSpringConstant();

            RealMatrix Jx = outerDotAB.scalarMultiply(oneOverInnerDotAB).add(twoByTwoI.subtract(outerDotAB.scalarMultiply(oneOverInnerDotAB)).scalarMultiply(1.0 - (length / currLength))).scalarMultiply(k);
            RealMatrix Jv = MatrixUtils.createRealIdentityMatrix(2).scalarMultiply(d);

            DfDx.setSubMatrix(Jx.getData(), i * 2, j * 2);
            DfDx.setSubMatrix(Jx.getData(), j * 2, i * 2);

            DfDx.setSubMatrix(DfDx.getSubMatrix(i * 2, i * 2 + 1, i * 2, i * 2 + 1).subtract(Jx).getData(), i * 2, i * 2);
            DfDx.setSubMatrix(DfDx.getSubMatrix(j * 2, j * 2 + 1, j * 2, j * 2 + 1).subtract(Jx).getData(), j * 2, j * 2);

            DfDv.setSubMatrix(Jv.getData(), i * 2, j * 2);
            DfDv.setSubMatrix(Jv.getData(), j * 2, i * 2);

            DfDv.setSubMatrix(DfDv.getSubMatrix(i * 2, i * 2 + 1, i * 2, i * 2 + 1).subtract(Jv).getData(), i * 2, i * 2);
            DfDv.setSubMatrix(DfDv.getSubMatrix(j * 2, j * 2 + 1, j * 2, j * 2 + 1).subtract(Jv).getData(), j * 2, j * 2);

            //set Jx for function "calculateDfDxV0ForImplicit"
            tmpSupport.setJx(Jx);
        }

        RealMatrix A = M.subtract(DfDv.scalarMultiply(dt)).subtract(DfDx.scalarMultiply(dt * dt));

        RealVector f0 = calculateF0ForImplicit(d);

        RealVector b = f0.mapMultiply(dt).add(calculateDfDxV0ForImplicit().mapMultiply(dt * dt));

        RealVector deltaV = HelperClass.gaussSeidel(A, b);

        //System.out.println("norm = " + A.operate(x0).subtract(b).getNorm() + " Matrix: " + A.operate(x0).subtract(b));

        for (BridgeSupportAnchorPoint tmpPoint : bridgeSupportAnchorPoints) {
            int i = tmpPoint.getMyIndex() * 2;
            if (!tmpPoint.isFixed()) {
                tmpPoint.setVelocity(tmpPoint.getVelocity().plus(new Vec2(deltaV.getEntry(i), deltaV.getEntry(i + 1))));
                tmpPoint.setForce(new Vec2(f0.getEntry(i), f0.getEntry(i + 1)));
                tmpPoint.setPos(tmpPoint.getPos().plus(tmpPoint.getVelocity().smult(dt)));
            }
        }
    }

    public void collapseBridge(double threshold) {
        ArrayList<BridgeSupportAnchorPoint> tmpNewAnchorPoints = new ArrayList();

        for (BridgeSupportAnchorPoint currAnchorPoint : bridgeSupportAnchorPoints) {

            if (!currAnchorPoint.isFixed()) {

                for (ListIterator<BridgeSupport> iterator = currAnchorPoint.getSupports().listIterator(); iterator.hasNext(); ) {
                    BridgeSupport currSupport = iterator.next();

                    if (bridgeIsBreakable && currSupport.calculateStress() > threshold && !currSupport.isBroken()) {
                        //make bridge breakable
                        BridgeSupportAnchorPoint pointA = currSupport.getPointA();
                        BridgeSupportAnchorPoint pointB = currSupport.getPointB();

                        BridgeSupportAnchorPoint newA = new BridgeSupportAnchorPoint(pointA.getxPos(), pointA.getyPos(), pointA.getWeight(), pointA.isFixed());
                        BridgeSupportAnchorPoint newB = new BridgeSupportAnchorPoint(pointB.getxPos(), pointB.getyPos(), pointB.getWeight(), pointB.isFixed());

                        newA.setVelocity(pointA.getVelocity());
                        newB.setVelocity(pointB.getVelocity());

                        currSupport.setLength(newA.getPos().minus(newB.getPos()).length());
                        currSupport.setPointA(newA);
                        currSupport.setPointB(newB);
                        currSupport.setBroken(true);

                        newA.addBridgeSupport(currSupport);
                        newB.addBridgeSupport(currSupport);
                        tmpNewAnchorPoints.add(newA);
                        tmpNewAnchorPoints.add(newB);

                        iterator.remove();
                    }

                }

            }

        }
        bridgeSupportAnchorPoints.addAll(tmpNewAnchorPoints);
    }

    public RealVector calculateDfDxV0ForImplicit() {
        //get b for Ax = b
        int dimension = bridgeSupportAnchorPoints.size() * 2;

        RealVector out = new ArrayRealVector(dimension); // out = zero vec

        for (BridgeSupport currSupport : supports) {
            RealVector tmp = currSupport.getJx().operate(currSupport.getPointA().getVelocity().getRealVec().subtract(currSupport.getPointB().getVelocity().getRealVec()));
            int i = currSupport.getPointA().getMyIndex() * 2;
            int j = currSupport.getPointB().getMyIndex() * 2;

            if (dimension < i || dimension < j) {
                System.out.println("DIMENSION FAIL");
            }

            out.setSubVector(i, out.getSubVector(i, 2).subtract(tmp));
            out.setSubVector(j, out.getSubVector(j, 2).add(tmp));
        }

        return out;
    }

    public RealVector calculateF0ForImplicit(double damping) {
        //get b for Ax = b
        int dimension = bridgeSupportAnchorPoints.size() * 2;

        RealVector out = new ArrayRealVector(dimension); // out = zero vec

        for (BridgeSupport currSupport : supports) {

            double k = currSupport.getSpringConstant();

            Vec2 _tmp = currSupport.getNormalizedVec().smult(-k * (currSupport.getCurrentLength() - currSupport.getLength()));

            RealVector tmp = _tmp.getRealVec();

            int i = currSupport.getPointA().getMyIndex() * 2;
            int j = currSupport.getPointB().getMyIndex() * 2;

            //damping

            RealVector dij = currSupport.getPointA().getVelocity().minus(currSupport.getPointB().getVelocity()).smult(damping).getRealVec();

            if (dimension < i || dimension < j) {
                System.out.println("DIMENSION FAIL");
            }

            out.setSubVector(i, out.getSubVector(i, 2).add(tmp).subtract(dij));
            out.setSubVector(j, out.getSubVector(j, 2).subtract(tmp).add(dij));
        }

        for (BridgeSupportAnchorPoint currPoint : bridgeSupportAnchorPoints) {
            int indexX = currPoint.getMyIndex() * 2;
            int indexY = currPoint.getMyIndex() * 2 + 1;

            out.setEntry(indexX, out.getEntry(indexX) + currPoint.getAppliedForces().getX());
            out.setEntry(indexY, out.getEntry(indexY) - 9.81 * currPoint.getWeight() + currPoint.getAppliedForces().getY());

            currPoint.clearAppliedForces();

            // Additional force for collision with ground
            double kr_ = 100;
            if (currPoint.getPos().getY() < -1)
                out.setEntry(indexY, out.getEntry(indexY) - (1 + currPoint.getPos().getY()) * (kr_));
        }

        return out;
    }


}
