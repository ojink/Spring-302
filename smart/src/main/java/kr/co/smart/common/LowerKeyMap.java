package kr.co.smart.common;

import org.apache.commons.collections4.map.ListOrderedMap;
import org.springframework.stereotype.Component;

@Component
public class LowerKeyMap  extends ListOrderedMap<String, Object>{

	@Override
	public Object put(String key, Object value) {
		return super.put(key.toLowerCase(), value);
	}
		
}
