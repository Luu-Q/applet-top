package com.data.mybatisplus.service.impl;

import com.data.mybatisplus.entity.pojo.ServiceItem;
import com.data.mybatisplus.dao.ServiceItemMapper;
import com.data.mybatisplus.service.ServiceItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ln
 * @since 2019-06-11
 */
@Service
public class ServiceItemServiceImpl extends ServiceImpl<ServiceItemMapper, ServiceItem> implements ServiceItemService {

}
