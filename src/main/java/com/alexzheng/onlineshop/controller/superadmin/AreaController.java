package com.alexzheng.onlineshop.controller.superadmin;

import com.alexzheng.onlineshop.entity.Area;
import com.alexzheng.onlineshop.service.AreaService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Alex Zheng
 * @Date created in 17:51 2020/4/6
 * @Annotation
 */
@Controller
@RequestMapping("/superadmin")
public class AreaController {

//    注意 包不要引错
    private static Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);


    @Autowired
    private AreaService areaService;

    @GetMapping("/listarea")
    @ResponseBody
    public Map<String,Object> listArea(){
        logger.info("===start===");
        long startTime = System.currentTimeMillis();
        Map<String,Object> modelMap = new HashMap<>();
        List<Area> areaList = new ArrayList<>();
        try{
            areaList = areaService.getAreaList();
            modelMap.put("rows",areaList);
            modelMap.put("total",areaList.size());
        }catch (Exception e){
            e.printStackTrace();
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
        }
        long endTime = System.currentTimeMillis();
        logger.debug("costTime:[{}ms]",endTime-startTime);
        logger.info("===end===");
        return modelMap;
    }

}
