package sample.bridge;

import com.sun.org.apache.regexp.internal.RE;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.MatrixType;
import org.apache.commons.math3.geometry.Vector;
import org.apache.commons.math3.geometry.euclidean.oned.Vector1D;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.linear.*;
import sample.Vec2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class Bridge {
    private ArrayList<BridgeSupport> supports;
    private ArrayList<BridgeSupportAnchorPoint> bridgeSupportAnchorPoints;
    private boolean bridgeIsBreakable = false;

    public Bridge(){
        //constuctor to be implemented
        bridgeSupportAnchorPoints = new ArrayList();
        supports = new ArrayList();
    }

    public void draw(GraphicsContext gc){
        Iterator<BridgeSupport> supportIterator;
        for(supportIterator = supports.iterator();supportIterator.hasNext();){
            supportIterator.next().draw(gc);
        }
    }

    public void addSupport(BridgeSupport inSupport){
        supports.add(inSupport);
    }

    public ArrayList<BridgeSupport> getSupports() {
        return supports;
    }

    public ArrayList<BridgeSupportAnchorPoint> getBridgeSupportAnchorPoints() {
        return bridgeSupportAnchorPoints;
    }


    public void createTestBridge(){
        //line 0-11 left -> right
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(100,200,1,true));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(150,200,1,false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(200,200,1,false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(250,200,1,false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(300,200,1,false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(350,200,1,false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(450,200,1,false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(500,200,1,false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(550,200,1,false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(600,200,1,false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(650,200,1,false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(700,200,1,true));
        //road
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(0),bridgeSupportAnchorPoints.get(1),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(1),bridgeSupportAnchorPoints.get(2),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(2),bridgeSupportAnchorPoints.get(3),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(3),bridgeSupportAnchorPoints.get(4),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(4),bridgeSupportAnchorPoints.get(5),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(5),bridgeSupportAnchorPoints.get(6),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(6),bridgeSupportAnchorPoints.get(7),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(7),bridgeSupportAnchorPoints.get(8),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(8),bridgeSupportAnchorPoints.get(9),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(9),bridgeSupportAnchorPoints.get(10),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(10),bridgeSupportAnchorPoints.get(11),70));

        //upper upper support points 12 -> 17
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(100,150,1,true));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(250,300,1,false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(350,350,1,false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(450,350,1,false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(550,300,1,false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(700,150,1,true));
        //lower upper support points 18 -> 23
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(100,100,1,true));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(250,250,1,false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(350,300,1,false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(450,300,1,false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(550,250,1,false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(700,100,1,true));
        //upper lower support points 24 -> 26
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(350,250,1,false));
        bridgeSupportAnchorPoints.add(new BridgeSupportAnchorPoint(450,250,1,false));

        //upper upper supports
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(12),bridgeSupportAnchorPoints.get(1),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(1),bridgeSupportAnchorPoints.get(13),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(13),bridgeSupportAnchorPoints.get(14),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(14),bridgeSupportAnchorPoints.get(15),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(15),bridgeSupportAnchorPoints.get(16),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(16),bridgeSupportAnchorPoints.get(10),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(10),bridgeSupportAnchorPoints.get(17),70));
        //can be commented out for original bridge (will break though)
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(16),bridgeSupportAnchorPoints.get(11),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(0),bridgeSupportAnchorPoints.get(13),70));


        //middle upper supports
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(12),bridgeSupportAnchorPoints.get(2),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(2),bridgeSupportAnchorPoints.get(13),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(13),bridgeSupportAnchorPoints.get(19),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(13),bridgeSupportAnchorPoints.get(20),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(14),bridgeSupportAnchorPoints.get(20),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(15),bridgeSupportAnchorPoints.get(21),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(14),bridgeSupportAnchorPoints.get(21),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(15),bridgeSupportAnchorPoints.get(20),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(21),bridgeSupportAnchorPoints.get(16),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(16),bridgeSupportAnchorPoints.get(22),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(16),bridgeSupportAnchorPoints.get(9),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(9),bridgeSupportAnchorPoints.get(17),70));


        //lower upper supports/*
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(18),bridgeSupportAnchorPoints.get(2),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(2),bridgeSupportAnchorPoints.get(19),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(19),bridgeSupportAnchorPoints.get(20),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(20),bridgeSupportAnchorPoints.get(21),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(21),bridgeSupportAnchorPoints.get(22),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(22),bridgeSupportAnchorPoints.get(9),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(9),bridgeSupportAnchorPoints.get(23),70));

        //smaller supports
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(4),bridgeSupportAnchorPoints.get(24),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(5),bridgeSupportAnchorPoints.get(24),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(5),bridgeSupportAnchorPoints.get(25),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(6),bridgeSupportAnchorPoints.get(24),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(6),bridgeSupportAnchorPoints.get(25),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(7),bridgeSupportAnchorPoints.get(25),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(24),bridgeSupportAnchorPoints.get(25),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(24),bridgeSupportAnchorPoints.get(20),70));
        supports.add( new BridgeSupport(bridgeSupportAnchorPoints.get(25),bridgeSupportAnchorPoints.get(21),70));

    }

    public void computeTimeStepExlicit(double d, double dt){
        ArrayList<BridgeSupportAnchorPoint> tmpNewAnchorPoints = new ArrayList();

        for (BridgeSupportAnchorPoint currAnchorPoint:bridgeSupportAnchorPoints) {

            if(!currAnchorPoint.isFixed()) {

                Vec2 f = new Vec2(0, -9.81 * currAnchorPoint.getWeight()).minus(currAnchorPoint.getVelocity().smult(d));

                Vec2 p = currAnchorPoint.getPos();

                for (ListIterator<BridgeSupport> iterator = currAnchorPoint.getSupports().listIterator(); iterator.hasNext();) {
                    BridgeSupport currSupport = iterator.next();

                    double k = currSupport.getSpringConstant();
                    Vec2 springF = currSupport.getNormalizedVec().smult(-k * (currSupport.getCurrentLength() - currSupport.getLength()));

                    if (currAnchorPoint == currSupport.getPointA()) {
                        f = f.plus(springF);
                    } else { // assuming else it's pointB
                        f = f.minus(springF);
                    }
                    if (bridgeIsBreakable && currSupport.calculateStress() > .9 && !currSupport.isBroken()){
                        //make bridge breakable
                        BridgeSupportAnchorPoint pointA = currSupport.getPointA();
                        BridgeSupportAnchorPoint pointB = currSupport.getPointB();

                        BridgeSupportAnchorPoint newA = new BridgeSupportAnchorPoint(pointA.getxPos(),pointA.getyPos(),pointA.getWeight(),pointA.isFixed());
                        BridgeSupportAnchorPoint newB = new BridgeSupportAnchorPoint(pointB.getxPos(),pointB.getyPos(),pointB.getWeight(),pointB.isFixed());

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

    public void computeTimeStepImplicit(double d, double dt){
        //setup jacobian matrices

        int dimension = bridgeSupportAnchorPoints.size()*2;

        SparseRealMatrix DfDx,DfDv,M;

        DfDx = new OpenMapRealMatrix(dimension,dimension);
        DfDv = new OpenMapRealMatrix(dimension,dimension);
        M  = new OpenMapRealMatrix(dimension,dimension);

        for (int i = 0; i < dimension; i++){
            DfDv.addToEntry(i,i,d);
            M.addToEntry(i,i,1);
        }

        for (BridgeSupport tmpSupport:supports){
            BridgeSupportAnchorPoint a = tmpSupport.getPointA();
            BridgeSupportAnchorPoint b = tmpSupport.getPointB();
            int i = a.getMyIndex();
            int j = b.getMyIndex();
            RealVector posA,posB,aMinusB;
            posA = new ArrayRealVector(new double[]{a.getxPos(),a.getyPos()});
            posB = new ArrayRealVector(new double[]{b.getxPos(),b.getyPos()});
            aMinusB = posA.subtract(posB);
            double innerDotAB = aMinusB.dotProduct(aMinusB);
            double oneOverInnerDotAB = 1.0 / innerDotAB;
            RealMatrix outerDotAB = aMinusB.outerProduct(aMinusB);
            RealMatrix twoByTwoI = MatrixUtils.createRealIdentityMatrix(2);
            double currLength = aMinusB.getNorm();
            double length = tmpSupport.getLength();
            double k = tmpSupport.getSpringConstant();

            RealMatrix Jx = outerDotAB.scalarMultiply(oneOverInnerDotAB).add(twoByTwoI.subtract(outerDotAB.scalarMultiply(oneOverInnerDotAB)).scalarMultiply(1.0 - (length / currLength))).scalarMultiply(k);
            DfDx.setEntry(i*2,j*2,Jx.getEntry(0,0));
            DfDx.setEntry(i*2 + 1,j*2,Jx.getEntry(1,0));
            DfDx.setEntry(i*2,j*2 + 1,Jx.getEntry(0,1));
            DfDx.setEntry(i*2 + 1,j*2 + 1,Jx.getEntry(1,1));

            tmpSupport.setJx(Jx);
        }

        RealMatrix A = M.subtract(DfDv.scalarMultiply(dt)).subtract(DfDx.scalarMultiply(dt * dt));

        RealVector f0 = calculateF0ForImplicit(d).mapMultiplyToSelf(dt);

        /*RealVector v0 = new ArrayRealVector(dimension);

        for (BridgeSupportAnchorPoint currPoint:bridgeSupportAnchorPoints){
            int index = currPoint.getMyIndex()*2;
            v0.setEntry(index,currPoint.getVelocity().getX());
            v0.setEntry(index + 1,currPoint.getVelocity().getY());
        }

        RealVector b = f0.add(DfDx.operate(v0));*/
        RealVector b = f0.add(calculateBForImplicit().mapMultiplyToSelf(dt * dt));

        b.mapMultiplyToSelf(dt * dt);

        RealVector deltaV = gaussSeidel(M,b);

        //System.out.println("norm = " + A.operate(x0).subtract(b).getNorm() + " Matrix: " + A.operate(x0).subtract(b));


        //TODO REMOVE!
        //RealVector f0 = calculateF0ForImplicit();
        for (BridgeSupportAnchorPoint tmpPoint:bridgeSupportAnchorPoints) {
            int i = tmpPoint.getMyIndex() * 2;
            if (!tmpPoint.isFixed()){
                tmpPoint.setVelocity(tmpPoint.getVelocity().plus(new Vec2(deltaV.getEntry(i),deltaV.getEntry(i+1))));

                tmpPoint.setPos(tmpPoint.getPos().plus(tmpPoint.getVelocity().smult(dt)));
            }

        }
        /*
           _f(p1) = (-k * ((p1 - p2).length() - L) * (p1 - p2).getNormalizedCopy())
           or easier:
           (-k * (1 - L/(p1 - p2).length()) * (p1 - p2))

           (-k * ((p1 - p2).length() - L) * (p1 - p2).getNormalizedCopy())
           f(x1,y1).x = -k * (1 - L/sqrt((x1-x2) * (x1-x2) + (y1-y2) * (y1-y2))) * (x1 - x2)
           f(x1,y1).y = -k * (1 - L/sqrt((x1-x2) * (x1-x2) + (y1-y2) * (y1-y2))) * (y1 - y2)
           -k * (sqrt((x-z) * (x-z) + (y-w) * (y-w)) - L) * (x - z) / (sqrt((x-z) * (x-z) + (y-w) * (y-w)))
           -k * (1 - L/sqrt((x-z) * (x-z) + (y-w) * (y-w))) * (x - z)

            fx. d/dx1 = k ((L (w - y)^2)/((w - y)^2 + (x - z)^2)^(3/2) - 1)
            fx. d/dy1 = (k L (w - y) (x - z))/((w - y)^2 + (x - z)^2)^(3/2)

            fy. d/dx1 = (k L (w - y) (x - z))/((w - y)^2 + (x - z)^2)^(3/2)
            fy. d/dy1 = k ((L (x - z)^2)/((w - x)^2 + (y - z)^2)^(3/2) - 1)

           dij = (vij)*kd
         */




    }

    public RealVector calculateBForImplicit(){
        //get b for Ax = b
        int dimension = bridgeSupportAnchorPoints.size()*2;

        RealVector out = new ArrayRealVector(dimension); // out = zero vec

        for (BridgeSupport currSupport: supports) {
            RealVector tmp = currSupport.getJx().operate(currSupport.getPointA().getPos().getRealVec().subtract(currSupport.getPointB().getPos().getRealVec()));
            int i = currSupport.getPointA().getMyIndex() * 2;
            int j = currSupport.getPointB().getMyIndex() * 2;

            if(dimension < i || dimension < j ){
                System.out.println("DIMENSION FAIL");
            }

            out.setSubVector(i, out.getSubVector(i,2).subtract(tmp));
            out.setSubVector(j, out.getSubVector(j,2).add(tmp));
        }

        return out;
    }

    public RealVector calculateF0ForImplicit(double damping){
        //get b for Ax = b
        int dimension = bridgeSupportAnchorPoints.size()*2;

        RealVector out = new ArrayRealVector(dimension); // out = zero vec

        for (BridgeSupport currSupport: supports) {

            double k = currSupport.getSpringConstant();

            Vec2 _tmp = currSupport.getNormalizedVec().smult(-k * (currSupport.getCurrentLength() - currSupport.getLength()));

            RealVector tmp = _tmp.getRealVec();

            int i = currSupport.getPointA().getMyIndex() * 2;
            int j = currSupport.getPointB().getMyIndex() * 2;

            //damping

            RealVector dij = currSupport.getPointA().getVelocity().minus(currSupport.getPointB().getVelocity()).smult(damping).getRealVec();

            if(dimension < i || dimension < j ){
                System.out.println("DIMENSION FAIL");
            }

            out.setSubVector(i, out.getSubVector(i,2).add(tmp).subtract(dij));
            out.setSubVector(j, out.getSubVector(j,2).subtract(tmp).add(dij));
        }

        for (BridgeSupportAnchorPoint currPoint : bridgeSupportAnchorPoints){
            int indexY = currPoint.getMyIndex() * 2 + 1;
            out.setEntry(indexY,out.getEntry(indexY) - 9.81 * currPoint.getWeight());
        }

        return out;
    }

    public RealVector gaussSeidel(RealMatrix M, RealVector b){
        int dimension = 2 * bridgeSupportAnchorPoints.size();
        RealVector out = new ArrayRealVector(dimension);
        double error = Integer.MAX_VALUE;
        double threshold = 0.001;
        for (int iterationstep = 0;iterationstep < 1000;iterationstep++) {
            error = 0;
            for (int k = 0; k < dimension; k++) {
                double lhs = 0, rhs = 0;
                for (int i = 0; i < k; i++) {
                    lhs += M.getEntry(k, i) * out.getEntry(i);
                }
                for (int i = k + 1; i < dimension; i++) {
                    lhs += M.getEntry(k, i) * out.getEntry(i);
                }
                double x_k = 1.0 / M.getEntry(k, k) * (b.getEntry(k) - lhs - rhs);
                error = Math.max(error, Math.abs(x_k - out.getEntry(k)));
                out.setEntry(k, x_k);
            }
            if (error < threshold){
                break;
            }
        }
        return out;
    }

}
