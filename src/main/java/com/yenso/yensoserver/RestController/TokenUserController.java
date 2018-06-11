package com.yenso.yensoserver.RestController;

import com.yenso.yensoserver.Domain.DTO.InfoDTO;
import com.yenso.yensoserver.Domain.Model.Info;
import com.yenso.yensoserver.Repository.InfoRepo;
import com.yenso.yensoserver.Repository.UserRepo;
import com.yenso.yensoserver.Service.Exceptions.UserEmailException;
import com.yenso.yensoserver.Service.Jwt;
import com.yenso.yensoserver.Service.Reqeust.NaverApi;
import com.yenso.yensoserver.Service.Response.ResponseApi;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.bytebuddy.utility.RandomString;
import org.json.simple.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.File;

import static com.yenso.yensoserver.RestController.UserController.expAccessToken;

@RestController
@RequestMapping("/token")
public class TokenUserController {

    @Autowired
    @Qualifier("infoRepo")
    private InfoRepo infoRepo;
    @Autowired
    @Qualifier("userRepo")
    private UserRepo userRepo;
    @Autowired
    private Jwt jwt;
    @Autowired
    private NaverApi naverApi;
    private ModelMapper modelMapper;

    @PostConstruct
    public void init(){
        modelMapper = new ModelMapper();
    }

    @RequestMapping(value = "/image", method = RequestMethod.POST)
    @ApiOperation(value = "이미지 업로드")
    public ResponseEntity<ResponseApi> uploadFile(@ApiParam(name = "file", value = "file", required = true) @RequestPart MultipartFile file,
                                                  HttpServletRequest request, @RequestHeader(value = "Authorization") String token) throws Exception {
        File saveFile;
        Info info = infoRepo.findByUserid(userRepo.findById(Long.valueOf((String) jwt.parser(Jwt.filterToken(token)))).orElseThrow(() -> new UserEmailException("Not found user"))).orElseThrow(() -> new UserEmailException("Not found user"));
        do {
            saveFile = new File(request.getSession().getServletContext().getRealPath("static") + "\\" + RandomString.make(32) + file.getOriginalFilename());
        } while (saveFile.exists());
        saveFile.getParentFile().mkdirs();
        file.transferTo(saveFile);
        info.setImgPath(saveFile.getPath());
        info.setId(info.getId());
        infoRepo.save(info);
        return new ResponseEntity<>(naverApi.celebritySearch(info, saveFile.getPath()), HttpStatus.OK);
    }

    @RequestMapping(value = "/refresh", method = RequestMethod.POST)
    @ApiOperation(value = "토큰 재발급")
    public ResponseEntity<Object> refreshToken(@RequestHeader(value = "Authorization") String token) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("refreshToken", jwt.builder(String.valueOf(jwt.parser(Jwt.filterToken(token))), "refresh", System.currentTimeMillis() + expAccessToken));
        return new ResponseEntity<>(jsonObject, HttpStatus.CREATED);
    }

}
