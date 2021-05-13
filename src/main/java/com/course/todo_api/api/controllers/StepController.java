package com.course.todo_api.api.controllers;

import com.course.todo_api.api.models.TodoItem;
import com.course.todo_api.api.models.TodoStep;
import com.course.todo_api.api.req_body_wrapper.StepRequest;
import com.course.todo_api.api.req_body_wrapper.TodoItemRequest;
import com.course.todo_api.api.services.StepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/resource/step")
public class StepController {
    @Autowired
    private StepService stepService;

    @GetMapping("")
    public ResponseEntity<List<TodoStep>> getAllSteps(){
        List<TodoStep> allSteps = stepService.findAll();
        if (allSteps == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else if (allSteps.isEmpty())
            return new ResponseEntity<>(allSteps, HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<>(allSteps, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoStep> getBook(@PathVariable Long id) {
        return stepService.findById(id)
                .map(step -> new ResponseEntity<>(step, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/by-item-id/{itemId}")
    public ResponseEntity<List<TodoStep>> getAllStepsByItemId(@PathVariable Long itemId) {
        List<TodoStep> stepsByGroupId = stepService.findByTodoItemId(itemId);

        if (stepsByGroupId.isEmpty())
            return new ResponseEntity<>(stepsByGroupId, HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(stepsByGroupId, HttpStatus.OK);
    }

    @GetMapping("/by-group-id/{groupId}")
    public ResponseEntity<List<TodoStep>> getAllStepsByGroupId(@PathVariable Long groupId) {
        List<TodoStep> stepsByGroupId = stepService.findAllByGroupId(groupId);

        if (stepsByGroupId.isEmpty())
            return new ResponseEntity<>(stepsByGroupId, HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(stepsByGroupId, HttpStatus.OK);
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

    @PostMapping("")
    public ResponseEntity<TodoStep> saveStep(@RequestBody @Valid StepRequest request){
        // TODO: 25.04.2021 Add errors
        TodoStep todoStep = stepService.req2obj(request);
        stepService.save(todoStep);
        return new ResponseEntity<>(todoStep, HttpStatus.CREATED);
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
    public ResponseEntity<TodoStep> updateItem(@PathVariable("id") Long id,
                                               @RequestBody @Valid StepRequest request,
                                               BindingResult bindingResult){
        // TODO: 25.04.2021 Error handling
        TodoStep newStep = stepService.update(id, request);
        if (newStep == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(newStep, HttpStatus.NO_CONTENT);
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
    public ResponseEntity<TodoStep> deleteItem(@PathVariable("id") Long id){
        Optional<TodoStep> step = stepService.findById(id);
        if (!step.isPresent())
            return new ResponseEntity<>((HttpStatus.NOT_FOUND));
        stepService.delete(id);
        return new ResponseEntity<>(step.get(),HttpStatus.NO_CONTENT);
    }
}
