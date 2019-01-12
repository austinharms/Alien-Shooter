import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.io.*;

/**
 * a turret shooting game 
 * 
 * @author (austin) 
 * @version (12/2/2018)
 */
public class MyWorld extends World
{
    private final static int WORLDWIDTH = 600;
    private final static int WORLDHEIGHT = 400;
    public final static int BACKGROUNDWIDTH = 1800;
    public int xImageOffset = -(BACKGROUNDWIDTH/2);
    private int yImageOffset = 0;
    private GreenfootImage backgroundImage;
    private Overlay overlay;
    private HealthBar healthBar;
    public long score = 0;
    public long highScore = 0;
    private boolean start;
    public int leftEdge = -(BACKGROUNDWIDTH/2);
    public boolean selectedLaser = true;
    public int stageNumber = 0;
    public boolean rocketDestroyed = false;
    /**
     * MyWorld Constructor sets the height of the world to WORLDWIDTH by WORLDHEIGHT, sets the speed to 50, and adds the objects to the world
     * it then starts the game
     *
     */
    public MyWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(WORLDWIDTH, WORLDHEIGHT, 1, false); 
        Greenfoot.setSpeed(75);
        backgroundImage = new GreenfootImage("HikingWorldBackground.png");
        getData();
        //showText("High Score: " + Long.toString(highScore), 300, 15);
        shiftWorldBackground( 0, 0 );
        healthBar = new HealthBar(500,500);
        addObject(healthBar, 170, (getHeight()/8) * 6);
        overlay = new Overlay();
        addObject(overlay, getWidth()/2, getHeight()/2);
        addObject(new GunSelector(), 350, 330);
        setPaintOrder(HealthBar.class, MinMap.class , GunSelector.class, Overlay.class, Missile.class, Alien.class);
        addObject(new MinMap(), getWidth() - 150, getHeight() - 86);
        start = true;
        int cloudNumber = Greenfoot.getRandomNumber(10);
        for(int i = 0; i < cloudNumber; i++)
        {
            addCloud();
        }
        showText("Press Space to Start", getWidth()/2, getHeight()/2);
        Greenfoot.start();
        Greenfoot.delay(50);
    }

    /**
     * MyWorld Constructor sets the height of the world to WORLDWIDTH by WORLDHEIGHT, sets the speed to 50, and adds the objects to the world
     * and sets the score to the currentScore parameter and the current health
     * of the health bar to health parameter it then starts the game
     *
     * @param health A parameter
     * @param currentScore A parameter
     */
    public MyWorld(int health, long currentScore, int stage)
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(WORLDWIDTH, WORLDHEIGHT, 1, false); 
        Greenfoot.setSpeed(75);
        backgroundImage = new GreenfootImage("HikingWorldBackground.png");
        getData();
        stageNumber = stage;
        score = currentScore + health;
        shiftWorldBackground( 0, 0 );
        healthBar = new HealthBar(health + 100,500);
        addObject(healthBar, 170, (getHeight()/8) * 6);
        overlay = new Overlay();
        addObject(overlay, getWidth()/2, getHeight()/2);
        addObject(new GunSelector(), 350, 330);
        setPaintOrder(HealthBar.class, MinMap.class, GunSelector.class, Overlay.class, Missile.class, Cloud.class, Alien.class);
        addObject(new MinMap(), getWidth() - 150, getHeight() - 86);
        for(int i = 0; i < 20; i++)
        {
            addObject(new Alien(), Greenfoot.getRandomNumber(BACKGROUNDWIDTH - 300) - (BACKGROUNDWIDTH/2 - 300), Greenfoot.getRandomNumber(300) + 150);
        }
        for(int j = 0; j < 5; j++)
        {
            addCloud();
        }
        start = false;
        rocket();
        Greenfoot.start();
        Greenfoot.playSound("level up.mp3");
        Greenfoot.delay(20);
    }

    /**
     * act is the main looping code that checks if the game has started if it has  it checks if a 
     * missle exist if not it runs the rocket method then it then runs the checkKeyPressed, checkHit, checkRound, checkLoss method and checks if there is a missle again
     *
     *@param nothing there are no parmeters
     *@return nothing is returned
     */
    public void act()
    {
        if(start == true)
        {
            if(Greenfoot.isKeyDown("space"))
            {
                Greenfoot.setWorld(new MyWorld(500, -500, 1));
            }
        }
        else
        {
            if(getObjects(Missile.class).isEmpty())
            {
                rocket();
            }
            checkKeyPressed();
            checkHit();
            checkRound();
            checkLoss();
            if(getObjects(Missile.class) == null)
            {
                rocket();
            }
        }
    }

    /**
     * checkKeyPressed checks if a key is pressed and depending on witch key it shifts the world
     * 
     * @param nothing there are no parmeters
     * @return nothing is returned
     */
    private void checkKeyPressed()
    {
        if(Greenfoot.isKeyDown("left"))
        {
            shiftWorld(1, 0);
        }
        if(Greenfoot.isKeyDown("right"))
        {
            shiftWorld(-1, 0);
        }
        if(Greenfoot.isKeyDown("up"))
        {
            shiftWorld(0, 1);
        }
        if(Greenfoot.isKeyDown("down"))
        {
            shiftWorld(0, -1);
        }
    }

    /**
     * Method shiftWorld checks that the input values are within range then it runs the shiftworldbackground and shiftworldactors with the movex and movey values
     *
     * @param moveX A parameter used to shift the world a certen direction moveY A parameter used to shift the world a certen direction
     * @return nothing is returnd
     */
    public void shiftWorld(int moveX, int moveY) {
        xImageOffset = xImageOffset + moveX;
        yImageOffset = yImageOffset + moveY;
        if(xImageOffset > 0)
        {
            xImageOffset = 0; 
        }
        else if(xImageOffset < -BACKGROUNDWIDTH)
        {
            xImageOffset = -BACKGROUNDWIDTH; 
        }
        else if(yImageOffset > 0)
        {
            yImageOffset = 0; 
        }
        else if(yImageOffset < -350)
        {
            yImageOffset = -350; 
        }
        else
        {
            leftEdge = xImageOffset;
            shiftWorldBackground(moveX, moveY);
            shiftWorldActors(moveX, moveY);
        }
    }

    /**
     * Method shiftWorldBackground pans around the background image based on the move x and y values
     *
     * @param moveX A parameter used to shift the image a certen direction moveY A parameter used to shift the image a certen direction
     * @return nothing is returnd
     */
    private void shiftWorldBackground(int moveX, int moveY) {
        GreenfootImage worldScreen = new GreenfootImage(WORLDWIDTH, WORLDHEIGHT);
        worldScreen.drawImage(backgroundImage, xImageOffset, yImageOffset);
        setBackground(worldScreen);
    }

    /**
     * Method shiftWorldActors moves the actors around the world based on the move x and y values
     *
     * @param moveX A parameter used to shift the actors a certen direction moveY A parameter used to shift the actors a certen direction
     * @return nothing is returnd
     */
    private void shiftWorldActors(int moveX, int moveY) {
        List<Alien> alienList = getObjects(Alien.class);
        for( Alien a : alienList ) {
            a.setAbsoluteLocation(moveX, moveY);
        }

        List<Cloud> cloudList = getObjects(Cloud.class);
        for( Cloud c : cloudList ) {
            c.setAbsoluteLocation(moveX, moveY);
        }

        List<Missile> missileList = getObjects(Missile.class);
        for( Missile m : missileList ) {
            m.setAbsoluteLocation(moveX, moveY);
        }
    }

    /**
     * checkLoss checks if you run out of health if you have it plays a sound and tells you you lost and then resets the world
     * 
     * @param nothing there are no parameters
     * @return nothing is returned
     */
    private void checkLoss()
    {
        if(healthBar.getCurrent() == 0)
        {
            Greenfoot.playSound("Gameover.mp3");
            removeObjects(getObjects(null));  
            saveData();
            setBackground("HikingWorldBackground.png");
            displayText(getBackground(), "YOU LOSE\nYour Score was\n" + Long.toString(score), Color.BLACK, 30, getWidth()/2, getHeight()/2);
            long timeOffset = System.currentTimeMillis() + 3000;
            while(System.currentTimeMillis() < timeOffset)
            {
                Greenfoot.delay(1);
            }
            Greenfoot.setWorld(new MyWorld());
        }
    }
    
    /**
     * checkHit checks if you hit an alien and destroys if if it is in range
     * 
     * @param Nothing there are no paramitors
     * @return nothing is returned
     */
    private void checkHit()
    {

        if(overlay.shotReloadCounter == 45 && selectedLaser == true)
        {
            List<Alien> hittingObjects = getObjectsAt(300, 145, Alien.class);
            if(getObjectsAt(300, 145, Missile.class).isEmpty() == false)
            {
                getObjectsAt(300, 145, Missile.class).get(0).explode = true;
            }
            if(hittingObjects.isEmpty() == false)
            {
                int size = hittingObjects.size();
                int tScore = 0;
                if(size >= 3)
                {
                    tScore = size * 50;
                }
                for(int i = 0; i < size; i++)
                {
                    int scale = hittingObjects.get(i).scale;
                    hittingObjects.get(i).explode = true;
                    if(scale < 20)
                    {
                        tScore = tScore + 20;
                    }
                    else if(scale < 30)
                    {
                        tScore = tScore + 15;
                    }
                    else if(scale < 40)
                    {
                        tScore = tScore + 10;
                    }
                    else
                    {
                        tScore = tScore + 5;
                    }
                }
                score = score + tScore;
            }
        }
        else if(overlay.shotReloadCounter == 250)
        {
            Alien explodeAlien = new Alien();
            addObject(explodeAlien, 300, 145);
            List<Alien> hittingObjects = explodeAlien.getRange(85);
            List<Missile> hitMissile = explodeAlien.getMissileRange(85);
            if(hitMissile.isEmpty() == false && hitMissile.get(0) != null)
            {
                hitMissile.get(0).explode = true;
            }
            removeObject(explodeAlien);
            if(hittingObjects.isEmpty() == false)
            {
                int size = hittingObjects.size();
                int tScore = 0;
                if(size >= 4)
                {
                    tScore = size * 25;
                }
                for(int i = 0; i < size; i++)
                {
                    int scale = hittingObjects.get(i).scale;
                    hittingObjects.get(i).explode = true;
                    if(scale < 20)
                    {
                        tScore = tScore + 20;
                    }
                    else if(scale < 30)
                    {
                        tScore = tScore + 15;
                    }
                    else if(scale < 40)
                    {
                        tScore = tScore + 10;
                    }
                    else
                    {
                        tScore = tScore + 5;
                    }
                }
                score = score + tScore;
            }
        }
    }

    /**
     * checkRound checks if the round is over and resets the world if it is
     * 
     * @param Nothing there are no paramitors
     * @return nothing is returned
     */
    private void checkRound()
    {
        if(getObjects(Alien.class).isEmpty())
        {   
            showText("next wave incomming", 300, 200);
            stageNumber++;
            long timeOffset = System.currentTimeMillis() + 2000;
            while(System.currentTimeMillis() < timeOffset)
            {
                Greenfoot.delay(1);
            }
            Greenfoot.setWorld(new MyWorld(healthBar.getCurrent(), score, stageNumber));
        }
    }

    /**
     * displayText overlays the text from the string over the image an the set size location and color
     * 
     * @param an image used to overlay the text a string with the text that should be displayed a color used to set the text an int used to set the size of the text and tow ints used to set the position
     * @return nothing is returned
     */
    private void displayText( GreenfootImage background, String display, Color color, int size, int x, int y)
    {
        GreenfootImage imgScore = new GreenfootImage(display,size, color, new Color(0, 0, 0, 0));
        x = x - imgScore.getWidth()/2;
        y = y - imgScore.getHeight()/2;
        background.drawImage(imgScore, x, y);
        setBackground(background);
    }

    /**
     * getData gets the high scores from the text file 
     * 
     * @pram no parameter
     * @return nothing is returned
     */
    private void getData()
    {
        FileInputStream fis;
        BufferedReader r = null;
        try {
            fis = new FileInputStream("game.txt");
            r = new BufferedReader(new InputStreamReader(fis));
            highScore = Long.parseLong(r.readLine()); 
        }
        catch( FileNotFoundException e1 )
        {
            e1.printStackTrace();
        }
        catch( IOException e2 )
        {
            e2.printStackTrace();
        }
    }

    /**
     * saveData if the current score is greater than one of the high scores it replaces it 
     * and shifts the others down and then saves the high scores to the text file
     * 
     * @pram no parameter
     * @return nothing is returned
     */
    private void saveData() 
    {
        if(score > highScore)
        {
            highScore = score;
        }
        try{
            FileWriter writer = null;
            writer = new FileWriter("game.txt");
            writer.write(Long.toString(highScore));
            /*
             * if i want to have a high score list
             * writer.write( System.getProperty("line.separator"));
             * writer.append(Long.toString(highScore[1]));
             * writer.write( System.getProperty("line.separator"));
             * writer.append(Long.toString(highScore[2]));
             * writer.write( System.getProperty("line.separator"));
             * writer.append(Long.toString(highScore[3]));
             * writer.write( System.getProperty("line.separator"));
             * writer.append(Long.toString(highScore[4]));
             * writer.write( System.getProperty("line.separator"));
             */
            writer.close();
        } 
        catch( IOException e )
        {
            e.printStackTrace();
        }
    }

    /**
     * addCloud adds a cloud to the world at the right side of the background image
     * 
     * @param Nothing there are no parameters
     * @return nothing is returned
     */
    public void addCloud()
    {
        addObject(new Cloud(Greenfoot.getRandomNumber(10) + 2), ((xImageOffset + BACKGROUNDWIDTH) + 600), Greenfoot.getRandomNumber(220) + 30);
    }

    /**
     * rocket adds a rocket to the world at a random location and at the bottom of the background image if the rocket was not destroyed by the player
     * 
     * @param Nothing there are no parameters
     * @return nothing is returned
     */
    public void rocket()
    {
        if(rocketDestroyed == false)
        {
            addObject(new Missile(),  map(Greenfoot.getRandomNumber(1000), 0, 999, leftEdge + 300, (xImageOffset + BACKGROUNDWIDTH)), 800);
        }
    }

    /**
     * map maps the input value to a new number between the output range
     * 
     * @param there are five ints the first is the input number the second is the minimum the input can be the third is the max the input can be the forth is the minimum the output should be and the fith is the max the output can be
     * @return an int that is the product of the equation
     */
    private int map(int x, int in_min, int in_max, int out_min, int out_max)
    {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }
}
