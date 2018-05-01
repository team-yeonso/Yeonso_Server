package com.yenso.yensoserver.Service;

import com.google.gson.Gson;
import com.yenso.yensoserver.Domain.Celebrity;
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
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;

@Setter
@EnableAsync
@Component
public class NaverApi implements InitializingBean {

    @Autowired
    private CelebrityRepo celebrityRepo;
    @Value("${naverId}")
    private String naverId;
    @Value("${naverSecret}")
    private String naverSecret;
    @Value("${naverUrl}")
    private String naverUrl;
    @Value("${kakaoSecret}")
    private String kakaoKey;
    @Value("${kakaoUrl}")
    private String kakaoUrl;

    private RestTemplate template;
    private HttpHeaders headers;
    private HttpEntity entity;
    private JsonParser springParser;
    private String path;
    private Long info_id;

    @Async
    public void naverRequset() throws Exception{
        Map data = new LinkedMultiValueMap();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.add("X-Naver-Client-Id", naverId);
        headers.add("X-Naver-Client-Secret", naverSecret);


        ((LinkedMultiValueMap) data).add("image", new FileSystemResource(path));
        entity = new HttpEntity<>(data, headers);
        Map<String, Object> info = springParser.parseMap(template.exchange(naverUrl, HttpMethod.POST, entity, String.class).getBody());
        Map<String,Object> celebrityData = (Map<String, Object>) springParser.parseMap(new Gson().toJson(info.get("faces")).replace("[","").replace("]","")).get("celebrity");
        Celebrity celebrity = celebrityRepo.findByInfoId(info_id);
        String name = (String) celebrityData.get("value");
        celebrity.setCelebrity(name);
        double t = Double.parseDouble(String.valueOf(celebrityData.get("confidence")));
        celebrity.setConfidence((int) (100 * t));
        celebrityRepo.save(celebrity);
        kakaoRequest(String.valueOf(celebrityData.get("value")));
   }

    @Async
    public void kakaoRequest(String celebrityData) {
        headers.add("Authorization", "KakaoAK " + kakaoKey);
        entity = new HttpEntity<>(null, headers);
        Map<String, Object> meta = springParser.parseMap(template.exchange(kakaoUrl + "&query=" + celebrityData, HttpMethod.GET, entity, String.class).getBody());
        Map<String, Object> documents = springParser.parseMap(new Gson().toJson(meta.get("documents")).replace("[", "").replace("]", ""));
        Celebrity celebrity = celebrityRepo.findByInfoId(info_id);
        celebrity.setImg_path(String.valueOf(documents.get("image_url")));
        celebrityRepo.save(celebrity);
    }
    @Override
    public void afterPropertiesSet() {
        template = new RestTemplate();
        template.getMessageConverters().add(new ByteArrayHttpMessageConverter());

        headers = new HttpHeaders();
        springParser = JsonParserFactory.getJsonParser();
    }
}
