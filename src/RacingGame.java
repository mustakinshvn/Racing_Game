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
    private static final int CAR_WIDTH = 30;
    private static final int CAR_HEIGHT = 60;
    private static final int GAME_SPEED = 10;
    public static final int BIKE_WIDTH=20;
    private static final int BIKE_SPEED = 4;
    private static final int CAR_SPEED = 2;
    private static final int SPEED_INCREMENT = 1;
    public static final int INITIAL_DELAY=1000;
    private  int score=0;


    private int bikeX;
    private int carX;

    private int carSpeed;
    private int timeElapsed;
    private List<Point> cars;
    private Random random;

    public RacingGame() {
        setTitle("Racing Bike Game");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        bikeX = WIDTH / 2 - BIKE_WIDTH / 2; // Bike starts at the center of the screen
        carX = -CAR_WIDTH; // Car starts off the screen initially
        score = 0;
        carSpeed = CAR_SPEED;
        timeElapsed = 0;

        cars = new ArrayList<>();
        random = new Random();

        Timer timer = new Timer(GAME_SPEED, this);
        timer.start();

        addKeyListener(this); // Register the key listener to the frame
        setFocusable(true);   // Ensure the frame is focused to receive key events
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        moveElements();
        checkCollisions();
        repaint();
    }

    private void moveElements() {
        if (timeElapsed >= INITIAL_DELAY && timeElapsed % 1000 == 0) {
            int randX = random.nextInt(WIDTH - CAR_WIDTH);
            cars.add(new Point(randX, -CAR_HEIGHT));
        }

        for (int i = 0; i < cars.size(); i++) {
            Point car = cars.get(i);
            car.y += carSpeed;
            if (car.y >= HEIGHT) {
                cars.remove(i);
                i--;
            }
        }


        timeElapsed += GAME_SPEED; // Track the time elapsed
    }
    private void checkCollisions() {
        Rectangle bikeRect = new Rectangle(bikeX, HEIGHT - BIKE_HEIGHT, BIKE_WIDTH, BIKE_HEIGHT);
        //Point giftBoxCenter = new Point(giftBoxX + GIFT_BOX_WIDTH / 2, giftBoxY + GIFT_BOX_HEIGHT / 2);

        for (Point car : cars) {
            Rectangle carRect = new Rectangle(car.x, car.y, CAR_WIDTH, CAR_HEIGHT);
            if (bikeRect.intersects(carRect)) {
                gameOver();
                return;
            }

        }
    }
    private void gameOver() {
        // Implement your game over logic here
        // For simplicity, we just display a message and exit the game.
        JOptionPane.showMessageDialog(this, "Game Over!\nYour score: " + score);
        System.exit(0);
    }



    public void paint(Graphics g){
        super.paint(g);

        g.setColor(Color.BLUE);
        g.fillRect(bikeX, HEIGHT - BIKE_HEIGHT, BIKE_WIDTH, BIKE_HEIGHT);

        g.setColor(Color.RED);
        for (Point car : cars) {
            g.fillRect(car.x, car.y, CAR_WIDTH, CAR_HEIGHT);
        }
        g.setColor(Color.BLACK);
        g.drawString("Score: " + score, 10, 20);
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