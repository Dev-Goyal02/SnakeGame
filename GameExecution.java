package com.company;

import java.awt.*;
import javax.swing.*;
import java.awt.dnd.DropTargetAdapter;
import java.awt.event.*;
import java.time.temporal.TemporalAdjuster;


public class GameExecution extends JPanel implements ActionListener {
    private Image apple;
    private Image dot;
    private Image head;
    private final int DOT_SIZE=10;
    private final int ALL_DOTS=900;
    private final int x[]=new int[900];
    private final int y[]=new int[90];
    private final int RANDOM_POSITION=27;
    private int apple_x;
    private int apple_y;

    private int dots;
    private Timer timer;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame=true;
    private int score=0;

    GameExecution(){
        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(300,300));
        setFocusable(true);
        //Without setFocusable true key events do not work
        loadImages();
        initGame();
    }
    public void loadImages(){
        ImageIcon i1= new ImageIcon(ClassLoader.getSystemResource("com/company/icons/apple.png"));
        apple = i1.getImage();

        ImageIcon i2= new ImageIcon(ClassLoader.getSystemResource("com/company/icons/dot.png"));
        dot=i2.getImage();

        ImageIcon i3= new ImageIcon(ClassLoader.getSystemResource("com/company/icons/head.png"));
        head = i3.getImage();
    }
    public void initGame(){
        dots=3;
        for(int i=0;i<dots;i++){
            x[i]=50-i*DOT_SIZE;
            y[i]=50;
        }
        locateApple();
        timer = new Timer(140,this);
        timer.start();

    }
    public void locateApple(){
        int r= (int)(Math.random() * RANDOM_POSITION);
        apple_x= r*DOT_SIZE;
        r= (int)(Math.random() * RANDOM_POSITION);
        apple_y= r*DOT_SIZE;
    }
    public void checkApple(){
        if((x[0]==apple_x) &&(y[0]==apple_y)){
            dots++;
            score+=10;
            locateApple();
        }
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        draw(g);
    }
    public void draw(Graphics g){
        if(inGame){
            g.drawImage(apple,apple_x,apple_y,this);

            for(int i=0;i<dots;i++){
                if(i==0){
                    g.drawImage(head,x[i],y[i],this);
                }
                else{
                    g.drawImage(dot,x[i],y[i],this);
                }
            }
            scoreDisplay(g);
            Toolkit.getDefaultToolkit().sync();
        }
        else{
            gameOver(g);
        }
    }
    public void scoreDisplay(Graphics g){
        String scr="Score: "+score;
        Font score_display=new Font("SAN_SERIF",Font.BOLD,14);
        FontMetrics metrics=getFontMetrics(score_display);
        g.setColor(Color.RED);
        g.setFont(score_display);
        g.drawString(scr,450/2,(108-metrics.stringWidth(scr))/2);
    }
    public void gameOver(Graphics g){
        String msg="Game Over!!! Your Score is :"+ score;
        Font font=new Font("SAN_SERIF",Font.BOLD,17);
        FontMetrics metrics=getFontMetrics(font);
        g.setColor(Color.GREEN);
        g.setFont(font);
        g.drawString(msg,(300-metrics.stringWidth(msg))/2,300/2);
    }
    public void checkCollision(){
        for(int i=dots;i>0;i--){
            if((i>4) && (x[0]==x[i]) &&(y[0]==y[i])){
                inGame=false;
                break;
            }
        }
        if((y[0]>=300) || (x[0]>=300) || (x[0]<=0) || (y[0]<=0)){
            inGame=false;
        }
        if(!inGame){
            timer.stop();
        }
    }
    public void move(){
        for(int i=dots;i>0;i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        if(left){
            x[0]=x[0]-DOT_SIZE;
        }
        if(right){
            x[0]=x[0]+DOT_SIZE;
        }
        if(up){
            y[0]=y[0]-DOT_SIZE;
        }
        if(down){
            y[0]=y[0]+DOT_SIZE;
        }
    }
    public void actionPerformed(ActionEvent ae){
         if(inGame){
           checkApple();
           checkCollision();
           move();
         }
         repaint();
    }
   private class TAdapter extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            int key=e.getKeyCode();
            if(key==KeyEvent.VK_LEFT &&(!right)){
                left=true;
                up=false;
                down=false;
            }
            if(key==KeyEvent.VK_RIGHT &&(!left)){
                right=true;
                up=false;
                down=false;
            }
            if(key==KeyEvent.VK_UP &&(!down)){
                up=true;
                left=false;
                right=false;
            }
            if(key==KeyEvent.VK_DOWN &&(!up)){
                down=true;
                left=false;
                right=false;
            }
        }
   }
}
