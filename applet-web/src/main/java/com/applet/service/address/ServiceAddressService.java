package com.applet.service.address;

import com.applet.common.result.ResultModel;
import com.applet.entity.request.address.CreateAddressReq;
import org.springframework.stereotype.Service;

@Service
public class ServiceAddressService {
    public ResultModel<?> createServiceAddress(CreateAddressReq req) {

        return ResultModel.succ();
    }
}
