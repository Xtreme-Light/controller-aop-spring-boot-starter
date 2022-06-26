package com.owl.aop.intercept.controller.interceptor.method;

import java.lang.reflect.Method;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** controller method 环绕 */
public class ControllerMethodAdvise implements MethodInterceptor {
  private final Logger log = LoggerFactory.getLogger(ControllerMethodAdvise.class);

  @Override
  public Object invoke(MethodInvocation invocation) throws Throwable {

    return execute(
        invocation, invocation.getThis(), invocation.getMethod(), invocation.getArguments()
    );
  }

  private Object execute(MethodInvocation invoker, Object target, Method method, Object[] args)
      throws Throwable {
    final Class<?> targetClass = target.getClass();
    Object ret = null;
    final MethodExecuteResult methodExecuteResult = new MethodExecuteResult(true, null, "");

    try {
      ret = invoker.proceed();
    } catch (Throwable e) {
      methodExecuteResult.setSuccess(false);
      methodExecuteResult.setErrorMessage(e.getMessage());
      methodExecuteResult.setThrowable(e);
    }

    if (methodExecuteResult.throwable != null) {
      throw methodExecuteResult.throwable;
    }
    return ret;
  }

  public static final class MethodExecuteResult {
    private boolean success;
    private Throwable throwable;
    private String errorMessage;

    public MethodExecuteResult() {}

    public MethodExecuteResult(boolean success, Throwable throwable, String errorMessage) {
      this.success = success;
      this.throwable = throwable;
      this.errorMessage = errorMessage;
    }

    public boolean isSuccess() {
      return success;
    }

    public void setSuccess(boolean success) {
      this.success = success;
    }

    public Throwable getThrowable() {
      return throwable;
    }

    public void setThrowable(Throwable throwable) {
      this.throwable = throwable;
    }

    public String getErrorMessage() {
      return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
      this.errorMessage = errorMessage;
    }
  }
}
