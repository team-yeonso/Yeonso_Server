package com.yenso.yensoserver.RestController;

import com.yenso.yensoserver.Domain.Celebrity;
import com.yenso.yensoserver.Domain.DTO.UserAuthDTO;
import com.yenso.yensoserver.Domain.DTO.UserDTO;
import com.yenso.yensoserver.Domain.Info;
import com.yenso.yensoserver.Domain.User;
import com.yenso.yensoserver.Domain.UserAuth;
import com.yenso.yensoserver.Repository.CelebrityRepo;
import com.yenso.yensoserver.Repository.InfoRepo;
import com.yenso.yensoserver.Repository.UserAuthRepo;
import com.yenso.yensoserver.Repository.UserRepo;
import com.yenso.yensoserver.Service.*;
import com.yenso.yensoserver.Service.Mail.EmailService;
import com.yenso.yensoserver.Service.Mail.EmailServiceImpl;
import com.yenso.yensoserver.Service.Reqeust.NaverApi;
import com.yenso.yensoserver.Service.Response.ResponseApi;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.bytebuddy.utility.RandomString;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.SendFailedException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Map;
import java.util.UUID;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserAuthRepo authRepo;
    @Autowired
    private InfoRepo infoRepo;
    @Autowired
    private CelebrityRepo celebrityRepo;
    @Autowired
    private Crypto crypto;
    @Autowired
    private Jwt jwt;
    @Autowired
    private NaverApi naverApi;
    @Autowired
    private EmailServiceImpl emailService;

    private final Long expAccessToken = (long) (24 * 60 * 60 * 1000);
    private final Long expRefreshToken = (long) (14 * 24 * 60 * 60 * 1000);


    @ApiOperation(value = "로그인")
    @ApiResponses({
            @ApiResponse(code = 200, message = "로그인 성공"),
            @ApiResponse(code = 400, message = "로그인 실패")
    })
    @RequestMapping(value = "/sign-in", method = RequestMethod.POST)
    public JSONObject signIn(@RequestBody UserAuthDTO data, HttpServletResponse response) throws Exception {
        JSONObject obj = new JSONObject();
        String user_id = userRepo.findByEmail(data.getEmail(), crypto.encode(data.getPassword()));
        if (Config.isEmpty(user_id)) {
            obj.put("accessToken", jwt.builder(user_id, "access", System.currentTimeMillis() + expAccessToken));
            obj.put("refreshToken", jwt.builder(user_id, "refresh", System.currentTimeMillis() + expRefreshToken));
            response.setStatus(200);
        } else {
            obj.put("error", "login is not success");
            response.setStatus(400);
        }
        return obj;
    }

    // TODO : 이메일 인증을 통한 uuid 전달
    @ApiOperation(value = "회원가입")
    @ApiResponses({
            @ApiResponse(code = 201, message = "회원가입 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청 ")
    })
    @RequestMapping(value = "/sign-up", method = RequestMethod.POST)
    public void signUp(@RequestBody UserAuthDTO data, HttpServletResponse response) {
        try {
            if (!Config.isEmpty(authRepo.findUser(data.getEmail())) && !Config.isEmpty(userRepo.findUser(data.getEmail()))) {
                UserAuth auth = authRepo.save(data.toEntity(crypto.encode(data.getPassword()), UUID.randomUUID().toString().replace("-", "")));
                emailService.sendMessage(data.getEmail(),"연소 인증 코드","옆의 코드를 어플에 입력해주세요!!  :   "+auth.getCode());
                response.setStatus(201);

            } else {
                throw new Exception("Already User SignUp : " + data.getEmail());
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(400);
        }
    }


    // TODO : 메소드 분할화 필요
    @ApiOperation(value = "회원 인증")
    @ApiResponses({
            @ApiResponse(code = 200, message = "회원 인증 성공"),
            @ApiResponse(code = 400, message = "회원 인증 실패")
    })
    @RequestMapping(value = "/auth", method = RequestMethod.PATCH)
    public void userAuth(@ApiParam(name = "data", value = "code", required = true)
                         @RequestBody Map<String, Object> data, HttpServletResponse response) {
        String uuid = (String) data.get("code");
        UserAuth userData = authRepo.findUserInfo(uuid);
        if (userData != null) {
            Info info = new Info();
            Celebrity celebrity = new Celebrity();
            User user = userRepo.save(new UserDTO().toEntity(userData));
            authRepo.deleteAuthUser((String) data.get("code"));
            info.setU_id(user);
            info = infoRepo.save(info);
            celebrity.setInfo_field_id(info);
            celebrityRepo.save(celebrity);
            response.setStatus(200);
        } else {
            response.setStatus(400);
        }
    }

    @RequestMapping(value = "/image", method = RequestMethod.POST)
    @ApiOperation(value = "이미지 업로드")
    public ResponseEntity<ResponseApi> uploadFile(@ApiParam(name = "file", value = "file", required = true) @RequestPart MultipartFile file,
                                                  HttpServletRequest request, @RequestHeader(value = "Authorization") String token) throws Exception {
        File saveFile;
        Info info = infoRepo.findByUserId(Long.valueOf((String) jwt.parser(Jwt.filterToken(token))));
        do {
            saveFile = new File(request.getSession().getServletContext().getRealPath("static") + "\\" + RandomString.make(32) + file.getOriginalFilename());
        } while (saveFile.exists());
        saveFile.getParentFile().mkdirs();
        file.transferTo(saveFile);
        info.setImgPath(saveFile.getPath());
        infoRepo.save(info);
        return new ResponseEntity<>(naverApi.celebritySearch(info.getInfo_id(), saveFile.getPath()), HttpStatus.OK);
    }

    @RequestMapping(value = "/refresh", method = RequestMethod.POST)
    @ApiOperation(value = "토큰 재발급")
    public ResponseEntity<Object> refreshToken(@RequestHeader(value = "Authorization") String token) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("refreshToken", jwt.builder(String.valueOf(jwt.parser(Jwt.filterToken(token))), "access", System.currentTimeMillis() + expAccessToken));
        return new ResponseEntity<>(jsonObject, HttpStatus.CREATED);
    }
}
