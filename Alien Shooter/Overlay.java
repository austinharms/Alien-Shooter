import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class overlay here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Overlay extends Actor
{
    private GreenfootImage tankOverlayImage;
    private int xPoints[] = {290, 310, 330, 270};
    private int yPoints[] = {170, 170, 400, 400};
    public int shotReloadCounter = 0;
    private int xShotPoints[] = {298, 302, 305, 295};
    private int yShotPoints[] = {145, 145, 170, 170};
    private int bombExplodeCount = 0;
    private GifImage explodeImage = new GifImage("explosionv2.gif");
    /**
     * Overlay Constructor makes a new image and draws out the images on the object and sets the image of the object to the new image
     *
     *@param nothing there are no parameters
     *@return nothing is returned
     */
    public Overlay() 
    {
        tankOverlayImage = new GreenfootImage("tank overlay v2.png");
        tankOverlayImage.setColor(Color.WHITE);
        tankOverlayImage.drawOval(300 - 5, 140, 10, 10);
        tankOverlayImage.setColor(new Color(83, 93, 69));
        tankOverlayImage.fillPolygon(xPoints, yPoints, 4);
        tankOverlayImage.drawImage(new GreenfootImage("tank overlay v2.png"), 0, 0);
        setImage(tankOverlayImage);
    }

    
    /**
     * act updates the shot reload counter and sets the proper gun icon for the user
     *
     */
    public void act() {
        if(bombExplodeCount > 0)
        {
            explodeBomb();
        }
        else
        {
            updateImage();
            MyWorld world = (MyWorld) getWorld();
            
            if(world.selectedLaser == true && getWorld().getObjects(GunSelector.class).get(0).justChanged == true && shotReloadCounter > 45)
            {
                shotReloadCounter = 44;
            }
            
            
            if(world.selectedLaser == false && getWorld().getObjects(GunSelector.class).get(0).justChanged == true && shotReloadCounter == 0)
            {
                shotReloadCounter = shotReloadCounter + 175;
            }
            
            if(shotReloadCounter != 0)
            {
                shotReloadCounter--;
            }
            if(shotReloadCounter < 0)
            {
                shotReloadCounter = 0;
            }
            if(world.selectedLaser == false && shotReloadCounter > 250)
            {
                shotReloadCounter = 250;
            }
            if(world.selectedLaser == true && shotReloadCounter > 45)
            {
                shotReloadCounter = 45;
            }
            if(Greenfoot.isKeyDown("space") && world.selectedLaser == true && shotReloadCounter == 0)
            {
                shot();
                Greenfoot.delay(15);
                updateImage();
                shotReloadCounter = 45;
            }
            else if(Greenfoot.isKeyDown("space") && world.selectedLaser == false && shotReloadCounter == 0)
            {
                Greenfoot.playSound("bomb launcer.wav");
                bombExplodeCount = 7;
                shotReloadCounter = 250;
            }
        }
    }

    
    /**
     * Method shot draws the laserbeam on the screen
     *
     *@param nothing there are no parameters
     *@return nothing is returned
     */
    private void shot()
    {
        Greenfoot.playSound("Laser Cannon.wav");
        getImage().setColor(Color.RED);
        getImage().fillPolygon(xShotPoints, yShotPoints, 4);
    }

    /**
     * Method explodeBomb draws the expolsion gif onto the screen and updates the user interface
     *
     *@param nothing there are no parameters
     *@return nothing is returned
     */
    private void explodeBomb()
    {
        if(bombExplodeCount == 7)
        {
            explodeImage = new GifImage("explosionv2.gif");
        }
        bombExplodeCount--;
        GreenfootImage explosion = new GreenfootImage(explodeImage.getCurrentImage());
        explosion.scale(150, 150);
        MyWorld world = (MyWorld) getWorld();
        tankOverlayImage = new GreenfootImage(world.getWidth(), world.getHeight());
        tankOverlayImage.drawImage(explosion, 230, 60);
        tankOverlayImage.drawImage(new GreenfootImage("tank overlay v2.png"), 0, 0);
        tankOverlayImage.setColor(Color.WHITE);
        if(world.selectedLaser)
        {
            tankOverlayImage.drawOval(295, 140, 10, 10);
            tankOverlayImage.setColor(new Color(83, 93, 69));
        }
        else
        {
            tankOverlayImage.drawOval(225, 60, 150, 150);
            tankOverlayImage.setColor(Color.GRAY);
        }
        tankOverlayImage.fillPolygon(xPoints, yPoints, 4);
        tankOverlayImage.drawImage(new GreenfootImage("tank overlay v2.png"), 0, 0);
        if(shotReloadCounter == 0)
        {
            tankOverlayImage.setColor(Color.GREEN);
            tankOverlayImage.fillRect(130, 240, 100, 20);
            displayText(tankOverlayImage, "Ready", Color.BLACK, 20, 180, 250);
        }
        else
        {
            tankOverlayImage.setColor(Color.RED);
            tankOverlayImage.fillRect(130, 240 + (shotReloadCounter/12), 100, 20 - (shotReloadCounter/12));
            displayText(tankOverlayImage, "Reloading", Color.BLACK, 20, 180, 250);
        }
        setImage(tankOverlayImage);
        displayText(getImage(), "Your Score is\n" + Long.toString(world.score), Color.WHITE, 30, 150, (world.getHeight()/10) * 9);
        displayText(getImage(), "High Score: " + Long.toString(world.highScore), Color.WHITE, 40, world.getWidth()/2, 20);
        Greenfoot.delay(5);
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
        setImage(background);
    }
    
    /**
     * updateImage makes a new image and draws out the images on the object and sets the image of the object to the new image with current information as a graphic display
     * if bomb explode count is less then o is runs the explode bomb method
     *
     *@param nothing there are no parameters
     *@return nothing is returned
     */
    private void updateImage()
    {
        MyWorld world = (MyWorld) getWorld();
        tankOverlayImage = new GreenfootImage("tank overlay v2.png");
        tankOverlayImage.setColor(Color.WHITE);
        if(world.selectedLaser)
        {
            tankOverlayImage.drawOval(295, 140, 10, 10);
            tankOverlayImage.setColor(new Color(83, 93, 69));
        }
        else
        {
            tankOverlayImage.drawOval(225, 60, 150, 150);
            tankOverlayImage.setColor(Color.GRAY);
        }
        tankOverlayImage.fillPolygon(xPoints, yPoints, 4);
        tankOverlayImage.drawImage(new GreenfootImage("tank overlay v2.png"), 0, 0);
        if(shotReloadCounter == 0)
        {
            tankOverlayImage.setColor(Color.GREEN);
            tankOverlayImage.fillRect(130, 240, 100, 20);
            displayText(tankOverlayImage, "Ready", Color.BLACK, 20, 180, 250);
        }
        else if(world.selectedLaser == true)
        {
            tankOverlayImage.setColor(Color.RED);
            tankOverlayImage.fillRect(130, 240 + (shotReloadCounter/2), 100, 20 - (shotReloadCounter/2));
            displayText(tankOverlayImage, "Reloading", Color.BLACK, 20, 180, 250);
        }
        else
        {
            tankOverlayImage.setColor(Color.RED);
            tankOverlayImage.fillRect(130, 240 + (shotReloadCounter/12), 100, 20 - (shotReloadCounter/12));
            displayText(tankOverlayImage, "Reloading", Color.BLACK, 20, 180, 250);
        }
        setImage(tankOverlayImage);
        displayText(getImage(), "Your Score is\n" + Long.toString(world.score), Color.WHITE, 30, 150, (world.getHeight()/10) * 9);
        displayText(getImage(), "High Score: " + Long.toString(world.highScore), Color.WHITE, 40, world.getWidth()/2, 20);

    }
}
