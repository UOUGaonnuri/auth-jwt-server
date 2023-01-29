package com.gaounuri.authjwtserver.todo.service;

import com.gaounuri.authjwtserver.constant.enums.ErrorCode;
import com.gaounuri.authjwtserver.constant.exception.CustomException;
import com.gaounuri.authjwtserver.todo.dto.TodoDTO;
import com.gaounuri.authjwtserver.todo.enums.Status;
import com.gaounuri.authjwtserver.todo.model.Todo;
import com.gaounuri.authjwtserver.todo.model.repository.TodoRepository;
import com.gaounuri.authjwtserver.todo.service.inter.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    @Override
    public List<TodoDTO.TodoDto> getTodosByUserId(Long userId) {
        return todoRepository.findAllByUserId(userId).stream().map(TodoDTO.TodoDto::entityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Long createTodoContent(TodoDTO.TodoContent request) {
        Todo entity = Todo.createTodo(request);
        todoRepository.save(entity);
        return entity.getTodoId();
    }

    @Override
    public Long changeTodoStatus(Long todoId) {
        Todo entity = todoRepository.findById(todoId)
                .orElseThrow(()-> new CustomException(ErrorCode.TODO_NOT_FOUND));
        if(entity.getTodoStatus().equals(Status.ASSIGNED)) {
            entity.setTodoStatus(Status.DONE);
        } else {
            entity.setTodoStatus(Status.ASSIGNED);
        }
        todoRepository.save(entity);
        return entity.getTodoId();
    }

    @Override
    public Long deleteTodoContent(Long todoId) {
        todoRepository.deleteById(todoId);
        return todoId;
    }
}
