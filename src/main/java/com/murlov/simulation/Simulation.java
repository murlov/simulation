package com.murlov.simulation;

import com.murlov.action.*;
import com.murlov.view.Renderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Simulation {
    private final Map map;
    private final Renderer renderer;
    private final List<Action> initActions;
    private final List<Action> turnActions;
    private final Scanner scanner;
    private final MoveListenerRegistry listenerRegistry;


    public Simulation() {
        map = new Map();
        initActions = new ArrayList<>();
        initActions.add(new EntitiesInitAction());
        turnActions = new ArrayList<>();
        turnActions.add(new EntitiesMoveAction());
        turnActions.add(new EntitiesSpawnAction());
        renderer = new Renderer();
        scanner = new Scanner(System.in);
        MoveEventListener logger = new MoveEventLogger(renderer);
        listenerRegistry = new MoveListenerRegistry(logger);
    }

    public void start() {
        executeInitActions();
        renderer.Map(map);
        while (input()) {
            if (!nextTurn()) {
                break;
            }
        }
    }

    private boolean nextTurn() {
        return executeTurnActions();
    }

    private boolean input() {

        while(true) {
            renderer.suggestContinue();
            String input = scanner.nextLine();
            if (input.equals("")) {
                return true;
            } else if (input.equals("0")) {
                return false;
            }
            renderer.tryAgainMessage();
        }
    }

    private void executeInitActions() {
        for (Action action : initActions) {
            if (action instanceof EntitiesInitAction) {
                action.execute(map, listenerRegistry);
            }
        }
    }

    private boolean executeTurnActions() {
        for (Action action : turnActions) {

            if (action instanceof EntitiesMoveAction) {
                if (!action.execute(map)) {
                    return false;
                }
            } else if (action instanceof EntitiesSpawnAction) {
                if (!action.execute(map, listenerRegistry)) {
                    return false;
                }
            }
        }
        return true;
    }
}
