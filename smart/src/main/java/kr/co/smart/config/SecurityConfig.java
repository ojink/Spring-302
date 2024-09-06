package kr.co.smart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import kr.co.smart.auth.AccessDeny;
import kr.co.smart.auth.LoginSuccess;
import kr.co.smart.auth.LogoutSuccess;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration //설정파일로 등록
@EnableWebSecurity //시큐리티 활성화
public class SecurityConfig {
	
	//비밀번호 암호화에 사용할 PasswordEncoder
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		//고객관리,사원관리 : 모든 접근 허용 
		//공지글, FAQ 등록 : 관리자 권한 필요
		//답글,방명록 등록, 사용자관련 : 사용자 권한 필요
		//조회(목록,정보) :  모든 접근 허용
		//수정/삭제 : 해당 글을 쓴 사용자만 접근 허용
		http.authorizeRequests()
			//.antMatchers("/**").permitAll() //모든 요청에 대한 접근 허용
			.antMatchers("/customer/**", "/hr/**").permitAll()
			.antMatchers("/**/list", "/**/info").permitAll()
			.antMatchers("/notice/register").hasAuthority("ADMIN")
			.antMatchers("/**/register", "/**/user/**").hasAuthority("USER")
			.antMatchers("/**/modify", "/**/delete").access("@accessUser.is(authentication, request)")
		
			//접근불가시 처리
			.and()
			.exceptionHandling().accessDeniedHandler(accessDeny)
			
			//스프링시큐리티 로그인 적용하기
			.and()
			.formLogin()
				.loginPage("/member/login")
				.usernameParameter("userid")
				.passwordParameter("userpw")
				.loginProcessingUrl("/member/smartLogin")
				.failureUrl("/member/login/fail")
				.successHandler(loginSuccess)
		
			.and()
			.logout()
				.logoutUrl("/member/logout")
				.invalidateHttpSession(true)
				.logoutSuccessHandler(logoutSuccess)
			;
		http.csrf().disable();  //사이트간 요청 위조 방지 처리- 비활성화
		return http.build();
	}

	private final LoginSuccess loginSuccess;
	private final LogoutSuccess logoutSuccess;
	private final AccessDeny accessDeny;
	
}
