package com.tolo.idFactory.impl;


import com.tolo.core.aware.GenerateBeanFactory;
import com.tolo.core.aware.GenerateFactory;
import lombok.Data;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * create by wly
 * date: 2020/05/22
 */
@Data
public abstract class AbstractStepFactory implements GenerateBeanFactory {

    private String separator = ",";

    private Map<String, String> input = new HashMap<>();

    private Map<String, GenerateFactory> tableCache = new HashMap<>();

    @PostConstruct
    public void init(){

        input.forEach((k, v)->{

            tableCache.put(k, cacheBean(v));
        });
    }

    @Override
    public GenerateFactory getFactory(String type) {

        GenerateFactory result = tableCache.get(type);
        if(result == null){
            throw new RuntimeException("please set thie idType : " + type);
        }
        return result;
    }
}
