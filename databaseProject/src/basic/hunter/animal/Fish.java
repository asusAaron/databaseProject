package basic.hunter.animal;

public abstract class Fish extends Animal
{
    public Fish(String name){
        super(name);
    }

    @Override
    public void action()
    {
        System.out.println("swim...");
    }
}
