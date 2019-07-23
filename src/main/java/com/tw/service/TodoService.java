package com.tw.service;

import com.tw.entity.Todo;
import com.tw.exception.BadRequestException;
import com.tw.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public Todo add(Todo todo) {
        List<Todo> byTitle = todoRepository.findByTitle(todo.getTitle());
        if(byTitle.size()>=1){
            throw new BadRequestException(1, "Input title is repeated!");
        }
        return todoRepository.save(todo);
    }

    public List<Todo> findAll() {
        return todoRepository.findAll();
    }

    public Todo update(Long id, Todo todo) {
        List<Todo> byTitle = todoRepository.findByTitle(todo.getTitle());
        Todo oldTodo = todoRepository.findById(id).get();
        if(!oldTodo.getTitle().equals(todo.getTitle()) && byTitle.size() >= 1){
            throw new BadRequestException(1, "Input title is repeated!");
        }
        todo.setId(id);
        return todoRepository.save(todo);
    }

    public void delete(Long id) {
        todoRepository.deleteById(id);
    }
}
