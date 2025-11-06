package com.murlov.simulation;

import com.murlov.action.EntitiesMoveAction;
import com.murlov.action.InitEntitiesAction;
import com.murlov.settings.SimulationSettings;
import com.murlov.view.Renderer;

import java.util.Scanner;

public class Simulation {

    private final Map map;
    private final Renderer renderer;
    private final SimulationSettings settings;

    public Simulation() {
        settings = SimulationSettings.getInstance();
        map = new Map();
        renderer = new Renderer();
    }

    public void start() {
        InitEntitiesAction.execute(map);
        renderer.viewMap(map);
        while (input()) {
            if (!EntitiesMoveAction.execute(map)) {
                System.out.println("Ресурсы закончились, симуляция завершена");
                break;
            }
            renderer.viewMap(map);
        }
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
            System.out.println("Ошибка, попробуйте ещё раз.");
        }
    }
}
