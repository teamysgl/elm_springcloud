package com.neusoft.controller;

import com.neusoft.po.CommonResult;
import com.neusoft.po.Wallet;
import com.neusoft.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/WalletController")
public class WalletController {
    @Autowired
    private WalletService walletService;

    @GetMapping("/listWallets")
    public CommonResult<List<Wallet>> listWallets(@RequestParam("userId") String userId, @RequestHeader("Authorization") String token) throws Exception {
        return new CommonResult<>(200, "success", walletService.listWallets(userId, token));
    }

    @PostMapping("/deposit/{userId}/{amount}")
    public CommonResult<Integer> deposit(@PathVariable("userId") String userId, @PathVariable("amount") double amount) {
        return new CommonResult<>(200, "success", walletService.deposit(userId, amount));
    }

    @PostMapping("/withdraw/{userId}/{amount}")
    public CommonResult<Integer> withdraw(@PathVariable("userId") String userId, @PathVariable("amount") double amount) {
        return new CommonResult<>(200, "success", walletService.withdraw(userId, amount));
    }

    @PostMapping("/expense/{userId}/{amount}")
    public CommonResult<Integer> transfer(@PathVariable("userId") String userId,@PathVariable("amount") double amount) {
        return new CommonResult<>(200, "success", walletService.expense(userId, amount));
    }

    @GetMapping("/getWalletBalance/{userId}")
    public CommonResult<Double> getWalletBalance(@PathVariable("userId") String userId) {
        return new CommonResult<>(200, "success", walletService.getWalletBalance(userId));
    }
}
