package de.dschoen.opco.board.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


public class Card {


    private int cardId;  
	
    private String title;
	
    private String text;
	
    private String status;

    private String effort;
	
    private boolean active;	
	
    private Instant createDate;
	
    private Instant startDate;
	
    private Instant endDate;
	
    private Instant lastUpdate;

    private ArrayList<Board> boards = new ArrayList<Board>();

    private ArrayList<Column> boardColumns = new ArrayList<Column>();
	

    private ArrayList<Row> boardRows = new ArrayList<Row>();
	
	// --- Constructor -----------------------------
	
	public Card() {
		this.active = true;
	}
	
	// ---------------------------------------------
	
	public int getCardId() {
		return cardId;
	}

	public void setCardId(int cardId) {
		this.cardId = cardId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEffort() {
		return effort;
	}

	public void setEffort(String effort) {
		this.effort = effort;
	}

	public Instant getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Instant createDate) {
		this.createDate = createDate;
	}

	public Instant getStartDate() {
		return startDate;
	}

	public void setStartDate(Instant startDate) {
		this.startDate = startDate;
	}

	public Instant getEndDate() {
		return endDate;
	}

	public void setEndDate(Instant endDate) {
		this.endDate = endDate;
	}

	public Instant getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Instant lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public ArrayList<Board> getBoards() {
		return boards;
	}

	public ArrayList<Column> getBoardColumns() {
		return boardColumns;
	}

	public ArrayList<Row> getBoardRows() {
		return boardRows;
	}
}