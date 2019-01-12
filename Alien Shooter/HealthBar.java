import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class HealthBar here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class HealthBar extends Actor
{
    private final int WIDTH = 250;
    private final int HEIGHT = 60;

    private GreenfootImage frame;
    private GreenfootImage healthBar;

    private Color good;
    private Color warning;
    private Color danger;

    private int target;
    private int current;
    private int max;
    private int speed = 10;

    /**
     * Default constructor for objects of the HealthBar class
     * 
     * @param There are no parameters
     * @return an object of the HealthBar type
     */
    public HealthBar()
    {
        frame = new GreenfootImage(WIDTH, HEIGHT);
        healthBar = new GreenfootImage(WIDTH, HEIGHT);

        frame.setColor( Color.GRAY );
        frame.fillRect(0, 0, WIDTH, HEIGHT);

        good = Color.GREEN;
        warning = Color.YELLOW;
        danger = Color.RED;

        max = 1000;
        current = 600;
        target = current;
        speed = 1;

        updateBar();
    }

    /**
     * Constructor for objects of the HealthBar class that allows for customization
     * 
     * @param c is the current value of health
     * @param m is the maximum health amount
     * @param s is the speed the health bar's health with change at
     * @return an object of the HealthBar type
     */
    public HealthBar(int c, int m)
    {

        frame = new GreenfootImage(WIDTH, HEIGHT);
        healthBar = new GreenfootImage(WIDTH, HEIGHT);

        frame.setColor( Color.GRAY );
        frame.fillRect(0, 0, WIDTH, HEIGHT);

        good = Color.GREEN;
        warning = Color.YELLOW;
        danger = Color.RED;
        if(c > m)
        {
         c = m;   
        }
        max = m;
        current = c;
        target = current;

        updateBar();
    }

    /**
     * Act - do whatever the HealthBar wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if( current > target )
        {
            current -= speed;

            if( current < target )
            {
                current = target;
            }
        }
        else
        {
            current += speed;

            if( current > target )
            {
                current = target;
            }
        }

        updateBar();
    }

    /**
     * updateBar updates the image of the health bar when the current health
     * changes values
     * 
     * @param There are no parameters
     * @return Nothing is returned
     */
    private void updateBar()
    {
        GreenfootImage text = new GreenfootImage(WIDTH, HEIGHT);
        double ratio = (double)current / (double)max;
        int healthWidth = (int)Math.round(ratio*frame.getWidth());
        
        if( current > max / 2 )
        {
            healthBar.setColor( good );
        }
        else if( current > max / 4 )
        {
            healthBar.setColor( warning );
        }
        else
        {
            healthBar.setColor( danger );
        }
        
        healthBar.clear();
        healthBar.fillRect( 0, 0, healthWidth, HEIGHT );
        
        text.clear();
        text.setColor( Color.BLACK );
        text.setFont( new Font( "Times New Roman", false, false, 19 ) );
        text.drawString("Current Turret Health\n      " + current + " / " + max, 0, 30);
        
        frame.clear();
        frame.setColor( Color.GRAY );
        frame.fillRect(0, 0, WIDTH, HEIGHT);
        frame.drawImage( healthBar, 0, 0 );
        frame.drawImage( text, WIDTH/3, 0 );

        setImage( frame );
    }

    /**
     * add will change the current value of the health in the health bar
     * 
     * @param change is the amount that the current health will be changed by
     * @return Nothing is returned
     */
    public void add( int change )
    {
        target += change;

        if( target > max )
        {
            target = max;
        }

        if( target < 0 )
        {
            target = 0;
        }
    }

    /**
     * setCurrent will change the current value to whatever the user chooses
     * 
     * @param c is the new, user-chosen current value
     * @return Nothing is returned
     */
    public void setCurrent( int c )
    {
        current = c;
    }

    /**
     * getCurrent returns the current health value for use in other sections of code or for the user's information
     * 
     * @param There are no parameters
     * @return an integer representing the current health value
     */
    public int getCurrent()
    {
        return current;
    }
}
