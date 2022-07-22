package com.qa.todolist_layered_architecture.controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.qa.todolist_layered_architecture.domain.entity.Todo;
import com.qa.todolist_layered_architecture.domain.repository.TodoRepository;
import com.qa.todolist_layered_architecture.utilities.InputUtilities;
import com.qa.todolist_layered_architecture.utilities.JdbcUtils;

public class TodoController {

	private TodoRepository todoRepository;
	
	public TodoController(TodoRepository todoRepository) {
		this.todoRepository = todoRepository;
	}
	
	public void create() {
		String name = InputUtilities.getString("Name: ");
		String date = InputUtilities.getString("Date (yyyy/mm/dd): ");
		
		Todo todo = todoRepository.save(new Todo(name, date.toLocalDate()));
		System.out.println("Todo created successfully!\n\t" + todo.toString() + "\n");
	}
	
	public Todo save(Todo todo) {
		//a question mark is a placeholder or also known as bind variable.
		String sql = "INSERT INTO todo(name, dueDate) VALUES(?,?);";
		String columnNames[] = new String[] { "id" }; //indicating what data we want returned
		
		try (Connection conn = JdbcUtils.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
			//a prepared statement has data inserted counting from 1
			//- each bind variable in the sql has an associated number, starting from 1 for the first questionmark ?
			ps.setString(1, todo.getName());
			ps.setDate(2, Date.valueOf(todo.getDueDate()));
			try {
				int modifiedCount = ps.executeUpdate();
				
				if (modifiedCount > 0) {
					
					ResultSet keys = ps.getGeneratedKeys();
					
					if(keys.next()) {
						todo.setId(keys.getLong("id"));
						return todo;
					}
				}
				return null;
		} catch (SQLException e) {
			e.printStackTrace();
		}			
	} catch (SQLexception e) {
		e.printStackTrace();
	}
}
	
	public List<Todo> read() {
		List<Todo> todoItems = new ArrayList<>();
		
		try (Connection conn = JdbcUtils.getConnection();
				Statement stmt = conn.createStatement()){
			ResultSet rs = stmt.executeQuery("SELECT * FROM todo");
			
			while (rs.next()) {
				long id = rs.getLong("id");
				String name = rs.getString("name");
				Date date = rs.getDate("dueDate");
				todoItems.add(new Todo(id, name, date.toLocalDate()));
			}
		} catch (SQLException e) {
				
		}
		return todoItems;
	}
	
	public void update() {
		
	}
	
	public boolean deleteById() {
		return false;
	}
}
