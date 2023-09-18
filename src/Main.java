import java.awt.*;
import javax.swing.*;

public class Main extends JFrame {
    static JPanel page = new JPanel(){
        Image background= new ImageIcon(Main.class.getResource("./image/grass.jpg")).getImage();
        public void paint (Graphics g){
            g.drawImage(background, 0,0,null);
        }
    };
    public Main(){
        frame();
    }
    public void frame(){
        setTitle("mole game");
        setSize(1024,666);
        setResizable(false);
        setLayout(null);
        setVisible(true);
        page.setLayout(null);
        page.setBounds(0,0,1024,666);
        add(page);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String[] args) {
        new Main();
    }
}