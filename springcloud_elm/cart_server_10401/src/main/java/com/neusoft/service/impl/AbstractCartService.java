package com.neusoft.service.impl;

import com.neusoft.po.Cart;
import com.neusoft.service.CartService;

import java.util.List;

public abstract class AbstractCartService implements CartService {

    protected abstract List<Cart> listCartInternal(Cart cart);

    protected abstract int saveCartInternal(Cart cart);

    protected abstract int updateCartInternal(Cart cart);

    protected abstract int removeCartInternal(Cart cart);

    @Override
    public List<Cart> listCart(Cart cart) {
        return listCartInternal(cart);
    }

    @Override
    public int saveCart(Cart cart) {
        return saveCartInternal(cart);
    }

    @Override
    public int updateCart(Cart cart) {
        return updateCartInternal(cart);
    }

    @Override
    public int removeCart(Cart cart) {
        return removeCartInternal(cart);
    }
}
