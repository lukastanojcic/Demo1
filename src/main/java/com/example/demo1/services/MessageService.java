package com.example.demo1.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.demo1.model.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MessageService {
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	DataSource dataSource;
	
	public String sendMessage(Message message) {
		String json = "";
		ObjectMapper objMap = new ObjectMapper();
		Map<String, Object> input = new HashMap<>();
		try {
			String sql = "INSERT INTO message(id, message1) VALUES(?,?)";
			jdbcTemplate.update(sql, new Object[] {message.getId(), message.getMessage()});
			input.put("result", 1);
			input.put("status", "Success");
		} catch(Exception e) {
			input.put("result", 0);
			input.put("status", "Fail");
		}
		try {
			json = objMap.writeValueAsString(input);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	public String getMessage(int id) {
		String json = "";
		ObjectMapper objMap = new ObjectMapper();
		Map<String, Object> input = new HashMap<>();
		String sql = "SELECT * FROM message WHERE id = ?";
		Message m = new Message();
		try {
			m = jdbcTemplate.queryForObject(sql, new Object[] {id}, new MessageRowMapper());
			//Map<String , Object> map = this.jdbcTemplate.queryForMap(sql, id);
			//Message m1 = jdbcTemplate.queryForObject(sql, new Object[] {id}, new MessageRowMapper());
			input.put("result", 1);
			input.put("status", "Success");
			input.put("message", m);
		}catch(Exception e) {
			input.put("result", 0);
			input.put("status", "Fail");
			e.printStackTrace();
		}
		try {
			json = objMap.writeValueAsString(input);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	public Message findMessage(Integer id) {
		Message m = null;
		String sql = "SELECT * FROM message WHERE id = ?";
		try {
			m = jdbcTemplate.queryForObject(sql, new Object[] {id}, new MessageRowMapper());
		}catch(Exception e) {
			e.printStackTrace();
		}
		return m;
	}
	
	public String updateMessage(int id, Message message) {
		String json = "";
		ObjectMapper objMap = new ObjectMapper();
		Map<String, Object> input = new HashMap<>();
		Message m = findMessage(id);
		if(m == null) {
			input.put("result", "0");
			input.put("status", "Client not find!");
			
		}
		else{
			try {
				String sql = "UPDATE message SET message1 = ? WHERE id = ?";
				jdbcTemplate.update(sql, message.getMessage(), id);
				input.put("result", "1");
				input.put("status", "Success");
			}catch(Exception e) {
				e.printStackTrace();
				input.put("result", "2");
				input.put("status", "Fail");
			}
		}
		try {
			json = objMap.writeValueAsString(input);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	public String deleteMessage(int id) {
		String json = "";
		ObjectMapper objMap = new ObjectMapper();
		Map<String, Object> input = new HashMap<>();
		Message m = findMessage(id);
		if(m == null) {
			input.put("result", "0");
			input.put("status", "Client not find!");
		}else {
			try {
				String sql = "DELETE FROM message WHERE id = ?";
				jdbcTemplate.update(sql, id);
				input.put("result", "1");
				input.put("status", "Success");
			}catch(Exception e) {
				e.printStackTrace();
				input.put("result", "2");
				input.put("status", "Fail");
			}
		}
		try {
			json = objMap.writeValueAsString(input);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	public String getAllMessages(){
		String json = "";
		String sql = "SELECT * FROM message";
		ObjectMapper objMap = new ObjectMapper();
		Map<String, Object> input = new HashMap<>();
		List<Map<String,Object>> list = null;
		List<Message> messages = new ArrayList<>();
		try {
			list = jdbcTemplate.queryForList(sql);
			list.forEach(item -> {
				Message m  = new Message();
				m.setId(Integer.valueOf(String.valueOf(item.get("id"))));
				m.setMessage(String.valueOf(item.get("message1")));
				messages.add(m);
			});
			input.put("result", "1");
			input.put("status", "Success");
			input.put("messages", messages);
		}catch(Exception e) {
			e.printStackTrace();
			input.put("result", "2");
			input.put("status", "Fail");
		}
		try {
			json = objMap.writeValueAsString(input);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}
}
