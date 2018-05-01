package com.yenso.yensoserver;

import com.yenso.yensoserver.Service.Excel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExcelTest {
    @Autowired
    private Excel excel;
    @Test
    public void test(){
        // Excel이 Bean 객체이기 때문에 AutoWried로 지정만해주면 알아서 진행됨
    }
}
