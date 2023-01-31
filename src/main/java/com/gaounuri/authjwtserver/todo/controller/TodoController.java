package com.gaounuri.authjwtserver.todo.controller;

import com.gaounuri.authjwtserver.constant.dto.BaseResponse;
import com.gaounuri.authjwtserver.todo.dto.TodoDTO;
import com.gaounuri.authjwtserver.todo.service.TodoServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/todo")
public class TodoController {
    private final TodoServiceImpl todoService;

    @GetMapping("/test")
    public ResponseEntity<BaseResponse<String>> test(){
        return ResponseEntity.ok().body(BaseResponse.success("Test"));
    }

    @GetMapping("/getTodos")
    public ResponseEntity<BaseResponse<List<TodoDTO.TodoDto>>> getTodos(@RequestParam Long userId){
        List<TodoDTO.TodoDto> result = todoService.getTodosByUserId(userId);
        return ResponseEntity.ok().body(BaseResponse.success(result));
    }

    @PostMapping("/createTodo")
    public ResponseEntity<BaseResponse<Long>> createTodo(@RequestBody TodoDTO.TodoContent request){
        Long result = todoService.createTodoContent(request);
        return ResponseEntity.ok().body(BaseResponse.success(result));
    }

    @PutMapping("/changeStatus")
    public ResponseEntity<BaseResponse<Long>> changeStatusTodo(@RequestBody TodoDTO.ChangeStatus request){
        Long result = todoService.changeTodoStatus(request.getTodoId());
        return ResponseEntity.ok().body(BaseResponse.success(result));
    }

    @DeleteMapping("/deleteTodo")
    public ResponseEntity<BaseResponse<Long>> deleteTodo(@RequestParam("todoId") Long todoId){
        Long result = todoService.deleteTodoContent(todoId);
        return ResponseEntity.ok().body(BaseResponse.success(result));
    }
}
