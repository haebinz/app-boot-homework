package it.korea.app_boot.common.handler;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import it.korea.app_boot.user.dto.UserSecureDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{

    //htt요청을 기억하는 객체
    private RequestCache requestCache = new HttpSessionRequestCache();
    //응답 전략
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public void setRequestCache(RequestCache requestCache){
        this.requestCache = requestCache;
    }

    //권한 처리 후 실행
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {
        
     //로그인 성공 후 처리
    setDefaultTargetUrl("/board/list");
    request.getSession().setMaxInactiveInterval(1800);

    SavedRequest savedRequest = requestCache.getRequest(request, response);

    //세션 가져오기
    HttpSession session = request.getSession();
    //사용자 정보 저장
    session.setAttribute("user", (UserSecureDTO)authentication.getPrincipal());

    if(savedRequest != null){
        String targetURI = savedRequest.getRedirectUrl();

        if(targetURI.contains("error") ||targetURI.contains(".well-known")){
            targetURI = getDefaultTargetUrl();
        }
        redirectStrategy.sendRedirect(request, response, targetURI);

    }else{
        redirectStrategy.sendRedirect(request, response, getDefaultTargetUrl());
    }

    }

}
