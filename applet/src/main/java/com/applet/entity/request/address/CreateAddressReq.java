package com.applet.entity.request.address;


import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@ApiModel(value = "用户创建服务地址")
public class CreateAddressReq {
//
//     `id` int(11) NOT NULL AUTO_INCREMENT,
//  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
//            `contact_name` varchar(30) COLLATE utf8_bin DEFAULT '' COMMENT '被服务联系人',
//            `mobile` varchar(20) COLLATE utf8_bin DEFAULT '' COMMENT '被服务手机号',
//            `address` varchar(200) COLLATE utf8_bin DEFAULT '' COMMENT '详细地址',
//            `lng_lat` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '经纬度',
//            `landmarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '地标名称',
//            `area` int(11) DEFAULT '0' COMMENT '面积',
//            `is_del` varbinary(1) DEFAULT '0' COMMENT '0正常',
//            `is_default` tinyint(1) DEFAULT '0' COMMENT '1为默认地址0正常',

    private int user_id;


}
