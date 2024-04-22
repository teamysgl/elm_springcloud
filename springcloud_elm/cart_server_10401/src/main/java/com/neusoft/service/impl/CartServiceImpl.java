//模板方法模式
/*模板方法模式可以将一些共同的行为放在一个抽象类中，然后由具体的子类实现具体的行为。创建一个抽象的 AbstractCartService 类，其中包含通用的方法实现，然后 CartServiceImpl 类继承这个抽象类并实现具体的方法。*/

package com.neusoft.service.impl;

import com.neusoft.mapper.CartMapper;
import com.neusoft.po.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl extends AbstractCartService {

    @Autowired
    private CartMapper cartMapper;

    @Override
    protected List<Cart> listCartInternal(Cart cart) {
        return cartMapper.listCart(cart);
    }

    @Override
    protected int saveCartInternal(Cart cart) {
        return cartMapper.saveCart(cart);
    }

    @Override
    protected int updateCartInternal(Cart cart) {
        return cartMapper.updateCart(cart);
    }

    @Override
    protected int removeCartInternal(Cart cart) {
        return cartMapper.removeCart(cart);
    }
}
