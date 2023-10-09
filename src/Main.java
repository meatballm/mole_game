import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
public class Main extends JFrame implements KeyListener {
    int x=100, y=100;
    public final int Width =1024;
    public final int Height =666;
    Image hammer = new ImageIcon(Main.class.getResource("./image/hammer.png")).getImage();
    Image background= new ImageIcon(Main.class.getResource("./image/grass.jpg")).getImage();
    public Main(){
        addKeyListener(this);
        setTitle("mole game");
        setSize(Width,Height);
        setResizable(false);
        setLayout(null);
        setVisible(true);
        setLayout(null);
        setBounds(0,0,1024,666);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    @Override
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
            switch(key){
                case KeyEvent.VK_RIGHT:
                    x=(x<Width-150)?x+10:x;
                    System.out.println("R");
                    break;
                case KeyEvent.VK_LEFT:
                    x=(x>0)?x-10:x;
                    System.out.println("L");
                    break;
                case KeyEvent.VK_UP:
                    y=(y>0)?y-10:y;
                    System.out.println("U");
                    break;
                case KeyEvent.VK_DOWN:
                    y=(y<Height-150)?y+10:y;
                    System.out.println("D");
                    break;
            }
            repaint();
    }
    public void paint (Graphics g){
        g.clearRect(0,0,Width,Height);
        g.drawImage(background, 0,0,null);
        g.drawImage(hammer,x,y,null);
        g.drawRect(x,y,1,1);
        System.out.println("ANIMATE");
    }
    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
    public static void main(String[] args) {
        new Main();
    }
}