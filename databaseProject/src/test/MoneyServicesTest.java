package test;

import com.neuedu.services.MoneyServices;

public class MoneyServicesTest {
    private static MoneyServices ms=new MoneyServices();
    public static void main(String[] args){
        try {
            addMoney();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void addMoney()throws Exception{
        String val[]={"aeo","36500"};
        String str=ms.addMoney(val)?"Add money suc!":"Add money fai!";
        System.out.println(str);
    }
}
