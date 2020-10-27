package com.platform.common.util;

import java.util.Collection;
import java.util.Map;

import com.platform.common.exception.BaseException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;


public class AssertUtil {

	public static void notNull(Object object, String message) {
		if (object == null) {
			throw new BaseException(message);
		}
	}
	
	public static void hasText(String text, String message) {
		if (StringUtils.isBlank(text)) {
			throw new BaseException(message);
		}
	}
	
	public static void notEmpty(Collection<?> collection, String message) {
		if (CollectionUtils.isEmpty(collection)) {
			throw new BaseException(message);
		}
	}
	
	public static void notEmpty(Map<?, ?> map, String message) {
		if (map == null || map.isEmpty()) {
			throw new BaseException(message);
		}
	}
	
}
