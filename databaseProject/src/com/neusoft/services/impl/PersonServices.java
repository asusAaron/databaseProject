/**
 * Person增删改查操作类
 */
package com.neusoft.services.impl;

import com.neusoft.services.support.JdbcServicesSupport;
import com.neusoft.system.db.DBUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PersonServices extends JdbcServicesSupport {

    /**
     * 查询方法
     * @return List<Map<String,String>>
     * @throws Exception
     */
    @Override
    public List<Map<String,String>> query()throws Exception
    {
        PreparedStatement pstm=null;
        ResultSet rs=null;
        try
        {
            StringBuilder sql=new StringBuilder()
                    .append("select a.pid,a.pname,a.pnumber,a.pbirthday,a.psex,a.pnation,a.pstate")
                    .append("  from person a")
                    .append(" where pstate!=?");

            List<Object> list=new ArrayList<>();
            list.add("x");

            if(this.isNotNull(this.get("qpname")))
            {
                sql.append(" and a.pname like ?");
                list.add("%"+this.get("qpname")+"%");
            }
            if(this.isNotNull(this.get("qpnumber")))
            {
                sql.append(" and a.pnumber=?");
                list.add(this.get("qpnumber"));
            }
            if(this.isNotNull(this.get("qspbirthday")))
            {
                sql.append(" and a.pbirthday>=?");
                list.add(this.get("qspbirthday"));
            }
            if(this.isNotNull(this.get("qepbirthday")))
            {
                sql.append(" and a.pbirthday<=?");
                list.add(this.get("qepbirthday"));
            }
            if(this.isNotNull(this.get("qpsex")))
            {
                sql.append(" and a.psex=?");
                list.add(this.get("qpsex"));
            }
            if(this.isNotNull(this.get("qpnation")))
            {
                sql.append(" and a.pnation=?");
                list.add(this.get("qpnation"));
            }
            sql.append(" limit  10");

            //编译sql语句
            pstm=DBUtils.prepareStatement(sql.toString());
            //填数据
            int index=1;
            for(Object o:list)
            {
                pstm.setObject(index++,o);
            }
            //执行查询
            rs=pstm.executeQuery();

            List<Map<String,String>> mapList=new ArrayList<>();
            Map<String,String> map=null;

            //获取rsmd，用于获取列名和列长度
            ResultSetMetaData rsmd=rs.getMetaData();
            while (rs.next())
            {
                map=new HashMap<>();
                //计算列数并循环
                for (int i=1;i<=rsmd.getColumnCount();i++)
                {
                    //列名+值构成map的键值对
                    map.put(rsmd.getColumnLabel(i),rs.getString(i));
                }
                mapList.add(map);
            }
            return mapList;
        }
        finally
        {
            DBUtils.close(rs);
            DBUtils.close(pstm);
        }
    }

    /**
     * 使用map装载查询得到的结果集内容
     * 使用ResultSetMetaData获取列名
     * ...
     * @return --- 返回一个map
     */
    @Override
    public Map<String,String> findById()throws Exception
    {
        PreparedStatement pstm=null;
        ResultSet rs=null;
        try
        {
            StringBuilder sql=new StringBuilder()
                    .append("select pname,pnumber,psex,pbirthday,pnation,psal,pmemo")
                    .append("  from person")
                    .append(" where pid=?");
            //编译sql语句
            pstm=DBUtils.prepareStatement(sql.toString());
            Object id=this.get("pid");
            //填参数
            pstm.setObject(1,id);
            //执行查询
            rs=pstm.executeQuery();

            Map<String,String> map=null;
            if (rs.next())
            {
                //获取rsmd，用于获取列名和列长度
                ResultSetMetaData rsmd=rs.getMetaData();
                map=new HashMap<>();
                //计算列数并循环
                for (int i=1;i<=rsmd.getColumnCount();i++)
                {
                    //列名+值构成map的键值对
                    map.put(rsmd.getColumnLabel(i),rs.getString(i));
                }
            }
            return map;
        }
        finally
        {
            DBUtils.close(rs);
            DBUtils.close(pstm);
        }
    }

    @Override
    public boolean update(String uType)throws Exception
    {
        if(uType.equals("add"))
        {
            return this.addPerson();
        }
        else if (uType.equals("delete"))
        {
            return this.delPerson();
        }
        else if(uType.equalsIgnoreCase("modify"))
        {
            return this.updatePerson();
        }
        else
        {
            throw new Exception("获得未定义的更新类别指令[ "+uType+" ]");
        }
    }

    /**
     * 添加操作
     * 1.创建数据库连接
     * 2.定义sql语句
     * 3.编译sql语句
     * 4.参数赋值
     * 5.执行并返回值
     * @return 返回一个布尔值
     * @throws Exception
     */
    public boolean addPerson()throws Exception
    {
            StringBuilder sql=new StringBuilder()
                    .append("insert into person(pname,pnumber,psex,pbirthday,pnation,psal,pstate,pmemo)")
                    .append(" values(?,?,?,?,?,?,?,?);");
            Object val[]={this.get("pname"),this.get("pnumber"),this.get("psex"),this.get("pbirthday"),
                    this.get("pnation"),this.get("psal"),"1",this.get("pmemo")};
            return executeUpdate(sql.toString(),val)>0;
    }

    /**
     * 形如 “delete from table where id=？”
     * 的批处理操作调用此方法
     * @return boolean
     * @throws Exception
     */
    public boolean delPerson()throws Exception{
        String sql="delete from person where pid=?";
        Object idlist[]=this.getArray("idlist");
        return batchUpdate(sql,idlist);
    }


    /**
     * 修改数据
     * @return 返回一个布尔值
     * @throws Exception
     */
    public boolean updatePerson()throws Exception{
        StringBuilder sql=new StringBuilder()
                    .append("update person p")
                    .append(" set p.pname=?,p.pnumber=?,p.psex=?,p.pbirthday=?,p.pnation=?,p.psal=?,p.pmemo=?")
                    .append(" where p.pid=?");
        Object val[]={this.get("pname"),this.get("pnumber"),this.get("psex"),this.get("pbirthday"),
                this.get("pnation"),this.get("psal"),this.get("pmemo"),this.get("pid")};
        return executeUpdate(sql.toString(),val)>0;
    }

}
