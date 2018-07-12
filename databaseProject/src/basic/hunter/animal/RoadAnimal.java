package basic.hunter.animal;

public abstract class RoadAnimal extends Animal
{
    public RoadAnimal(String name){
        super(name);
    }

    @Override
    public void action()
    {
        System.out.println("run...");
    }
}
