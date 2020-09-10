package com.howtodoinjava.demo.controller;

import java.time.Duration;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
 
import com.howtodoinjava.demo.model.Employee;
import com.howtodoinjava.demo.service.EmployeeService;
 
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
 
@RestController
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
 
    @RequestMapping(value = { "/create", "/" }, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public void create(@RequestBody Employee e) {
        employeeService.create(e);
    }
 
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Mono<Employee>> findById(@PathVariable("id") Integer id) {
        Mono<Employee> e = employeeService.findById(id);
        HttpStatus status = e != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<Mono<Employee>>(e, status);
    }
 
    @RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
    @ResponseBody
    public Flux<Employee> findByName(@PathVariable("name") String name) {
        return employeeService.findByName(name);
    }
 
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public Flux<Employee> findAll() {
        Flux<Employee> emps = employeeService.findAll().delayElements(Duration.ofMillis(1500));
        return emps;
    }

    @RequestMapping(value = "/tail", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public Flux<Employee> findWithTailableCursorBy() {
        Flux<Employee> emps = employeeService.findWithTailableCursorBy().delayElements(Duration.ofMillis(1500));
        return emps;
    }
 
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public Mono<Employee> update(@RequestBody Employee e) {
        return employeeService.update(e);
    }
 
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Integer id) {
        employeeService.delete(id).subscribe();
    }

    @GetMapping("/stream-sse")
    public Flux<ServerSentEvent<String>> streamEvents() {
    	return Flux.interval(Duration.ofSeconds(1))
			.map(sequence -> ServerSentEvent.<String> builder()
				.id(String.valueOf(sequence))
				.event("periodic-event")
				.data("SSE - " + LocalTime.now().toString())
				.build()
			);
    }
    
    @RequestMapping(path = "/stream-string", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamManual() {
    	return Flux.just("AA", "BB", "CC").delayElements(Duration.ofMillis(1500));
    }
 
}
