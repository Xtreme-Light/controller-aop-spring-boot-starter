package com.owl.aop.intercept.controller.autoconfig;

import com.owl.aop.intercept.controller.interceptor.method.ControllerMethodAdvise;
import com.owl.aop.intercept.controller.point.cut.ControllerMethodMatherPointCut;
import org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy(proxyTargetClass = true)
@Configuration
@ConditionalOnProperty(
    name = "owl.aop.controller.enable",
    havingValue = "true",
    matchIfMissing = true)
public class ControllerProxyAutoConfiguration {

  @Bean
  public ControllerAopConfiguration controllerAopConfiguration() {
    return new ControllerAopConfiguration();
  }

  public static final class ControllerAopConfiguration
      implements BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry)
        throws BeansException {}

    @Override
    public void postProcessBeanFactory(
        ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
      final ControllerMethodMatherPointCut controllerMethodMatherPointCut =
          new ControllerMethodMatherPointCut();
      configurableListableBeanFactory.registerSingleton(
          "controllerMethodMatherPointCut", controllerMethodMatherPointCut);
      DefaultBeanFactoryPointcutAdvisor defaultBeanFactoryPointcutAdvisor =
          new DefaultBeanFactoryPointcutAdvisor();
      defaultBeanFactoryPointcutAdvisor.setPointcut(controllerMethodMatherPointCut);
      defaultBeanFactoryPointcutAdvisor.setAdvice(new ControllerMethodAdvise());
      configurableListableBeanFactory.registerSingleton(
          "controllerMethodMatherPointcutAdvisor", defaultBeanFactoryPointcutAdvisor);
    }
  }
}
