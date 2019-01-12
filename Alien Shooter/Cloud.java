import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Cloud here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Cloud extends Actor
{
    private int speed = 1;
    private GreenfootImage cloudImage;
    private int loopCount = 0;
    /**
     * Cloud Constructor sets the move speed of the cloud to a random number and sets the image
     *
     * @param speedSet A parameter
     */
    public Cloud(int speedSet)
    {
        speed = speedSet;
        cloudImage = new GreenfootImage("cloud.png");
        int scale = Greenfoot.getRandomNumber(70) + 30;
        cloudImage.scale(scale * 2, scale);
        setImage(cloudImage);
    }

    /**
     * Act checks if it is at the side of the world is removes its self and adds a new one to the world
     * 
     * @param nothing there are no parameters
     * @return nothing is returned
     */
    public void act() 
    {
        MyWorld world = (MyWorld) getWorld();
        loopCount++;
        if(loopCount >= 10)
        {
            setLocation(getX() - speed, getY());
            loopCount = 0;
        }
        if(world.leftEdge > getX())
        {
            world.addCloud();
            world.removeObject(this);
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
