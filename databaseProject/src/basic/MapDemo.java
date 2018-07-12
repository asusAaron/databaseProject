package basic;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

public class MapDemo
{
    public static void main(String[] args)
    {
        parseKeyValue();
    }

    /**
     * 确定HashMap的初始大小
     */
    private static void initSize(int i){
        int initsize=(int)(i/0.75)+1;
        System.out.println(initsize);

    }

    /**
     * 根据HashMap的Entry获取键和值
     * 比传统的keySet方法效率更高
     */
    private static void parseKeyValue(){
        HashMap<String,String> data=new HashMap<>();
        data.put("pid", "1001");
        data.put("pname","王兴刚");
        data.put("psex", "1");
        data.put("pnumber", "110");
        data.put("pname", "老王");
        System.out.println(data);

        //获取Entry对象集合
        Set<Entry<String,String>> entrySet=data.entrySet();
        //循环遍历Set
        for (Entry<String,String> entry:entrySet)
        {
            //使用entry.getKey()和entry.getValue()获取键值对
            System.out.println(entry.getKey()+" ："+entry.getValue());
        }
    }

    /**
     * 传统的keySet获取键值对
     */
    private static void parseKeyValue_old()
    {
        //1.获取所有的键,形成集合,然后遍历集合,读取每个键名,通过键名获取对应值
        HashMap<String,String> data=new HashMap<>();
        data.put("pid", "1001");
        data.put("pname","王兴刚");
        data.put("psex", "1");
        data.put("pnumber", "110");
        data.put("pname", "老王");
        System.out.println(data);
        //获取所有的键
        Set<String> keyset= data.keySet();
        System.out.println("keyset="+keyset);

        //循环keySet
        for(String key:keyset)
        {
            System.out.println(key+" : "+data.get(key));
        }


    }
}
