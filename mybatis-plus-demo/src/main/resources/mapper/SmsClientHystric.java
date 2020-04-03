package mapper;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class SmsClientHystric implements SmsClient {

    @Autowired
    HttpServletRequest request;

    @Override
    public String test(String name) {
        return name + " - error - port:" + request.getLocalPort();
    }
}
