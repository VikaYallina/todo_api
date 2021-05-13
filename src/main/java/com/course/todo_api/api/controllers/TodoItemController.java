package com.course.todo_api.api.controllers;

import com.course.todo_api.api.models.TodoGroup;
import com.course.todo_api.api.models.TodoItem;
import com.course.todo_api.api.req_body_wrapper.GroupRequest;
import com.course.todo_api.api.req_body_wrapper.TodoItemRequest;
import com.course.todo_api.api.services.TodoItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/resource/todo")
public class TodoItemController {

    @Autowired
    private TodoItemService todoItemService;

    @GetMapping("")
    public ResponseEntity<List<TodoItem>> getAllTodos(){
        List<TodoItem> allTodos = todoItemService.findAll();
        if (allTodos == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else if (allTodos.isEmpty())
            return new ResponseEntity<>(allTodos, HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<>(allTodos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoItem> getBook(@PathVariable Long id) {
        return todoItemService.findById(id)
                .map(item -> new ResponseEntity<>(item, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/by-user-id/{userId}")
    public ResponseEntity<List<TodoItem>> getAllItemsByUserId(@PathVariable Long userId) {
        List<TodoItem> itemsByUserId = todoItemService.findByUserId(userId);

        if (itemsByUserId == null || itemsByUserId.isEmpty())
            return new ResponseEntity<>(itemsByUserId, HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(itemsByUserId, HttpStatus.OK);
    }

    @GetMapping("/by-group-id/{groupId}")
    public ResponseEntity<List<TodoItem>> getAllItemsByGroupId(@PathVariable Long groupId) {
        List<TodoItem> itemsByGroupId = todoItemService.findAllByGroupId(groupId);

        if (itemsByGroupId == null || itemsByGroupId.isEmpty())
            return new ResponseEntity<>(itemsByGroupId, HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(itemsByGroupId, HttpStatus.OK);
    }
//
//    @GetMapping("/by-author-id/{authorIds}")
//    public ResponseEntity<List<Book>> getAllBooksByAuthorId(@PathVariable Long[] authorIds) {
//        List<Book> allBooksByAuthorId = new ArrayList<>();
//        for (Long authorId : authorIds) {
//            List<Book> booksByAuthorId = bookService.findByAuthorsId(authorId);
//            if (!booksByAuthorId.isEmpty()) allBooksByAuthorId.addAll(booksByAuthorId);
//        }
//        if (allBooksByAuthorId.isEmpty())
//            return new ResponseEntity<>(allBooksByAuthorId, HttpStatus.NO_CONTENT);
//
//        return new ResponseEntity<>(allBooksByAuthorId, HttpStatus.OK);
//    }
//
//    @PostMapping("")
//    public ResponseEntity<Book> saveBook(@RequestBody @Valid Book book, BindingResult bindingResult,
//                                         UriComponentsBuilder uriComponentsBuilder) {
//        BindingErrorsResponse errors = new BindingErrorsResponse();
//        HttpHeaders headers = new HttpHeaders();
//        if (bindingResult.hasErrors() || (book == null)) {
//            errors.addAllErrors(bindingResult);
//            headers.add("errors", errors.toJSON());
//            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
//        }
//
//        bookService.save(book);
//        headers.setLocation(uriComponentsBuilder.path("/books/{id}").buildAndExpand(book.getId()).toUri());
//        return new ResponseEntity<>(book, headers, HttpStatus.CREATED);
//
//    }

    @PutMapping("/toggle/{id}")
    public ResponseEntity<TodoItem> toggleItem(@PathVariable("id") Long id){
        return todoItemService.findById(id)
                .map(item -> {
                    item.setCompleted(!item.getCompleted());
                    item.setCompletedOn(new Date());
                    todoItemService.save(item);
                    return new ResponseEntity<>(item, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("")
    public ResponseEntity<TodoItem> saveItem(@RequestBody @Valid TodoItemRequest request){
        // TODO: 25.04.2021 Add errors
        TodoItem todoItem = todoItemService.req2obj(request);
        todoItemService.save(todoItem);
        return new ResponseEntity<>(todoItem, HttpStatus.CREATED);
    }
    //
//    @PutMapping("/{id}")
//    public ResponseEntity<Book> updateBook(@PathVariable("id") Long id, @RequestBody @Valid Book book,
//                                           BindingResult bindingResult) {
//        Optional<Book> currentBook = bookService.findById(id);
//        BindingErrorsResponse errors = new BindingErrorsResponse();
//        HttpHeaders headers = new HttpHeaders();
//        if (bindingResult.hasErrors() || (book == null)) {
//            errors.addAllErrors(bindingResult);
//            headers.add("errors", errors.toJSON());
//            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
//        }
//        if (!currentBook.isPresent())
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//
//        bookService.update(book);
//        return new ResponseEntity<>(book, HttpStatus.NO_CONTENT);
//    }
    @PutMapping("/{id}")
    public ResponseEntity<TodoItem> updateItem(@PathVariable("id") Long id,
                                                 @RequestBody @Valid TodoItemRequest todoItemRequest,
                                                 BindingResult bindingResult){
        // TODO: 25.04.2021 Error handling
        TodoItem newItem = todoItemService.update(id, todoItemRequest);
        if (newItem == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(newItem, HttpStatus.NO_CONTENT);
    }
    //
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Book> deleteBook(@PathVariable("id") Long id) {
//        Optional<Book> bookToDelete = bookService.findById(id);
//        if (!bookToDelete.isPresent())
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        bookService.delete(id);
//        return new ResponseEntity<>(bookToDelete.get(), HttpStatus.NO_CONTENT);
//
//    }
    @DeleteMapping("/{id}")
    public ResponseEntity<TodoItem> deleteItem(@PathVariable("id") Long id){
        Optional<TodoItem> item = todoItemService.findById(id);
        if (!item.isPresent())
            return new ResponseEntity<>((HttpStatus.NOT_FOUND));
        todoItemService.delete(id);
        return new ResponseEntity<>(item.get(),HttpStatus.NO_CONTENT);
    }
}
