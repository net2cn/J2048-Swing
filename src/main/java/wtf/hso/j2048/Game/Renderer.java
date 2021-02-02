package wtf.hso.j2048.Game;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.HashMap;

public class Renderer{
    Controller controller;
    HashMap<String, Color> color;

    JFrame frame;
    Grid gameGrid;

    public Renderer(String title, Controller controller){
        this.controller = controller;
        
        // Tile color table.
        this.color = new HashMap<>();
        this.color.put("Background",new Color(0x00fbf8ef));
        this.color.put("GameBoard", new Color(0x00bbada0));
        this.color.put("Tile0",     new Color(0xc4eee4da));
        this.color.put("Tile2",     new Color(0x00eee4da));
        this.color.put("Tile4",     new Color(0x00ede0c8));
        this.color.put("Tile8",     new Color(0x00f2b179));
        this.color.put("Tile16",    new Color(0x00f59563));
        this.color.put("Tile32",    new Color(0x00f67c5f));
        this.color.put("Tile64",    new Color(0x00f65e3b));
        this.color.put("Tile128",   new Color(0x00edcf72));
        this.color.put("Tile256",   new Color(0x00edcc61));
        this.color.put("Tile512",   new Color(0x00edc850));
        this.color.put("Tile1024",  new Color(0x00edc53f));
        this.color.put("Tile2048",  new Color(0x00edc22e));
        this.color.put("TileUndefined", new Color(0x00414141));

        // Generate window.
        this.frame = new JFrame();
        this.frame.setTitle(title);
        this.frame.setSize(640, 480);
        this.frame.setLocationRelativeTo(null);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.setResizable(false);

        // Set background color.
        this.gameGrid = new Grid(this.color);
        this.gameGrid.setDoubleBuffered(true);

        // Add game view.
        this.frame.add(this.gameGrid);
    }

    public void update(){
        if(this.controller.gameBoard.isGameOver){
            this.gameGrid.paint(this.gameGrid.getGraphics(), this.controller.gameBoard.board, this.controller.gameBoard.score);
            JOptionPane.showConfirmDialog(null, "Close the dialog box or press \"R\" key to restart.","You lose!",JOptionPane.OK_CANCEL_OPTION);
            controller.gameBoard = new GameBoard(controller.gameBoard.size);
            update();
        }else if(this.controller.gameBoard.isAccomplished && !this.controller.gameBoard.isContinue){
            this.gameGrid.paint(this.gameGrid.getGraphics(), this.controller.gameBoard.board, this.controller.gameBoard.score);
            int result = JOptionPane.showConfirmDialog(null, "You've reached 2048!\nContinue playing?","You Win!",JOptionPane.OK_CANCEL_OPTION);
            if(result == JOptionPane.OK_OPTION){
                this.controller.gameBoard.isContinue = true;
            }else{
                controller.gameBoard = new GameBoard(controller.gameBoard.size);
                update();
            }
        }else{
            this.gameGrid.paint(this.gameGrid.getGraphics(), this.controller.gameBoard.board, this.controller.gameBoard.score);
        }
    }

    public void start(){
        JRootPane rootPane = this.frame.getRootPane();

        // Add key bindings.
        Action rightAction = new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e){
                controller.update(0);
            }
        };
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"), "RIGHT");
        rootPane.getActionMap().put("RIGHT", rightAction);

        Action upAction = new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e){
                controller.update(1);
            }
        };
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "UP");
        rootPane.getActionMap().put("UP", upAction);

        Action leftAction = new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e){
                controller.update(2);
            }
        };
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), "LEFT");
        rootPane.getActionMap().put("LEFT", leftAction);

        Action downAction = new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e){
                controller.update(3);
            }
        };
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "DOWN");
        rootPane.getActionMap().put("DOWN", downAction);

        Action rAction = new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e){
                controller.gameBoard = new GameBoard(controller.gameBoard.size);
                update();
            }
        };
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("R"), "R");
        rootPane.getActionMap().put("R", rAction);

        Action escAction = new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e){
                System.out.println("Game exited. Bye!");
                System.exit(0);
            }
        };
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "ESCAPE");
        rootPane.getActionMap().put("ESCAPE", escAction);

        // Visualize game window.
        this.frame.setVisible(true);
    }
}
