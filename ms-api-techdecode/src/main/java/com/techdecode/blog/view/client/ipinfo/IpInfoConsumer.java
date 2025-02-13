package com.techdecode.blog.view.client.ipinfo;

import com.techdecode.blog.view.client.dtos.IpInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "value", url = "https://ipinfo.io/")
@Controller
public interface IpInfoConsumer {

    @GetMapping("/{ip}?token={token}")
    public IpInfoDto showInfoIp(@PathVariable("ip") String ip, @PathVariable("token") String token);

}
