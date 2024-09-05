package kr.co.smart.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kr.co.smart.member.MemberMapper;
import kr.co.smart.member.MemberVO;
import lombok.RequiredArgsConstructor;

@Service @RequiredArgsConstructor
public class LoginUserService implements UserDetailsService {
	private final MemberMapper mapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//username(userid가 담겨짐) 의 회원정보 조회
		MemberVO user = mapper.getOneMember(username);
		if( user==null ) throw new UsernameNotFoundException(username);
		return new LoginUser(user);
	}

}
