package com.gaounuri.authjwtserver.todo.model;

import com.gaounuri.authjwtserver.constant.entity.BaseTimeEntity;
import com.gaounuri.authjwtserver.todo.dto.TodoDTO;
import com.gaounuri.authjwtserver.todo.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "todo")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Todo extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_id")
    private Long todoId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "todo_content")
    private String todoContent;

    @Column(name = "todo_status")
    @Enumerated(EnumType.STRING)
    private Status todoStatus;

    public static Todo createTodo(TodoDTO.TodoContent dto){
        return Todo.builder()
                .userId(dto.getUserId())
                .todoContent(dto.getContent())
                .todoStatus(Status.ASSIGNED)
                .build();
    }
}

