package services;

import objects.RoutePoint;
import visualComponents.WarehouseMap;

import java.util.ArrayList;

public class RouteCalculator {

    public static int calculateDistanceBetweenPoints(RoutePoint point1, RoutePoint point2) {
        int xDistance = Math.abs(point1.getX() - point2.getX());
        int yDistance = Math.abs(point1.getY() - point2.getY());
        int distance = xDistance + yDistance;
        return distance;
    }

    public static ArrayList<RoutePoint> calculateRoute(ArrayList<String> locations) {
        ArrayList<RoutePoint> routePoints = new ArrayList<>();

        ArrayList<RoutePoint> fastestRoute = new ArrayList<>();

        //validating strings and creating route points
        for (int i = 0; i < locations.size(); i++) {
            String location = locations.get(i);
            if (location == null || location.isEmpty()) {
                throw new IllegalArgumentException("Location cannot be null or empty");
            }
            if (location.length() != 2) {
                throw new IllegalArgumentException("Location must be 2 characters long");
            }

            char firstChar = location.charAt(0);

            if (firstChar < 'A' || firstChar > 'Z') {
                throw new IllegalArgumentException("First character of location must be a capital letter");
            }

            routePoints.add(new RoutePoint(firstChar, Integer.parseInt(location.substring(1)), 999999999));

        }

        //setting the starting point as close to the starting point of the robot
        int closestRoutePointId = 0;
        for (int i = 0; i < routePoints.size(); i++) {
             if (routePoints.get(i).getX() + routePoints.get(i).getY() < routePoints.get(closestRoutePointId).getX() + routePoints.get(closestRoutePointId).getY()) {
                 closestRoutePointId = i;
             }
        }
        RoutePoint startingPoint = routePoints.get(closestRoutePointId);
        routePoints.remove(closestRoutePointId);
        startingPoint.setDistance(0);
        startingPoint.setBorderingPoints(routePoints);
        fastestRoute.add(startingPoint);

        ArrayList<RoutePoint> unvisitedPoints = new ArrayList<>(routePoints);

        RoutePoint currentPoint = startingPoint;

        while (!unvisitedPoints.isEmpty()) {
            int shortestDistance = 999999999;
            RoutePoint nextPoint = null;
            if (currentPoint.getBorderingPoints() == null) {
                currentPoint.setBorderingPoints(unvisitedPoints);
            }
            for (int i = 0; i < currentPoint.getBorderingPoints().size(); i++) {
                currentPoint.getBorderingPoints().get(i).setDistance(calculateDistanceBetweenPoints(currentPoint, currentPoint.getBorderingPoints().get(i)));
            }
            for (int i = 0; i < currentPoint.getBorderingPoints().size(); i++) {
                if (currentPoint.getBorderingPoints().get(i).getDistance() < shortestDistance) {
                    shortestDistance = currentPoint.getBorderingPoints().get(i).getDistance();
                    nextPoint = currentPoint.getBorderingPoints().get(i);
                }
            }
            if (nextPoint == null) {
                throw new IllegalArgumentException("No route found");
            }
            currentPoint = nextPoint;
            unvisitedPoints.remove(currentPoint);
            fastestRoute.add(currentPoint);
        }

        return fastestRoute;

    }
}
