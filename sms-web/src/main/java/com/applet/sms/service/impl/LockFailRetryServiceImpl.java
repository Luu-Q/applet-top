package com.applet.sms.service.impl;

import com.applet.common.result.ResultModel;
import com.applet.sms.common.exceptions.ApiResultEnum;
import com.applet.sms.common.retryaop.LockFailRetry;
import com.applet.sms.common.retryaop.LockFailRetryException;
import com.applet.sms.dao.CityMapper;
import com.applet.sms.service.LockFailRetryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @description: todo
 * @author: LUNING
 * @create: 2019-05-10 21:25
 */
@Service
public class LockFailRetryServiceImpl implements LockFailRetryService {

    @Autowired
    CityMapper cityMapper;


    @LockFailRetry
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultModel lockFailRetry() {
        Map<String, Object> cityNameByCode = cityMapper.getCityNameByCode();
        int update = cityMapper.update((Integer) cityNameByCode.get("status"));
        System.out.println("【更新了========================】");
        if(update!=1){
            //如果更新失败就抛出去，重试
            throw new LockFailRetryException(ApiResultEnum.ERROR_TRY_AGAIN);
        }
        return ResultModel.succ();
    }
}
