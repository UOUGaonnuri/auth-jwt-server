package com.gaounuri.authjwtserver.todo.service.inter;

import com.gaounuri.authjwtserver.todo.dto.TodoDTO;

import java.util.List;

public interface TodoService {
    List<TodoDTO.TodoDto> getTodosByUserId(Long userId);
    Long createTodoContent(TodoDTO.TodoContent request);
    Long changeTodoStatus(Long todoId);
    Long deleteTodoContent(Long todoId);
}
