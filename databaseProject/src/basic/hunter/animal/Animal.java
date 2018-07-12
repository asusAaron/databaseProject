package basic.hunter.animal;

public abstract class Animal implements Actions
{
    private String name;

    public Animal(String name){
        this.name=name;
    }

    @Override
    public String getName()
    {
        return this.name;
    }
}
