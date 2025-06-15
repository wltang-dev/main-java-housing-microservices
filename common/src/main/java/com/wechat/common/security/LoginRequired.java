package com.wechat.common.security;





import com.wechat.common.enums.Role;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginRequired {
    Role value() default Role.USER;
}
