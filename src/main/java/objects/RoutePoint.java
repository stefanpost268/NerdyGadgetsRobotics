package objects;

import java.util.ArrayList;

public class RoutePoint {
    private char x;
    private int y;
    private int distance;
    private ArrayList <RoutePoint> borderingPoints;

    public RoutePoint(char x, int y, int distance) {
        this.x = x;
        this.y = y;
        this.distance = distance;
    }

    public char getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDistance() {
        return distance;
    }

    public ArrayList<RoutePoint> getBorderingPoints() {
        return borderingPoints;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public void addBorderingPoint(RoutePoint point) {
        borderingPoints.add(point);
    }

    public void clearBorderingPoints() {
        borderingPoints.clear();
    }

    public void setBorderingPoints(ArrayList<RoutePoint> borderingPoints) {
        this.borderingPoints = borderingPoints;
    }
}
