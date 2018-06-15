package com.yenso.yensoserver.Api.Reqeust;

import lombok.Setter;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.*;

@Setter
@EnableAsync
public class CustomRestTemplate extends RestTemplate {

    private RestTemplate restTemplate;
    private HttpHeaders headers;
    public static JsonParser jsonParser;
    private Map requestData;

    public CustomRestTemplate() {

        restTemplate = new RestTemplate();
        requestData = new LinkedHashMap();
        headers = new HttpHeaders();
        jsonParser = JsonParserFactory.getJsonParser();
    }

    public void setHeaders(Map<String, String> headersData) {
        for (Entry<String, String> entry : headersData.entrySet()) {
            if(headers.get(entry.getKey()) == null){
                headers.add(entry.getKey(), entry.getValue());
            }
        }
    }

    public void setMediaType(){
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
    }

    public Map<String, Object> request(String url, HttpMethod method) {
        HttpEntity entity = new HttpEntity<>(this.requestData, this.headers);
        return jsonParser.parseMap(restTemplate.exchange(url, method, entity, String.class).getBody());
    }
}
