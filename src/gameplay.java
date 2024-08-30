import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;


public class gameplay extends JPanel implements KeyListener, ActionListener{
    private boolean play = false;
    private int score = 0;
    private int totalBricks = 28;
    private Timer timer;
    private int delay = 0;
    private int playerX = 310;
    private int ballposX = 120;
    private int ballposY = 300;
    private int ballXdir = -1;
    private int ballYdir = -2;
    private int changecolor=100;
    private mapGenerator map;


    public gameplay(){
        map = new mapGenerator(4,7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();  
    }

    public void color(){
        changecolor=1;
        repaint();
    }

    public void color1(){
        changecolor=0;
        repaint();
    }
    
    public void paint(Graphics g){
        // background
        super.paint(g);
        g.setColor(Color.black); 
        g.fillRect(1,1,692,592);

        // drawing map
        map.draw((Graphics2D) g);

        // borders
        g.setColor(Color.yellow);
        g.fillRect(0,0,3,592);
        g.fillRect(0,0,692,3);
        g.fillRect(691,0,3,592);

        // scores 
        g.setColor(Color.white);
        g.setFont(new Font("Serif", Font.BOLD, 25));
        g.drawString(""+score,590,30);

        // paddle
        g.setColor(Color.yellow);
        g.fillRect(playerX,550,80,5);

        // ball
        g.setColor(Color.yellow);
        g.fillOval(ballposX,ballposY,20,20);

        // ball color change
        if(changecolor==1){
            g.setColor(Color.white);
            g.fillOval(ballposX,ballposY,20,20);
        }
        else if(changecolor==0){
            g.setColor(Color.green);
            g.fillOval(ballposX,ballposY,20,20);
        }
        

        if(totalBricks<=0){
            play=false;
            ballXdir=0;
            ballYdir=0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("You Won : "+score,190,300);

            g.setFont(new Font("serif",Font.BOLD,20));
            g.drawString("Press Enter to Restart",230,350);
        }

        if(ballposY>570){
            play=false;
            ballXdir=0;
            ballYdir=0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("Game Over, Scores: "+ score,190,300);

            g.setFont(new Font("serif",Font.BOLD,20));
            g.drawString("Press Enter to Restart",230,350);
        }
        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e){
        timer.start();
        if(play){
            if(new Rectangle(ballposX,ballposY,20,20).intersects(new Rectangle(playerX,550,80,5))){
                ballYdir = -ballYdir;
            }
           A: for(int i=0;i<map.brickarray.length;i++){
                for(int j=0;j<map.brickarray[0].length;j++){
                    if(map.brickarray[i][j]>0){
                        int brickX = j*map.brickWidth+80;
                        int brickY = i*map.brickHeight+50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle rect = new Rectangle(brickX,brickY,brickWidth,brickHeight);
                        Rectangle ballRect = new Rectangle(ballposX,ballposY,20,20);
                        Rectangle brickRect = rect;
                        

                        if(ballRect.intersects(brickRect)){
                            map.setBrickValue(0,i,j);
                            totalBricks--;
                            score+=5;
                            if((i+j)%2==0){
                                color();
                            }
                            else color1();
                            // delay-=2;

                            if(ballposX + 19 <= brickRect.x || ballposX+1 >= brickRect.x+brickRect.width){
                                ballXdir = -ballXdir;
                            }
                            else{
                                ballYdir = -ballYdir;
                            }
                            break A;
                        }
                    }
                }
            }
            ballposX += ballXdir;
            ballposY +=ballYdir;
            if(ballposX<0){
                ballXdir = -ballXdir;
            }
            if(ballposY<0){
                ballYdir = -ballYdir;
            }
            if(ballposX>670){
                ballXdir = -ballXdir;
            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_RIGHT){
            if(playerX >= 600){
                playerX = 600;
            }
            else{
                moveRight();
            }
        }
        if(e.getKeyCode()==KeyEvent.VK_LEFT){
            if(playerX < 15){
                playerX = 15;
            }
            else{
                moveLeft();
            }
        }
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            if(!play){
                play = true;
                Random random = new Random();
                int ma = 600;
                int mi = 100;
                int mi1 = 300;
                int ma1 = 500;
                ballposX = random.nextInt(ma - mi + 1) + mi;
                ballposY = random.nextInt(ma1 - mi1 + 1) + mi1;
                ballXdir = -1;
                ballYdir = -2;
                playerX = 310;
                score = 0;
                totalBricks = 28;
                map = new mapGenerator(4, 7);
                timer.start();
            }
        }
    }

    public void moveRight(){
        play = true;
        playerX += 30;
    }

    public void moveLeft(){
        play = true;
        playerX -= 30;
    }
}