package test;

import com.neuedu.services.PersonServices;

public class PersonServicesTest {
    private static PersonServices ps=new PersonServices();
    public static void main(String[] args) {
        try{
//            addPerson();
//            updatePerson();
//            delPerson();
//            delBatchPerson();
                updateBatchPerson();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void updateBatchPerson()throws Exception{
        String stateList[][] ={{"lvchao","4396"},{"gouxiaosen","2211"},{"gong","666"}};
        String idlist[]={"39","40","41"};
        String msg=ps.batchUpdate(stateList,idlist)?"Update batch suc!":"Update batch fai!";
        System.out.println(msg);
    }

    private static void updateSigleStateBatchPerson()throws Exception{
        String stateList[] ={"psp","4396"};
        String idlist[]={"31","37","38"};
        String msg=ps.batchUpdate(stateList,idlist)?"Update batch suc!":"Update batch fai!";
        System.out.println(msg);
    }

    private static void delBatchPerson()throws Exception{
        String val[]={"12","22","16"};
        String msg=ps.batchDelete(val)?"Del batch suc!":"Del batch fai!";
        System.out.println(msg);
    }

    private static void addPerson()throws Exception{
        String val[]={"gpd","2015302580276","3","1998-01-02","1","200022","1","big gay"};
        String msg=ps.addPerson(val)?"Add successfully!":"Add falied!";
        System.out.println(msg);
    }

    private static void delPerson()throws Exception{
        String id="2";
        String msg=ps.delPerson(id)?"Del successfully!":"Del failed!";
        System.out.println(msg);
    }

    private static void updatePerson()throws Exception{
        String val[]={"gps","20010101","211113","1"};
        String msg=ps.updatePerson(val)?"Update successfully!":"Update failed!";
        System.out.println(msg);
    }
}
