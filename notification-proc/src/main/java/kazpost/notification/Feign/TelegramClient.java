package kazpost.notification.Feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.cloud.openfeign.support.SpringEncoder;

import java.beans.Encoder;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

@FeignClient(name = "telegramClient", url = "http://172.30.110.8:1991/newbot/notifier/api")
public interface TelegramClient {

    @PostMapping(value = "/send",consumes = APPLICATION_FORM_URLENCODED_VALUE)
    String sendMessage(Map<String, ?> form
    );

}
