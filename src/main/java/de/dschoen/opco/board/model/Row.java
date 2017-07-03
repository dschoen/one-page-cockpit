package de.dschoen.opco.board.model;

public class Row {

    private int rowId;  
    private String name;
	
	// --- Constructor -------------------------------
	
	public Row(String name) {
		this.name = name;
	}

	// -----------------------------------------------
	
	public int getRowId() {
		return rowId;
	}

	public void setRowId(int rowId) {
		this.rowId = rowId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	
}