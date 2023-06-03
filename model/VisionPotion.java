package model;

public class VisionPotion extends Potion {
    public VisionPotion() {
        super("Vision Potion");
    }
    @Override
    public void use(final Hero theHero) {
        theHero.removeFromInventory(this);
    }
}
