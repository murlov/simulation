package com.murlov.action;

import com.murlov.entity.*;
import com.murlov.simulation.Coordinates;
import com.murlov.simulation.SimulationMap;

import java.util.*;

public class BfsPathFinder implements PathFinder {

    private static final List<Coordinates> SHIFT_COORDINATES = List.of (
            new Coordinates(0, -1),
            new Coordinates(1, 0),
            new Coordinates(0, 1),
            new Coordinates(-1, 0),
            new Coordinates(1, -1),
            new Coordinates(1, 1),
            new Coordinates(-1, 1),
            new Coordinates(-1, -1)
    );

    @Override
    public List<Coordinates> find(SimulationMap simulationMap, Coordinates start, Class<? extends Entity> target){
        if (!simulationMap.isInside(start)) {
            throw new IllegalArgumentException("Coordinates out of bounds:" + start);
        }

        List<Coordinates> result = new LinkedList<>();
        Queue<Coordinates> queue = new LinkedList<>();

        int[] parents = new int[simulationMap.getSize().getArea()];
        boolean[] visited = new boolean[simulationMap.getSize().getArea()];


        final int startId = getId(start, simulationMap.getSize().height());
        queue.add(start);
        parents[startId] = startId;

        while(!queue.isEmpty()){
            Coordinates currentCoordinates = queue.poll();
            int currentCoordinatesId = getId(currentCoordinates, simulationMap.getSize().height());

            for (Coordinates shift : SHIFT_COORDINATES) {
                int x, y;
                x = currentCoordinates.x() + shift.x();
                y = currentCoordinates.y() + shift.y();
                Coordinates nextCoordinates = new Coordinates(x, y);
                int nextCoordinatesId = getId(nextCoordinates, simulationMap.getSize().height());

                if (simulationMap.isInside(nextCoordinates)) {
                    if (visited[nextCoordinatesId]) continue;
                    visited[nextCoordinatesId] = true;

                    if (simulationMap.getEntities().containsKey(nextCoordinates)) {
                        Entity next = simulationMap.getEntity(nextCoordinates);

                        if (target.isInstance(next)) {
                            parents[nextCoordinatesId] = currentCoordinatesId;
                            createPath(startId, nextCoordinatesId, parents, simulationMap.getSize().height(), result);
                            return result;
                        }
                    } else {
                        parents[nextCoordinatesId] = currentCoordinatesId;
                        queue.add(nextCoordinates);
                    }
                }
            }
        }

        return result;
    }

    private int getId(Coordinates c, int height){
        return c.x()*height + c.y();
    }

    private Coordinates getCoordinates(int id, int height){
        return new Coordinates(id/height, id%height);
    }

    private void createPath(int startId, int goalId, int[] parents, int height, List<Coordinates> path) {
        int id = goalId;
        while (parents[id] != startId) {
            path.add(getCoordinates(id, height));
            id = parents[id];
        }
        path.add(getCoordinates(id, height));
        path.add(getCoordinates(startId, height));
        Collections.reverse(path);
    }
}