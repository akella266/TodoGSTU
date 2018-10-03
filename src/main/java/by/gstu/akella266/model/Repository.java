package by.gstu.akella266.model;

import by.gstu.akella266.model.dto.Todo;

import java.util.List;

public interface Repository {
    void insert(Todo todo);
    void update(Todo todo);
    void remove(String id);
    List<Todo> getAll();
    Todo getTodo(String id);
}
