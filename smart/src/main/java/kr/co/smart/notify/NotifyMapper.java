package kr.co.smart.notify;

import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import kr.co.smart.common.LowerKeyMap;

@Mapper
public interface NotifyMapper {
	//인증된유저의 방명록에 대한 미확인 댓글 건수 조회
	LowerKeyMap countOfUncheckedComment( HashMap<String, Object> map);
}
