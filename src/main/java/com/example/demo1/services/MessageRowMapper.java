package com.example.demo1.services;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.demo1.model.Message;



public class MessageRowMapper implements RowMapper<Message>{

	@Override
	public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
		Message message = new Message();
		message.setId(rs.getInt("id"));
		message.setMessage(rs.getString("message1"));
		return message;
	}
	

}
