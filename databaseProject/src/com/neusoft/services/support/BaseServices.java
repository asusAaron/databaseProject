package com.neusoft.services.support;

import java.util.List;
import java.util.Map;

public interface BaseServices
{
    /**
     * 设置DTO的值
     * @param dto
     */
    void setMapDto(Map<String,Object> dto);

    /**
     * 更新操作
     * @param uType --- 操作的类型
     * @return
     * @throws Exception --- 异常抛出
     */
    default boolean update(String uType) throws Exception
    {
            return false;
    }

    /**
     * 多条件查询操作
     * @return
     * @throws Exception
     */
    default List<Map<String,String>> query()throws Exception
    {
        return null;
    }

    /**
     * 按ID查询
     * @return
     * @throws Exception
     */
    default Map<String,String> findById()throws Exception
    {
        return null;
    }

}
