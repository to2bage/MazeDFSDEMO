package com.algo.dfs;

import java.awt.*;

public class AlgoVisualizer {

    private MazeData mzData;
    private AlgoFrame frame;
    private int DELAY = 5;
    private int[][] direction = {
            {-1, 0},     // up
            {0, -1},     // left
            {1, 0},     // down
            {0, 1}      // right
    };

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
        this.renderData();          // render
        this.goDFSWithRecursion(
                this.mzData.getRowOfEntrance(),
                this.mzData.getColOfEntrance()
        );
        this.renderData();          // render
    }

    private boolean goDFSWithRecursion(int row, int col) {

        if (!this.mzData.inArea(row, col)) {
            throw new IllegalArgumentException("row or col is out of range");
        }

        this.mzData.visited[row][col] = true;
        this.mzData.path[row][col] = true;

        this.renderData();          // render

        if (
                row == this.mzData.getRowOfExit() &&
                col == this.mzData.getColOfExit()
        ) {
            return true;
        }

        for (int i = 0; i < 4; i++) {
            int newOfRow = row + this.direction[i][0];
            int newOfCol = col + this.direction[i][1];

            if (
                    this.mzData.inArea(newOfRow, newOfCol) &&
                    this.mzData.getMazeAt(newOfRow, newOfCol) == MazeData.ROAD &&
                    !this.mzData.visited[newOfRow][newOfCol]
            ) {
                this.mzData.visited[newOfRow][newOfCol] = true;
                this.mzData.path[newOfRow][newOfCol] = true;
                this.renderData();              // render

                if (goDFSWithRecursion(newOfRow, newOfCol)) {
                    return true;
                }
            }
        }

        this.mzData.path[row][col] = false;
        this.renderData();
        return false;
    }

    private void renderData() {
        this.frame.render(this.mzData);
        AlgoVisHelper.pause(this.DELAY);
    }
}
