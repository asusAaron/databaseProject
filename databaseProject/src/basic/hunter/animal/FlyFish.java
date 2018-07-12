package basic.hunter.animal;

public class FlyFish extends Fish
{
    public FlyFish(String name){
        super(name);
    }

    @Override
    public void builder()
    {
        System.out.println("in the sky?...");
    }
}
