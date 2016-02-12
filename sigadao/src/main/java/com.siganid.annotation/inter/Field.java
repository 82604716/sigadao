package com.siganid.annotation.inter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Administrator on 2016/2/3.
 */
@Retention(RetentionPolicy.SOURCE)
public @interface Field {
    String value() default "";
}
