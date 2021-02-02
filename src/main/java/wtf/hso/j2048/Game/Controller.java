package wtf.hso.j2048.Game;

public class Controller {
    GameBoard gameBoard;
    Renderer renderer;

    public Controller(String gameTitle){
        this.gameBoard = new GameBoard(4);
        this.renderer = new Renderer(gameTitle, this);
    }

    public void update(int input){
        this.gameBoard.Update(input);
        this.renderer.update();
    }

    public void start(){
        this.renderer.start();

        // Dunno why I had to update twice here.
        this.renderer.update();
        this.renderer.update();
    }
}
