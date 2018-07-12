/**
 * Person增删改查操作类
 */
package com.neuedu.services;

import com.neuedu.services.support.JdbcServicesSupport;


public class PersonServices extends JdbcServicesSupport {


    /**
     * 多状态更新
     * @param stateList --- 二维数组
     * @param idlist
     * @return
     * @throws Exception
     */
    public boolean batchUpdate(final Object[][] stateList,final Object...idlist)throws Exception
    {
        String sql = "update person set pname=?,psal=? where pid=?";
        return batchUpdate(sql,stateList,idlist);
    }

    /**
     * 单一状态更新
     * @param stateList --- 一维数组
     * @param idlist
     * @return
     * @throws Exception
     */
    public boolean batchUpdate(final Object[] stateList,final Object...idlist)throws Exception
    {
        String sql = "update person set pname=?,psal=? where pid=?";
        return batchUpdate(sql,stateList,idlist);
    }

    /**
     * 形如 “delete from table where id=？”
     * 的批处理操作调用此方法
     * @param idlist
     * @return boolean
     * @throws Exception
     */
    public boolean batchDelete(final Object...idlist)throws Exception{
        String sql="delete from person where pid=?";
        return batchUpdate(sql,idlist);
    }

    /**
     * 添加操作
     * 1.创建数据库连接
     * 2.定义sql语句
     * 3.编译sql语句
     * 4.参数赋值
     * 5.执行并返回值
     * @param val
     * @return 返回一个布尔值
     * @throws Exception
     */
    public boolean addPerson(Object...val)throws Exception
    {
            StringBuilder sql=new StringBuilder()
                    .append("insert into person(pname,pnumber,psex,pbirthday,pnation,psal,pstate,pmemo)")
                    .append(" values(?,?,?,?,?,?,?,?);");
            return executeUpdate(sql.toString(),val)>0;
    }

    /**
     * 删除操作
     * 步骤同添加
     * @param id --- 根据id删除
     * @return  返回一个布尔值
     * @throws Exception
     */
    public boolean delPerson(Object id)throws Exception{
        String sql="delete from person where pid=?";
        return executeUpdate(sql,id)>0;
    }


    /**
     * 修改数据
     * @param val --- update语句中的参数值
     * @return 返回一个布尔值
     * @throws Exception
     */
    public boolean updatePerson(Object...val)throws Exception{
        StringBuilder sql=new StringBuilder()
                    .append("update person p")
                    .append(" set p.pname=?,p.pnumber=?,p.psal=?")
                    .append(" where p.pid=?");
        return executeUpdate(sql.toString(),val)>0;
    }

}
