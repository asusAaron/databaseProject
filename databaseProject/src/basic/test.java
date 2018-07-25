package basic;

import java.util.*;

public class test
{
    public test(String s,String s1)
    {
        
    }

    public static void main(String[] args)
    {
        Integer a=3333;
        Integer b=3333;
        System.out.println("Integer :"+(a==b));
        a=33;
        b=33;
        System.out.println("New Integer :"+(a==b));
        test1();
        test2();
    }

    public static void test1()
    {
        List<Map<String,String>> list=new ArrayList<>();
        Map<String,String> map=new HashMap<>();
        map.put("gay","gyx");
        list.add(map);
        map=new HashMap<>();
        map.put("gay","lc");
        list.add(map);
        System.out.println(list.get(0));

    }

    public static void test2()
    {
        short a=128;
        byte b=(byte)a;
        System.out.println(a);
        System.out.println(b);
    }


}
