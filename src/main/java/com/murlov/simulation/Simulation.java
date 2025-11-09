package com.murlov.simulation;

import com.murlov.action.EntitiesMoveAction;
import com.murlov.action.InitEntitiesAction;
import com.murlov.settings.SimulationSettings;
import com.murlov.view.Renderer;

import java.util.Scanner;

public class Simulation {

    private final Map map;
    private final SimulationSettings settings;

    public Simulation() {
        settings = SimulationSettings.getInstance();
        map = new Map();
    }

    public void start() {
        InitEntitiesAction.execute(map);
        Renderer.viewMap(map);
        boolean hasResources = true;
        while (input() && hasResources) {
            hasResources = nextTurn();
        }
    }

    private boolean nextTurn() {
        if (!EntitiesMoveAction.execute(map)) {
            Renderer.noResourcesMessage();
            return false;
        }
        Renderer.viewMap(map);
        return true;
    }

    private boolean input() {
        Scanner scanner = new Scanner(System.in);

        while(true) {
            Renderer.suggestContinue();
            String input = scanner.nextLine();
            if (input.equals("")) {
                return true;
            } else if (input.equals("0")) {
                return false;
            }
            Renderer.tryAgainMessage();
        }
    }
}
