package mapper;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "applet-sms", fallback = SmsClientHystric.class)
public interface SmsClient {

    @RequestMapping(value = "/sms/test", method = RequestMethod.GET)
    String test(@RequestParam(value = "name") String name);
}
