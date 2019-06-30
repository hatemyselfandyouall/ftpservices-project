package com.insigma.enums;

import star.modules.cache.enumerate.BaseCacheEnum;

/**
 * redis 缓存key
 * （按业务名称命名定义，大家不要重复了）
 * @author jinxm
 * @since:2019年5月15日下午15:08
 */
public enum OpenapiCacheEnum implements BaseCacheEnum {

	/**
	 * APPkey对应的接口列表
	 */
    OPENAPI_BY_APPKEY("work_openapi_by_appkey"),

	/**
	 * APPid对应的接口列表
	 */
	OPENAPI_BY_APPID("work_openapi_by_appid"),

    /**
     * APPid对应的接口列表
     */
    INTERFACE_BY_CODE("work_interface_by_code");
	private String type;

    OpenapiCacheEnum(String type) {
		this.type = type;
	}

	@Override
	public String getAnchor() {
		return this.type;
	}

	@Override
	public BaseCacheEnum get(String key) {
		if (key != null && key.length() > 0) {
			for (OpenapiCacheEnum type : OpenapiCacheEnum.values()) {
				if (key.equals(type.getAnchor())) {
					return type;
				}
			}
		}
		return null;
	}

}
