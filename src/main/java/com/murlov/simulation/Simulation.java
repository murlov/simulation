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


    public Simulation() {
        settings = SimulationSettings.getInstance();
        map = new Map();
        initActions = new ArrayList<>();
        initActions.add(new EntitiesInitAction());
        turnActions = new ArrayList<>();
        turnActions.add(new EntitiesMoveAction());
        turnActions.add(new EntitiesSpawnAction());
        renderer = new Renderer();
    }

    public void start() {
        executeInitActions();
        renderer.viewMap(map);
        while (input()) {
            if (!nextTurn()) {
                break;
            }
            renderer.viewMap(map);
        }
    }

    private boolean nextTurn() {
        return executeTurnActions();
    }

    private boolean input() {
        Scanner scanner = new Scanner(System.in);

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
            if (action.execute(map) == false) {
                return false;
            };
        }
        return true;
    }
}
