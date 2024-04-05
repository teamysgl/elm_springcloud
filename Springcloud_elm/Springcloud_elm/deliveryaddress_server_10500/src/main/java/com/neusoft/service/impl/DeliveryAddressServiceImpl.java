package com.neusoft.service.impl;

import com.neusoft.mapper.DeliveryAddressMapper;
import com.neusoft.po.DeliveryAddress;
import com.neusoft.service.DeliveryAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryAddressServiceImpl implements DeliveryAddressService {

    @Autowired
    private DeliveryAddressMapper deliveryAddressMapper;

    @Override
    public List<DeliveryAddress> listDeliveryAddressByUserId(String userId) {
        return deliveryAddressMapper.listDeliveryAddressByUserId(userId);
    }

    @Override
    public DeliveryAddress getDeliveryAddressById(Integer daId) {
        return deliveryAddressMapper.getDeliveryAddressById(daId);
    }

    @Override
    public int saveDeliveryAddress(DeliveryAddress deliveryAddress) {
        return deliveryAddressMapper.saveDeliveryAddress(deliveryAddress);
    }

    @Override
    public int updateDeliveryAddress(DeliveryAddress deliveryAddress) {
        DeliveryAddress da = deliveryAddressMapper.getDeliveryAddressById(deliveryAddress.getDaId());
        deliveryAddress.setUsed(da.getUsed());
        deliveryAddress.setDeleted(da.getDeleted());
        if (da.getUsed() == 1) {
            deliveryAddressMapper.saveDeliveryAddress(deliveryAddress);
            deliveryAddress.setDeleted(1);
        }
        return deliveryAddressMapper.updateDeliveryAddress(deliveryAddress);
    }

    @Override
    public int removeDeliveryAddress(Integer daId) {
        DeliveryAddress deliveryAddress = deliveryAddressMapper.getDeliveryAddressById(daId);
        if (deliveryAddress == null) return 0;
        if (deliveryAddress.getUsed() == 1) {
            deliveryAddress.setDeleted(1);
            return deliveryAddressMapper.updateDeliveryAddress(deliveryAddress);
        } else return deliveryAddressMapper.removeDeliveryAddress(daId);
    }
}
