package com.neusoft.web.impl;

import com.neusoft.services.impl.PersonServices;
import com.neusoft.web.support.BaseController;

public class Operation extends BaseController
{
    public Operation()
    {
        services=new PersonServices();
    }

    @Override
    public String execute(String oType)throws Exception
    {
        switch (oType)
        {
            case "add":
                add();
                return "add";
            case "query":
                query();
                return "query";
            case "delete":
                delete();
                return "query";
            case "update":
                update();
                return "add";
            case "find":
                findById();
                return "add";
                default:
                    throw new Exception("类型与已知操作不匹配，请重试！");
        }
    }

}
