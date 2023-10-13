import org.w3c.dom.css.Rect;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.sound.sampled.*;
import javax.swing.*;
import java.io.IOException;
import java.util.Iterator;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;
import static java.lang.Math.random;
class Mole{
    Image molek;
    int x,y;
    boolean life=true;
    public Mole(int x,int y,Image k){
        this.x=x;
        this.y=y;
        this.molek=k;
    }
    public void Printmole(Graphics g){
        if(life)
            g.drawImage(molek,x,y,null);
    }
    public void Killmole(){
        life=false;
        try{
            Clip clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                    Main.class.getResourceAsStream("./sound/dead.wav"));
            clip.open(inputStream);
            clip.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void Catchmole(int mx, int my){
        Rectangle ham = new Rectangle(mx, my, 135, 135);
        Rectangle mol = new Rectangle(x, y, 50,50);
        if(mol.intersects(ham))
            Killmole();
    }
}
public class Main extends JFrame implements KeyListener {
    Stack<Mole> mole = new Stack<>();
    Iterator<Mole> itr = mole.iterator();
    int x=100, y=100;
    int count=0;
    static int score=0;
    public final int Width =1024;
    public final int Height =666;
    Image hammer = new ImageIcon(Main.class.getResource("./image/hammer.png")).getImage();
    Image dig =new ImageIcon(Main.class.getResource("./image/digda.png")).getImage();
    Image bg;//= new ImageIcon(Main.class.getResource(".image/grass.png")).getImage();

    public Main(){
        Timer timer=new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                count++;
                if(count%3==0){
                    mole.add(new Mole((int)(Math.random()*900)+50,(int)(Math.random()*450)+100,dig));
                }
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
                    try{
                        Clip clip = AudioSystem.getClip();
                        AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                                Main.class.getResourceAsStream("./sound/pop.wav"));
                        clip.open(inputStream);
                        clip.start();
                    } catch (Exception ei) {
                        throw new RuntimeException(ei);
                    }
                    Timer t = new Timer();
                    TimerTask tas=new TimerTask() {
                        @Override
                        public void run() {
                            hammer = new ImageIcon(Main.class.getResource("./image/hammer.png")).getImage();
                            for(Mole i:mole){
                                Rectangle ham = new Rectangle(x, y, 135, 135);
                                Rectangle mol = new Rectangle(i.x, i.y, 50,50);
                                if(mol.intersects(ham)){
                                    i.Killmole();
                                    score+=10;
                                }
                            }
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
        g.drawImage(bg, 0,0,null);
        //g.drawRect(50,100,950,500);
        System.out.println(count);
        g.setColor(Color.RED);
        g.setFont(new Font("Serif",Font.PLAIN,40));
        g.drawString("시간 : "+count,470,70);
        g.drawString("점수 : "+score,800,70);
        for(Mole i:mole){
            i.Printmole(g);
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