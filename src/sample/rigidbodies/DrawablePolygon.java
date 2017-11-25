package sample.rigidbodies;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sample.Vec2;

public class DrawablePolygon {
    double[] xVals,negyVals;
    double[] yVals;
    int numberOfPoints;
    double area;
    double momentOfInertia;
    double mass;

    public DrawablePolygon(double[] xValues, double [] yValues, int n, double mass){
        //care! points must be defined as a traversal over the edges
        this.mass = mass;
        xVals = xValues.clone();
        yVals = yValues.clone();
        negyVals = new double[n];
        numberOfPoints = n;
        computeArea();
        //move shape so that it's centroid is in the origin
        Vec2 centroid = computeCentroid();

        for (int i = 0; i < numberOfPoints;i++){
            xVals[i] -= centroid.getX();
            yVals[i] -= centroid.getY();
        }

        computeMomentOfInertia();

        for (int i = 0; i < numberOfPoints;i++){
            negyVals[i] = -yVals[i];
        }
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillPolygon(xVals,negyVals,numberOfPoints);
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
        for (int i = 0; i < numberOfPoints-1; i++){
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

        for (int i = 0; i < numberOfPoints-1; i++){
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

    public Vec2 computeCentroid(){
        double cx,cy;
        cx = 0;
        cy = 0;

        for (int i = 0; i < numberOfPoints-1; i++){
            double xi = xVals[i];
            double yi = yVals[i];
            double xiplus1 = xVals[i+1];
            double yiplus1 = yVals[i+1];

            cx += (xi + xiplus1) * (xi * yiplus1 - xiplus1 * yi);
        }

        cx += (xVals[numberOfPoints-1] + xVals[0]) * (xVals[numberOfPoints-1] * yVals[0] - xVals[0] * yVals[numberOfPoints-1]);

        cx *= 1.0/(area * 6);

        for (int i = 0; i < numberOfPoints-1; i++){
            double xi = xVals[i];
            double yi = yVals[i];
            double xiplus1 = xVals[i+1];
            double yiplus1 = yVals[i+1];

            cy += (yi + yiplus1) * (xi * yiplus1 - xiplus1 * yi);
        }

        cy += (yVals[numberOfPoints-1] + yVals[0]) * (xVals[numberOfPoints-1] * yVals[0] - xVals[0] * yVals[numberOfPoints-1]);

        cy *= 1.0/(area * 6);

        return new Vec2(cx,cy);
    }

    public double getMomentOfInertia() {
        return momentOfInertia;
    }

    public Vec2 getSupport(Vec2 d) {
        //Eric rather did work in Distributed than this
        double highest = Double.MAX_VALUE;
        Vec2 support = new Vec2(0,0);

        for (int i = 0; i < numberOfPoints; ++i) {
            Vec2 v = new Vec2(xVals[i],yVals[i]);
            double dot = v.dot(d);

            if (dot > highest) {
                highest = dot;
                support = v;
            }
        }

        return support;
    }
}
