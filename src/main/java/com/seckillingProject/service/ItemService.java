package com.seckillingProject.service;

import com.seckillingProject.error.BusinessException;
import com.seckillingProject.service.model.ItemModel;

import java.util.List;

public interface ItemService {
    //create item
    ItemModel createItem(ItemModel itemModel) throws BusinessException;

    //item list
    List<ItemModel> listItem();

    //item detail
    ItemModel getItemById(Integer id);

    //decrease the stock
    boolean decreaseStock(Integer itemId, Integer amount) throws BusinessException;

    //increase sales
    void increaseSales(Integer itemId,Integer amount) throws BusinessException;

}
