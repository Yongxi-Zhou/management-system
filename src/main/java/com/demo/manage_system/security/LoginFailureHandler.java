package com.demo.manage_system.security;

import cn.hutool.json.JSONUtil;
import com.demo.manage_system.common.lang.Result;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class LoginFailureHandler implements
    AuthenticationFailureHandler {

  @Override
  public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse, AuthenticationException e)
      throws IOException, ServletException {
    httpServletResponse.setContentType("application/json;charset=UTF-8");
    ServletOutputStream outputStream = httpServletResponse.getOutputStream();

    Result result = Result.fail(e.getMessage());

    outputStream.write(JSONUtil.toJsonStr(result).getBytes("UTF-8"));
    outputStream.flush();
    outputStream.close();
  }
}
