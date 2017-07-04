package de.dschoen.opco.board.model;


public class Column {

    private int columnId;  
    private int boardId;
    private String name;
	
	// --- Factory -------------------------------
	
	public static Column fromName(String name) {
		Column column = new Column();
		column.setName(name);
		return column;
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

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}	
}
