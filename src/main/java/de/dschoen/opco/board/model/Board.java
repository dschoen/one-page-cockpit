package de.dschoen.opco.board.model;

import java.time.Instant;

public class Board  {

    private int boardId;  
    private String name;
    private Instant createDate;
    private Instant lastUpdate;
    	
	// --- Factory --------------------------------------------

	public static Board fromName(String name) {
		Board board = new Board();
		board.setName(name);
		return board;
	}
	
	// -----------------------------------------------
	
	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Instant getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Instant createDate) {
		this.createDate = createDate;
	}

	public Instant getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Instant lastUpdate) {
		this.lastUpdate = lastUpdate;
	}	
}
