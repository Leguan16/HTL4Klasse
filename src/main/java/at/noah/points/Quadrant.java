package at.noah.points;

public class Quadrant {

    private int quadrant;
    private double x;
    private double y;
    public Quadrant(int quadrant, double x, double y) {
        this.quadrant = quadrant;
        this.x = x;
        this.y = y;
    }

    public int getQuadrant() {
        return quadrant;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setQuadrant(int quadrant) {
        this.quadrant = quadrant;
    }

    @Override
    public String toString() {
        return "Quadrant{" +
                "quadrant=" + quadrant +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
