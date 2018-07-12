package basic.hunter.people;

import basic.hunter.animal.Actions;
import basic.hunter.animal.Animal;

public class Hunter
{
    public void kill(Animal animal)
    {
        System.out.println("kill" + animal.getName());
        animal.action();
        animal.builder();
    }
}
