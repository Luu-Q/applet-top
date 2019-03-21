package com.applet.controller.item;

import com.applet.entity.result.ResultModel;
import com.applet.service.item.ServiceItemService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//@Api(value = "service-api", description = "产品API")
@RestController
@RequestMapping("/item")
public class ServiceItemController {

    @Autowired
    ServiceItemService itemService;

    @ApiOperation(value = "产品大类", notes = "产品大类", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "成功时,data示例", response = ResultModel.class)})
    @GetMapping("/bigItem")
    public ResultModel<?> bigItem() {

        return itemService.bigItem();
    }

    @ApiOperation(value = "产品大类", notes = "产品大类", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "成功时,data示例", response = ResultModel.class)})
    @GetMapping("/iemtSpu")
    public ResultModel<?> iemtSpu(@RequestParam(value = "itemCode") String itemCode) {

        return itemService.iemtSpu(itemCode);
    }
}
