package com.tw;

import com.alibaba.fastjson.JSON;
import com.tw.entity.Todo;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void should_return_bad_request_status_when_add_with_repeated_title() throws Exception {
        //given
        Todo todo1 = new Todo("todo", 0);
        Todo todo2 = new Todo("todo", 0);
        this.mockMvc.perform(post("/todos")
                .contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONString(todo1)))
                .andExpect(status().isCreated());
        //when + then
        this.mockMvc.perform(post("/todos")
                .contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONString(todo2)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Input title is repeated!"));

    }

    @Test
    public void should_return_bad_request_status_when_update_with_repeated_title() throws Exception {
        //given
        Todo todo1 = new Todo("todo", 0);
        Todo todo2 = new Todo("todo1", 0);
        //when
        this.mockMvc.perform(post("/todos")
                .contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONString(todo1)))
                .andExpect(status().isCreated());
        MvcResult mvcResult = this.mockMvc.perform(post("/todos")
                .contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONString(todo2)))
                .andExpect(status().isCreated()).andReturn();
        JSONObject todo2Saved = new JSONObject(mvcResult.getResponse().getContentAsString());
        //then
        todo2.setTitle(todo1.getTitle());
        this.mockMvc.perform(put("/todos/"+todo2Saved.getLong("id"))
                .contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONString(todo2)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Input title is repeated!"));
    }

}
