package blueduck.mysticalpumpkins.block;

public class LuminousMysticalPumpkinBlock extends MysticalPumpkinBlock {
    int l;
    public LuminousMysticalPumpkinBlock(int light) {
        super();
        l = light;
    }

    public int getLightValue() {
        return l;
    }
}
