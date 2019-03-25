package com.applet.controller;

import com.applet.common.result.ResultModel;
import com.applet.dao.mongodb.MongoTestRepository;
import com.applet.entity.mongodb.MongoTestEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Api(value = "applet-order-api", description = "test")
@RequestMapping(value = "/mg")
@RestController
public class MongoDbController {


    @Autowired
    MongoTestRepository mongoTestRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private MongoOperations mongoOperations;

    @ApiOperation(value = "mongodb测试", notes = "mongodb测试", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "成功时,data示例", response = ResultModel.class)})
    @GetMapping("/mfind")
    public ResultModel<?> mfind() {

        MongoTestEntity mongoTestEntity1 = new MongoTestEntity();
        mongoTestEntity1.setFirstName("luning");
        mongoTestEntity1.setLastName("luning1");
        mongoTestEntity1.setMobile("15710098442");
        mongoTestEntity1.setJson("sdsdgsdfgsdfgsdfg");
        mongoTestEntity1.setTest("test");

        mongoTemplate.save(mongoTestEntity1);



        MongoTestEntity mongoTestEntity = mongoTestRepository.findByFirstName("luning");

        return ResultModel.succWithData(mongoTestEntity);
    }
}
