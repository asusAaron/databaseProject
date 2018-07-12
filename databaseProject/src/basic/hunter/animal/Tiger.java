package basic.hunter.animal;

public class Tiger extends RoadAnimal
{
    public Tiger(String name){
        super(name);
    }

    @Override
    public void builder()
    {
        System.out.println("on the road...");
    }
}
