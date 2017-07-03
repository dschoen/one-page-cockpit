package de.dschoen.opco.board.model;

import java.time.Instant;
import java.util.ArrayList;

public class Board  {

    private int boardId;  
    private String name;
    private Instant createDate;
    private Instant lastUpdate;
    
    private ArrayList<Column> columns 	= new ArrayList<Column>();
    private ArrayList<Row> rows 		= new ArrayList<Row>();
    private ArrayList<Card> cards 		= new ArrayList<Card>();
    
	// --- Constructor -------------------------------
	
	public Board(String name) {
		this.name = name;
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

	public ArrayList<Column> getColumns() {
		return columns;
	}

	public void setColumns(ArrayList<Column> columns) {
		this.columns = columns;
	}

	public ArrayList<Row> getRows() {
		return rows;
	}

	public void setRows(ArrayList<Row> rows) {
		this.rows = rows;
	}

	public ArrayList<Card> getCards() {
		return cards;
	}

	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
	}	
}
