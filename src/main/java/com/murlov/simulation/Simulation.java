package com.murlov.simulation;

import com.murlov.action.*;
import com.murlov.settings.SimulationSettings;
import com.murlov.view.Renderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Simulation {
    private final SimulationMap simulationMap;
    private final Renderer renderer;
    private final List<Action> initActions;
    private final List<Action> turnActions;
    private final Scanner scanner;
    private final MoveListenerRegistry listenerRegistry;
    private volatile boolean paused = false;
    private volatile boolean running = true;
    private final Object pauseLock = new Object();


    public Simulation(SimulationSettings settings) {
        simulationMap = new SimulationMap(settings.getSizeOfMap());
        initActions = new ArrayList<>();
        initActions.add(new EntitiesInitAction(settings.getNumberOfEntitiesPerGroup(), settings.getNumberOfRemainingEntities()));
        turnActions = new ArrayList<>();
        EntitiesMoveAction entitiesMoveAction = new EntitiesMoveAction();
        entitiesMoveAction.setPauseCallback(this::pause);
        entitiesMoveAction.setExitCallback(this::exite);
        turnActions.add(entitiesMoveAction);
        turnActions.add(new EntitiesSpawnAction(settings.getMinNumbersInGroups()));
        renderer = new Renderer();
        scanner = new Scanner(System.in);
        MoveEventListener logger = new MoveEventLogger(renderer);
        listenerRegistry = new MoveListenerRegistry(logger);
    }

    public void start() {
        Thread thread = new Thread(() -> {
            String input;
            while (true) {
                input = scanner.nextLine();
                if (input.equals("") && paused) {
                    paused = false;
                    synchronized (pauseLock) {
                        pauseLock.notify();
                    }
                } else if (input.equals("") && !paused) {
                    paused = true;
                } else if (input.equals("q")) {
                    running = false;
                    paused = false;
                    synchronized (pauseLock) {
                        pauseLock.notify();
                    }
                    break;
                }
            }
        });

        thread.start();
        executeInitActions();
        renderer.clearScreen();
        renderer.Map(simulationMap);

        while (running) {
            nextTurn();
        }
    }

     private void pause() {
        if (paused) {
            synchronized (pauseLock) {
                try {
                    pauseLock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private boolean exite() {
        return !running;
    }

    private void nextTurn() {
        executeTurnActions();
    }

    private void executeInitActions() {
        for (Action action : initActions) {
            if (action instanceof EntitiesInitAction) {
                action.execute(simulationMap, listenerRegistry);
            }
        }
    }

    private void executeTurnActions() {
        for (Action action : turnActions) {

            if (action instanceof EntitiesMoveAction) {
                action.execute(simulationMap);
            } else if (action instanceof EntitiesSpawnAction) {
                action.execute(simulationMap, listenerRegistry);
            }
        }
    }
}
