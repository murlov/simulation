package com.murlov.action;

import com.murlov.model.*;
import com.murlov.simulation.Coordinates;
import com.murlov.simulation.SimulationMap;
import com.murlov.simulation.Size;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BfsPathFinder implements PathFinder {

    private final int numberOfCells;
    private final Size sizeOfMap;

    public BfsPathFinder(Size sizeOfMap, int numberOfCells) {
        this.sizeOfMap = sizeOfMap;
        this.numberOfCells = numberOfCells;
    }

    @Override
    public List<Coordinates> execute(SimulationMap simulationMap, Coordinates start, Class<? extends Entity> target){
        if (!simulationMap.isInside(start)) {
            throw new IllegalArgumentException("Coordinates out of bounds:" + start);
        }

        List<Coordinates> result = new LinkedList<>();
        Queue<Coordinates> queue = new LinkedList<>();

        int[] parents = new int[numberOfCells];
        boolean[] visited = new boolean[numberOfCells];

        int[][] directions = {
                {0, -1},
                {1, 0},
                {0, 1},
                {-1, 0},
                {1, -1},
                {1, 1},
                {-1, 1},
                {-1, -1}
        };

        final int startId = getId(start, sizeOfMap.height());
        queue.add(start);
        parents[startId] = startId;

        while(!queue.isEmpty()){
            Coordinates currentCoordinates = queue.poll();
            int currentCoordinatesId = getId(currentCoordinates, sizeOfMap.height());

            for (int[] direction : directions) {
                int x, y;
                x = currentCoordinates.x() + direction[0];
                y = currentCoordinates.y() + direction[1];
                Coordinates nextCoordinates = new Coordinates(x, y);
                int nextCoordinatesId = getId(nextCoordinates, sizeOfMap.height());

                if (simulationMap.isInside(nextCoordinates)) {
                    if (visited[nextCoordinatesId]) continue;
                    visited[nextCoordinatesId] = true;

                    if (simulationMap.getEntities().containsKey(nextCoordinates)) {
                        Entity next = simulationMap.getEntity(nextCoordinates);

                        if (next.getClass() == target) {
                            parents[nextCoordinatesId] = currentCoordinatesId;
                            createPath(startId, nextCoordinatesId, parents, sizeOfMap.height(), result);
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