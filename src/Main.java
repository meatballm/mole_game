import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Math.random;

public class Main extends JFrame implements KeyListener {
    int x=100, y=100;
    int count=0;
    public final int Width =1024;
    public final int Height =666;
    Image hammer = new ImageIcon(Main.class.getResource("./image/hammer.png")).getImage();
    Image digda= new ImageIcon(Main.class.getResource("./image/digda.png")).getImage();
    Image digtrio= new ImageIcon(Main.class.getResource("./image/digtrio.png")).getImage();
    Image background= new ImageIcon(Main.class.getResource("./image/grass.jpg")).getImage();
    public Main(){
        Timer timer=new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                count++;
                repaint();
            }
        };
        timer.schedule(task,1000,1000);
        addKeyListener(this);
        setTitle("mole game");
        setSize(Width,Height);
        setResizable(false);
        setLayout(new BorderLayout());
        setBounds(0,0,1024,666);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    public void mole(int x, int y, Graphics g){
        g.drawImage(digda,x,y,null);
    }
    @Override
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
            switch(key){
                case KeyEvent.VK_RIGHT:
                    x=(x<Width-150)?x+50:x;
                    System.out.println("R");
                    break;
                case KeyEvent.VK_LEFT:
                    x=(x>0)?x-50:x;
                    System.out.println("L");
                    break;
                case KeyEvent.VK_UP:
                    y=(y>0)?y-50:y;
                    System.out.println("U");
                    break;
                case KeyEvent.VK_DOWN:
                    y=(y<Height-150)?y+50:y;
                    System.out.println("D");
                    break;
                case KeyEvent.VK_SPACE:
                    hammer = new ImageIcon(Main.class.getResource("./image/hamdown.png")).getImage();
                    System.out.println("space");
                    Timer t = new Timer();
                    TimerTask tas=new TimerTask() {
                        @Override
                        public void run() {
                            hammer = new ImageIcon(Main.class.getResource("./image/hammer.png")).getImage();
                            repaint();
                        }
                    };
                    t.schedule(tas, 200);
                    break;
                default:
                    return;
            }
            repaint();
    }
    public void paint (Graphics g){
        g.clearRect(0,0,Width,Height);
        g.drawImage(background, 0,0,null);
        //g.drawRect(50,100,950,500);
        System.out.println(count);
        g.setColor(Color.RED);
        g.setFont(new Font("Serif",Font.PLAIN,40));
        g.drawString("시간 : "+count,470,70);
        if(count%3!=0){
            g.drawImage(digda,(int)(Math.random()*900)+50,(int)(Math.random()*450)+100,null);
            g.drawImage(digtrio,500,500,null);
        }
        g.setColor(Color.blue);
        g.drawOval(x,y,30,30);
        g.drawOval(x-5,y-5,40,40);
        g.drawImage(hammer,x,y,null);
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }
    @Override
    public void keyTyped(KeyEvent e) {}
    public static void main(String[] args) {
        new Main();
    }
}