package com.murlov.simulation;

import com.murlov.action.Action;
import com.murlov.action.EntitiesMoveAction;
import com.murlov.action.EntitiesInitAction;
import com.murlov.action.EntitiesSpawnAction;
import com.murlov.settings.SimulationSettings;
import com.murlov.view.Renderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Simulation {

    private final Map map;
    private final SimulationSettings settings;
    private final Renderer renderer;
    private final List<Action> initActions;
    private final List<Action> turnActions;
    private final Scanner scanner;


    public Simulation() {
        settings = SimulationSettings.getInstance();
        map = new Map();
        initActions = new ArrayList<>();
        initActions.add(new EntitiesInitAction());
        turnActions = new ArrayList<>();
        turnActions.add(new EntitiesMoveAction());
        turnActions.add(new EntitiesSpawnAction());
        renderer = new Renderer();
        scanner = new Scanner(System.in);
    }

    public void start() {
        executeInitActions();
        renderer.Map(map);
        while (input()) {
            if (!nextTurn()) {
                break;
            }
            renderer.Map(map);
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
            action.execute(map);
        }
    }

    private boolean executeTurnActions() {
        for (Action action : turnActions) {
            Runnable callback = null;

            if (action instanceof EntitiesMoveAction) {
                callback = () -> {
                    renderer.Map(map);
                    renderer.newLine();
                };
            }

            if (!action.execute(map, callback)) {
                return false;
            };
        }
        return true;
    }
}
