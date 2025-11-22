package com.murlov.action;

import com.murlov.model.Entity;
import com.murlov.model.EntityGroup;
import com.murlov.settings.SimulationSettings;
import com.murlov.simulation.Coordinates;
import com.murlov.simulation.Map;

import java.util.LinkedList;
import java.util.Queue;

public class PathFinder {

    public static Coordinates execute(Map map, Entity entity){
        EntityGroup resourceGroup = getResourceGroup(entity.getGroup());
        Queue<Coordinates> queue = new LinkedList<>();
        SimulationSettings settings = SimulationSettings.getInstance();

        int[] parents = new int[settings.getCountOfCells()];
        boolean[] visited = new boolean[settings.getCountOfCells()];

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

        final int startId = getId(entity.getCoordinates(), settings.getSizeOfMap().getLength());
        queue.add(entity.getCoordinates());
        parents[startId] = startId;

        while(!queue.isEmpty()){
            Coordinates currentCoordinates = queue.poll();
            int currentCoordinatesId = getId(currentCoordinates, settings.getSizeOfMap().getLength());
            
            for (int i = 0; i < directions.length; i++) {
                int x, y;
                x = currentCoordinates.getX() + directions[i][0];
                y = currentCoordinates.getY() + directions[i][1];
                Coordinates nextCoordinates = new Coordinates(x, y);
                int nextCoordinatesId = getId(nextCoordinates, settings.getSizeOfMap().getLength());

                if (isCoordinatesCorrect(nextCoordinates)) {
                    if (visited[nextCoordinatesId]) continue;
                    visited[nextCoordinatesId] = true;

                    if (map.getEntities().containsKey(nextCoordinates)) {
                        Entity next = map.getEntities().get(nextCoordinates);

                        if (next.getGroup() == resourceGroup) {
                            parents[nextCoordinatesId] = currentCoordinatesId;
                            return getFirstStep(startId, nextCoordinatesId, parents, settings.getSizeOfMap().getLength());
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

    private static int getId(Coordinates c, int length){
        return c.getX()*length + c.getY();
    }

    private static Coordinates getCoordinates(int id, int length){
        return new Coordinates(id/length, id%length);
    }

    private static boolean isCoordinatesCorrect(Coordinates coordinates) {
        SimulationSettings settings = SimulationSettings.getInstance();
        return coordinates.getX() >= 0 && coordinates.getX() < settings.getSizeOfMap().getWidth()
        && coordinates.getY() >= 0 && coordinates.getY() < settings.getSizeOfMap().getLength();
    }

    private static EntityGroup getResourceGroup(EntityGroup entityGroup){
        if (entityGroup == EntityGroup.PREDATOR) {
            return EntityGroup.HERBIVORE;
        } else if (entityGroup == EntityGroup.HERBIVORE) {
            return EntityGroup.GRASS;
        }
        throw new IllegalArgumentException("Unknown entity group: " + entityGroup);
    }

    private static Coordinates getFirstStep(int startId, int goalId, int[] parents, int length) {
        int id = goalId;
        while (parents[id] != startId) {
            id = parents[id];
        }
        return getCoordinates(id, length);
    }
}
