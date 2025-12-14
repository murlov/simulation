package com.murlov.action;

import com.murlov.model.Creature;
import com.murlov.model.Entity;
import com.murlov.model.EntityGroup;
import com.murlov.simulation.Coordinates;
import com.murlov.simulation.Map;
import com.murlov.simulation.Size;

import java.util.ArrayList;
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

    public Coordinates execute(Map map, Creature creature){
        EntityGroup resourceGroup = getResourceGroup(creature.getGroup());
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

                if (isCoordinatesCorrect(nextCoordinates, map)) {
                    if (visited[nextCoordinatesId]) continue;
                    visited[nextCoordinatesId] = true;

                    if (map.getEntities().containsKey(nextCoordinates)) {
                        Entity next = map.getEntities().get(nextCoordinates);

                        if (next.getGroup() == resourceGroup) {
                            parents[nextCoordinatesId] = currentCoordinatesId;
                            return getFirstStep(startId, nextCoordinatesId, parents, sizeOfMap.length());
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

    private boolean isCoordinatesCorrect(Coordinates coordinates, Map map) {
        return coordinates.X() >= 0 && coordinates.X() < map.getSize().width()
        && coordinates.Y() >= 0 && coordinates.Y() < map.getSize().length();
    }

    private EntityGroup getResourceGroup(EntityGroup entityGroup){
        if (entityGroup == EntityGroup.PREDATOR) {
            return EntityGroup.HERBIVORE;
        } else if (entityGroup == EntityGroup.HERBIVORE) {
            return EntityGroup.GRASS;
        } else {
            throw new IllegalArgumentException("Unknown entity group: " + entityGroup);
        }
    }

    private Coordinates getFirstStep(int startId, int goalId, int[] parents, int length) {
        int id = goalId;
        while (parents[id] != startId) {
            id = parents[id];
        }
        return getCoordinates(id, length);
    }
}
