package wtf.hso.j2048.Game;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GameBoard {
    int size;
    int[][] lastBoard;
    int[][] board;
    int score;

    boolean isGameOver;
    boolean isAccomplished;
    boolean isContinue;

    public GameBoard(int size) {
        System.out.println("Creating new board...");
        this.size = size;
        this.lastBoard = new int[size][size];
        this.board = new int[size][size];
        this.isGameOver = false;
        this.isAccomplished = false;
        this.isContinue = false;
        this.generateNewTile();
        this.printBoard();
    }

    private void printBoard() {
        for (int i = 0; i < this.size; i++) {
            System.out.println(Arrays.toString(this.board[i]));
        }
    }

    private void generateNewTile() {
        ArrayList<int[]> emptyTiles = new ArrayList<int[]>();

        for (int x = 0; x < this.size; x++) {
            for (int y = 0; y < this.size; y++) {
                if (this.board[x][y] == 0) {
                    emptyTiles.add(new int[] { x, y });
                }
            }
        }

        if (emptyTiles.isEmpty()) {
            return;
        }

        Random rand = new Random(Instant.now().toEpochMilli());

        int maximum = rand.nextInt(2) + 1;
        if (maximum > emptyTiles.size()) {
            maximum = emptyTiles.size();
        }

        for (int i = 0; i < maximum; i++) {
            int idx = rand.nextInt(emptyTiles.size());
            int value = (rand.nextInt(2) + 1) * 2;

            int[] cord = emptyTiles.get(idx);
            this.board[cord[0]][cord[1]] = value;
        }
    }

    // Rotate board by 90 degrees.
    private void rotateBoard(int times) {
        for (int i = 0; i < times; i++) {
            for (int x = 0; x < this.size / 2; x++) {
                for (int y = x; y < this.size - x - 1; y++) {
                    int temp = this.board[x][y];
                    this.board[x][y] = this.board[this.size - 1 - y][x];
                    this.board[this.size - 1 - y][x] = this.board[this.size - 1 - x][this.size - 1 - y];
                    this.board[this.size - 1 - x][this.size - 1 - y] = this.board[y][this.size - 1 - x];
                    this.board[y][this.size - 1 - x] = temp;
                }
            }
        }
    }

    // Check if game over.
    private void isGameOver() {
        int horizontalMovement = 0;
        int verticalMovement = 0;

        for (int x = 0; x < this.size; x++) {
            for (int y = 0; y < this.size; y++) {
                if (y < this.size-1) {
                    if ((this.board[x][y] == this.board[x][y + 1] || this.board[x][y] == 0)){
                        horizontalMovement++;
                    }
                }
                if (x < this.size-1) {
                    if (this.board[x][y] == this.board[x + 1][y] || this.board[x][y] == 0){
                        verticalMovement++;
                    }
                }

                if (this.board[x][y] == 2048) {
                    this.isAccomplished = true;
                }
            }
        }

        if (horizontalMovement + verticalMovement < 1) {
            this.isGameOver = true;
        }
    }

    // Apply tile calculation towards right.
    private void applyTileCalculation(int direction){
        this.lastBoard=this.board.clone();

        int directionBack = direction==0?0:4-direction;

        this.rotateBoard(direction);

        for(int x=0;x<this.size;x++){
            for(int i=0;i<this.size-1;i++){
                for(int y = this.size-1;y>0;y--){
                    if(this.board[x][y]==0){
                        this.board[x][y]=this.board[x][y-1];
                        this.board[x][y-1]=0;
                    }else{
                        if (this.board[x][y]==this.board[x][y-1]){
                            this.board[x][y]*=2;
                            this.board[x][y-1]=0;
                            this.score+=this.board[x][y];
                        }
                    }
                }
            }
        }

        this.rotateBoard(directionBack);
    }

    // Update board and check game.
    public void Update(int input){
        if(!this.isGameOver){
            if(input!=-1&&(!this.isAccomplished||this.isContinue)){
                this.applyTileCalculation(input);
                this.generateNewTile();
                this.isGameOver();
                this.printBoard();
                System.out.println("Score: " + this.score);
            }else{
                System.out.println("You won! Press Enter to continue.");
            }
        }else{
            System.out.println("Game over! Press R to restart.");
        }
    }
}
