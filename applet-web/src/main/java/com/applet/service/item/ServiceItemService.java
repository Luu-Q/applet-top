package com.applet.service.item;

import com.applet.common.result.ResultModel;
import com.applet.dao.item.ServiceItemMapper;
import com.applet.model.item.ServiceItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ServiceItemService {

    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    ServiceItemMapper itemMapper;

    public ResultModel<?> bigItem() {
        ServiceItem serviceItem = new ServiceItem();
        serviceItem.setParentId(0);
        serviceItem.setIsDel(0);
        serviceItem.setStatus(1);

        List<ServiceItem> serviceItems = itemMapper.selectByKey(serviceItem);


        return ResultModel.succWithData(serviceItems);
    }


    public ResultModel<?> iemtSpu(String itemCode) {
        return null;
    }
}
