import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RacingGame extends JFrame implements  ActionListener,KeyListener {
    public static final int HEIGHT=400;
    public static final int WIDTH=600;
    public static final int BIKE_HEIGHT=40;
    public static final int BIKE_WIDTH=20;
    private static final int BIKE_SPEED = 4;
    public static final int INITIAL_DELAY=1000;
    private static final int GAME_SPEED = 10;

    private int bikeX;
    private Random random;
    private int timeElapsed;
    private ActionEvent e;

    public RacingGame() {
        setTitle("Racing Bike Game");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        bikeX = WIDTH / 2 - BIKE_WIDTH / 2; // Bike starts at the center of the screen

        timeElapsed = 0;


        random = new Random();

        Timer timer = new Timer(GAME_SPEED, this);
        timer.start();

        addKeyListener(this); // Register the key listener to the frame
        setFocusable(true);   // Ensure the frame is focused to receive key events
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        moveElements();
        repaint();
    }

    private void moveElements() {


        timeElapsed += GAME_SPEED; // Track the time elapsed
    }




    public void paint(Graphics g){
        super.paint(g);

        g.setColor(Color.BLUE);
        g.fillRect(bikeX, HEIGHT - BIKE_HEIGHT, BIKE_WIDTH, BIKE_HEIGHT);

    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            bikeX -= BIKE_SPEED; // Move the bike left when the LEFT key is pressed
        } else if (key == KeyEvent.VK_RIGHT) {
            bikeX += BIKE_SPEED; // Move the bike right when the RIGHT key is pressed
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            RacingGame racingGame = new RacingGame();
            racingGame.setVisible(true);
   });
}
}