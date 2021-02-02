package wtf.hso.j2048;

import wtf.hso.j2048.Game.*;

public class App 
{
    public static void main( String[] args )
    {
        String windowTitle = "J2048 Swing";
        System.out.println( "Hello J2048!" );

        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");

        Controller game = new Controller(windowTitle);
        game.start();
    }
}