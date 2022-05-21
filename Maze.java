package maze;

import java.io.*; 
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;

public class Maze extends JFrame implements Runnable {

    static final int numRows = 20;
    static final int numColumns = 20;
    static final int XBORDER = 40;
    static final int YBORDER = 60;
    static final int YTITLE = 30;
    static final int WINDOW_BORDER = 8;
    static final int WINDOW_WIDTH = 2*(WINDOW_BORDER + XBORDER) + numColumns*30;
    static final int WINDOW_HEIGHT = YTITLE + WINDOW_BORDER + 2 * YBORDER + numRows*30;
    
    boolean animateFirstTime = true;
    static int xsize = -1;
    static int ysize = -1;
    int time = 0;
    Image image;
    Graphics2D g;

    final static int PATH = 0;
    final static int WALL = 1;
    final static int HIDD = 2;
    static int board[][] = 
    {{WALL,PATH,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL},
     {WALL,PATH,WALL,PATH,WALL,PATH,PATH,PATH,PATH,PATH,PATH,PATH,PATH,PATH,WALL,PATH,PATH,PATH,PATH,WALL},        
     {WALL,PATH,WALL,PATH,WALL,PATH,WALL,WALL,WALL,WALL,WALL,PATH,WALL,PATH,WALL,PATH,WALL,WALL,PATH,WALL},        
     {WALL,PATH,WALL,PATH,WALL,PATH,WALL,WALL,WALL,WALL,WALL,PATH,WALL,PATH,WALL,PATH,PATH,PATH,PATH,WALL},        
     {WALL,PATH,PATH,PATH,WALL,PATH,WALL,WALL,WALL,WALL,WALL,PATH,PATH,PATH,WALL,PATH,WALL,WALL,PATH,WALL},        
     {WALL,PATH,WALL,PATH,WALL,PATH,PATH,PATH,HIDD,HIDD,HIDD,WALL,WALL,WALL,WALL,PATH,PATH,PATH,PATH,WALL},        
     {WALL,PATH,WALL,PATH,WALL,WALL,WALL,PATH,WALL,WALL,HIDD,WALL,PATH,PATH,WALL,WALL,WALL,PATH,WALL,WALL},        
     {WALL,PATH,WALL,PATH,WALL,WALL,WALL,PATH,WALL,PATH,PATH,PATH,PATH,PATH,PATH,PATH,PATH,PATH,PATH,WALL},        
     {PATH,PATH,WALL,PATH,WALL,WALL,WALL,PATH,WALL,PATH,WALL,PATH,WALL,WALL,PATH,WALL,WALL,WALL,PATH,PATH},        
     {WALL,PATH,WALL,PATH,PATH,PATH,PATH,PATH,WALL,PATH,WALL,PATH,WALL,WALL,PATH,WALL,WALL,WALL,PATH,WALL},        
     {WALL,PATH,WALL,PATH,WALL,PATH,WALL,WALL,WALL,PATH,WALL,PATH,WALL,WALL,PATH,WALL,WALL,WALL,PATH,WALL},        
     {WALL,WALL,WALL,PATH,WALL,PATH,PATH,PATH,WALL,PATH,WALL,PATH,PATH,PATH,PATH,WALL,PATH,PATH,PATH,WALL},        
     {WALL,PATH,PATH,PATH,WALL,PATH,WALL,PATH,WALL,PATH,WALL,PATH,WALL,PATH,WALL,WALL,PATH,WALL,WALL,WALL},        
     {WALL,PATH,WALL,WALL,WALL,PATH,WALL,PATH,WALL,PATH,WALL,PATH,WALL,PATH,WALL,PATH,PATH,PATH,WALL,WALL},        
     {WALL,PATH,PATH,PATH,WALL,PATH,PATH,PATH,WALL,PATH,WALL,WALL,WALL,PATH,WALL,PATH,WALL,PATH,WALL,WALL},        
     {WALL,PATH,WALL,PATH,WALL,WALL,PATH,WALL,WALL,PATH,WALL,PATH,PATH,PATH,WALL,PATH,WALL,PATH,WALL,WALL},        
     {WALL,PATH,WALL,PATH,PATH,PATH,PATH,WALL,WALL,PATH,WALL,PATH,WALL,PATH,WALL,PATH,WALL,PATH,WALL,WALL},        
     {WALL,PATH,WALL,WALL,WALL,WALL,WALL,WALL,WALL,PATH,WALL,PATH,WALL,PATH,WALL,PATH,PATH,PATH,WALL,WALL},        
     {WALL,PATH,PATH,PATH,PATH,PATH,PATH,PATH,PATH,PATH,PATH,PATH,PATH,PATH,PATH,PATH,WALL,WALL,WALL,WALL},        
     {WALL,PATH,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL}
    };
    
//Variables for player.
    Charicter[] player = new Charicter[Charicter.numCharicters];
    
//Variables for coin
    Coin[] coins = new Coin[Coin.numCoins];
    
//score
    static int score = 0;
    static int highScore = 0;
    
    
    static Maze frame;
    public static void main(String[] args) {
        frame = new Maze();
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public Maze() {
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.BUTTON1 == e.getButton()) {
                    //left button

// location of the cursor.
                    int xpos = e.getX();
                    int ypos = e.getY();
                    
                    /*
                    //System.out.println((-(getX(getWidth2())-xpos)+600) +", "+ (-(getY(getHeight2())-ypos)+600));
                    
                    int xy = (-(getX(getWidth2())-xpos)+600);
                    int yy = (-(getY(getHeight2())-ypos)+600);
                    
                    //System.out.println(( (xy)/numColumns ) +", "+ (yy/numRows));
                    if (board[(yy)/numColumns][(xy/numRows)] == PATH)
                    {
                        board[(yy)/numColumns][(xy/numRows)] = WALL;
                    }
                    if (board[(yy)/numColumns][(xy/numRows)] == WALL)
                    {
                        board[(yy)/numColumns][(xy/numRows)] = PATH;
                    }
                    */
                    
                    
                }
                if (e.BUTTON3 == e.getButton()) {
                    //right button
                    reset();
                }
                repaint();
            }
        });

    addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseDragged(MouseEvent e) {
        repaint();
      }
    });

    addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseMoved(MouseEvent e) {

        repaint();
      }
    });

        addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
                Charicter p = player[0];
                if (e.VK_UP == e.getKeyCode()) {
                    p.Ydir = -1;
                    p.Xdir = 0;
                } else if (e.VK_DOWN == e.getKeyCode()) {
                    p.Ydir = 1;
                    p.Xdir = 0;
                } else if (e.VK_LEFT == e.getKeyCode()) {
                    p.Xdir = -1;
                    p.Ydir = 0;
                } else if (e.VK_RIGHT == e.getKeyCode()) {
                    p.Xdir = 1;
                    p.Ydir = 0;
                }
                
                repaint();
            }
        });
        init();
        start();
    }
    Thread relaxer;
////////////////////////////////////////////////////////////////////////////
    public void init() {
        requestFocus();
    }
////////////////////////////////////////////////////////////////////////////
    public void destroy() {
    }

 

////////////////////////////////////////////////////////////////////////////
    public void paint(Graphics gOld) {
        if (image == null || xsize != getSize().width || ysize != getSize().height) {
            xsize = getSize().width;
            ysize = getSize().height;
            image = createImage(xsize, ysize);
            g = (Graphics2D) image.getGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
        }
//fill background
        g.setColor(Color.cyan);
        g.fillRect(0, 0, xsize, ysize);

        int x[] = {getX(0), getX(getWidth2()), getX(getWidth2()), getX(0), getX(0)};
        int y[] = {getY(0), getY(0), getY(getHeight2()), getY(getHeight2()), getY(0)};
//fill border
        g.setColor(Color.white);
        g.fillPolygon(x, y, 4);
// draw border
        g.setColor(Color.red);
        g.drawPolyline(x, y, 5);

        if (animateFirstTime) {
            gOld.drawImage(image, 0, 0, null);
            return;
        }

        
        g.setColor(Color.red);
//horizontal lines
        for (int zi=1;zi<numRows;zi++)
        {
            g.drawLine(getX(0) ,getY(0)+zi*getHeight2()/numRows ,
            getX(getWidth2()) ,getY(0)+zi*getHeight2()/numRows );
        }
//vertical lines
        for (int zi=1;zi<numColumns;zi++)
        {
            g.drawLine(getX(0)+zi*getWidth2()/numColumns ,getY(0) ,
            getX(0)+zi*getWidth2()/numColumns,getY(getHeight2())  );
        }
        
//Display the objects of the board
        for (int zrow=0;zrow<numRows;zrow++)
        {
            for (int zcolumn=0;zcolumn<numColumns;zcolumn++)
            {
                
                if (board[zrow][zcolumn] == WALL)
                {
                    g.setColor(Color.gray);
                        
                    g.fillRect(getX(0)+zcolumn*getWidth2()/numColumns,
                    getY(0)+zrow*getHeight2()/numRows,
                    getWidth2()/numColumns,
                    getHeight2()/numRows);
                
                } 
                if (board[zrow][zcolumn] == HIDD)
                {
                    g.setColor(Color.gray);
                        
                    g.fillRect(getX(0)+zcolumn*getWidth2()/numColumns,
                    getY(0)+zrow*getHeight2()/numRows,
                    getWidth2()/numColumns,
                    getHeight2()/numRows);
                
                } 
                
                
            }
        }
        
        
        //draw players
        for (Charicter p:player)
            p.draw(frame);
        
        //draw  coins
        for (Coin i:coins)
            i.draw(frame);


        double xs = 1.5;
        double xy = 1.5;
        g.setColor(Color.black);
        g.scale(xs,xy);
        g.drawString("Score: " + score, (int)((getWidth2()/2)/xs), 40);
        g.drawString("HighScore: " + highScore, (int)((getWidth2()/2)/xs), 40+(int)(xy*10));
        g.scale(1/xs,1/xy);        

        gOld.drawImage(image, 0, 0, null);
    }

////////////////////////////////////////////////////////////////////////////
// needed for     implement runnable
    public void run() {
        while (true) {
            animate();
            repaint();
            
            
            double seconds = .05;    //time that 1 frame takes.
            int miliseconds = (int) (1000.0 * seconds);
            try {
                Thread.sleep(miliseconds);
            } catch (InterruptedException e) {
            }
        }
    }
/////////////////////////////////////////////////////////////////////////
    public void reset() {
        
        score = 0;
        
        //Coins
        for (int i = 0; i < coins.length;i++)
            coins[i] = new Coin(frame);
        
        //Charcters
        for (int i = 0; i < player.length;i++)
            player[i] = new Charicter(frame);
            
        //prime player vars
        player[0].type = 0;
        player[0].Pcolor = Color.green;
        
        //mach charicters
        for (Charicter i:player)
            i.newPlayer();
        
        //make coins
        for (Coin i:coins)
            i.randomCoin();
    }
/////////////////////////////////////////////////////////////////////////
    public void animate() {
        if (animateFirstTime) {
            animateFirstTime = false;
            if (xsize != getSize().width || ysize != getSize().height) {
                xsize = getSize().width;
                ysize = getSize().height;
            }
            reset();
        }
        
        for (Charicter p:player)
            p.move();
        
        //enhanced for loop
        for (Charicter p:player)
            for (Coin i:coins)
                i.collide(p.px,p.py,p.type);
        
        //enemy colides with player
        for (Charicter p:player)
            if (p.px == player[0].px && p.py == player[0].py && p.type != 0 && p.able)
                player[0].end = true;
        
        if (score > highScore)
            highScore = score;

        for (Charicter p:player)
            p.time++;
    }

    

 

////////////////////////////////////////////////////////////////////////////
    public void start() {
        if (relaxer == null) {
            relaxer = new Thread(this);
            relaxer.start();
        }
    }
////////////////////////////////////////////////////////////////////////////
    public void stop() {
        if (relaxer.isAlive()) {
            relaxer.stop();
        }
        relaxer = null;
    }


/////////////////////////////////////////////////////////////////////////
    public static int getX(int x) {
        return (x + XBORDER + WINDOW_BORDER);
    }

    public static int getY(int y) {
        return (y + YBORDER + YTITLE );
    }

    public int getYNormal(int y) {
        return (-y + YBORDER + YTITLE + getHeight2());
    }
    
    public static int getWidth2() {
        return (xsize - 2 * (XBORDER + WINDOW_BORDER));
    }

    public static int getHeight2() {
        return (ysize - 2 * YBORDER - WINDOW_BORDER - YTITLE);
    }
}


class Coin {
    static int numCoins = 8;
    int value = 0;
    int coinX;
    int coinY;
    int[][] board;
    int numRows;
    int numColumns;
    int PATH;
    Coin(Maze frame) {
        board = frame.board;
        numRows = frame.numRows;
        numColumns = frame.numColumns;
        PATH = frame.PATH;
    }
    public void randomCoin()
    {
        value = (int)(Math.random()*5)+2;
        coinX = (int)(Math.random()*numRows);
        coinY = (int)(Math.random()*numColumns);
        while (board[coinY][coinX] != PATH)
        {
            coinX = (int)(Math.random()*numRows);
            coinY = (int)(Math.random()*numColumns);
        }
    }
    public void collide(int px, int py,int type)
    {
        if (px == coinX && py == coinY && type == 0)
        {
            Maze.score+=value;
            randomCoin();
        }
    }
    public void draw(Maze frame)
    {
        Graphics2D g = frame.g;
        g.setColor(Color.black);
        g.fillOval(frame.getX(0)+coinX*frame.getWidth2()/numColumns,
        frame.getY(0)+coinY*frame.getHeight2()/numRows,
        frame.getWidth2()/numColumns,
        frame.getHeight2()/numRows);
        g.setColor(Color.yellow);
        g.fillOval(frame.getX(1)+coinX*frame.getWidth2()/numColumns,
        frame.getY(1)+coinY*frame.getHeight2()/numRows,
        frame.getWidth2()/numColumns-2,
        frame.getHeight2()/numRows-2);
        double xs = 1.2;
        double xy = 1.2;
        g.setColor(Color.black);
        g.scale(xs,xy);
        g.drawString("" + value, (int)(frame.getX(0)+(coinX*frame.getWidth2()/numColumns)/xs), (int)(frame.getY(0)+(coinY*frame.getHeight2()/numRows)/xy));
        g.scale(1/xs,1/xy);
    }
}

class Charicter {
    static boolean able = true;
    static int numCharicters = 4;
    int px = 2;
    int py = 2;
    int Xdir = 0;
    int Ydir = 0;
    int[][] board = Maze.board;
    int numRows = Maze.numRows;
    int numColumns = Maze.numColumns;
    int WALL = Maze.WALL;
    int type;
    int timesmoved = 0;
    int PATH;
    int time = 0;
    int tptime = 0;
    boolean end = false;
    Color Pcolor = Color.red;
    Charicter(Maze frame) {
        PATH = frame.PATH;
        type = (int)(Math.random()*3)+1;
    }
    public void newPlayer()
    {
        px = (int)(Math.random()*numRows);
        py = (int)(Math.random()*numColumns);
        while (board[py][px] != PATH)
        {
            px = (int)(Math.random()*numRows);
            py = (int)(Math.random()*numColumns);
        }
    }
    public void move()
    {
        switch(type)
        {
            case 0:
                if (!end)
                    playerMove();
                break;
            case 1:
                randomMove();
                break;
            case 2:
                if (tptime < time)
                    runnerMove();
                break;
            case 3:
                if (tptime < time)
                    teleporterMove();
                break;
        }
    }
    //types of movement
    public void playerMove()
    {
        if (px + Xdir <= -1 || px + Xdir >= Maze.numColumns)
            px = (px+Xdir)-(Maze.numColumns*Xdir);
        
        else if (py + Ydir <= -1 || py + Ydir >= Maze.numRows)
            py = (py+Ydir)-(Maze.numRows*Ydir);
        
        else if (board[py+Ydir][px+Xdir]!=WALL)
        {
            px += Xdir;
            py += Ydir;
            Xdir = 0;
            Ydir = 0;
        }
    }
    public void randomMove()
    {
        boolean keeplooping = true;
        while (keeplooping)
        {
            Xdir = (int)(Math.random()*3)-1;
            Ydir = (int)(Math.random()*3)-1;
            if (Xdir != 0 && Ydir != 0)
            {
                int e = (int)(Math.random()*2)+1;
                if (e == 1)
                    Xdir = 0;
                else
                    Ydir = 0;
            }
            if (px + Xdir <= -1 || px + Xdir >= Maze.numColumns)
                px = (px+Xdir)-(Maze.numColumns*Xdir);

            else if (py + Ydir <= -1 || py + Ydir >= Maze.numRows)
                py = (py+Ydir)-(Maze.numRows*Ydir);

            else if (board[py+Ydir][px+Xdir]!=WALL)
            {
                px += Xdir;
                py += Ydir;
                Xdir = 0;
                Ydir = 0;
                keeplooping = false;
            }
        }
    }
    public void runnerMove()
    {
        if (timesmoved>=20)
        {
            timesmoved=0;
            tptime = time+10;
            newPlayer();
        }
        if (Xdir == 0 && Ydir == 0)
        { 
            timesmoved=0;
            Xdir = (int)(Math.random()*3)-1;
            Ydir = (int)(Math.random()*3)-1;
        }
        try {
            if (board[py+Ydir][px+Xdir] == WALL)
            {
                Xdir = (int)(Math.random()*3)-1;
                Ydir = (int)(Math.random()*3)-1;
                if (Xdir != 0 && Ydir != 0)
                {
                    int e = (int)(Math.random()*2)+1;
                    if (e == 1){
                        Xdir = 0;
                    }
                    else {
                        Ydir = 0;
                    }
                }
            }
        }
        catch(Exception e){}
        if (px + Xdir <= -1 || px + Xdir >= Maze.numColumns)
            px = (px+Xdir)-(Maze.numColumns*Xdir);

        else if (py + Ydir <= -1 || py + Ydir >= Maze.numRows)
            py = (py+Ydir)-(Maze.numRows*Ydir);

        else if (board[py+Ydir][px+Xdir]!= WALL)
        {
            px += Xdir;
            py += Ydir;
            timesmoved++;
        }
        
    }
    public void teleporterMove()
    {
        if (time >= tptime+100)
        {
            timesmoved=0;
            tptime = time+10;
            newPlayer();
        }
    }
    
    //draw
    public void draw(Maze frame)
    {
        if (tptime > time) {
            predraw(frame);
            return;
        }
        able = true;
        Graphics2D g = frame.g;
        
        g.setColor(Pcolor);

        g.fillRect(frame.getX(0)+px*frame.getWidth2()/numColumns,
        frame.getY(0)+py*frame.getHeight2()/numRows,
        frame.getWidth2()/numColumns,
        frame.getHeight2()/numRows);
    }
    public void predraw(Maze frame)
    {
        able = false;
        Graphics2D g = frame.g;

        g.setColor(Color.cyan);

        g.fillRect(frame.getX(0)+px*frame.getWidth2()/numColumns,
                frame.getY(0)+py*frame.getHeight2()/numRows,
                frame.getWidth2()/numColumns,
                frame.getHeight2()/numRows);
    }
}
