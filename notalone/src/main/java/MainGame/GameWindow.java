package MainGame;

import Input.KeyHandler;
import Input.MouseHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

/*! \brief The game window.
    \note Implements the Singleton design pattern.
 */
public class GameWindow {
    private final JFrame frame;
    private final Canvas canvas;

    /*!
        \brief Parameterized constructor.

        Creates a new Canvas for rendering, sets its preferred, maximum, and minimum size, and adds a mouse
        event listener. Sets the properties of the game window such as size, title, closing, resize and visibility
        behavior. Adds the created canvas, a keyboard event listener, and a window closing listener that
        calls the \ref Game.stopGame() function to stop the game.

        @param title - a string representing the window title;
        @param width - a value representing the window width;
        @param height - a value representing the window height;
     */
    public GameWindow(String title, int width, int height) {
        frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        canvas = new Canvas();
        Dimension size = new Dimension(width, height);
        canvas.setPreferredSize(size);
        canvas.setMaximumSize(size);
        canvas.setMinimumSize(size);

        frame.add(canvas);
        frame.pack();

        frame.addKeyListener(new KeyHandler());
        frame.setFocusable(true);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                Game.getInstance().stopGame();
            }
        });
        canvas.addMouseListener(MouseHandler.getInstance());
    }

    public BufferStrategy getCanvasBufferStrategy() {
        return canvas.getBufferStrategy();
    }

    public void createCanvasBufferStrategy(int numBuffers) {
        canvas.createBufferStrategy(numBuffers);
    }

    public void requestWindowFocus() {
        frame.requestFocusInWindow();
    }
}
