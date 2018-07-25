/**
 * 抽象类JdbcServicesSupport，提供services的解决方案
 */
package com.neuedu.services.support;


import com.neuedu.system.db.DBUtils;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public abstract class JdbcServicesSupport {


    /**------------------------------------------------------------------------------------------------------
     * 单一表批处理事务封装
     --------------------------------------------------------------------------------------------------------*/

    /**
     * 多状态批量更新(状态不同)
     * <
     *   update table
     *      set col1=?,col2=?,col3=?..........colN=?
     *    where id=?
     *
     *   举个例子:
     *   update person set pstate=?,psal=psal+? where pid=?
     *   newStateTable[]={{"1","333"},{"2","500"}}
     *   idlist[]={"24","25","26"};
     * >
     * @param sql
     * @param newStateTable   ----  状态列表(二维数组)
     * @param idlist         ----  主键列表
     * @return
     * @throws Exception
     */
    protected final boolean batchUpdate(final String sql,final Object[][] newStateTable,final Object...idlist)throws Exception
    {
        PreparedStatement pstm=null;
        try{
            pstm=DBUtils.prepareStatement(sql);

            for (int rownum=0;rownum<idlist.length;rownum++)
            {
                int index=1;
                for(Object state:newStateTable[rownum])
                {
                    pstm.setObject(index++,state);
                }
                pstm.setObject(index,idlist[rownum]);
                pstm.addBatch();
            }

            return this.executeBatchTransaction(pstm);
        }
        finally
        {
            DBUtils.close(pstm);
        }
    }

    /**
     * 简化上一种方法，将id放入stateTable中
     * @param sql   --- sql语句
     * @param newStateTable --- 状态表，包括id
     * @return
     * @throws Exception
     */
    protected final boolean batchUpdate(final String sql,final Object[][] newStateTable)throws Exception
    {
        PreparedStatement pstm=null;
        try{
            pstm=DBUtils.prepareStatement(sql);

            for (Object[] statelist:newStateTable)
            {
                int index=1;
                for(Object state:statelist)
                {
                    pstm.setObject(index++,state);
                }
                pstm.addBatch();
            }

            return this.executeBatchTransaction(pstm);
        }
        finally
        {
            DBUtils.close(pstm);
        }
    }

    /**
     * 多状态的批量更新（状态相同）
     * 适用于如下语法格式：
     *  “update table set column1=?,column2=?,...,columnn=? where id=？”
     *  思路：-->创建pstm
     *       -->DBUtils.prepareStatement(sql)编译sql语句
     *       -->遍历statelist，为要更改的参数赋值
     *       -->遍历idlist，为查找条件参数赋值
     *       -->将赋值的pstm加入缓冲区
     *       -->执行批处理操作executeBatchUpdate()
     *       -->销毁pstm
     * @param sql
     * @param idlist
     * @return
     * @throws Exception
     */
    protected final boolean batchUpdate(final String sql,final Object[] statelist,final Object...idlist)throws Exception
    {
        PreparedStatement pstm=null;
        try{
            pstm=DBUtils.prepareStatement(sql);
            int index=1;
            for(Object state:statelist){
                pstm.setObject(index++,state);
            }
            for(Object id:idlist){
                pstm.setObject(index,id);
                pstm.addBatch();
            }

            return this.executeBatchTransaction(pstm);
        }
        finally
        {
            DBUtils.close(pstm);
        }
    }

    /**
     * 单一状态的批量更新
     * 适用于如下语法格式：
     *  “update table set column=? where id=?”
     *  思路：-->创建pstm
     *       -->DBUtils.prepareStatement(sql)编译sql语句
     *       -->使用state为修改后的参数赋值
     *       -->遍历idlist，为修改条件参数赋值
     *       -->将赋值的pstm加入缓冲区
     *       -->执行批处理操作executeBatchUpdate()
     *       -->销毁pstm
     * @param sql
     * @param idlist
     * @return
     * @throws Exception
     */
    protected final boolean batchUpdate(final String sql,final Object state,final Object...idlist)throws Exception
    {
        PreparedStatement pstm=null;
        try
        {
            pstm=DBUtils.prepareStatement(sql);
            pstm.setObject(1,state);
            for(Object id:idlist)
            {
                pstm.setObject(2,id);
                pstm.addBatch();
            }
            return this.executeBatchTransaction(pstm);
        }
        finally
        {
            DBUtils.close(pstm);
        }
    }

    /**
     * 单一表的批处理
     * 适用于如下语法格式：
     *  “delete from table where id=？”
     *  思路：-->创建pstm
     *       -->DBUtils.prepareStatement(sql)编译sql语句
     *       -->遍历idlist，为参数赋值
     *       -->将赋值的pstm加入缓冲区
     *       -->执行批处理操作executeBatchUpdate()
     *       -->销毁pstm
     * @param sql
     * @param idlist
     * @return
     * @throws Exception
     */
    protected final boolean batchUpdate(final String sql,final Object...idlist)throws Exception
    {
        PreparedStatement pstm=null;
        try
        {
            pstm=DBUtils.prepareStatement(sql);
            for(Object id:idlist)
            {
                pstm.setObject(1,id);
                pstm.addBatch();
            }
            return this.executeBatchTransaction(pstm);
        }
        finally
        {
            DBUtils.close(pstm);
        }
    }

    /**
     * 批处理执行方法的封装
     * 思路：-->一个布尔值做标记，用于做返回值
     *      -->进入事务机制
     *          -->关闭自动提交
     *          -->try：执行批处理/提交/更改标记量
     *          -->catch：出现异常回滚
     *          -->finally：不论执行情况如何，关闭数据库连接，恢复开启自动提交
     *      -->结束事务机制，返回标记量
     * @param pstm
     * @return
     * @throws Exception
     */
    protected final boolean executeBatchTransaction(final PreparedStatement pstm)throws Exception{
        boolean tag=false;
        DBUtils.closeAutoCommit();
        try{
            pstm.executeBatch();
            DBUtils.commit();
            tag=true;
        }
        catch (Exception e){
            DBUtils.rollback();
            e.printStackTrace();
        }
        finally
        {
            DBUtils.startAutoCommit();
        }
        return tag;
    }


    /**---------------------------------------------------------------------------------
     * 多表关联更新事务处理
     -----------------------------------------------------------------------------------*/

    private final List<PstmBean> beanList=new ArrayList<>();

    /**
     * appendsql()用于将事务中要使用的sql语句注册为bean对象
     * bean对象存入一个list中，
     * 在所有的sql语句注册结束后共同执行
     * @param sql --- sql语句
     * @param val --- 赋值参数列表
     * @throws Exception
     */
    protected final void appendsql(final String sql,final Object...val)throws Exception{
        //编译sql语句
        PreparedStatement pstm=DBUtils.prepareStatement(sql);
        //参数赋值
        int index=1;
        for(Object param:val){
            pstm.setObject(index++,param);
        }
        //构建描述对象
        PstmBean bean=new PstmBean(pstm,false);
        //添加至语句列表
        beanList.add(bean);
    }

    /**
     * 这里放事务执行的整个流程
     * 事务处理部分是对beanList进行遍历并处理
     * 结束后销毁所有bean语句对象（本质是销毁pstm）
     * 事务结束后需要清空list
     * @return
     * @throws Exception
     */
    protected final boolean executeTransaction()throws Exception{
        boolean tag=false;
        DBUtils.closeAutoCommit();
        try
        {
            for(PstmBean bean:beanList)
            {
                bean.execute();
            }
            DBUtils.commit();
            tag=true;
        }catch (Exception e)
        {
            DBUtils.rollback();
            e.printStackTrace();
        }finally
        {
            DBUtils.startAutoCommit();
            //销毁所有事务中的语句对象
            for(PstmBean bean:beanList)
            {
                bean.close();
            }
            //清空语句对象列表
            beanList.clear();
        }
        return tag;
    }


    /**----------------------------------------------------------------------------------
     * 单一sql非事务处理
     ------------------------------------------------------------------------------------*/

    /**
     * 封装executeUpdate()方法，
     * 抽象出操作中共同的部分，将sql语句和需要赋的值传进来
     * @param sql --- sql语句
     * @param val --- sql语句中需要赋值的参数组成的数组
     * @return  executeUpdate()返回一个数字，表示操作影响的行数
     * @throws Exception
     */
    protected final int executeUpdate(final String sql,final Object...val) throws Exception{
        PreparedStatement pstm = null;
        try{
            pstm=DBUtils.prepareStatement(sql);
            int index=1;
            for(Object param:val){
                pstm.setObject(index++,param);
            }
            return pstm.executeUpdate();
        }finally {
            DBUtils.close(pstm);
        }
    }

    /**--------------------------------------------------------------------------------------
     * 辅助工具
     ----------------------------------------------------------------------------------------*/

    /**
     * 辅助方法，判断对象不为空
     * @param o
     * @return
     */

    protected final boolean isNotNull(Object o)
    {
        return o!=null && !o.equals("");
    }
}
