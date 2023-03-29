package com.demo.manage_system.controller;
import cn.hutool.core.map.MapUtil;
import com.demo.manage_system.common.lang.Const;
import com.demo.manage_system.common.lang.Result;
import com.google.code.kaptcha.Producer;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

@RestController
@Slf4j
public class AuthController extends BaseController {

  @Autowired
  Producer producer;

  @GetMapping("/captcha")
  public Result captcha() throws IOException {
    String key = UUID.randomUUID().toString();
//    生成验证码
    String code = producer.createText();

    BufferedImage image = producer.createImage(code);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    ImageIO.write(image, "jpg", outputStream);

    BASE64Encoder encoder = new BASE64Encoder();
    String str = "data:image/jpeg;base64," + encoder.encode(outputStream.toByteArray());

    redisUtil.hset(Const.CAPTCHA_KEY, key, code, 120);
    log.info("验证码 -- {} - {}", key, code);
//    返回key和验证码图片
    return Result.succ(
        MapUtil.builder().put("token", key).put("captchaImg", str).build()
    );
  }

}
