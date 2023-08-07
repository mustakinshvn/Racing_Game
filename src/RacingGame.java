import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RacingGame extends JFrame implements ActionListener, KeyListener {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;
    private static final int BIKE_WIDTH = 20;
    private static final int BIKE_HEIGHT = 40;

    private static final int GIFT_BOX_WIDTH = 30;
    private static final int GIFT_BOX_HEIGHT = 30;
    private static final int GAME_SPEED = 10; // Milliseconds
    private static final int BIKE_SPEED = 4;  // Bike's horizontal movement speed
    private static final int CAR_SPEED = 2;  // Car's vertical movement speed

    private static final int INITIAL_DELAY = 1000; // Initial delay without other vehicles (in milliseconds)

    private int bikeX;

    private int giftBoxX;

    private int score=0;
    private int carSpeed;
    private int timeElapsed;


    private List<Point> gift;
    private Random random;

    public RacingGame() {
        setTitle("Racing Bike Game");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        bikeX = WIDTH / 2 - BIKE_WIDTH / 2; // Bike starts at the center of the screen

        giftBoxX = WIDTH/2 - GIFT_BOX_WIDTH/2; // Gift box starts off the screen initially
        //giftBoxY = -GIFT_BOX_HEIGHT;
        score = 0;
        carSpeed = CAR_SPEED;
        timeElapsed = 0;


        gift = new ArrayList<>();
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
            int randX = random.nextInt(WIDTH - GIFT_BOX_WIDTH);
            gift.add(new Point(randX, -GIFT_BOX_HEIGHT));
        }



        for (int i = 0; i < gift.size(); i++) {
            Point gif = gift.get(i);
            gif.y += carSpeed;
            if (gif.y >= HEIGHT ) {
                gift.remove(i);
                i--;
            }
        }


        timeElapsed += GAME_SPEED; // Track the time elapsed
    }

    private void checkCollisions() {
        Rectangle bikeRect = new Rectangle(bikeX, HEIGHT - BIKE_HEIGHT, BIKE_WIDTH, BIKE_HEIGHT);
        //Point giftBoxCenter = new Point(giftBoxX + GIFT_BOX_WIDTH / 2, giftBoxY + GIFT_BOX_HEIGHT / 2);

        for (Point gf : gift) {
            Rectangle gifRect = new Rectangle(gf.x, gf.y, GIFT_BOX_WIDTH, GIFT_BOX_HEIGHT);

            if (bikeRect.intersects(gifRect)) {
                score++;
                gift.remove(gf);
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.BLUE);
        g.fillRect(bikeX, HEIGHT - BIKE_HEIGHT, BIKE_WIDTH, BIKE_HEIGHT);


        g.setColor(Color.GREEN);
        for (Point gif : gift) {
            g.fillRect(gif.x, gif.y, GIFT_BOX_WIDTH, GIFT_BOX_HEIGHT);
        }

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
        // Implement any required logic when a key is released
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Implement any required logic when a key is typed
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            RacingGame racingGame = new RacingGame();
            racingGame.setVisible(true);
        });
    }
}
