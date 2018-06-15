package com.yenso.yensoserver.RestController;

import com.yenso.yensoserver.Domain.DTO.*;
import com.yenso.yensoserver.Domain.Model.Info;
import com.yenso.yensoserver.Domain.Model.User;
import com.yenso.yensoserver.Repository.CelebrityRepo;
import com.yenso.yensoserver.Repository.InfoRepo;
import com.yenso.yensoserver.Repository.TempUserRepo;
import com.yenso.yensoserver.Repository.UserRepo;
import com.yenso.yensoserver.Service.*;
import com.yenso.yensoserver.Exceptions.UserEmailException;
import com.yenso.yensoserver.Service.Mail.EmailServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/user")
@RestController("userController")
public class UserController {

    private final UserRepo userRepo;
    private final TempUserRepo tempUserRepo;
    private final InfoRepo infoRepo;
    private final CelebrityRepo celebrityRepo;
    private final Crypto crypto;
    private final Jwt jwt;
    private final EmailServiceImpl emailService;

    static final Long expAccessToken = (long) (24 * 60 * 60 * 1000);
    static final Long expRefreshToken = (long) (14 * 24 * 60 * 60 * 1000);

    @Autowired
    public UserController(@Qualifier("userRepo") UserRepo userRepo, TempUserRepo tempUserRepo,
                          @Qualifier("infoRepo") InfoRepo infoRepo, CelebrityRepo celebrityRepo,
                          Crypto crypto, Jwt jwt, EmailServiceImpl emailService) {
        this.userRepo = userRepo;
        this.tempUserRepo = tempUserRepo;
        this.infoRepo = infoRepo;
        this.celebrityRepo = celebrityRepo;
        this.crypto = crypto;
        this.jwt = jwt;
        this.emailService = emailService;
    }

    @ApiOperation(value = "로그인")
    @ApiResponses({
            @ApiResponse(code = 200, message = "로그인 성공"),
            @ApiResponse(code = 400, message = "로그인 실패")
    })
    @RequestMapping(value = "/sign-in", method = RequestMethod.POST)
    public ResponseEntity<TokenDTO> signIn(@RequestBody TempUserDTO data) throws Exception {
        TokenDTO tokenDTO = new TokenDTO();
        String user_id = String.valueOf(userRepo.findByEmailAndPassword(data.getEmail(), crypto.encode(data.getPassword())).orElseThrow((Exception::new)).getUser_id());
        tokenDTO.setAccessToken(jwt.builder(user_id, "access", System.currentTimeMillis() + expAccessToken));
        tokenDTO.setRefreshToken(jwt.builder(user_id, "refresh", System.currentTimeMillis() + expRefreshToken));
        return new ResponseEntity<>(tokenDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "회원가입")
    @ApiResponses({
            @ApiResponse(code = 201, message = "회원가입 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청 ")
    })
    @RequestMapping(value = "/sign-up", method = RequestMethod.POST)
    public ResponseEntity<Void> signUp(@RequestBody TempUserDTO data) throws Exception {
        if (!tempUserRepo.existsByEmail(data.getEmail()) && !userRepo.existsByEmail(data.getEmail())) {
            String uuid = UUID.randomUUID().toString().replace("-", "");
            tempUserRepo.save(data.toEntity(crypto.encode(data.getPassword()), uuid));
            emailService.sendMessage(data.getEmail(), "연소 인증 코드", "옆의 코드를 어플에 입력해주세요!!  :   " + uuid);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "회원 인증")
    @ApiResponses({
            @ApiResponse(code = 200, message = "회원 인증 성공"),
            @ApiResponse(code = 400, message = "회원 인증 실패")
    })
    @RequestMapping(value = "/auth", method = RequestMethod.PATCH)
    public ResponseEntity<Void> userAuth(@ApiParam(name = "data", value = "code", required = true)
                                         @RequestBody TempUserDTO tempUserDTO) throws Exception {
        InfoDTO info = new InfoDTO();
        CelebrityDTO celebrity = new CelebrityDTO();
        User user = userRepo.save(new UserDTO().toEntity(tempUserRepo.findByCode(tempUserDTO.getCode()).orElseThrow(Exception::new)));
        tempUserRepo.deleteByCode(tempUserDTO.getCode());
        info.setUserid(user);
        Info infoId = infoRepo.save(info.toEntity());
        celebrity.setInfoValue(infoId);
        celebrityRepo.save(celebrity.toEntity());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/find/password", method = RequestMethod.PATCH)
    @ApiOperation(value = "비밀번호 찾기")
    public ResponseEntity<Void> findPassWord(@RequestBody UserDTO userData) throws UserEmailException {
        String email = userData.getEmail();
        String rePassword = UUID.randomUUID().toString().replace("-", "");
        userData = userRepo.findEmail(email);
        userData.setPassword(rePassword);
        userRepo.save(userData.toEntity());
        emailService.sendMessage(email, "연소 임시 비밀번호 입니다", rePassword);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}