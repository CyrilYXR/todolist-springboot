package com.tw.controller;

import com.tw.entity.Todo;
import com.tw.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @PostMapping
    private ResponseEntity add(@RequestBody Todo todo){
        Todo addTodo = todoService.add(todo);
        return ResponseEntity.status(HttpStatus.CREATED).body(addTodo);
    }

    @GetMapping
    private ResponseEntity findAll(){
        List<Todo> todoList = todoService.findAll();
        return ResponseEntity.ok().body(todoList);
    }

    @PutMapping("/{id}")
    private ResponseEntity update(@PathVariable Long id, @RequestBody Todo todo){
        Todo updateTodo = todoService.update(id, todo);
        return ResponseEntity.ok().body(updateTodo);
    }
}
