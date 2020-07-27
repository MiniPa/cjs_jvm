package com.bxg.hessiandemo;

/**
 * HessianServiceImpl:
 *
 * @version 0.0.1  @date: 2020-07-19
 * @author: <a href="mailto:chengjs_minipa@outlook.com">chengjs_minipa</a>,
 */
public class HessianServiceImpl implements IHessianService{

    @Override
    public String getString(String value) {
        return "REQ " + value;
    }

    @Override
    public Bean getBean() {
        Bean a = new Bean("value");
        return a;
    }

}
