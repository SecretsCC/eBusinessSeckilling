package com.seckillingProject.controller;

import com.alibaba.druid.sql.ast.expr.SQLCaseExpr;
import com.seckillingProject.controller.viewobject.ItemVO;
import com.seckillingProject.error.BusinessException;
import com.seckillingProject.response.CommonReturnType;
import com.seckillingProject.service.ItemService;
import com.seckillingProject.service.model.ItemModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Controller("/item")
@RequestMapping("item")
@CrossOrigin(origins = {"*"},allowCredentials = "true")
public class ItemController extends BaseController{

    @Autowired
    private ItemService itemService;

    public CommonReturnType createItem(@RequestParam(name="title") String title,
                                       @RequestParam(name="description") String description,
                                       @RequestParam(name="price") BigDecimal price,
                                       @RequestParam(name="stock") Integer stock,
                                       @RequestParam(name="imgUrl") String imgUr) throws BusinessException {

        //package service request to create item
        ItemModel itemModel = new ItemModel();
        itemModel.setTitle(title);
        itemModel.setDescription(description);
        itemModel.setPrice(price);
        itemModel.setImgUrl(imgUr);
        itemModel.setStock(stock);

        ItemModel itemModelForReturn = itemService.createItem(itemModel);
        ItemVO itemVO = convertVOFromModel(itemModelForReturn);

        return CommonReturnType.create(itemVO);



    }

    private ItemVO convertVOFromModel(ItemModel itemModel){
        if(itemModel == null){
            return null;
        }
        ItemVO itemVO = new ItemVO();
        BeanUtils.copyProperties(itemModel,itemVO);
        return itemVO;
    }
}
