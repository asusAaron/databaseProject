package com.neusoft.services.support;


import com.neusoft.system.db.DBUtils;

import java.io.Serializable;
import java.sql.PreparedStatement;

/**
 * 这是一个用于描述语句对象的类
 * 类的作用范围仅限support包下
 * 作用是存储执行操作的类型（批处理or not）
 */
final class PstmBean implements Serializable
{
    private PreparedStatement pstm=null;
    //默认不是批处理
    private boolean isBatch=false;

    //构造
    public PstmBean(PreparedStatement pstm,boolean isBatch){
        this.pstm=pstm;
        this.isBatch=isBatch;
    }

    /**
     * 重新封装execute()，根据是否为批处理判断调用的方法
      * @param batch
     */
    public void execute() throws Exception
    {
        if(this.isBatch){
            this.pstm.executeBatch();
        }
        else{
            this.pstm.executeUpdate();
        }
    }

    /**
     * 关闭连接
     */
    public void close(){
        DBUtils.close(pstm);
    }
}
