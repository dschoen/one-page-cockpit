package de.dschoen.opco.board;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="cards")
public class Card implements Serializable{

	private static final long serialVersionUID = 1L;
		
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="card_id")
    private int cardId;  
	
	@Column(name="title")
    private String title;
	
	@Column(name="text")
    private String text;
	
	@Column(name="status")
    private String status;
	
	@Column(name="effort")
    private String effort;
	
	@Column(name="active")
	@NotNull
    private boolean active;

	@ManyToOne
	@JoinColumn(name="board_row_id")
    private BoardRow boardRow;
	
	@ManyToOne
	@JoinColumn(name="board_column_id")
    private BoardColumn boardColumn;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="board_id")
    private Board board;
	
	@Column(name="create_date")
    private Instant createDate;
	
	@Column(name="start_date")
    private Instant startDate;
	
	@Column(name="end_date")
    private Instant endDate;
	
	@Column(name="last_update")
    private Instant lastUpdate;

	
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

	public BoardRow getBoardRow() {
		return boardRow;
	}

	public void setBoardRow(BoardRow boardRow) {
		this.boardRow = boardRow;
	}

	public BoardColumn getBoardColumn() {
		return boardColumn;
	}

	public void setBoardColumn(BoardColumn boardColumn) {
		this.boardColumn = boardColumn;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
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
}