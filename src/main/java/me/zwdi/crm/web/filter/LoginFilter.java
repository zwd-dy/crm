package me.zwdi.crm.web.filter;

import me.zwdi.crm.settings.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 验证登录状态的过滤器
 */
public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String path = request.getServletPath();

        // 不应该被拦截的资源自动放行
        if("/login.jsp".equals(path) || "/settings/user/login.do".equals(path)){

            chain.doFilter(req,res);

        } else {

            // 其他都要拦截
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");

            // 如果user不为null，说明登录过
            if (user != null) {
                chain.doFilter(req, res);
            } else {
                // 没有登录过
                // 重定向到登录页
            /*
                重定向路径怎么写？
                    在实际项目开发中，对于路径的使用无论操作的是前端还是后端，应该一律使用绝对路径
                    关于转发和重定向的路径的写法如下：
                        转发：
                            使用的是一种特殊的绝对路径的使用方式，这种绝对路径前面不加 /项目名 ，这种路径称之为内部路径 /login
                        重定向：
                            使用的是传统绝对路径的写法，前面必须以 /项目名 开头，后面跟具体的资源路径
             */
                response.sendRedirect(request.getContextPath() + "/login.jsp");
            }
        }
    }
}
