package by.gstu.akella266.service;

import by.gstu.akella266.model.Repository;
import by.gstu.akella266.model.dto.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TodoService {

    @Autowired
    private Repository repository;

    public TodoService() {}

    public void insert(Todo todo) {
        repository.insert(todo);
    }

    public void update(Todo todo) {
        repository.update(todo);
    }

    public void remove(String id) {
        repository.remove(id);
    }

    public List<Todo> getAll() {
        return repository.getAll();
    }

    public int getAllCount(){
        return getAll().size();
    }

    public int getCountDone() {
        return (int) repository.getAll().stream().filter(Todo::isStatus).count();
    }

    public Todo getTodo(String id){
        return repository.getTodo(id);
    }

    public int getCountTodo(){
        return (int) repository.getAll().stream().filter(t -> !t.isStatus()).count();
    }
}
