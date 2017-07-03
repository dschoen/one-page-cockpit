package de.dschoen.opco.board.model;


public class Column {

    private int columnId;  
    private String name;
	
	// --- Constructor -------------------------------
	
	public Column(String name) {
		this.name = name;
	}

	// -----------------------------------------------
	
	public int getColumnId() {
		return columnId;
	}

	public void setColumnId(int columnId) {
		this.columnId = columnId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	
}
