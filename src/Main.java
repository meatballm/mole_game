import org.w3c.dom.css.Rect;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.sound.sampled.*;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.Timer;

class Mole{
    Image dig =new ImageIcon(Main.class.getResource("./image/digda.png")).getImage();
    Image digtrio =new ImageIcon(Main.class.getResource("./image/digtrio.png")).getImage();
    Image aloladig =new ImageIcon(Main.class.getResource("./image/aloladigda.png")).getImage();
    Image aloladigtrio =new ImageIcon(Main.class.getResource("./image/alolatrio.png")).getImage();
    Image molek;
    int special =0;
    int birth;
    boolean trio=Math.random()*7>5?true:false;
    boolean hard;
    int x,y;
    boolean life=true;
    public Mole(int x,int y,boolean k,int t){
        this.x=x;
        this.y=y;
        this.hard=k;
        this.birth=t;
        if(hard==true){
            if(trio==true)
                molek=aloladigtrio;
            else
                molek=aloladig;
        }
        else if(hard==false){
            if(trio==true)
                molek=digtrio;
            else
                molek=dig;
        }
    }
    public Mole(int x,int y,boolean k,int t,int a){
        this.x=x;
        this.y=y;
        this.hard=k;
        this.birth=t;
        this.special=a;
        if(hard==true){
            molek=aloladig;
        }
        else if(hard==false){
            molek=dig;
        }
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
                    Main.class.getResourceAsStream(special==0?"./sound/dead.wav":"./sound/start.wav"));
            clip.open(inputStream);
            clip.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
public class Main extends JFrame implements KeyListener {
    Queue<Mole> mole = new LinkedList<>();
    Iterator<Mole> itr = mole.iterator();
    int x=100, y=100;
    boolean hardmode=false;
    int gamemode=0;//0 시작전,1게임중,2게임끝
    int count;
    static int score=0;
    public final int Width =1024;
    public final int Height =666;
    Image hammer = new ImageIcon(Main.class.getResource("./image/hammer.png")).getImage();
    Image bg= new ImageIcon(Main.class.getResource("./image/grass.png")).getImage();

    public Main(){
        addKeyListener(this);
        setTitle("mole game");
        setSize(Width,Height);
        setResizable(false);
        setLayout(new BorderLayout());
        setBounds(0,0,1024,666);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setGame();
    }
    public void setGame(){
        switch(gamemode){
            case 0:
                count=60;
                score=0;
                mole.clear();
                mole.add(new Mole(300, 300, false,0,1));
                mole.add(new Mole(700, 300, true,0,2));
                break;
            case 1:
                System.out.println("gamestart");
                Timer timer = new Timer();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        count--;
                        if(count==0){
                            gamemode=2;
                            timer.cancel();
                            setGame();
                        }
                        if (count % 2 == 0&&count>2) {
                            System.out.println("spawn");
                            for(int i=0;i<Math.random()*4;i++)
                                mole.add(new Mole((int) (Math.random() * 800) + 50, (int) (Math.random() * 450) + 100, hardmode,count));
                        }
                        while(mole.size()>0&&mole.peek().birth>count+(hardmode==true?1:2)){
                                System.out.println("despawn");
                                mole.remove();
                        }
                        repaint();
                    }
                };
                timer.schedule(task, 1000, 1000);
                break;
            case 2:
                count=0;
                System.out.println("mole : "+mole.size());
                mole.add(new Mole(500, 450, false,0,-1));
                repaint();
                break;
        }
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
                                Rectangle ham = new Rectangle(x, y, 30, 30);
                                Rectangle mol = new Rectangle(i.x, i.y, i.trio?70:50,i.trio?70:50);
                                if(mol.intersects(ham)&&i.life==true){
                                    i.Killmole();
                                    if(i.special>0) {
                                        gamemode = 1;
                                        if(i.special==2)
                                            hardmode=true;
                                        mole.clear();
                                        setGame();
                                        repaint();
                                        break;
                                    }
                                    else if(i.special==-1){
                                        gamemode=0;
                                        setGame();
                                        repaint();
                                        break;
                                    }
                                    else {
                                        score += i.trio?30:10;
                                    }
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
        switch(gamemode){
            case 0:
                for (Mole i : mole) {
                    i.Printmole(g);
                }
                g.setColor(Color.gray);
                g.fillRect(295,355,60,20);
                g.fillRect(695,355,60,20);
                g.setColor(Color.white);
                g.drawString("이지모드", 300, 370);
                g.drawString("하드모드", 700, 370);
                break;
            case 1:
                System.out.println(count);
                g.setColor(Color.RED);
                g.setFont(new Font("Serif", Font.PLAIN, 40));
                g.drawString("시간 : " + count, 470, 70);
                g.drawString("점수 : " + score, 800, 70);
                for (Mole i : mole) {
                    i.Printmole(g);
                }
                break;
            case 2:
                g.setColor(Color.RED);
                g.setFont(new Font("Serif", Font.PLAIN, 100));
                g.drawString("끝났습니다!", 400, 200);
                g.setFont(new Font("Serif", Font.PLAIN, 50));
                g.drawString("점수 : " + score, 400, 400);
                g.setColor(Color.GRAY);
                g.fillRect(495,500,90,30);
                g.setColor(Color.WHITE);
                g.setFont(new Font("Serif", Font.PLAIN, 20));
                g.drawString("다시하기", 500, 520);
                for (Mole i : mole) {
                    i.Printmole(g);
                }
                break;
        }
        g.setColor(Color.blue);
        g.drawOval(x, y, 30, 30);
        g.drawOval(x - 5, y - 5, 40, 40);
        g.drawImage(hammer, x, y, null);
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