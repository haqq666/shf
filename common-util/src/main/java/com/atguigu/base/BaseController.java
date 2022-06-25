package com.atguigu.base;

import org.springframework.ui.Model;

/**
 * @author HaQQ
 * @version 1.0
 * @date 2022/6/13 15:57
 */

public class BaseController {

    private final static String PAGE_SUCCESS = "common/successPage";
    public String successPage(Model model,String successMessage){
        model.addAttribute("messagePage",successMessage);
        return PAGE_SUCCESS;
    };

}
