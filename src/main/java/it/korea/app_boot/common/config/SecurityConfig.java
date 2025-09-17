package it.korea.app_boot.common.config;



import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.HttpStatusAccessDeniedHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import it.korea.app_boot.common.handler.LoginFailureHandler;
import it.korea.app_boot.common.handler.LoginSuccessHandler;
import it.korea.app_boot.common.handler.LogoutHandler;
import it.korea.app_boot.user.service.UserServiceDetails;
import lombok.RequiredArgsConstructor;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserServiceDetails serviceDetails;

    //시큐리티 우선 무시하기
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web ->  web.ignoring()
            //.requestMatchers("/webjars/**","/dist/**","/css/**")
            //.requestMatchers("/static/img/**","/static/img/**")
           // .requestMatchers("/font/**","/images/**")
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations()); 
            //마지막 명령어는 스프링 리소스 관련 처리
            /*
             * 1.classpath:/META-INF/resources/ //라이브러리 리소스폴더
             * 2.classpath:/resources/
             * 3.classpath:/static/
             * 4.classpath:/public/
             */
        
    }
    //보안처리
    /** 
     * security 6 특징
     * @Param
     * @Return
     * @throws Exception
     * */
    //보안 처리
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        http.csrf(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable)
        //인증/비인증경로처리
        .authorizeHttpRequests(
            auth-> 
            auth.requestMatchers("/user/login/**").permitAll()
            .requestMatchers("/user/login/error").permitAll()
            .requestMatchers("/user/logout/**").permitAll()
            .requestMatchers(HttpMethod.OPTIONS,"/").permitAll()
            .requestMatchers("/.well-known/**").permitAll()
            .requestMatchers("/favicon.ico").permitAll()
            .requestMatchers(HttpMethod.GET,"/gal/**","/api/v1/gal/**").permitAll()
           // .requestMatchers(HttpMethod.GET,"/board/**","/api/v1/board/**").permitAll()
            .requestMatchers("/admin/**","/api/v1/admin/**").hasAnyRole("ADMIN")  //인증처리 안하고 패스
            .anyRequest().authenticated() //나머지request는 인증처리를 하겠다라는 뜻    

        )//로그인처리
        .formLogin(
            form->
            form.loginPage("/user/login")//내가만든 로그인 페이지 사용할것(원래 security는 로그인 페이지를 줌)
            .loginProcessingUrl("/login/proc") //로그인 처리 시작 경로
           .successHandler(new LoginSuccessHandler()) //성공시
           .failureHandler(new LoginFailureHandler()) //실패시 
        ).logout(
            out->
            out.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .invalidateHttpSession(true) //spring sessionwprj
            .deleteCookies("SESSIONOD") //세션 id제거
            .clearAuthentication(true) //로그인 객체 삭제
            .logoutSuccessHandler(new LogoutHandler()) //로그아웃 후처리
        )
        .exceptionHandling(exp->
        //비로그인 상태에서 api 호출 시 오류
        exp.defaultAuthenticationEntryPointFor(
            new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
            new AntPathRequestMatcher("/api/**")
            //로그인 이지만 권한 없는 api호출 시 오류
            ).defaultAccessDeniedHandlerFor(
                new HttpStatusAccessDeniedHandler(HttpStatus.FORBIDDEN),
                new AntPathRequestMatcher("/api/**"))
                ); 

        return http.build();
    }

    //auth provider 생성해서 객체전달 ->사용자가 만든걸 전달한다
    @Bean
    public AuthenticationProvider authProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(serviceDetails);
        provider.setPasswordEncoder(bcyPasswordEncoder());
        return provider;


    }

    //패스워드 암호화 객체 설정
    @Bean
    public PasswordEncoder bcyPasswordEncoder(){
        // 단방향 암호화 방식.  복호화 없음.  값 비교는 가능  
        return  new BCryptPasswordEncoder();
    }

}
