import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * Write a description of class Rocket here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Missile extends Actor
{
    private GreenfootImage missile;
    public int startingX = 0;
    public boolean explode = false;
    private int explodeCount = 0;
    private GifImage explodeImage;
    private boolean justAdded = true;
    private boolean hitAlien = false;
    
    /**
     * Missile Constructor sets the image for the missle
     * 
     * @param nothing there are no parameters
     * @return nothing is returned
     */
    public Missile()
    {
        missile = new GreenfootImage("missile.png");
        missile.scale(25, 50);
        setImage(missile);
    }

    /**
     * Act checks if it has hit an aliean if it has it explodes the aliean and then explodes its self
     * 
     * @param nothing there are no parameters
     * @return nothing is returned
     */
    public void act() 
    {
        MyWorld world = (MyWorld) getWorld();
        if(justAdded == true)
        {
            startingX = getX();
            justAdded = false;
        }
        setLocation(getX(), getY() - 4);
        if(getIntersectingObjects(Alien.class).isEmpty() == false)
        {
                for(int j = 0; j < getIntersectingObjects(Alien.class).size(); j++)
                {
                    getIntersectingObjects(Alien.class).get(j).explode = true;
                }
                hitAlien = true;
        }
        if(hitAlien == true)
        {
            if(explodeCount == 0)
            {
                explodeImage = new GifImage("explosionv2.gif");
            }
            missile = new GreenfootImage(explodeImage.getCurrentImage());
            missile.scale(100, 100);
            setImage(missile);
            explodeCount++;
            if(explodeCount == 10)
            {
                Greenfoot.playSound("bomb.wav");
                getWorld().removeObject(this);
            }
        }
        else if(explode == true)
        {
            if(explodeCount == 0)
            {
                explodeImage = new GifImage("explosionv2.gif");
                getWorld().getObjects(HealthBar.class).get(0).add(-100);
                world.rocketDestroyed = true;
            }
            missile = new GreenfootImage(explodeImage.getCurrentImage());
            missile.scale(100, 100);
            setImage(missile);
            explodeCount++;
            if(explodeCount == 10)
            {
                Greenfoot.playSound("bomb.wav");
                getWorld().removeObject(this);
            }
        }
        else if(getY() < 3)
        {
            getWorld().removeObject(this);
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
}
