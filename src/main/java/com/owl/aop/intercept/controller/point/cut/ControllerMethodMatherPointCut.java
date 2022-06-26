package com.owl.aop.intercept.controller.point.cut;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

public class ControllerMethodMatherPointCut extends StaticMethodMatcherPointcut
    implements Serializable {

  @Override
  public ClassFilter getClassFilter() {
    return new ClassFilter() {
      @Override
      public boolean matches(Class<?> clazz) {
        return clazz.getName().endsWith("Api");
      }
    };
  }

  @Override
  public boolean matches(Method method, Class<?> targetClass) {
    return isController(method, targetClass);
  }

  private boolean isController(Method method, Class<?> targetClass) {
    if (AnnotationUtils.findAnnotation(targetClass, Controller.class) != null
        || AnnotationUtils.findAnnotation(targetClass, RestController.class) != null) {
      if (Modifier.isPublic(method.getModifiers()) && !Modifier.isStatic(method.getModifiers())) {
        return isMapping(method);
      }
    }

    return false;
  }

  private boolean isMapping(Method method) {
    return (AnnotationUtils.findAnnotation(method, GetMapping.class) != null
        || AnnotationUtils.findAnnotation(method, PostMapping.class) != null
        || AnnotationUtils.findAnnotation(method, PutMapping.class) != null
        || AnnotationUtils.findAnnotation(method, DeleteMapping.class) != null
        || AnnotationUtils.findAnnotation(method, RequestMapping.class) != null);
  }
}
