package kr.co.smart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

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
		http.authorizeRequests()
			.antMatchers("/**").permitAll(); //모든 요청에 대한 접근 허용
		
		http.csrf().disable();  //사이트간 요청 위조 방지 처리- 비활성화
		return http.build();
	}

}
