package com.applet.service.address;

import com.applet.entity.request.address.CreateAddressReq;
import com.applet.entity.result.ResultModel;
import org.springframework.stereotype.Service;

@Service
public class ServiceAddressService {
    public ResultModel<?> createServiceAddress(CreateAddressReq req) {


        return ResultModel.succ();
    }
}
