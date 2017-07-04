package de.dschoen.opco.board.model;

public class Row {

    private int rowId;  
    private int boardId;
    private String name;
	
	// --- Factory -------------------------------
	
	public static Row fromName(String name) {
		Row row = new Row();
		row.setName(name);
		return row;
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

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}	
}