package com.neusoft.controller;

import com.neusoft.po.CommonResult;
import com.neusoft.po.DeliveryAddress;
import com.neusoft.service.DeliveryAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RefreshScope
@RestController
@RequestMapping("/DeliveryAddressController")
public class DeliveryAddressController {
    @Autowired
    private DeliveryAddressService deliveryAddressService;

    @GetMapping("/listDeliveryAddressByUserId/{userId}")
    public CommonResult<List<DeliveryAddress>> listDeliveryAddressByUserId(@PathVariable("userId") String userId) throws Exception {
        List<DeliveryAddress> list = deliveryAddressService.listDeliveryAddressByUserId(userId);
        return new CommonResult<>(200, "success", list);
    }

    @GetMapping("/getDeliveryAddressById/{daId}")
    public CommonResult<DeliveryAddress> getDeliveryAddressById(@PathVariable("daId") Integer daId) throws Exception {
        DeliveryAddress deliveryAddress=deliveryAddressService.getDeliveryAddressById(daId);
        return new CommonResult<>(200, "success", deliveryAddress);
    }

    @PostMapping("/saveDeliveryAddress/{contactName}/{contactSex}/{contactTel}/{address}/{userId}")
    public CommonResult<Integer> saveDeliveryAddress(
            @PathVariable("contactName") String contactName,
            @PathVariable("contactSex") Integer contactSex,
            @PathVariable("contactTel") String contactTel,
            @PathVariable("address") String address,
            @PathVariable("userId") String userId
    ) throws Exception {
        DeliveryAddress param=new DeliveryAddress();
        param.setContactName(contactName);
        param.setContactSex(contactSex);
        param.setContactTel(contactTel);
        param.setAddress(address);
        param.setUserId(userId);
        int result=deliveryAddressService.saveDeliveryAddress(param);
        return new CommonResult<>(200, "success", result);
    }

    @PutMapping("/updateDeliveryAddress/{daId}/{contactName}/{contactSex}/{contactTel}/{address}")
    public CommonResult<Integer> updateDeliveryAddress(
            @PathVariable("daId") Integer daId,
            @PathVariable("contactName") String contactName,
            @PathVariable("contactSex") Integer contactSex,
            @PathVariable("contactTel") String contactTel,
            @PathVariable("address") String address
    ) throws Exception {
        DeliveryAddress param=new DeliveryAddress();
        param.setDaId(daId);
        param.setContactName(contactName);
        param.setContactSex(contactSex);
        param.setContactTel(contactTel);
        param.setAddress(address);
        int result=deliveryAddressService.updateDeliveryAddress(param);
        return new CommonResult<>(200, "success", result);
    }

    @DeleteMapping("/removeDeliveryAddress/{daId}")
    public CommonResult<Integer> removeDeliveryAddress(@PathVariable("daId") Integer daId) throws Exception {
        int result=deliveryAddressService.removeDeliveryAddress(daId);
        return new CommonResult<>(200, "success", result);
    }

    @GetMapping("/AddressInfo")
    public ArrayList<String> getAddressInfo(@RequestParam("addressInfo") String addressInfo) throws Exception {
        String serverName = "localhost";
        int port = 9999;
        Socket client = new Socket(serverName, port);
        OutputStream outToServer = client.getOutputStream();
        DataOutputStream out = new DataOutputStream(outToServer);

        out.writeUTF(addressInfo);
        out.flush();
        InputStream inFromServer = client.getInputStream();
        DataInputStream in = new DataInputStream(inFromServer);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        String s1 = reader.readLine();
        System.out.println("服务器响应： " + s1);
        String[] tmp = s1.split("\\t");
        for (String i : tmp) {
            System.out.println(i);
        }
        client.close();
        reader.close();
        return new ArrayList<String>(Arrays.asList(tmp));
    }
}