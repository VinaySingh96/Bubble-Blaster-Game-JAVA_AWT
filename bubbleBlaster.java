import java.awt.*;
import java.awt.event.*;
import java.lang.Math;
import java.util.Scanner;
import java.util.Vector;
import javax.swing.Timer;
import javax.swing.ImageIcon;
import java.awt.*;
import java.util.Formatter;
import javax.swing.*;

public class bubbleBlaster extends Frame implements KeyListener {

  WindowListener wl = new WindowAdapter() {
    public void windowClosing(WindowEvent e) {
      System.exit(0);
    }
  };

  Label l = new Label("Key Listener");
  Label scoreval = new Label("0");
  Label highscoreval = new Label("0");
  int x = 250, y = 430;
  ImageIcon blaster, missile, ball;
  int playagain = 0;
  Vector xcord = new Vector<>();
  Vector ycord = new Vector<>();
  Vector missile_xcord = new Vector<>();
  Vector missile_ycord = new Vector<>();
  // Image img = ImageIO.read(new File("bgimage.jpg"));
  Image img = Toolkit.getDefaultToolkit().createImage("bgimage.jpg");

  int len = 0, itr = 0, scoreValue = 0, highScoreValue = 0;

  private int radius = 20;
  private int xDelta = 4;
  int time = 50;

  bubbleBlaster() {
    addKeyListener(this);
    addWindowListener(wl);

    // l.setBounds(x, y, 100, 40);
    // add(l);

    Thread gameThread = new Thread() {
      public void run() {
        blaster = new ImageIcon("blaster.png");
        missile = new ImageIcon("missile.png");
        ball = new ImageIcon("ball.png");

        // setBackground(bgimage);
        // Timer timer = new Timer(time, new ActionListener() {
          // @Override
          // public void actionPerformed(ActionEvent e) {
            while(true){
            itr++;
            if (itr % 5 == 0) {
              missile_ycord.add(y + 15);
              missile_xcord.add(x + 47);
              for (int i = 0; i < missile_ycord.size() - 1; i++) {
                int yval = (int) missile_ycord.elementAt(i);
                yval -= 30;
                if (yval < 0) {
                  missile_xcord.remove(i);
                  missile_ycord.remove(i);
                }
                missile_ycord.set(i, yval);
                for (int j = 0; j < ycord.size(); j++) {
                  if (yval - 26 <= (int) ycord.elementAt(j)
                      && (int) missile_xcord.elementAt(i) >= (int) xcord.elementAt(j) - 35
                      && (int) missile_xcord.elementAt(i) <= (int) xcord.elementAt(j) + 35) {
                    // xcord.set(j, 900);
                    xcord.remove(j);
                    ycord.remove(j);
                    missile_xcord.remove(i);
                    missile_ycord.remove(i);
                    scoreValue++;
                    scoreval.setText(Integer.toString(scoreValue));
                  }
                }
              }
            }
            if ((int) (Math.random() * 10) % 10 == 0) {
              ycord.add(20);
              xcord.add(50 + len * 100 + (int) (Math.random() * 100));
              if ((int) (xcord.elementAt(xcord.size() - 1)) >= 500)
                len = 0;
              len++;
            }
            for (int i = 0; i < ycord.size(); i++) {
              int yval = (int) ycord.elementAt(i);
              yval += xDelta;
              ycord.set(i, yval);
              if (yval >= 600) {
                time = 10000;
                xcord.clear();
                ycord.clear();
                while (playagain == 0) {
                  Scanner sc = new Scanner(System.in);
                  playagain = sc.nextInt();
                }
                playagain = 0;
                highScoreValue = Math.max(highScoreValue, scoreValue);
                scoreValue = 0;
                highscoreval.setText(Integer.toString(highScoreValue));
                return;
              }
              // System.out.println(yval+" ");
              if (yval + (radius * 2) > getHeight()) {
                ycord.set(i, getHeight() + (radius * 2));
                xDelta *= 1;

              } else if (yval < 0) {
                ycord.set(i, 0);
                xDelta *= -1;
              }
            }
            repaint();
            try {
                  Thread.sleep(1000 / 40);  // milliseconds
               } catch (InterruptedException ex) { }
          }
        };
        // timer.start();
      // }
    };
    gameThread.start();
    Label score = new Label("Score : ");
    Label highScore = new Label("High Score :");
    score.setBounds(650, 50, 112, 30);
    scoreval.setBounds(760, 50, 100, 30);
    highScore.setBounds(650, 80, 112, 30);
    highscoreval.setBounds(760, 80, 100, 30);

    // score.setFont(100.0);
    Font myfont = new Font("Serif", Font.BOLD, 20);
    score.setFont(myfont);
    scoreval.setFont(myfont);
    highScore.setFont(myfont);
    highscoreval.setFont(myfont);

    this.add(score);
    this.add(scoreval);
    this.add(highScore);
    this.add(highscoreval);
    setSize(800, 630);
    setLayout(null);
    setVisible(true);

  }

  public void paint(Graphics g) {
    super.paint(g);
    g.drawImage(img, 0, 0, 800, 630, null);
    g.drawImage(blaster.getImage(), x, y, 120, 120, this);
    for (int i = 0; i < missile_xcord.size() - 1; i++)
      g.drawImage(missile.getImage(), (int) missile_xcord.elementAt(i), (int) missile_ycord.elementAt(i), 25, 33, this);
    for (int i = 0; i < xcord.size(); i++) {
      // g.setColor(Color.ORANGE);
      g.drawImage(ball.getImage(), (int) xcord.elementAt(i), (int) ycord.elementAt(i) - radius, 50, 50, this);

      // g.fillOval((int) xcord.elementAt(i), (int) ycord.elementAt(i) - radius,
      // radius * 2, radius * 2);
    }

  }

  @Override
  public void keyTyped(KeyEvent e) {
  }

  @Override
  public void keyPressed(KeyEvent e) {
    int dir = e.getKeyCode();
    if (dir == 39) {
      if (x < 600)
        x += 10;
      // repaint();
    }
    if (dir == 37) {
      if (x > 20)
        x -= 10;
      // repaint();
    }

  }

  @Override
  public void keyReleased(KeyEvent e) {
  }

  public static void main(String[] args) {
    // new bubbleBlaster();
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {
         // Set up main window (using Swing's Jframe)
         JFrame frame = new JFrame("A Bouncing Ball");
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setContentPane(new bubbleBlaster());
         frame.pack();
         frame.setVisible(true);
      }
   });
  }
}
