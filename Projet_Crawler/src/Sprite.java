import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Sprite implements Displayable {
    protected double x;
    protected double y;
    protected final Image image;
    protected final double width;
    protected final double height;
    protected boolean isTrap = false; // Indique si ce Sprite est un piège
    protected int damage = 0;
    protected boolean isExit = false;

    public boolean isExit() {
        return isExit;
    }

    public void setExit() {
        this.isExit = true;
    }


    public Sprite(double x, double y, Image image, double width, double height) {
        this.x = x;
        this.y = y;
        this.image = image;
        this.width = width;
        this.height = height;
    }

    public boolean isTrap() {
        return isTrap;
    }

    public void setTrap(int damage) {
        this.isTrap = true;
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public Rectangle2D getHitBox() {
        return new Rectangle((int) x, (int) y, (int) width, (int) height);
    }

    @Override
    public void draw(Graphics g) {

        g.drawImage(image, (int) x, (int) y, null);
        if (isTrap) {
            g.setColor(Color.RED);
            Rectangle2D hitBox = getHitBox();
            g.drawRect((int) hitBox.getX(), (int) hitBox.getY(), (int) hitBox.getWidth(), (int) hitBox.getHeight());
        }

    }
}
