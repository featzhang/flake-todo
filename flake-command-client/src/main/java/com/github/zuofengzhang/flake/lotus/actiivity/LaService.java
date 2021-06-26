package com.github.zuofengzhang.flake.lotus.actiivity;

import com.github.zuofengzhang.flake.lotus.actiivity.entity.COMMAND_TYPE;
import com.github.zuofengzhang.flake.lotus.actiivity.entity.Instruction;
import com.github.zuofengzhang.flake.lotus.actiivity.entity.Query;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author averyzhang
 * @date 2021/6/7
 */
@Slf4j
public class LaService {

    private final LinkedBlockingQueue<Instruction> queue = new LinkedBlockingQueue<>(100);

    private final Query query;

    private boolean alive = true;

    public LaService(Query query) {
        this.query = query;
    }

    public void showStatus() {
        query.showInfo("现在的状态");
    }

    private void showInfo(String info) {
        query.showInfo(info);
    }

    private void addInstruction(Instruction instruction) {
        queue.add(instruction);
    }

    private Instruction getInstruction() {
        return queue.poll();
    }

    public void close() {
        alive = false;
    }

    public void addQuery(String line) {
        addInstruction(Instruction.builder().content(line).type(COMMAND_TYPE.NEW_INPUT).build());
    }

    private boolean isWaitInput = false;

    /**
     * 消费指令，处理业务逻辑
     */
    public void start() {
        queue.add(Instruction.builder().type(COMMAND_TYPE.WAIT_INPUT).build());
        CompletableFuture.runAsync(() -> {
            while (alive) {
                System.out.println("new wait while");
                try {
                    final Instruction instruction = queue.take();
                    System.out.println("get new instructionQueue: " + instruction);
                    switch (instruction.getType()) {
                        case NEW_INPUT:
                            processCommand(instruction.getContent());
                            break;
                        case WAIT_INPUT:
                            showInfo("wait input:");
                            isWaitInput = true;
                            break;
                        default:
                            showInfo("illedge!");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void processCommand(String content) {
        showInfo("process command: " + content);
    }
}
