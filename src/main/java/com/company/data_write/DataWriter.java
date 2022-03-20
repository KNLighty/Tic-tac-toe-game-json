package com.company.data_write;

import com.company.game.Player;
import com.company.game.Step;

import java.util.List;

public abstract class DataWriter {
    protected Player player1;
    protected Player player2;
    protected List<Step> stepList;

    public DataWriter(Player player1, Player player2, List<Step> stepList) {
        this.player1 = player1;
        this.player2 = player2;
        this.stepList = stepList;
    }

    public abstract void write();
    public abstract void createPlayerNode(Player player1, Player player2);
    public abstract void createStepNode();
    public abstract void createGameResultNode();
}
