import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class DynamicSprite extends SolidSprite{
    boolean isWalking = true;
    double speed = 5;
    final int spriteSheetNumberOfColumns = 4;
    double timeBetweenFrame = 50;
    private Direction direction = Direction.EAST;
    private int health, maxHealth;

    public DynamicSprite(double x, double y, Image image, double width, double height) {
        super(x, y, image, width, height);
        this.maxHealth = 100; // Santé maximale
        this.health = maxHealth;
    }


    private void drawHealthBar(Graphics g) {
        int barWidth = (int) width;
        int barHeight = 10;
        int barX = (int) x;
        int barY = (int) (y - 15);


        int healthBarWidth = (int) ((double) health / maxHealth * barWidth);


        g.setColor(Color.RED);
        g.fillRect(barX, barY, barWidth, barHeight);


        g.setColor(Color.GREEN);
        g.fillRect(barX, barY, healthBarWidth, barHeight);


        g.setColor(Color.BLACK);
        g.drawRect(barX, barY, barWidth, barHeight);
    }

    public void takeDamage(int damage) {
        health = Math.max(0, health - damage);
    }


    public void heal(int amount) {
        health = Math.min(maxHealth, health + amount);
    }

    private boolean isMovingPossible(ArrayList<Sprite> environment){
        Rectangle2D.Double moved = new Rectangle2D.Double();
        switch(direction){
            case EAST: moved.setRect(super.getHitBox().getX()+speed,super.getHitBox().getY(),
                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case WEST:  moved.setRect(super.getHitBox().getX()-speed,super.getHitBox().getY(),
                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case NORTH:  moved.setRect(super.getHitBox().getX(),super.getHitBox().getY()-speed,
                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case SOUTH:  moved.setRect(super.getHitBox().getX(),super.getHitBox().getY()+speed,
                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
        }

        for (Sprite s : environment){
            if ((s instanceof SolidSprite) && (s!=this)){
                if (((SolidSprite) s).intersect(moved)){
                    return false;
                }
            }
        }
        return true;
    }
    public void setDirection(Direction direction) {
        this.direction = direction;
    }
    private void move(){
        switch (direction){
            case NORTH -> {
                this.y-=speed;
            }
            case SOUTH -> {
                this.y+=speed;
            }
            case EAST -> {
                this.x+=speed;
            }
            case WEST -> {
                this.x-=speed;
            }
        }
    }


    public void moveIfPossible(ArrayList<Sprite> environment) {
        if (isMovingPossible(environment)) {
            move();
        }


        for (Sprite s : environment) {
            if (s.isTrap() && s.getHitBox().intersects(this.getHitBox())) {
                takeDamage(s.getDamage());
                System.out.println("Hero stepped on a trap! Health: " + health);
            }
        }
    }









    @Override
    public void draw(Graphics g) {
        int index= (int) (System.currentTimeMillis()/timeBetweenFrame%spriteSheetNumberOfColumns);

        g.drawImage(image,(int) x, (int) y, (int) (x+width),(int) (y+height),
                (int) (index*this.width), (int) (direction.getFrameLineNumber()*height),
                (int) ((index+1)*this.width), (int)((direction.getFrameLineNumber()+1)*this.height),null);

        drawHealthBar(g);
    }

}
