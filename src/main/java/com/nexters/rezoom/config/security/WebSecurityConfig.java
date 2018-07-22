package com.nexters.rezoom.config.security;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * Spring Security 설정 클래스
 * @author JaeeonJin
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	private Filter ssoFilter; /** {@link com.nexters.rezoom.config.security.SocialProviderConfig*/
	private RESTAuthenticationEntryPoint authenticationEntryPoint;
	
	@Autowired
	public WebSecurityConfig(
			@Qualifier("ssoFilter") Filter ssoFilter, RESTAuthenticationEntryPoint authenticationEntryPoint) {
		this.ssoFilter = ssoFilter;
		this.authenticationEntryPoint = authenticationEntryPoint;
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 보안 처리
		http.cors().and().csrf().disable();
		
		// 요청 처리 : 디버깅할 때는 모두 허락..
		http.authorizeRequests()
				.antMatchers("/**").permitAll();

		// 요청 처리: 필요할 때 주석 해제(위에껀 주석 달고..)
//		http.authorizeRequests()
//			.antMatchers("/").permitAll()
//			.antMatchers("/static/**").permitAll()
//			.antMatchers("/favicon.ico").permitAll()
//			.antMatchers("/resources/**").permitAll()
//			.antMatchers("/webjars/**").permitAll()
//			.antMatchers("/error/**").permitAll()
//			.anyRequest().authenticated();
		
		// 로그인 성공시 / 이동
		// TODO : 로그인 성공하면 / 이동이 되어야 하는데 안됌...
		// >>>>> Success Handler로 해결했음
		http.exceptionHandling()
			.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/"));
		
		// 로그아웃시 / 이동
		http.logout()
//			.deleteCookies(cookieName, "JSESSIONID")
			.logoutSuccessUrl("/").permitAll();
		
		// 권한이 없으면 무조건 401 Unauthhorized error를 보낸다.
		http.exceptionHandling()
			.authenticationEntryPoint(authenticationEntryPoint);
		
		// Basic Auth 끄기
		http.httpBasic().disable();
		
		
		// 소셜 관련 필터 
		http.addFilterBefore(ssoFilter, BasicAuthenticationFilter.class);
	}
	
	/**
	 * Spring Security 초기 비밀번호 생성 막기
	 */
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
	    return super.authenticationManagerBean();
	}
	
	/**
	 * {@link #authenticationEntryPoint} 
	 * 위 링크된 메소드를 사용하면 이상하게 의존성 사이클 에러가 발생하는데, 이거 해결하려면 오버라이드하면 된다고 구글신이 알려줌. 
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	}
}
