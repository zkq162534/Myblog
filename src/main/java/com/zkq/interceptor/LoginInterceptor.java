package com.zkq.interceptor;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;
/**
 * @author zkq15
 * */
@Data
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    private List<String> uncheckUrls;
    private Boolean flag=false;
    private String userName;
              @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                  //获得请求的URL
       String requestUrl=request.getRequestURI();
        log.debug("请求的URL:"+requestUrl);
        //获取session
        HttpSession session=request.getSession();
        //从session中得到登录用户的相关信息
            //判断是否存在cookie,存在cookie拦截，直接进入后台管理页面，免登录
            Cookie[] cookies = request.getCookies();
            if(cookies!=null&&cookies.length!=0){
                for(Cookie cookie:cookies){
                    if(Objects.equals(cookie.getName(),"www.zkq.cn")){
                        flag=true;
                        userName=cookie.getValue();
                        break;
                    }
                }
            }
            //此时cookie存在，进行拦截并且存储转发到主页面，设置session
            if(flag){
                if(requestUrl.contains("/toLoginView.action")){
                    log.debug("请求toLogin且存在cookie，拦截请求免登录");
                    session.setAttribute("userName",userName);
                    response.sendRedirect("/toMain.action");
                    return false;
                }
                //cookie存在，但不是请求tologin页面
                if(requestUrl.contains("/logout.action")){
                   flag=false;
                }
                return  true;
            }else{
                // 没有勾选记住密码，或者退出时清楚了cookie
                log.debug("不存在cookie");
                if(uncheckUrls.contains(requestUrl)){
                    log.debug("公开地址");
                    return true;
                }
                if(session.getAttribute("username")!=null){
                    log.debug("用户在session中存在，放行");
                    return  true;
                }else{
                    log.debug("session中不存在用户，拦截");
                    response.sendRedirect("/toLoginView.action");
                    return false;
                }
            }

    }
@Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }
@Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }
}
