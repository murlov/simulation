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
    private volatile boolean paused = false;
    private volatile boolean running = true;
    private final Object pauseLock = new Object();


    public Simulation(SimulationSettings settings, Renderer renderer) {
        simulationMap = new SimulationMap(settings.getSizeOfMap());
        initActions = new ArrayList<>();
        MoveEventListener listener = new MoveEventLogger(renderer);
        MoveListenerRegistry listenerRegistry = new MoveListenerRegistry(listener);
        initActions.add(new EntitiesInitAction(settings.getNumberOfEntitiesPerEntityType(), settings.getNumberOfRemainingEntities(), listenerRegistry));
        turnActions = new ArrayList<>();
        EntitiesSpawnAction entitiesSpawnAction = new EntitiesSpawnAction(settings.getMinNumbersForEntityTypes(), listenerRegistry, listener);
        turnActions.add(entitiesSpawnAction);
        EntitiesMoveAction entitiesMoveAction = new EntitiesMoveAction(listener);
        entitiesMoveAction.setPauseCallback(this::pause);
        entitiesMoveAction.setExitCallback(this::exite);
        entitiesMoveAction.setSpawnCallback(entitiesSpawnAction::execute);
        turnActions.add(entitiesMoveAction);
        this.renderer = renderer;
        scanner = new Scanner(System.in);
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
        renderer.viewMap(simulationMap);

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
                    Thread.currentThread().interrupt();
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
            action.execute(simulationMap);
        }
    }

    private void executeTurnActions() {
        for (Action action : turnActions) {
            action.execute(simulationMap);
        }
    }
}
