package com.yenso.yensoserver.ControllerTest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.yenso.yensoserver.Domain.User;
import com.yenso.yensoserver.Repository.UserRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    @Qualifier("userRepo")
    private UserRepo userRepo;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUser(){
        User user = new User();
        user.setUser_id(1L);
        user.setEmail("test@test.com");
        user.setPassword("1234");
        user.setTimestamp(new Timestamp(System.currentTimeMillis()));
        userRepo.save(user);
    }

    @Test
    public void findPasswordTest() throws Exception {
        User user = new User();
        user.setEmail("test@test.com");
        mockMvc.perform(patch("/user/find/password").contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(user)))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
