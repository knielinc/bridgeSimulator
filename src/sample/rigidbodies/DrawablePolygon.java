package sample.rigidbodies;

import sample.myVec;

public class DrawablePolygon {
    double[] xVals;
    double[] yVals;
    int numberOfPoints;
    double area;
    double momentOfInertia;
    double mass;

    DrawablePolygon(double[] xValues, double [] yValues, int n, double mass){
        //care! points must be defined as a traversal over the edges
        xVals = xValues;
        yVals = yValues;
        numberOfPoints = n;
        computeArea();
        //move shape so that it's centroid is in the origin
        myVec centroid = computeCentroid();

        for (double xVal:xVals){
            xVal -= centroid.getX();
        }
        for (double yVal:yVals){
            yVal -= centroid.getY();
        }

        computeMomentOfInertia();
    }

    public double[] getxVals() {
        return xVals;
    }

    public double[] getyVals() { return yVals; }

    public int getNumberOfPoints() {
        return numberOfPoints;
    }

    public void computeArea(){
        area = 0;
        for (int i = 0; i < numberOfPoints-1;){
            double xi = xVals[i];
            double yi = yVals[i];
            double xiplus1 = xVals[i+1];
            double yiplus1 = yVals[i+1];

            area += xi * yiplus1  - xiplus1 * yi;
        }

        double xi = xVals[numberOfPoints-1];
        double xiplus1 = xVals[0];
        double yi = yVals[numberOfPoints-1];
        double yiplus1 = yVals[0];

        area += xi * yiplus1  - xiplus1 * yi;

        area *= .5;
    }

    public void computeMomentOfInertia(){
        double enumerator,denominator;
        enumerator = 0;
        denominator = 0;

        for (int i = 0; i < numberOfPoints-1;){
            double xi = xVals[i];
            double yi = yVals[i];
            double xiplus1 = xVals[i+1];
            double yiplus1 = yVals[i+1];

            enumerator += (xi * xi + yi * yi + xi * xiplus1 + yi * yiplus1 + yiplus1 * yiplus1 + xiplus1 * xiplus1) * (xi * yiplus1 - xiplus1 * yi);
        }

        double xi = xVals[numberOfPoints-1];
        double xiplus1 = xVals[0];
        double yi = yVals[numberOfPoints-1];
        double yiplus1 = yVals[0];

        enumerator += (xi * xi + yi * yi + xi * xiplus1 + yi * yiplus1 + yiplus1 * yiplus1 + xiplus1 * xiplus1) * (xi * yiplus1 - xiplus1 * yi);

        denominator = area;

        momentOfInertia = (enumerator * mass)/ (denominator * 12);
    }

    public myVec computeCentroid(){
        double cx,cy;
        cx = 0;
        cy = 0;

        for (int i = 0; i < numberOfPoints-1;){
            double xi = xVals[i];
            double yi = yVals[i];
            double xiplus1 = xVals[i+1];
            double yiplus1 = yVals[i+1];

            cx += (xi + xiplus1) * (xi * yiplus1 - xiplus1 * yi);
        }

        cx += (xVals[numberOfPoints-1] + xVals[0]) * (xVals[numberOfPoints-1] * yVals[0] - xVals[0] * yVals[numberOfPoints-1]);

        cx *= 1.0/(area * 6);

        for (int i = 0; i < numberOfPoints-1;){
            double xi = xVals[i];
            double yi = yVals[i];
            double xiplus1 = xVals[i+1];
            double yiplus1 = yVals[i+1];

            cy += (yi + yiplus1) * (xi * yiplus1 - xiplus1 * yi);
        }

        cy += (yVals[numberOfPoints-1] + yVals[0]) * (xVals[numberOfPoints-1] * yVals[0] - xVals[0] * yVals[numberOfPoints-1]);

        cy *= 1.0/(area * 6);

        return new myVec(cx,cy);
    }

    public double getMomentOfInertia() {
        return momentOfInertia;
    }
}
