package me.zwdi.crm.web.filter;

import javax.servlet.*;
import java.io.IOException;

public class EncodingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // 过滤post请求乱码
        request.setCharacterEncoding("UTF-8");
        // 过滤响应乱码
        response.setContentType("text/html;charset=utf-8");
        // 将请求放行
        chain.doFilter(request,response);

    }
}
