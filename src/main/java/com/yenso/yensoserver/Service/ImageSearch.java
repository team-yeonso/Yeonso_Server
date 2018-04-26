package com.yenso.yensoserver.Service;

import com.google.gson.Gson;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Setter
@Component
public class ImageSearch extends Thread implements InitializingBean {

    @Value("${kakaoSecret}")
    private String kakaoKey;
    @Value("${kakaoUrl}")
    private String url;
    private RestTemplate template;
    private HttpEntity entity;
    private HttpHeaders headers;
    private String celebrity;
    private JsonParser springParser;

    @Override
    public void run() {
        entity = new HttpEntity<>(null, headers);
        Map<String, Object> meta = springParser.parseMap(template.exchange(url + "&" + celebrity, HttpMethod.GET, entity, String.class).getBody());
        Map<String, Object> documents = springParser.parseMap(new Gson().toJson(meta.get("documents")).replace("[", "").replace("]", ""));
        System.out.println(documents.get("image_url"));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        template = new RestTemplate();
        headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + kakaoKey);
        springParser = JsonParserFactory.getJsonParser();
    }
}