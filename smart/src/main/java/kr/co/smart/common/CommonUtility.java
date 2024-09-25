package kr.co.smart.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import kr.co.smart.member.MemberVO;

//@Component : @Service, @Repository, 
@Service
public class CommonUtility {
	
	@Value("${spring.mail.host}") private String emailHost;
	@Value("${spring.mail.username}") private String emailUser;
	@Value("${spring.mail.password}") private String emailPass;
	@Value("${smart.upload}")  private String uploadPath;  // d://smart/app/upload/
	
	//키의 존재유무에 따라 데이터처리하기
	public String hasKey(JSONObject json, String key) {
		return json.has(key) ? json.getString(key) : "";
	}
	//기본값을 지정해야 하는 경우
	public String hasKey(JSONObject json, String key, String defaultValue) {
		return json.has(key) ? json.getString(key) : defaultValue;
	}
	
	
	//첨부된 파일 삭제하기-물리적삭제
	public void fileDelete(String fileInfo, HttpServletRequest request) {
		if( fileInfo != null ) {
			//url경로형태 -> 실제파일경로형태
			File file = new File( toRealFilePath(fileInfo, request) );
			if( file.exists() ) file.delete();
		}
	}
	
	//파일 존재유무 확인
	public void fileExist(String filepath, Model model, HttpServletRequest request) {
		if( filepath != null ) {
			//물리적인 파일의 존재유무 확인
			filepath = toRealFilePath(filepath, request);
			model.addAttribute("file", new File( filepath ).exists() );
		}
	}
	
	public void fileExist( List<FileVO> files, Model model, HttpServletRequest request  ) {
		if( files != null ) {
			HashMap<Integer, Boolean> map = new HashMap<Integer, Boolean>(); 
			for(FileVO f : files) {
				String filepath = toRealFilePath(f.getFilepath(), request);
				map.put( f.getId(), new File( filepath ).exists() );
			}
			model.addAttribute("files", map);
		}
	}
	
	
	
	//파일 다운로드
	public void fileDownload(String filepath, String filename
							, HttpServletRequest request
							, HttpServletResponse response) throws Exception {
		// url경로:      http://localhost:8080/smart/upload/ notice/2024/09/02/f1b6d4d3-e799-4437-b9ab-8d41d93ac0bf.pdf
		// 물리적실제경로                 d://smart/app/upload/ notice/2024/09/02/f1b6d4d3-e799-4437-b9ab-8d41d93ac0bf.pdf
		filepath = toRealFilePath(filepath, request);
		
		//클라이언트컴에 쓰기작업할 파일의 타입
		String mime = request.getServletContext().getMimeType(filename);
		response.setContentType(mime);
		
		//파일명에 있는 한글 처리, 공백 처리
		filename = URLEncoder.encode( filename, "utf-8" ).replaceAll("\\+", "%20");
		response.setHeader("content-disposition", "attachment; filename=" + filename);
		FileCopyUtils.copy( new FileInputStream(filepath), response.getOutputStream());
	}
	
	//다중 파일 업로드
	public ArrayList<FileVO> fileUpload(String category, MultipartFile[] files
							, HttpServletRequest request) {
		
		ArrayList<FileVO> list = null;
		for( MultipartFile file : files ) {
			if( file.isEmpty() ) continue;
			if(list == null) list = new ArrayList<FileVO>();
			FileVO vo = new FileVO();
			vo.setFilename( file.getOriginalFilename() );
			vo.setFilepath( fileUpload(category, file, request) );
			list.add(vo);
		}
		return list;
	}
	
	//단일 파일 업로드
	public String fileUpload(String category, MultipartFile file
							, HttpServletRequest request) {
		// d://smart/app/upload/ profile /2024/08/27
		// d://smart/app/upload/ notice  /2024/08/28
		// d://smart/app/upload/ board   /2024/09/01
		String upload = uploadPath + category 
						+ new SimpleDateFormat("/yyyy/MM/dd/").format(new Date());
		//해당 폴더의 존재유무를 확인해 없다면 폴더 만들기
		File dir = new File( upload );
		if( ! dir.exists() ) dir.mkdirs();
		
		// 업로드할 파일명을 고유한 id로 변경하기 : ad24-3ag-f234-adf-h.jpg
		String filename = UUID.randomUUID().toString() + "."
						+ StringUtils.getFilenameExtension( file.getOriginalFilename() );
		//클라이언트에서 선택한 파일을 서버의 영역에 물리적으로 저장
		try {
			file.transferTo( new File(upload, filename) );
		}catch(Exception e) {}
	
		// d://smart/app/upload/profile/2024/08/27/ad24-3ag-f234-adf-h.jpg
		// http://localhost:8080/smart/upload/profile/2024/08/27/ad24-3ag-f234-adf-h.jpg
		// http://localhost:8080/smart
		return  toUrlFilePath(upload, request) + filename;  
	}
	
	//물리적형태 -> url형태로 바꾸기
	//uploadPath;    			  d://smart/app/upload/
	//물리적형태  				      d://smart/app/upload/  profile/2024/08/27/
	//url형태 		http://localhost:8080/smart/upload/  profile/2024/08/27/
	public String toUrlFilePath(String filepath, HttpServletRequest request) {
		return filepath.replace( uploadPath,  appURL(request, "/upload/") ); 
	}
	
	//url형태 -> 실제물리적형태로 바꾸기
	//url형태 		http://localhost:8080/smart/upload/  profile/2024/08/27/
	//물리적형태  				      d://smart/app/upload/  profile/2024/08/27/
	public String toRealFilePath(String filepath, HttpServletRequest request) {
		return filepath.replace( appURL(request, "/upload/"), uploadPath );
	}
	
	
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
	
	//공공데이터 응답결과 
	public JSONObject responseBody( String url ) {
		JSONObject json = new JSONObject( requestAPI(url) );
		return json.getJSONObject( "response" ).getJSONObject( "body" );
	}
	
	
	
	private void mailSender(HtmlEmail sender) {
		sender.setDebug(true);
		sender.setCharset("utf-8");
		
		sender.setHostName(emailHost);
		sender.setAuthentication(emailUser, emailPass); //이메일주소, 비번
		sender.setSSLOnConnect(true); //로그인버튼 클릭
	}
	
	//회원가입축하 메시지 보내기
	public void emailForJoin(MemberVO vo, String filename) {
		HtmlEmail sender = new HtmlEmail();
		mailSender( sender );
		
		try {
			sender.setFrom(emailUser, "스마트IoT 관리자"); //송신인
			sender.addTo(vo.getEmail(), vo.getName()); //수신인
		
			sender.setSubject("스마트IoT 회원가입 축하"); //제목
			StringBuffer content = new StringBuffer(); //내용
			content.append("<h3><a target='_blank' href='https://www.naver.com/'>스마트IoT</a></h3>")
				   .append("<div>[<strong>").append(vo.getName())
				   		.append("</strong>]님 회원가입을 축하합니다</div>")
				   .append("<div>당신의 취업성공을 응원합니다</div>")
				   ;
			sender.setHtmlMsg( content.toString() );
			
			EmailAttachment file = new EmailAttachment(); //파일첨부하기
			file.setPath( filename );
			sender.attach(file);
					
			sender.send();//보내기
			
		}catch(Exception e) {
		}
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
