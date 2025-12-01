package com.murlov.action;

import com.murlov.model.Creature;
import com.murlov.model.Entity;
import com.murlov.model.EntityGroup;
import com.murlov.settings.SimulationSettings;
import com.murlov.simulation.Coordinates;
import com.murlov.simulation.Map;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class PathFinder {

    public static Coordinates execute(Map map, Creature creature){
        EntityGroup resourceGroup = getResourceGroup(creature.getGroup());
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

        final int startId = getId(creature.getCoordinates(), settings.getSizeOfMap().length());
        queue.add(creature.getCoordinates());
        parents[startId] = startId;

        while(!queue.isEmpty()){
            Coordinates currentCoordinates = queue.poll();
            int currentCoordinatesId = getId(currentCoordinates, settings.getSizeOfMap().length());

            for (int[] direction : directions) {
                int x, y;
                x = currentCoordinates.X() + direction[0];
                y = currentCoordinates.Y() + direction[1];
                Coordinates nextCoordinates = new Coordinates(x, y);
                int nextCoordinatesId = getId(nextCoordinates, settings.getSizeOfMap().length());

                if (isCoordinatesCorrect(nextCoordinates)) {
                    if (visited[nextCoordinatesId]) continue;
                    visited[nextCoordinatesId] = true;

                    if (map.getEntities().containsKey(nextCoordinates)) {
                        Entity next = map.getEntities().get(nextCoordinates);

                        if (next.getGroup() == resourceGroup) {
                            parents[nextCoordinatesId] = currentCoordinatesId;
                            return getFirstStep(startId, nextCoordinatesId, parents, settings.getSizeOfMap().length(), creature.getSpeed());
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
        return c.X()*length + c.Y();
    }

    private static Coordinates getCoordinates(int id, int length){
        return new Coordinates(id/length, id%length);
    }

    private static boolean isCoordinatesCorrect(Coordinates coordinates) {
        SimulationSettings settings = SimulationSettings.getInstance();
        return coordinates.X() >= 0 && coordinates.X() < settings.getSizeOfMap().width()
        && coordinates.Y() >= 0 && coordinates.Y() < settings.getSizeOfMap().length();
    }

    private static EntityGroup getResourceGroup(EntityGroup entityGroup){
        if (entityGroup == EntityGroup.PREDATOR) {
            return EntityGroup.HERBIVORE;
        } else if (entityGroup == EntityGroup.HERBIVORE) {
            return EntityGroup.GRASS;
        } else {
            throw new IllegalArgumentException("Unknown entity group: " + entityGroup);
        }
    }

    private static Coordinates getFirstStep(int startId, int goalId, int[] parents, int length, int speed) {
        List<Integer> chain = new ArrayList<>();
        int id = goalId;
        while (id != startId) {
            chain.add(id);
            id = parents[id];
        }

        id = chain.get(chain.size() - speed);
        return getCoordinates(id, length);
    }
}
