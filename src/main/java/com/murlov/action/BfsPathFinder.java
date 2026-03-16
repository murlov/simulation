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

        final int startId = getId(creature.getCoordinates(), sizeOfMap.height());
        queue.add(creature.getCoordinates());
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

                if (isCoordinatesCorrect(nextCoordinates, simulationMap)) {
                    if (visited[nextCoordinatesId]) continue;
                    visited[nextCoordinatesId] = true;

                    if (simulationMap.getEntities().containsKey(nextCoordinates)) {
                        Entity next = simulationMap.getEntity(nextCoordinates);

                        if (next.getClass() == resourceType) {
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

    private boolean isCoordinatesCorrect(Coordinates coordinates, SimulationMap simulationMap) {
        return coordinates.x() >= 0 && coordinates.x() < simulationMap.getSize().width()
        && coordinates.y() >= 0 && coordinates.y() < simulationMap.getSize().height();
    }

    private Class<? extends Entity> getResourceType(Class<? extends Creature> type){
        if (type == Wolf.class) {
            return Rabbit.class;
        } else if (type == Rabbit.class) {
            return Grass.class;
        }
        throw new IllegalArgumentException("Unknown entity type: " + type);
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
