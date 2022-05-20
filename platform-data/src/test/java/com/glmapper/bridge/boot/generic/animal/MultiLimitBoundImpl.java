package com.glmapper.bridge.boot.generic.animal;

import javax.annotation.PostConstruct;

/**
 * @author Advance
 * @date 2022年04月03日 14:10
 * @since V1.0.0
 */
public class MultiLimitBoundImpl implements MultiLimitInterfaceA,MultiLimitInterfaceB{

    @PostConstruct
    public void getParentData(){
        System.out.println(MultiLimitInterfaceA.ALL.trim());
    }


}
