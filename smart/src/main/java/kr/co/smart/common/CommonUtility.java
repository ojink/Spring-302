package kr.co.smart.common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import kr.co.smart.member.MemberVO;

//@Component : @Service, @Repository, 
@Service
public class CommonUtility {
	
	@Value("${spring.mail.host}") private String emailHost;
	@Value("${spring.mail.username}") private String emailUser;
	@Value("${spring.mail.password}") private String emailPass;
	
	
	//Http통신API요청
	public String requestAPI( HttpURLConnection con ) throws Exception{
        int responseCode = con.getResponseCode();
        BufferedReader br;
        //System.out.print("responseCode="+responseCode);
        if(responseCode==200) { // 정상 호출
          br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        } else {  // 에러 발생
          br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
        }
        String inputLine;
        StringBuffer res = new StringBuffer();
        while ((inputLine = br.readLine()) != null) {
          res.append(inputLine);
        }
        br.close();
        
        if(responseCode==200) {
        	System.out.println( res.toString() ); //"{ 'a': 10, 'b': 20 }"
        }
        
      return res.toString();			
	}
	
	public String requestAPI( String apiURL, String property ) {
	    try {
	        HttpURLConnection con = (HttpURLConnection) (new URL(apiURL)).openConnection();
	        con.setRequestMethod("GET");
	        con.setRequestProperty("Authorization", property);
	        apiURL = requestAPI( con );
	    }catch(Exception e) {
	    }
        return apiURL; 
	}
	
	public String requestAPI( String apiURL ) {
	    try {
	        HttpURLConnection con = (HttpURLConnection) (new URL(apiURL)).openConnection();
	        con.setRequestMethod("GET");
	        apiURL = requestAPI( con );
	    }catch(Exception e) {
	    }    
	    return apiURL;
	}
	
	
	private void mailSender(HtmlEmail sender) {
		sender.setDebug(true);
		sender.setCharset("utf-8");
		
		sender.setHostName(emailHost);
		sender.setAuthentication(emailUser, emailPass); //이메일주소, 비번
		sender.setSSLOnConnect(true); //로그인버튼 클릭
	}
	
	//임시비밀번호 이메일 보내기
	public boolean emailForPassword(MemberVO vo, String pw) {
		boolean send = true;
		
		HtmlEmail sender = new HtmlEmail();
		mailSender( sender );
		
		try {
			sender.setFrom(emailUser, "스마트IoT 관리자"); //이메일 보내는이 정보
			sender.addTo(vo.getEmail(), vo.getName() ); //메일 수신인 정보
			
			sender.setSubject("스마트 IoT 임시비밀번호"); //제목
			//내용
			StringBuffer content = new StringBuffer();
			content.append("<h3>[").append(vo.getName()).append("]님 임시 비밀번호가 발급되었습니다</h3>");
			content.append("<div>아이디: ").append( vo.getUserid() ).append("</div>");
			content.append("<div>임시 비밀번호: <strong>").append( pw ).append("</strong></div>");
			content.append("<div>발급된 임시 비밀번호로 로그인 후 비밀번호를 변경하세요</div>");
			sender.setHtmlMsg( content.toString() );
			
			sender.send(); //보내기 버튼 클릭
			
		} catch (EmailException e) {
			send = false;
		} 
		return send;
	}
	
	
	
	//애플리케이션 URL
	public String appURL(HttpServletRequest request) {
		// http://localhost:8080/smart
		// http://127.0.0.1:80/smart
		// http://192.168.0.10:8090/smart
		StringBuffer url = new StringBuffer("http://");
		url.append( request.getServerName() ).append(":"); // http://localhost:, http://127.0.0.1:
		url.append( request.getServerPort() );    // http://localhost:8080, http://127.0.0.1:80
		url.append( request.getContextPath() );   // http://localhost:8080/iot, http://127.0.0.1:80/smart
		return url.toString();
	}
	public String appURL(HttpServletRequest request, String path) {
		// http://127.0.0.1:80/smart + /member/naverCallback
		return new StringBuffer( appURL(request) ).append(path).toString();
	}
	
}
