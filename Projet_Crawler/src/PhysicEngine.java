import java.util.ArrayList;

public class PhysicEngine implements Engine{

    ArrayList<Sprite> environement;

    ArrayList<DynamicSprite> movingSpriteList;

    public PhysicEngine() {
        movingSpriteList = new ArrayList<>();
        environement = new ArrayList<>();
    }

    public void addToMovingSpriteList(DynamicSprite sprite){
        if (!movingSpriteList.contains(sprite)){
            movingSpriteList.add(sprite);
        }
    }

    public void setEnvironement(ArrayList<Sprite> environement) {
        this.environement = environement;
    }

    @Override
    public void update() {
        for (DynamicSprite dynamicSprite : movingSpriteList) {
            System.out.println("Updating sprite at position: " + dynamicSprite.x + ", " + dynamicSprite.y);

            dynamicSprite.moveIfPossible(environement);
        }
    }


}
