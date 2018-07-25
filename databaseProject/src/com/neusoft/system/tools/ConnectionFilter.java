package com.neusoft.system.tools;

import com.neusoft.system.db.DBUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import java.io.IOException;

@WebFilter("/*")
public class ConnectionFilter extends HttpServlet implements Filter
{

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException
    {
        try
        {
            chain.doFilter(req, resp);
        } finally
        {
            DBUtils.close();
        }
    }

    public void init(FilterConfig config) throws ServletException
    {

    }

}
