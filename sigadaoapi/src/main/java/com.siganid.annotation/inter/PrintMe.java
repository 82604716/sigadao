package com.siganid.annotation.inter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Administrator on 2016/2/2.
 */
@Retention(RetentionPolicy.SOURCE)
public @interface PrintMe {
    String value() default "123";
}
