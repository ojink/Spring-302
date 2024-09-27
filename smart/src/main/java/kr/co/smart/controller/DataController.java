package kr.co.smart.controller;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.smart.common.CommonUtility;
import kr.co.smart.common.PageVO;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller @RequestMapping("/data")
public class DataController {
	private final CommonUtility common;
	private String Key="FPgj2NXbJw46TcGkmAfZEiYFDbxilys7KLjk3KaB7AfeJE00ZhPNM0M8unwbsI69fSmT8SNfVEimE6ZZ2U14hA%3D%3D";
	
	private String animalURL = "http://apis.data.go.kr/1543061/abandonmentPublicSrvc/";
	
	//유기동물 보호소 조회 요청
	@RequestMapping("/animal/shelter")
	public String animalShelter(String sido, String sigungu, Model model) {
		StringBuffer url = new StringBuffer( animalURL );
		url.append("shelter?serviceKey=").append(Key)
		   .append("&_type=json")
		   .append("&upr_cd=").append(sido)
		   .append("&org_cd=").append(sigungu)
		;		
		model.addAttribute("list"
				, common.responseBody( url.toString() ).toMap() );
		return "data/animal/shelter";
	}
	
	//유기동물 시도 조회 요청
	@RequestMapping("/animal/sido")
	public String animalSido( Model model ) {
		StringBuffer url = new StringBuffer( animalURL );
		url.append("sido?serviceKey=").append(Key)
		   .append("&_type=json")
		   .append("&numOfRows=50")
		;
		
		model.addAttribute( "list", common.responseBody(url.toString()).toMap() );
		
		return "data/animal/sido";
	}
	
	//유기동물 시군구 조회 요청
	@RequestMapping("/animal/sigungu")
	public String animalSigungu(String sido, Model model) {
		StringBuffer url = new StringBuffer( animalURL );
		url.append("sigungu?serviceKey=").append(Key)
		   .append("&_type=json")
		   .append("&upr_cd=").append(sido)
		   ;
		
		model.addAttribute("list"
						, common.responseBody(url.toString()).toMap() );
		
		return "data/animal/sigungu";
	}
	
	//유기동물 품종 조회 요청
	@RequestMapping("animal/kind")
	public String animalKind(String upkind, Model model) {
		StringBuffer url = new StringBuffer( animalURL);
		url.append("kind?serviceKey=").append(Key)
		   .append("&_type=json")
		   .append("&up_kind_cd=").append(upkind)
			;
		model.addAttribute("list", common.responseBody(url.toString()).toMap() );
		
		return "data/animal/kind";
	}
	
	
	//유기동물 목록 조회 요청
	@RequestMapping("/animal/list/{pageNo}/{listSize}")
	public String animalList( @RequestBody HashMap<String, Object> map,  PageVO page, Model model) {
		StringBuffer url = new StringBuffer( animalURL );
		url.append("abandonmentPublic?serviceKey=").append(Key)
		   .append("&_type=json")
		   .append("&pageNo=").append(page.getPageNo())
		   .append("&numOfRows=").append(page.getListSize())
		   .append("&upr_cd=").append(map.get("sido"))
		   .append("&org_cd=").append(map.get("sigungu"))
		   .append("&care_reg_no=").append(map.get("shelter"))
		   .append("&upkind=").append(map.get("upkind"))
		   .append("&kind=").append(map.get("kind"))
		   ;
		
		//JSONObject json = common.responseBody( url.toString() );
		//model.addAttribute("animal", json.toMap());
		
		JSONObject json = common.response( url.toString() );
		
		//오류가 있다면 오류내용을 출력할 수 있게 하기
		model.addAttribute("response", json.getJSONObject("header").toMap() );
		// "reqNo" : 306273111,      "resultCode" : "10",      
		// "resultMsg" : "INVALID REQUEST PARAMETER ERROR.",     
		// "errorMsg" : "kind=00216 → 품종코드 요청변수 오류 - 품종 조회 OPEN API 참조" 
		
		json = json.getJSONObject("body");
		model.addAttribute("animal", json.toMap());
		
		page.setTotalList( json.getInt("totalCount") );
		model.addAttribute("subPage", page);
		
		return "data/animal/list";
	}
	
	//약국목록 조회 요청 - 화면을 보내는 경우
	@RequestMapping("/pharmacy")
	public Object pharmacy(Model model, PageVO page) {
		//공공데이터 포털에서 약국정보 조회해오기
		//http://apis.data.go.kr/B551182/pharmacyInfoService/getParmacyBasisList?_type=json&ServiceKey=FPgj2NXbJw46TcGkmAfZEiYFDbxilys7KLjk3KaB7AfeJE00ZhPNM0M8unwbsI69fSmT8SNfVEimE6ZZ2U14hA%3D%3D
		StringBuffer url = new StringBuffer(
				"http://apis.data.go.kr/B551182/pharmacyInfoService/getParmacyBasisList");
		url.append("?_type=json");
		url.append("&ServiceKey=").append(Key)
		   .append("&pageNo=").append(page.getPageNo())
		   .append("&numOfRows=").append(page.getListSize())
		;
		
		JSONObject json =  common.responseBody( url.toString() );
		model.addAttribute("pharmacy", json.toMap());
		
		page.setTotalList( json.getInt("totalCount") );
		model.addAttribute("subPage", page);
		return "data/pharmacy/list";
	}
	
	//약국목록 조회 요청 - 데이터를 보내는 경우
	//@ResponseBody @RequestMapping("/pharmacy")
	public Object pharmacy() {
		//공공데이터 포털에서 약국정보 조회해오기
		//http://apis.data.go.kr/B551182/pharmacyInfoService/getParmacyBasisList?_type=json&ServiceKey=FPgj2NXbJw46TcGkmAfZEiYFDbxilys7KLjk3KaB7AfeJE00ZhPNM0M8unwbsI69fSmT8SNfVEimE6ZZ2U14hA%3D%3D
		StringBuffer url = new StringBuffer(
				"http://apis.data.go.kr/B551182/pharmacyInfoService/getParmacyBasisList");
		url.append("?_type=json");
		url.append("&ServiceKey=").append(Key);
		JSONObject json =  common.responseBody( url.toString() );
		return json.toMap();
	}
	
	
	
	//공공데이터 목록화면 요청
	@RequestMapping("/list")
	public String list(HttpSession session) {
		session.setAttribute("category", "da");
		return "data/list";
	}

}
