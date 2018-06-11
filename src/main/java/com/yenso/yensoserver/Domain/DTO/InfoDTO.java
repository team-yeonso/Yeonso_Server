package com.yenso.yensoserver.Domain.DTO;

import com.yenso.yensoserver.Domain.EnumEntity.Gender;
import com.yenso.yensoserver.Domain.Model.Info;
import com.yenso.yensoserver.Domain.Model.Job;
import com.yenso.yensoserver.Domain.Model.User;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class InfoDTO {

    private Long id;

    private double weight;

    private double height;

    private boolean marriage;

    private String imgPath;

    private Gender gender;

    private String job;

    private Job job_id;

    private User userid;


    public Info toEntity(){
        return Info.builder()
                .weight(weight)
                .height(height)
                .marriage(marriage)
                .imgPath(imgPath)
                .gender(gender)
                .job(job)
                .job_id(job_id)
                .userid(userid)
                .build();
    }

}
