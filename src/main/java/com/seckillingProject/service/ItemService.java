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

}
