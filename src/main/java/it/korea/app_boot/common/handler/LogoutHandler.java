package it.korea.app_boot.common.handler;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import it.korea.app_boot.user.dto.UserSecureDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LogoutHandler implements LogoutSuccessHandler{

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

          //세션  가져오기
        HttpSession session = request.getSession();
        //사용자 정보 저장
        if(session.getAttribute("user") != null) {
            session.removeAttribute("user");        
        }

	Cookie[]  cookies = request.getCookies();
		
		if(cookies != null)  {
			//시간을 0으로  해서 삭제 
			for( Cookie  cookie :  cookies) {
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}
		}
		
		response.setStatus(HttpServletResponse.SC_OK);
        response.sendRedirect("/login/form");
                
    }
    
}
