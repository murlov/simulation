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

    public List<Coordinates> execute(SimulationMap simulationMap, Creature creature){

        Class<? extends Entity> resourceType = getResourceType(creature.getClass());

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

        final int startId = getId(creature.getCoordinates(), sizeOfMap.length());
        queue.add(creature.getCoordinates());
        parents[startId] = startId;

        while(!queue.isEmpty()){
            Coordinates currentCoordinates = queue.poll();
            int currentCoordinatesId = getId(currentCoordinates, sizeOfMap.length());

            for (int[] direction : directions) {
                int x, y;
                x = currentCoordinates.X() + direction[0];
                y = currentCoordinates.Y() + direction[1];
                Coordinates nextCoordinates = new Coordinates(x, y);
                int nextCoordinatesId = getId(nextCoordinates, sizeOfMap.length());

                if (isCoordinatesCorrect(nextCoordinates, simulationMap)) {
                    if (visited[nextCoordinatesId]) continue;
                    visited[nextCoordinatesId] = true;

                    if (simulationMap.getEntities().containsKey(nextCoordinates)) {
                        Entity next = simulationMap.getEntities().get(nextCoordinates);

                        if (next.getClass() == resourceType) {
                            parents[nextCoordinatesId] = currentCoordinatesId;
                            return getPath(startId, nextCoordinatesId, parents, sizeOfMap.length(), result);
                        }
                    } else {
                        parents[nextCoordinatesId] = currentCoordinatesId;
                        queue.add(nextCoordinates);
                    }
                }
            }
        }
        return null;
    }

    private int getId(Coordinates c, int length){
        return c.X()*length + c.Y();
    }

    private Coordinates getCoordinates(int id, int length){
        return new Coordinates(id/length, id%length);
    }

    private boolean isCoordinatesCorrect(Coordinates coordinates, SimulationMap simulationMap) {
        return coordinates.X() >= 0 && coordinates.X() < simulationMap.getSize().width()
        && coordinates.Y() >= 0 && coordinates.Y() < simulationMap.getSize().length();
    }

    private Class<? extends Entity> getResourceType(Class<? extends Creature> type){
        if (type == Wolf.class) {
            return Rabbit.class;
        } else if (type == Rabbit.class) {
            return Grass.class;
        } else {
            throw new IllegalArgumentException("Unknown entity type: " + type);
        }
    }

    private List<Coordinates> getPath(int startId, int goalId, int[] parents, int length, List<Coordinates> path) {
        int id = goalId;
        while (parents[id] != startId) {
            path.add(getCoordinates(id, length));
            id = parents[id];
        }
        path.add(getCoordinates(id, length));
        path.add(getCoordinates(startId, length));
        Collections.reverse(path);
        return path;
    }
}
