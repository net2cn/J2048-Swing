package wtf.hso.j2048.Game;

import java.util.HashMap;
import java.awt.*;
import javax.swing.*;

public class Grid extends JPanel {
    private HashMap<String, Color> color;
    private static final Font TILE_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 32);
    private static final Font SCORE_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 24);
    private static final Font HINTS_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 24);
    private static final int TILESIZE = 96;
    private static final int MARGIN = 8;

    public Grid(HashMap<String, Color> color){
        super(true);
        this.color = color;
        setFocusable(true);
    }

    // function used for drawing
    private static int offsetCoors(int arg) {
        return arg * (MARGIN + TILESIZE) + MARGIN;
    }

    // draws the tile
    private void drawTile(Graphics g, int tile, int x, int y) {
        int xOffset = offsetCoors(x);
        int yOffset = offsetCoors(y);
        g.setColor(tile<=2048 ? this.color.get("Tile"+Integer.toString(tile)):this.color.get("TileUndefined"));
        g.fillRect(xOffset, yOffset, TILESIZE, TILESIZE);

        if(tile<=2048){
            g.setColor(new Color(0,0,0));
        }else{
            g.setColor(new Color(255,255,255));
        }
        
        if(tile != 0){
            g.drawString(Integer.toString(tile), xOffset + (TILESIZE/2) - (Integer.toString(tile).length()-1)*8 - MARGIN, yOffset + (TILESIZE/2)+8);
        }
    }
    
    // Paints the grid, called whenever there's a key event to update the board
    public void paint(Graphics g, int[][] board, int score) {
        // Set anti-aliasing for the fonts!
        // Graphics2D g2d = (Graphics2D) g;
        // g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 

        super.paint(g);

        // Draw tiles.
        g.setColor(this.color.get("Background"));
        g.setFont(TILE_FONT);
        g.fillRect(0, 0, this.getSize().width, this.getSize().height);
        for (int x = 0; x < board.length; x++){
            for (int y = 0; y < board.length; y++){
                this.drawTile(g, board[y][x], x, y);
            }
        }

        // Draw score
        g.setFont(SCORE_FONT);
        g.drawString("Score", 440, 50);
        g.drawString(Integer.toString(score), 440, 90);

        // Draw key hints.
        g.setFont(HINTS_FONT);
        g.drawString("Key Hints", 440, 280);
        g.drawString("↑↓←→: Move", 440, 320);
        g.drawString("R: Restart", 440, 360);
        g.drawString("Esc: Exit Game", 440, 400);
    }
}
