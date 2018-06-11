package com.yenso.yensoserver.Service.Reqeust;

import com.google.gson.Gson;
import com.yenso.yensoserver.Domain.DTO.CelebrityDTO;
import com.yenso.yensoserver.Domain.Model.Celebrity;
import com.yenso.yensoserver.Domain.Model.Info;
import com.yenso.yensoserver.Repository.CelebrityRepo;
import com.yenso.yensoserver.Service.Exceptions.InfoException;
import com.yenso.yensoserver.Service.Exceptions.UserCodeException;
import com.yenso.yensoserver.Service.Exceptions.UserEmailException;
import com.yenso.yensoserver.Service.Response.ResponseApi;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;

import java.util.HashMap;
import java.util.Map;

import static com.yenso.yensoserver.Service.Reqeust.CustomRestTemplate.jsonParser;

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
        Map<String, Object> data = (Map<String, Object>) jsonParser.parseMap(new Gson().toJson(
                restTemplate.request(naverUrl, HttpMethod.POST))
                .replace("[", "").replace("]", "")).get("faces");
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
