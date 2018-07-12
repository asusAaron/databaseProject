package com.neuedu.system.tools;

import com.neuedu.system.db.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 工具类
 */
public class Tools{

    private Tools(){}

    /**
     * 返回序列号，用于查询主键值
     * @param sname
     * @return
     * @throws Exception
     */
    public static int getSequence(String sname) throws Exception{
        PreparedStatement pstm1=null;
        PreparedStatement pstm2=null;
        ResultSet rs=null;

        try{
            //查询序列
            String sql="select svalue from sequence where sname=?";
            pstm1=DBUtils.prepareStatement(sql);
            pstm1.setObject(1,sname);
            //查询
            rs=pstm1.executeQuery();

            //序列当前值
            int current_val=0;
            //序列修改sql
            StringBuilder sql2=new StringBuilder();

            if(rs.next()){
                //读取序列第一列的值
                current_val=rs.getInt(1);
                sql2.append("update sequence")
                        .append(" set svalue=?")
                        .append(" where sname=?");
            }else{
                sql2.append("insert into sequence(svalue,sname,sdate)")
                        .append(" values(?,?,current_date)");

            }

            pstm2=DBUtils.prepareStatement(sql2.toString());
            pstm2.setObject(1,++current_val);
            pstm2.setObject(2,sname);
            pstm2.executeUpdate();

            //返回序列号
            return current_val;
        }finally {
            DBUtils.close(rs);
            DBUtils.close(pstm1);
            DBUtils.close(pstm2);
        }
    }

}
