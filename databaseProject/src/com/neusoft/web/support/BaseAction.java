package com.neusoft.web.support;

import java.util.Map;

/**
 * 这是一个包含了控制器动作的接口，所有控制器的父类会实现这个接口的方法
 */
public interface BaseAction
{
    /**
     * 控制器执行业务逻辑的核心方法（创建需要的实例，调用对应的增删查改方法）
     * @return 返回一个字符串，代表跳转的目标
     * @throws Exception --- 异常抛出
     */
    String execute(String oType) throws Exception;

    /**
     * set方法，用于设置Dto
     * @param dto --- Map<String,Object>
     */
    void setDto(Map<String,Object> dto);

    /**
     * get方法，用于获取属性
     * @return Map<String,Object>
     */
    Map<String,Object> getAttributes();

    /**
     * 清除所有的属性
     */
    void clearAttributes();
}
