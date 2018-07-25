package com.neusoft.web.support;


import com.neusoft.services.support.BaseServices;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 抽象类BaseController是所有控制器的父类
 * 它负责实现所有BaseAction的动作
 *
 * 包括Dto的set/get/put
 * attributes的get/add
 * 以及attributes的clear方法
 */
public abstract class BaseController implements BaseAction
{

    private Map<String,Object> dto=null;
    private Map<String,Object> attributes=new HashMap<>();
    protected BaseServices services=null;

    /**
     * 数据查询操作
     */
    protected final void query()throws Exception
    {
        List<Map<String,String>> rows=services.query();
        if(rows.size()>0)
        {
            addAttributes("rows",rows);
        }
        else
        {
            addAttributes("msg","没有符合条件的数据！");
        }
    }

    /**
     * 数据删除操作
     */
    protected final void delete()throws Exception
    {
        String msg=services.update("delete")?"删除成功！":"不存在该数据或访问权限不足！";
        addAttributes("msg",msg);

        List<Map<String,String>> rows=services.query();
        if (rows.size()>0)
        {
            addAttributes("rows",rows);
        }
    }


    /**
     * 数据添加操作
     */
    protected final void add()throws Exception
    {
        String msg=services.update("add")?"添加成功！":"添加失败，请检查输入后重试！";
        addAttributes("msg",msg);
    }

    /**
     * 数据更新操作
     */
    protected final void update()throws Exception
    {
        String msg=services.update("modify")?"修改成功！":"不存在该数据或访问权限不足！";
        addAttributes("msg",msg);
    }

    /**
     * 页面跳转数据显示
     */
    protected final void findById() throws Exception
    {
        Map<String,String> ins=services.findById();
        addAttributes("ins",ins);
    }

    /**---------------------------------------------------------------
     * 参数处理
     ----------------------------------------------------------------*/
    protected final Map<String, Object> getDto()
    {
        return dto;
    }

    @Override
    public void setDto(Map<String, Object> dto)
    {
        this.dto = dto;
        services.setMapDto(dto);
    }

    protected final void putDto(String key,Object value)
    {
        dto.put(key,value);
    }


    /**-------------------------------------------------------------------------
     * 属性处理
     --------------------------------------------------------------------------*/
    @Override
    public Map<String, Object> getAttributes()
    {
        return this.attributes;
    }

    protected final void addAttributes(String key,Object value)
    {
        attributes.put(key, value);
    }

    @Override
    public void clearAttributes()
    {
        attributes.clear();
    }
}
