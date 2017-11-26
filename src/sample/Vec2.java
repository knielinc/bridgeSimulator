package sample;


import org.apache.commons.math3.geometry.Vector;
import org.apache.commons.math3.geometry.euclidean.oned.Vector1D;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

public class Vec2 {

    public static final Vec2 VEC_ZERO = new Vec2(0, 0);
    private double x, y;

    public Vec2(double x, double y) {
        super();
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double dot(Vec2 r) {
        return (getX() * r.getX() + getY() * r.getY());
    }

    public Vec2 minus(Vec2 o) {
        return new Vec2(getX() - o.getX(), getY() - o.getY());
    }

    public Vec2 mul(Vec2 o) {
        return new Vec2(getX() * o.getX(), getY() * o.getY());
    }

    public double length() {
        return Math.sqrt(getX() * getX() + getY() * getY());
    }

    public Vec2 normalize() {
        double q = length();
        return new Vec2(getX() / q, getY() / q);
    }

    public Vec2 plus(Vec2 r) {
        return new Vec2(getX() + r.getX(), getY() + r.getY());
    }

    public Vec2 smult(double f) {
        return new Vec2(getX() * f, getY() * f);
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setPos(double x, double y){
        this.x = x;
        this.y = y;
    }

    public void set(Vec2 in){
        this.x = in.getX();
        this.y = in.getY();
    }

    @Override
    public boolean equals(Object obj) {
        return (((Vec2)obj).x == x) && ((Vec2)obj).y == y;
    }

    public RealVector getRealVec(){
        return new ArrayRealVector(new double[] {x,y});
    }

    @Override
    public String toString() {
        return "Vec2 [x=" + x + ", y=" + y + "]";
    }

}
