package com.neuedu.services;

import com.neuedu.services.support.JdbcServicesSupport;
import com.neuedu.system.db.DBUtils;
import com.neuedu.system.tools.Tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class MoneyServices extends JdbcServicesSupport
{
    /**
     * add操作，通知插入三张表，用外键关联，使用事务机制
     * 第二版：修改后只需要定义sql语句，
     * 并将参数值作为数组传入就可以了，
     * 语句的编译操作和参数的赋值操作由appendsql执行
     * @param val
     * @return
     * @throws Exception
     */
    public boolean addMoney(Object...val)throws Exception{

            //操作all表的sql语句
            StringBuilder sql=new StringBuilder()
                    .append("insert into base(aid,aname,acount)")
                    .append(" values(?,?,?)");
            //返回主键值
            int aname=Tools.getSequence("aname");
            //组织数据
            Object vals1[]={aname,val[0],val[1]};
            //注册sql语句
            this.appendsql(sql.toString(),vals1);


            //操作money表的sql语句
            StringBuilder sql2=new StringBuilder()
                    .append("insert into money(mid,mmoney)")
                    .append(" values(?,?)");
            Object vals2[]={aname,Double.parseDouble(val[1].toString())*500};
            this.appendsql(sql2.toString(),vals2);

            //操作else表的sql语句
            StringBuilder sql3=new StringBuilder()
                    .append("insert into elsey(eid,estate)")
                    .append(" values(?,?)");
            Object vals3[]={aname,val[1]+" GAY!!"};
            this.appendsql(sql3.toString(),vals3);

            //全部注册结束后执行
            return executeTransaction();
    }
}
