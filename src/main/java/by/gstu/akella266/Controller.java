package by.gstu.akella266;

import by.gstu.akella266.model.dto.Todo;
import by.gstu.akella266.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@org.springframework.stereotype.Controller
public class Controller {

    private boolean isEdit = false;

    @Autowired
    private TodoService todoService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView defaultPage(){
        ModelAndView modelAndView = new ModelAndView();
        setDefault(modelAndView);
        return modelAndView;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView showAddTask(@ModelAttribute Todo todo){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("currTodo", new Todo("-1", "", false));
        modelAndView.setViewName("details.html");
        isEdit = false;
        return modelAndView;
    }

    @RequestMapping(value = "/changed", method = RequestMethod.POST)
    public String addTask(@ModelAttribute Todo todo){
        if (isEdit) todoService.update(todo);
        else todoService.insert(todo);
        return "redirect:/";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView showEdit(@PathVariable String id){
        Todo todo = todoService.getTodo(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("todo", todo);
        modelAndView.setViewName("details.html");
        isEdit = true;
        return modelAndView;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView remove(@PathVariable String id){
        todoService.remove(id);
        ModelAndView modelAndView = new ModelAndView();
        setDefault(modelAndView);
        return modelAndView;
    }

    @RequestMapping(value = "/changeState/{id}", method = RequestMethod.GET)
    public ModelAndView changeState(@PathVariable String id){
        Todo todo = todoService.getTodo(id);
        boolean state = todo.isStatus();
        todo.setStatus(!state);
        todoService.update(todo);
        ModelAndView modelAndView = new ModelAndView();
        setDefault(modelAndView);
        return modelAndView;
    }

    private void setDefault(ModelAndView modelAndView){
        modelAndView.addObject("todos", todoService.getAll());
        modelAndView.addObject("countTotal", todoService.getAllCount());
        modelAndView.addObject("countDone", todoService.getCountDone());
        modelAndView.addObject("countTodo", todoService.getCountTodo());
        modelAndView.addObject("currTodo", new Todo("-1", "", false));
        modelAndView.setViewName("index.html");
    }
}
