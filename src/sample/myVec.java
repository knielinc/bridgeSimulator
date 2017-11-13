package sample;


public class myVec {

    public static final myVec VEC_ZERO = new myVec(0, 0);
    private double x, y;

    public myVec(double x, double y) {
        super();
        this.x = x;
        this.y = y;
    }

    // TODO: implement these myVec functions!
    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double dot(myVec r) {
        return (getX() * r.getX() + getY() * r.getY());
    }

    public myVec minus(myVec o) {
        return new myVec(getX() - o.getX(), getY() - o.getY());
    }

    public myVec mul(myVec o) {
        return new myVec(getX() * o.getX(), getY() * o.getY());
    }

    public double length() {
        return Math.sqrt(getX() * getX() + getY() * getY());
    }

    public myVec normalize() {
        double q = length();
        return new myVec(getX() / q, getY() / q);
    }

    public myVec plus(myVec r) {
        return new myVec(getX() + r.getX(), getY() + r.getY());
    }

    public myVec smult(double f) {
        return new myVec(getX() * f, getY() * f);
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "myVec [x=" + x + ", y=" + y + "]";
    }

}
