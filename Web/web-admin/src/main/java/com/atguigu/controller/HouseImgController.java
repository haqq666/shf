package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.House;
import com.atguigu.entity.HouseImage;
import com.atguigu.result.Result;
import com.atguigu.service.HouseImgService;
import com.atguigu.service.HouseService;
import com.atguigu.util.FileUtil;
import com.atguigu.util.QiniuUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author HaQQ
 * @version 1.0
 * @date 2022/6/17 19:15
 */
@Controller
@RequestMapping("/houseImage")
public class HouseImgController extends BaseController {

    public static final String PAGE_UPLOAD = "house/upload";
    public static final String SHOW_DETAIL = "redirect:/house/";

    @Reference
    private HouseImgService houseImgService;
    @Reference
    private HouseService houseService;

    @RequestMapping("/uploadShow/{houseId}/{type}")
    public String uploadShow(@PathVariable("houseId") Long houseId,
                             @PathVariable("type")Integer type,
                             Model model){

        model.addAttribute("houseId",houseId);
        model.addAttribute("type",type);

       return PAGE_UPLOAD;
    }
    @PostMapping("/upload/{houseId}/{type}")
    @ResponseBody
    public Result upload(@PathVariable("houseId")Long houseId,
                         @PathVariable("type") Integer type,
                         @RequestParam("file")MultipartFile[] files) throws IOException {


        for (int i = 0;i < files.length;i++) {
//上传图片到七牛云
        // 获取文件的名字
            String originalFilename = files[i].getOriginalFilename();
        //获取文件的唯一的名字
            String uuidName = FileUtil.getUUIDName(originalFilename);
        //将文件上传到七牛云
            QiniuUtils.upload2Qiniu(files[i].getBytes(),uuidName);

//保存路径到数据库
            House house = houseService.getById(houseId);
            if (house.getDefaultImageUrl() ==  null|| "".equals(house.getDefaultImageUrl()) || "null".equalsIgnoreCase(house.getDefaultImageUrl())){
               house.setDefaultImageUrl(QiniuUtils.getUrl(uuidName));
               houseService.update(house);
            }

           //获取文件路径
            String url = QiniuUtils.getUrl(uuidName);

//            将文件路径保存到数据库
            HouseImage houseImage = new HouseImage();
            houseImage.setHouseId(houseId);
            houseImage.setImageName(uuidName);
            houseImage.setType(type);
            houseImage.setImageUrl(url);
            houseImgService.insert(houseImage);
        }
        return Result.ok();
    }

    //删除
    @RequestMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable("houseId")Long houseId,
                         @PathVariable("id")Long id){
        //获得图片
        HouseImage houseImage = houseImgService.getById(id);

        //删除
        //七牛云里面删除
        QiniuUtils.deleteFileFromQiniu(houseImage.getImageName());
        //数据库里删除
        houseImgService.delete(id);

        //判断删除的是否是默认图片，如果是就设置默认图片为null
        House house = houseService.getById(houseId);
        if (houseImage.getImageUrl().equals(house.getDefaultImageUrl())) {
            house.setDefaultImageUrl("null");
            houseService.update(house);
        }

        return SHOW_DETAIL + houseId;
    }


}
