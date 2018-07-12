package basic.hunter.animal;

public class GoldFish extends Fish
{
    public GoldFish(String name){
        super(name);
    }

    @Override
    public void builder()
    {
        System.out.println("in the sea....");
    }
}
