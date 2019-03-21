package com.applet.controller.address;

import com.applet.entity.request.address.CreateAddressReq;
import com.applet.entity.result.ResultModel;
import com.applet.service.address.ServiceAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(value = "applet-address-api", description = "用户服务地址")
@RestController
@RequestMapping("/address")
public class ServiceAddressController {

    @Autowired
    ServiceAddressService serviceAddressService;

    @ApiOperation(value = "新建服务地址", notes = "新建服务地址", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "成功时,data示例", response = ResultModel.class)})
    @PostMapping("/createAddress")
    public ResultModel<?> createAddress(@RequestBody @Valid CreateAddressReq req, BindingResult result) {
        if (result.hasErrors()) {
            return ResultModel.fail(result.getFieldError().getDefaultMessage());
        }


        return serviceAddressService.createServiceAddress(req);
    }
}
