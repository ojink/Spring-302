package kr.co.smart.auth;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.security.web.authentication.rememberme.CookieTheftException;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException;

import kr.co.smart.remember.RememberMapper;
import kr.co.smart.remember.RememberVO;

public class RememberService extends AbstractRememberMeServices{
	private  RememberMapper mapper;
	private  SecureRandom random;
	
	
	public RememberService(String key, LoginUserService userService, RememberMapper mapper) {
		super(key, userService);
		this.mapper = mapper;
		random = new SecureRandom();
	}

	private String generateToken() {
		byte[] token = new byte[16];
		random.nextBytes(token);
		return new String( Base64.getEncoder().encode(token) );
	}
	
	@Override
	protected void onLoginSuccess(HttpServletRequest request, HttpServletResponse response,
									Authentication auth) {
		String username = auth.getName(); //userid
		//쿠키 저장
		//series, token 을 쿠키로 사용
		String series = generateToken();
		String token = generateToken();
		
		//DB에 저장
		RememberVO vo = new RememberVO(username, series, token, new Date());
		mapper.registerRemember(vo);
		
		//브라우저에 쿠키저장
		String[] cookie = { series, token };
		super.setCookie(cookie, getTokenValiditySeconds(), request, response);
	}

	@Override
	protected UserDetails processAutoLoginCookie(String[] cookieTokens, HttpServletRequest request,
			HttpServletResponse response) throws RememberMeAuthenticationException, UsernameNotFoundException {
		//쿠키: series, token 2개로 된 배열
		if( cookieTokens.length != 2 ) {
			throw new RememberMeAuthenticationException("잘못된 쿠키");
		}
		String series = cookieTokens[0];
		String token = cookieTokens[1];
		
		//DB에 저장한 정보와 일치하는지 확인
		RememberVO vo = mapper.getOneRemember( series );
		if( vo == null ) {
			//DB에 없는 정보인 쿠키는 삭제
			super.cancelCookie(request, response);
			throw new RememberMeAuthenticationException("DB에 쿠키정보 없음");
		}else if( ! token.equals(vo.getToken()) ) {
			//DB의 series 는 일치하나 token 이 같지 않은 경우 쿠키가 도용됨
			super.cancelCookie(request, response);
			mapper.deleteRemember(series);
			throw new CookieTheftException("쿠키가 도용됨");
		}else if( vo.getLast_used().getTime() + getTokenValiditySeconds()*1000L < System.currentTimeMillis() ) {
			//유효기간이 초과된 경우 DB정보 삭제
			super.cancelCookie(request, response);
			mapper.deleteRemember(series);
			throw new RememberMeAuthenticationException("쿠키 유효기간 만료됨");
		}
		
		//자동로그인되면 token값 갱신하기
		vo.setToken( generateToken() );
		vo.setLast_used( new Date() );
		//갱신된 token 정보 DB에 저장 + 갱신된 token으로 쿠키를 생성해 브라우저에 저장하기 
		mapper.updateRemember(vo);
		
		String[] cookie = { series, vo.getToken() };
		super.setCookie(cookieTokens, getTokenValiditySeconds(), request, response);
		
		//반환할 정보는 UserDetails(LoginUser)
		LoginUser user = (LoginUser)super.getUserDetailsService().loadUserByUsername( vo.getUsername() );
		return user;
	}

}
