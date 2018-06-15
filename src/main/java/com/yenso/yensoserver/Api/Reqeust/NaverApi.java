package com.yenso.yensoserver.Api.Reqeust;

import com.google.gson.Gson;
import com.yenso.yensoserver.Domain.Model.Celebrity;
import com.yenso.yensoserver.Domain.Model.Info;
import com.yenso.yensoserver.Repository.CelebrityRepo;
import com.yenso.yensoserver.Exceptions.InfoException;
import com.yenso.yensoserver.Api.Response.ResponseApi;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.yenso.yensoserver.Api.Reqeust.CustomRestTemplate.jsonParser;

@Component
public class NaverApi implements InitializingBean {
    private CelebrityRepo celebrityRepo;
    private KakaoApi kakaoApi;
    @Value("${naverSecret}")
    private String naverKey;
    @Value("${naverId}")
    private String naverId;
    @Value("${naverUrl}")
    private String naverUrl;
    private CustomRestTemplate restTemplate;

    @Autowired
    public NaverApi(CelebrityRepo celebrityRepo, KakaoApi kakaoApi) {
        this.celebrityRepo = celebrityRepo;
        this.kakaoApi = kakaoApi;
    }

    @Override
    public void afterPropertiesSet() {
        restTemplate = new CustomRestTemplate();
        restTemplate.setHeaders(createHeader());
        restTemplate.setMediaType();
    }

    private Map<String, String> createHeader() {
        Map<String, String> map = new HashMap<>();
        map.put("X-Naver-Client-Secret", naverKey);
        map.put("X-Naver-Client-Id", naverId);
        return map;
    }

    public ResponseApi celebritySearch(Info id, String path) throws InfoException {
        Map imageMap = new LinkedMultiValueMap();
        ((LinkedMultiValueMap) imageMap).add("image", new FileSystemResource(path));
        restTemplate.setRequestData(imageMap);
        ArrayList<Object> b = (ArrayList<Object>) restTemplate.request(naverUrl,HttpMethod.POST).get("faces");
        Map<String, Object> data = jsonParser.parseMap(new Gson().toJson(b.get(0)));
        Map<String, Object> celebrityData = (Map<String, Object>) data.get("celebrity");
        Celebrity celebrity = celebrityRepo.findByInfoValue(id).orElseThrow(InfoException::new);
        String name = (String) celebrityData.get("value");
        celebrity.setCelebrity(name);
        celebrity.setConfidence((int) (100 * Double.parseDouble(String.valueOf(celebrityData.get("confidence")))));
        celebrityRepo.save(celebrity);
        ResponseApi response = kakaoApi.imageSearch(id, name);
        response.setImg_path(path);
        response.setCelebrity(celebrity.getCelebrity());
        response.setConfidence(celebrity.getConfidence());
        return response;
    }

}
