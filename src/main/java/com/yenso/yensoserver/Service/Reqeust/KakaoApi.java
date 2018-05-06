package com.yenso.yensoserver.Service.Reqeust;

import com.google.gson.Gson;
import com.yenso.yensoserver.Domain.Celebrity;
import com.yenso.yensoserver.Repository.CelebrityRepo;
import com.yenso.yensoserver.Service.Response.ResponseApi;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.yenso.yensoserver.Service.Reqeust.CustomRestTemplate.jsonParser;

@Component
public class KakaoApi implements InitializingBean {

    @Autowired
    private CelebrityRepo celebrityRepo;
    @Value("${kakaoSecret}")
    private String kakaoKey;
    @Value("${kakaoUrl}")
    private String kakaoUrl;
    private CustomRestTemplate restTemplate;
    private ResponseApi response;

    private Map<String, String> createHeader() {
        response = new ResponseApi();
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "KakaoAK " + kakaoKey);
        return map;
    }

    public ResponseApi imageSearch(Long id, String queryName) {
        restTemplate.setHeaders(createHeader());
        Map<String, Object> celebrityData = jsonParser.parseMap(new Gson().toJson(
                restTemplate.request(kakaoUrl + "&query=" + queryName, HttpMethod.GET).get("documents"))
                .replace("[", "").replace("]", ""));
        Celebrity celebrity = celebrityRepo.findByInfoId(id);
        celebrity.setImg_path((String) celebrityData.get("image_url"));
        celebrityRepo.save(celebrity);
        response.setCelebrity_path(celebrity.getImg_path());
        return response;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        restTemplate = new CustomRestTemplate();
    }
}
