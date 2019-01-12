import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class gunSelector here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GunSelector extends Actor
{
    private GreenfootImage image;
    public boolean justChanged = false;
    
    /**
     * GunSelector sets and scales the image to the proper image
     * 
     *@param nothing there are no parmeters
     *@return nothing is returned
     */
    public GunSelector()
    {
        image = new GreenfootImage("grenade.png");
        image.scale( 50, 50);
        setImage(image);
    }

    /**
     * Act changes the image based on if you clicked on it
     * 
     * @param nothing there are no parameters
     * @return nothing is returned
     */
    public void act() 
    {
        justChanged = false;
        MyWorld world = (MyWorld) getWorld();
        if(Greenfoot.mouseClicked(this))
        {
            if(world.selectedLaser)
            {
                world.selectedLaser = false;
            }
            else
            {
                world.selectedLaser = true;
            }
            justChanged = true;
        }
        if(Greenfoot.isKeyDown("c"))
        {
            while(Greenfoot.isKeyDown("c"))
            {
            }
            if(world.selectedLaser)
            {
                world.selectedLaser = false;
            }
            else
            {
                world.selectedLaser = true;
            }
            justChanged = true;
        }
        image = new GreenfootImage(50, 50);
        if(world.selectedLaser == false)
        {
            image.setColor(new Color(83, 93, 69));
        }
        else
        {
            image.setColor(Color.GRAY);
        }
        image.fillRect(0, 0, 50, 50);
        if(world.selectedLaser)
        {
            image.drawImage(new GreenfootImage("grenade.png"), 0, 0);
        }
        else
        {
            image.drawImage(new GreenfootImage("laser.png"), 0, 0);
        }
        setImage(image);
        Greenfoot.delay(5);
    }    
}
