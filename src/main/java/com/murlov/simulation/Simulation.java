package com.murlov.simulation;

import com.murlov.action.*;
import com.murlov.action.listener.EventBus;
import com.murlov.settings.SimulationSettings;
import com.murlov.view.Renderer;

import java.util.List;
import java.util.Scanner;

public class Simulation {
    private final static String QUIT = "q";
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
        initActions = List.of(new InitAction(settings.getNumberOfEntitiesPerEntityType(),
                settings.getNumberOfRemainingEntities(), spawner));
        SpawnAction spawnAction = new SpawnAction(settings.getMinNumbersForEntityTypes(), spawner);
        MoveAction moveAction = new MoveAction(new EventBus(), renderer, spawnAction, new BfsPathFinder());
        turnActions = List.of(spawnAction, moveAction);
        this.renderer = renderer;
        scanner = new Scanner(System.in);
    }

    public void start() {
        Thread thread = new Thread(() -> {
            String input;
            while (true) {
                input = scanner.nextLine();
                if (input.isEmpty() && paused) {
                    paused = false;
                    synchronized (pauseLock) {
                        pauseLock.notify();
                    }
                } else if (input.isEmpty() && !paused) {
                    paused = true;
                } else if (input.equals(QUIT)) {
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

        System.out.printf("Для начала/паузы симуляции используйте Enter. Для выхода введите %s. %n", QUIT);
        pause();
        renderer.clearScreen();

        executeActions(initActions);

        while (running) {
            nextTurn();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
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
        renderer.printMap(simulationMap);
        renderer.clearScreen();
        executeActions(turnActions);
        pause();
    }

    private void executeActions(List<Action> actions) {
        for (Action action: actions) {
            action.execute(simulationMap);
        }
    }

}
