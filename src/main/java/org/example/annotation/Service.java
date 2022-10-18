package org.example.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE}) // 어디다 붙일 수 있는지
@Retention(RetentionPolicy.RUNTIME)// 유지 기간
public @interface Service {
}
