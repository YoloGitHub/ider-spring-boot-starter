package com.tolo.core.aware;

/**
 * create by wly
 * date: 2020/05/21
 */
public interface GenerateFactory extends Factory {

    Object generate();

    String generateString();

}
