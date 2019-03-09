package com.algo.dfs;

import java.awt.*;

public class AlgoVisualizer {

    private MazeData mzData;
    private AlgoFrame frame;
    private int DELAY = 5;

    public AlgoVisualizer(String filename, String title) {

        this.mzData = new MazeData(filename);

        int blockSideLen = 8;
        int canvasWidth = this.mzData.getColsOfMaze() * blockSideLen;
        int canvasHeight = this.mzData.getRowsOfMaze() * blockSideLen;

        EventQueue.invokeLater(() -> {
            this.frame = new AlgoFrame(title, canvasWidth, canvasHeight);
            new Thread(() -> {
                this.run();
            }).start();
        });
    }

    private void run() {
        this.renderData();
    }

    private void renderData() {
        this.frame.render(this.mzData);
        AlgoVisHelper.pause(this.DELAY);
    }
}
