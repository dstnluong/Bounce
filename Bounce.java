import java.util.Random;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import javax.swing.JComponent;
import javax.swing.JFrame;

import java.net.URL;
import javax.sound.sampled.*;
import javax.swing.*;
import java.io.IOException;
import javax.swing.SwingUtilities;


public class Bounce {
    private static JFrame frame;
    public static void main(String [] args){
        frame = new JFrame();
        frame.setSize(400, 425);
        frame.setTitle("Bounce");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.WHITE);
        Ball b = new Ball(100,170); 
        frame.add(b);
        Sound s = new Sound();
        frame.setVisible(true);
        while(true){
            try {
                Thread.sleep(17);
            } catch(InterruptedException e){
                e.printStackTrace();
            }
            if(b.hitXEdge() || b.hitYEdge()){
                s.bounce();
            }
            b.repaint(); 
        }
    }
}
class Ball extends JComponent{
    private static final long serialVersionUID = 918638542296082208L;
    private int x, y;
    private final int radius = 50;
    private Color color;
    private Random rand;
    private int xRate = 5;
    private int yRate = -2;
    public Ball(int xcoord, int ycoord){
        x = xcoord;
        y = ycoord;
        if(Math.random() < 0.5){
            yRate *= -1;
        }
        if(Math.random() < 0.5){
            xRate *= -1; 
        }
    }
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        Ellipse2D.Double circle = new Ellipse2D.Double(x, y, radius, radius);

        g2.draw(circle);
        g2.setColor(new Color((x * 255) / 400 + 20, (y * 255) / 400 + 20, 255));
        g2.fill(circle);
        if(hitXEdge()){
            xRate *= -1;
        }
        if(hitYEdge()){
            yRate *= -1;
        }
        x += xRate;
        y += yRate;
    }
    public boolean hitXEdge(){
        return (x < 0 || x + radius > 400);
    }
    public boolean hitYEdge(){
        return (y < 0 || y + radius > 400);
    }
}
class Sound {
    public void bounce () {
        try {
            // Open an audio input stream.
            URL url = this.getClass().getClassLoader().getResource("bounce.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            // Get a sound clip resource.
            Clip clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }   
    }
}
