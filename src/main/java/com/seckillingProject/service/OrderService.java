package com.seckillingProject.service;

import com.seckillingProject.error.BusinessException;
import com.seckillingProject.service.model.OrderModel;

public interface OrderService {
    OrderModel createOrder(Integer userId,Integer itemId,Integer promoId, Integer amount) throws BusinessException;
}
