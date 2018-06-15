package com.yenso.yensoserver.RestController;

import com.yenso.yensoserver.Domain.DTO.InfoDTO;
import com.yenso.yensoserver.Domain.Model.Info;
import com.yenso.yensoserver.Domain.Model.Job;
import com.yenso.yensoserver.Repository.InfoRepo;
import com.yenso.yensoserver.Repository.JobRepo;
import com.yenso.yensoserver.Repository.UserRepo;
import com.yenso.yensoserver.Service.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Entity;
import java.util.Map;

@RequestMapping(value = "/info")
@RestController
public class InfoController {

    @Autowired
    @Qualifier(value = "infoRepo")
    private InfoRepo infoRepo;

    @Autowired
    @Qualifier("userRepo")
    private UserRepo userRepo;
    @Autowired
    private JobRepo jobRepo;

    @Autowired
    private Jwt Jwt;

    @RequestMapping(value = "/insert",method = RequestMethod.PATCH)
    public void insertData(@RequestBody InfoDTO infoData, @RequestHeader(value = "Authorization") String token) throws Exception {
         Info info = infoRepo.findByUserid(userRepo.findById(Jwt.parser(token)).orElseThrow(Exception::new)).orElseThrow(Exception::new);
         Job job = jobRepo.findById(infoData.getJob()).orElseThrow(Exception::new);
         info.setGender(infoData.getGender());
         info.setHeight(infoData.getHeight());
         info.setWeight(infoData.getWeight());
         info.setMarriage(infoData.isMarriage());
         info.setJob_id(job);
         info.setJob(job.getCategory());
         infoRepo.save(info);
    }
}