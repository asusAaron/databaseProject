package basic.hunter;

import basic.hunter.animal.FlyFish;
import basic.hunter.animal.GoldFish;
import basic.hunter.animal.Tiger;
import basic.hunter.people.Hunter;

public class Test
{
    public static void main(String[] args)
    {
        Tiger tiger=new Tiger("龚宇祥");
        GoldFish fish=new GoldFish("吕超");
        FlyFish flyFish=new FlyFish("狗小森");
        Hunter hunter=new Hunter();
        hunter.kill(tiger);
        hunter.kill(fish);
        hunter.kill(flyFish);
    }
}
