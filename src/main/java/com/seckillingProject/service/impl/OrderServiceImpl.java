package com.seckillingProject.service.impl;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import com.seckillingProject.dao.OrderDOMapper;
import com.seckillingProject.dao.SequenceDOMapper;
import com.seckillingProject.dataobject.OrderDO;
import com.seckillingProject.dataobject.SequenceDO;
import com.seckillingProject.error.BusinessException;
import com.seckillingProject.error.EmBusinessError;
import com.seckillingProject.service.ItemService;
import com.seckillingProject.service.OrderService;
import com.seckillingProject.service.UserService;
import com.seckillingProject.service.model.ItemModel;
import com.seckillingProject.service.model.OrderModel;
import com.seckillingProject.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderDOMapper orderDOMapper;

    @Autowired
    private SequenceDOMapper sequenceDOMapper;

    @Override
    @Transactional
    public OrderModel createOrder(Integer userId, Integer itemId, Integer amount) throws BusinessException {
        //verify order status
        //check whether a user exists
        //check whether a Item exists
        ItemModel itemModel = itemService.getItemById(itemId);
        if(itemModel == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"Item doesn't exist");
        }
        UserModel userModel = userService.getUserById(userId);
        if(userModel == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"User doesn't exist");
        }
        if(amount <= 0 || amount > 99){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"order number illegal ");
        }

        //decrease the stock once order have been set
        boolean result = itemService.decreaseStock(itemId,amount);
        if(!result){
            throw new BusinessException(EmBusinessError.STOCK_NOT_ENOUGH);
        }

        //save order
        OrderModel orderModel = new OrderModel();
        orderModel.setUserId(userId);
        orderModel.setItemId(itemId);
        orderModel.setAmount(amount);
        orderModel.setItemPrice(itemModel.getPrice());
        orderModel.setOrderPrice(itemModel.getPrice().multiply(new BigDecimal(amount)));

        orderModel.setId(generateOrderNo());
        OrderDO orderDO = converFromOrderModel(orderModel);
        orderDOMapper.insertSelective(orderDO);

        //add item sales
        itemService.increaseSales(itemId,amount);

        //return to front end


        return orderModel;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private String generateOrderNo(){
        //16 digits order id
        StringBuilder sb = new StringBuilder();
        //pre 8 bit is year month day
        LocalDateTime now = LocalDateTime.now();
        String nowDate = now.format(DateTimeFormatter.ISO_DATE).replace("-","");
        sb.append(nowDate);


        //middle 6 is auto-increment number
        //generate order id
        //get current sequency
        int sequence = 0;
        SequenceDO sequenceDO = sequenceDOMapper.getSequenceByName("order_info");
        sequence = sequenceDO.getCurrentValue();
        sequenceDO.setCurrentValue(sequenceDO.getCurrentValue()+sequenceDO.getStep());
        sequenceDOMapper.updateByPrimaryKeySelective(sequenceDO);
        String sequenceStr = String.valueOf(sequence);
        for(int i = 0; i < 6 - sequenceStr.length();i++){
            sb.append(0);
        }
        sb.append(sequenceStr);

        //last 2 is position number
        //static for this project
        sb.append("00");

        return sb.toString();
    }

    private OrderDO converFromOrderModel(OrderModel orderModel){
        if(orderModel == null){
            return null;
        }
        OrderDO orderDO = new OrderDO();
        BeanUtils.copyProperties(orderModel,orderDO);
        orderDO.setItemPrice(orderModel.getItemPrice().doubleValue());
        orderDO.setOrderPrice(orderModel.getOrderPrice().doubleValue());

        return orderDO;

    }
}
