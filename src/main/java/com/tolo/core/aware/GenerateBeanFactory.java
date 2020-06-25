package com.tolo.core.aware;

/**
 * create by wly
 * date: 2020/05/21
 */
public interface GenerateBeanFactory extends Factory {

    GenerateFactory cacheBean(String param);

    GenerateFactory getFactory(String type);
}

