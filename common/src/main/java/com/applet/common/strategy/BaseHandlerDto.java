package com.applet.common.strategy;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @description: TODO
 * @author: LUNING
 * @create: 2019/4/9 10:45 AM
 */
@Data
@AllArgsConstructor
public class BaseHandlerDto<T> {

    private T data;
}
