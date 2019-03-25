package com.applet.controller.area;

import com.applet.common.result.ResultModel;
import com.applet.service.area.AreaService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

//@Api(value = "区域管理接口", description = "区域管理接口")
@RestController
@RequestMapping("/area")
public class AreaController {

    @Autowired
    AreaService areaService;

    @ApiOperation(value = "省市区联动", notes = "省市区联动", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 20000, message = "成功时,data示例", response = ResultModel.class)})
    @GetMapping(value="/areaList")
    public ResultModel<?> areaList(
            @NotEmpty
            @ApiParam(value = "地区类型（sheng,shi,qu）", name = "areaType")
            @RequestParam(value="areaType") String areaType,
            @NotEmpty
            @ApiParam(value = "parentId", name = "parentId")
            @RequestParam(value="parentId",defaultValue = "1") Integer parentId){

        ResultModel resMsg = areaService.areaList(areaType,parentId);
        return  resMsg;

    }

    @ApiOperation(value = "省市区联动全量数据", notes = "省市区联动全量数据", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 20000, message = "成功时,data示例", response = ResultModel.class)})
    @GetMapping(value="/areaAll")
    public ResultModel<?> areaAll(){

        List<Map<String, Object>> mapList = areaService.areaCityAll();
        return  ResultModel.succWithData(mapList);

    }
}
