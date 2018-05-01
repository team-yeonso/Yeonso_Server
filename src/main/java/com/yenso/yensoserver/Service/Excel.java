package com.yenso.yensoserver.Service;

import com.yenso.yensoserver.Domain.Job;
import com.yenso.yensoserver.Repository.JobRepo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;

@Service
public class Excel implements InitializingBean {
    @Autowired
    private JobRepo jobRepo;
    private Workbook workbook;

    @Override
    public void afterPropertiesSet() throws Exception {
        try {

            workbook = new XSSFWorkbook(new FileInputStream(System.getProperty("user.dir") + "\\JobCategory.xlsx"));
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 0; i < 8; i++) {
                Job job = new Job();
                Row row = sheet.getRow(i);
                row.forEach(cell -> {
                    if (!cell.toString().equals("")) {
                        job.setCategory(cell.toString());
                    }
                });
                jobRepo.save(job);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
