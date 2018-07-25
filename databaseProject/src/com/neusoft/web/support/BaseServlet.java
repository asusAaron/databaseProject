package com.neusoft.web.support;


import com.neusoft.web.impl.Operation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Map;
import java.util.Set;

/**
 * 拦截所有搜索后缀为.do的Servlet的请求
 * 作为唯一的Servlet，执行所有请求的分派
 * 或者说，BaseServlet是一个中转中心，负责分析request的意图，并调用不同的控制器处理
 */
@WebServlet("*.do")
public class BaseServlet extends HttpServlet
{

    /**
     * 在BaseServlet完成doGet()和doPost()操作，
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        //获取完整的Request请求
        String uri=req.getRequestURI();
        //获取最后一个斜线的下标
        int index=uri.lastIndexOf("/");
        //从最后一个斜线的下一位截取字符串
        String uriName=uri.substring(index+1);
        //把.do去掉
        String baseName=uriName.replaceAll(".do","").toLowerCase();

        try
        {
            //动态方法调度获取控制器实例
            BaseAction controller=new Operation();
            //创建DTO
            controller.setDto(createDto(req));
            //执行控制器操作，获取跳转路径
            String path=controller.execute(baseName);
            //为request添加属性
            setRequestAttribute(controller.getAttributes(),req);
            //清除控制器属性
            controller.clearAttributes();
            //页面跳转
            //跳转格式：/xxx.jsp，所以取得的path返回值应为jsp文件名
            req.getRequestDispatcher("/"+path+".jsp").forward(req,resp);


        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        doGet(req,resp);
    }

    /**
     * 处理request中的所有属性
     */
    private final void setRequestAttribute(Map<String,Object> atts,HttpServletRequest request)
    {
        //1.解析属性中的所有Entry
        Set<Entry<String,Object>> entrySet=atts.entrySet();
        for(Entry<String,Object> entry:entrySet)
        {
            //保存属性
            request.setAttribute(entry.getKey(), entry.getValue());
        }
    }

    /**
     * 创建DTO
     * @param request
     * @return DTO
     */
    protected final Map<String,Object> createDto(HttpServletRequest request)
    {
        Map<String,String[]> map=request.getParameterMap();
        //获取Entry
        Set<Entry<String,String[]>> entrySet=map.entrySet();
        //定义字符串表示value
        String value[]=null;
        //实例化DTO
        Map<String,Object> dto=new HashMap<>();
        //循环获取键值对
        for(Entry<String,String[]> entry:entrySet)
        {
            value=entry.getValue();
            if(value.length==1)
            {
                //认为不是复选框
                dto.put(entry.getKey(),value[0]);
            }
            else
            {
                //认为是复选框
                dto.put(entry.getKey(),value);
            }
        }
        return dto;
    }
}