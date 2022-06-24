/**
 * Copyright &copy; 2012-2015. All rights reserved.
 */
package com.braindata.gateway.controller;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 控制器支持类
 * @author ThinkStone
 * @version 2013-3-23
 */
@Controller
public abstract class BaseController {
    /**
     * 解决String和javabean中的Date的属性自动转换
     * @param binder
     */
    /**
     * 接收参数转换
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        //所有的Date的子类和Date 参数 ，都会被这个自定义转换
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        //所有的String都会被trim
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}
