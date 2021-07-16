package com.skl.community.community.strategy;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shoukailiang
 * @version 1.0
 * @date 2021/7/16 20:05
 */
@Service
public class UserStrategyFactory {

    @Autowired
    private List<UserStrategy> strategies;

    public UserStrategy getStrategy(String type){
        for (UserStrategy strategy:strategies){
            if(StringUtils.equals(strategy.getSupportedType(),type)){
                return strategy;
            }
        }
        return null;
    }




}
