import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * Write a description of class Alien here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Alien extends Actor
{
    private GreenfootImage alien;
    public int scale = 10;
    private int scaleSpeed = Greenfoot.getRandomNumber(50) + 30;
    public int loopCount = 0;
    private boolean shot = false;
    private int shotTimer = 0;
    public int startingX = 0;
    public boolean explode = false;
    private int explodeCount = 0;
    private GifImage explodeImage;
    private String image = "";
    
    /**
     * Alien sets the image to a random image
     * 
     * @parameter nothing there are no parmeters
     */
    public Alien()
    {
        int imageNum = Greenfoot.getRandomNumber(3);
        if(imageNum == 0)
        {
            image = "UFO.png";
        }
        else if(imageNum == 1)
        {
            image = "UFO 2.png";
        }
        else
        {
            image = "UFO 3.png";
        }
        alien = new GreenfootImage(image);
        alien.scale(scale * 2, scale);
        setImage(alien);
    }

    /**
     * Act first scales up the image and once it is at full scale it starts shooting and if it has been shot it runs an explode animation
     * 
     * @parameter nothing there are no parameters
     * @return nothing is returned
     */
    public void act() 
    {
        MyWorld world = (MyWorld) getWorld();
        if(explode == false)
        {
            if(loopCount == 0 && scale == 10)
            {
                startingX = getX();
                int speedAddition = (world.stageNumber/2) * 5;
                scaleSpeed += speedAddition;
            }
            loopCount++;
            if(shot == false)
            {
                if(scaleSpeed == loopCount)
                {
                    loopCount = 0;
                    if(scale < 50)
                    {
                        scale++;
                    }
                    else
                    {
                        shot = true; 
                    }
                }
                alien = new GreenfootImage(image);
                alien.scale(scale * 2, scale);
                setImage(alien);
            }
            else
            {
                alien = new GreenfootImage(image);
                alien.scale(scale * 2, scale);
                setImage(alien);
                if(shotTimer <= loopCount)
                {
                    Greenfoot.playSound("Alien Gunv2.wav");
                    HealthBar tankHealth = getWorld().getObjects(HealthBar.class).get(0);
                    loopCount = 0;
                    getImage().setColor(Color.RED);
                    getImage().fillOval( getImage().getWidth()/2, getImage().getHeight() - 10, 10, 10);
                    shotTimer = Greenfoot.getRandomNumber(100) + 150;
                    int attackDamage = 5 + (world.stageNumber/2);
                    tankHealth.add(-attackDamage);
                }
            }
        }
        else
        {
            //alien = new GreenfootImage("UFODie.png");
            if(explodeCount == 0)
            {
                explodeImage = new GifImage("explosionv2.gif");
            }
            alien = new GreenfootImage(explodeImage.getCurrentImage());
            alien.scale(scale * 2, scale);
            setImage(alien);
            explodeCount++;
            if(explodeCount == 10)
            {
                Greenfoot.playSound("bomb.wav");
                getWorld().removeObject(this);
            }
        }
    }  
    
    /**
     * Method setAbsoluteLocation set the location of this object based on the move x and y values
     *
     * @param moveX A parameter used to set the x location
     * @param moveY A parameter used to set the y location
     * @return nothing is returned
     */
    public void setAbsoluteLocation(int moveX, int moveY) {
        setLocation(getX() + moveX, getY() + moveY);
    }
    
    /**
     * Method getRange gets all of the Aliens in the spesified range
     *
     * @param range A parameter used to set the range that it gets the aliean
     * @return returns a list of all of the nearby aliens
     */
    public List getRange(int range)
    {
        List<Alien> objetctsInRange = getObjectsInRange(range, Alien.class);
        return objetctsInRange;
    }
    
    /**
     * Method getRange gets all of the missles in the spesified range
     *
     * @param range A parameter used to set the range that it gets the missle
     * @return returns a list of all of the nearby missles
     */
    public List getMissileRange(int range)
    {
        List<Missile> objetctsInRange = getObjectsInRange(range, Missile.class);
        return objetctsInRange;
    }
}
