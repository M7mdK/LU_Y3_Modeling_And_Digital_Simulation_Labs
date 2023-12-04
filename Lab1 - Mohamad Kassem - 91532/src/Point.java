public class Point {
    private double x,y;
    public Point(double X, double Y){
        x = X;
        y = Y;
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    @Override
    public String toString() {
        return "( " +  x + " , " + y + " )";
    }
}
