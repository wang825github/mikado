package com.shinetech.dalian.mikado.controller;

import com.shinetech.dalian.mikado.entity.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value="/packagingData")
public class PackagingDataManageController {
    @RequestMapping(value="/manage")
    public String deliveryManagePage(HttpServletRequest request){


        return "jsp/packaging-data-manage";
    }
}
