package com.yenso.yensoserver.Service;

import com.google.gson.Gson;
import com.yenso.yensoserver.Repository.CelebrityRepo;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Setter
@Component
public class NaverApi extends Thread implements InitializingBean {

    @Autowired
    private CelebrityRepo celebrityRepo;
    @Value("${naverId}")
    private String naverId;
    @Value("${naverSecret}")
    private String naverSecret;
    private RestTemplate template;
    private String url = "https://openapi.naver.com/v1/vision/celebrity/";
    private HttpHeaders headers;
    private HttpEntity entity;
    private Map data;
    private JsonParser springParser;
    @Autowired
    private ImageSearch search;
    private String path;

    @Override
    public void run() {
        ((LinkedMultiValueMap) data).add("image", new FileSystemResource(path));
        entity = new HttpEntity<>(data, headers);
        Map<String, Object> info = springParser.parseMap(template.exchange(url, HttpMethod.POST, entity, String.class).getBody());
        Map<String,Object> celebrity = (Map<String, Object>) springParser.parseMap(new Gson().toJson(info.get("faces")).replace("[","").replace("]","")).get("celebrity");
        search.setCelebrity((String) celebrity.get("value"));
        search.start();
    }

    @Override
    public void afterPropertiesSet() {
        template = new RestTemplate();
        template.getMessageConverters().add(new ByteArrayHttpMessageConverter());
        data = new LinkedMultiValueMap();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.add("X-Naver-Client-Id", naverId);
        headers.add("X-Naver-Client-Secret", naverSecret);
        springParser = JsonParserFactory.getJsonParser();
    }
}
