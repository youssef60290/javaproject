import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class GameEngine  implements Engine, KeyListener{
    DynamicSprite hero;

    public GameEngine(DynamicSprite hero) {
        this.hero = hero;
    }

    @Override
    public void update() {

    }

    @Override
    public void keyTyped(KeyEvent e) {


    }

    @Override
    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_Z:
                
                hero.setDirection(Direction.NORTH);
                break;
            case KeyEvent.VK_S:
                hero.setDirection(Direction.SOUTH);
                break;
            case KeyEvent.VK_Q:
                hero.setDirection(Direction.WEST);
                break;
            case KeyEvent.VK_D:
                hero.setDirection(Direction.EAST);
                break;
            case KeyEvent.VK_CONTROL:
                hero.speed = 15;
                break;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
            hero.speed = 5;
        }
    }


}
