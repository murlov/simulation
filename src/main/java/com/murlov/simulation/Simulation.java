package com.murlov.simulation;

import com.murlov.action.*;
import com.murlov.action.listener.EventBus;
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
    private volatile boolean paused = true;
    private volatile boolean running = true;
    private final Object pauseLock = new Object();


    public Simulation(SimulationSettings settings, Renderer renderer) {
        simulationMap = new SimulationMap(settings.getMapSize());
        Spawner spawner = new Spawner(settings);
        initActions = new ArrayList<>();
        initActions.add(new InitAction(settings.getNumberOfEntitiesPerEntityType(), settings.getNumberOfRemainingEntities(), spawner));
        turnActions = new ArrayList<>();
        SpawnAction spawnAction = new SpawnAction(settings.getMinNumbersForEntityTypes(), spawner);
        turnActions.add(spawnAction);
        MoveAction moveAction = new MoveAction(new EventBus(), renderer, spawnAction, new BfsPathFinder());
        turnActions.add(moveAction);
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

        System.out.println("Для начала/паузы симуляции используйте Enter. Для выхода введите q.");
        pause();
        renderer.clearScreen();

        executeInitActions();
        renderer.clearScreen();
        renderer.printMap(simulationMap);


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

    private void nextTurn() {
        executeTurnActions();
        renderer.clearScreen();
        renderer.printMap(simulationMap);
        pause();
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
