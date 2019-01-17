import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * Write a description of class MinMap here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MinMap extends Actor
{
    private GreenfootImage background;
    private GreenfootImage tank;
    
    
    /**
     * MinMap Constructor sets the image fot the object
     *
     *@param nothing there are no parameters
     *@return nothing is retuurned
     */
    public MinMap()
    {
        //background = new GreenfootImage(150, 150);
        background = new GreenfootImage("radar1.png");
        background.scale(150, 150);
        background.setColor(Color.BLACK);
        //background.fillRect( 0, 0, 200, 200);
        setImage(background);
        tank = new GreenfootImage("arrow.png");
        tank.scale(10, 20);
    }

    /**
     * Act puts a square on the image for each alien in the world and rotates the arrow based on the turret rotation
     * 
     * @param nothing there are no parameters
     * @return nothing is returned
     */
    public void act() 
    {
        background.clear();
        background.setColor(Color.BLACK);
        //background.fillRect( 0, 0, 200, 200);
        background = new GreenfootImage("radar1.png");
        background.scale(150, 150);
        List<Alien> alienObjects = getWorld().getObjects(Alien.class);
        List<Enemy> enemyObjects = getWorld().getObjects(Enemy.class);
        getWorld().removeObjects(enemyObjects);
        int forLoopCount = alienObjects.size();
        for(int i = 0; i < forLoopCount; i++)
        {
            Alien alien = alienObjects.get(i);
            Enemy enemy = new Enemy();
            GreenfootImage icon = new GreenfootImage(3, 3);
            icon.setColor(Color.GREEN);
            icon.fillRect( 0, 0, 3, 3);

            int yOffset =  map(alien.startingX, -750, 1050, -20, 20);
            int rotation =  map(alien.startingX, -750, 1050, -28, 28);
            if(yOffset < 0 && yOffset > -3)
            {
                yOffset = -4;
            }
            if(yOffset > 0 && yOffset < 3)
            {
                yOffset = 4;
            }
            if(yOffset < 0)
            {
                yOffset = yOffset * -1;
            }

            getWorld().addObject( enemy, map(alien.startingX, -750, 1050, 375, 525), 240 + yOffset); 
            enemy.setRotation(rotation + 90);
            enemy.move(alien.scale * 2);
            if(alien.scale == 50 && alien.loopCount <= 2)
            {
                icon.setColor(Color.RED);
            }
            else
            {
                icon.setColor(Color.GREEN);
            }
            icon.fillRect( 0, 0, 3, 3);
            enemy.setImage(icon);
        }
        
        List<Missile> missileObjects = getWorld().getObjects(Missile.class);
        if(missileObjects.isEmpty() == false)
        {
            int j = -1;
            do
            {
                j++;
                if(j == missileObjects.size())
                {
                    j = 0;
                }
            }while(missileObjects.get(j) == null);
            Missile missile = missileObjects.get(j);
            Enemy enemyTwo = new Enemy();
            GreenfootImage iconTwo = new GreenfootImage(3, 3);
            iconTwo.setColor(Color.GREEN);
            iconTwo.fillRect( 0, 0, 3, 3);

            int yOffsetTwo =  map(missile.startingX, -750, 1050, -20, 20);
            int rotationTwo =  map(missile.startingX, -750, 1050, -28, 28);
            if(yOffsetTwo < 0 && yOffsetTwo > -3)
            {
                yOffsetTwo = -4;
            }
            if(yOffsetTwo > 0 && yOffsetTwo < 3)
            {
                yOffsetTwo = 4;
            }
            if(yOffsetTwo < 0)
            {
                yOffsetTwo = yOffsetTwo * -1;
            }

            getWorld().addObject( enemyTwo, map(missile.startingX, -750, 1050, 375, 525), 240 + yOffsetTwo); 
            iconTwo.setColor(Color.BLUE);
            iconTwo.fillRect( 0, 0, 3, 3);
            enemyTwo.setImage(iconTwo);
        }
        
        MyWorld world = (MyWorld) getWorld();
        tank = new GreenfootImage("arrow.png");
        tank.scale(10, 20);
        tank.rotate(map(world.xImageOffset, -1800, 0, -38, 35) * -1);
        background.drawImage(tank, 70, 125);
        setImage(background);
    }    

    /**
     * Method map maps the input to between the minimun output value and the maximum output value
     *
     * @param x A parameter that is the input value for the equasion
     * @param in_min A parameter the minimum value the input can be
     * @param in_max A parameter the maximum value the input can be
     * @param out_min A parameter the minimum value the output can be
     * @param out_max A parameter the maximum value the output can be
     * @return The return value is the result of the equasion
     */
    private int map(int x, int in_min, int in_max, int out_min, int out_max)
    {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }
}
