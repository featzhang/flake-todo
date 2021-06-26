package com.github.zuofengzhang.flake.lotus.actiivity;

/**
 * @author averyzhang
 * @date 2021/6/7
 */

import com.github.zuofengzhang.flake.lotus.actiivity.entity.Query;
import org.apache.commons.cli.ParseException;

import java.util.Scanner;

public class CommandQuery implements Query {

    private boolean waitForQuery = true;

    private final LaService laService;

    public CommandQuery() {
        this.laService = new LaService(this);
    }

    public static void main(String[] args) throws ParseException {
        final CommandQuery commandQuery = new CommandQuery();
        commandQuery.start();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                commandQuery.onShutDown();
            }
        });

    }

    private void onShutDown() {
        laService.close();
        waitForQuery = false;
    }

    private void start() {
        laService.start();
        laService.showStatus();
        waitForInput(laService);
    }

    private void waitForInput(LaService laService) {
        while (waitForQuery) {
            Scanner scanner = new Scanner(System.in);
            String  line    = null;
            if ((line = scanner.nextLine()) != null) {

                laService.addQuery(line);
            }
        }
    }


    @Override
    public void showInfo(String info) {
        System.out.println("> " + info);
    }
}

