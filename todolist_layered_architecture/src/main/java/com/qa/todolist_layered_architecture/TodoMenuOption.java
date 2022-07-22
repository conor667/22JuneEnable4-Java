package com.qa.todolist_layered_architecture;

public enum TodoMenuOption {

	CREATE("C", "Create an Item"), 
	READ("R", "Read the current list"), 
	UPDATE("U", "Update an item on the list"), 
	DELETE_BY_ID("D", "Delete an item by using the item ID"), 
	DELETE_BY_NAME("DN", "Delete an item by using the item name"), 
	EXIT("E", "exit the program");
	
	private String inputCode;
	private String description;
	
	private TodoMenuOption(String inputCode, String description) {
		this.inputCode = inputCode;
		this.description = description;
	}
	
	public String getInputCode() {
		return this.inputCode;
	}
	public String getDescription() {
		return this.description;
	}
}
