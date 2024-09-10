package kr.co.smart.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import kr.co.smart.member.MemberVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor @Getter @Setter
public class LoginUser implements UserDetails, OAuth2User{
	private static final long serialVersionUID = 1L;
	
	private MemberVO user;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		ArrayList<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
		auths.add( new SimpleGrantedAuthority(user.getRole()));
		//관리자는 사용자 권한도 부여
		if( user.getRole().equals("ADMIN") ) {
			auths.add( new SimpleGrantedAuthority("USER") );
		}
		return auths;
	}

	@Override
	public String getPassword() {
		return user.getUserpw();
	}

	@Override
	public String getUsername() {
		return user.getUserid();
	}

	@Override
	public boolean isAccountNonExpired() {//계정의 유효기간 상태
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {//계정의 잠금상태
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() { //자격정보(비번)의 유효기간의 상태
		return true;
	}

	@Override
	public boolean isEnabled() { //유효한 패스워드인지
		return true;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return null;
	}

	@Override
	public String getName() {
		return null;
	}

}
