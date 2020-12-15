package com.example.demo1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo1.model.Message;
import com.example.demo1.services.MessageService;

@Controller
public class MessagesController {
	@Autowired
	private MessageService messageService;
	
	@RequestMapping(value="/addMessage", method = RequestMethod.POST, produces = "application/json",consumes = "application/json")
	@ResponseBody
	public ResponseEntity<String> sendMessage(@RequestBody Message message){
		return ResponseEntity.status(HttpStatus.OK).body(messageService.sendMessage(message));
	}
	
	@RequestMapping(value="/getMessage/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<String> getMessage(@PathVariable int id){
		return ResponseEntity.status(HttpStatus.OK).body(messageService.getMessage(id));
	}
	
	@RequestMapping(value="/updateMessage/{id}", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public ResponseEntity<String> updateMessage(@PathVariable int id, @RequestBody Message message){
		return ResponseEntity.status(HttpStatus.OK).body(messageService.updateMessage(id, message));
	}
	
	@RequestMapping(value="/deleteMessage/{id}", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	public ResponseEntity<String> deleteMessage(@PathVariable int id){
		return ResponseEntity.status(HttpStatus.OK).body(messageService.deleteMessage(id));
	}
	@RequestMapping(value="/getAllMessages", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<String> getAllMessages(){
		return ResponseEntity.status(HttpStatus.OK).body(messageService.getAllMessages());
	}
}
