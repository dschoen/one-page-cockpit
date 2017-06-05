package de.dschoen.opco.board;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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

	@ManyToOne
    private BoardRow boardRow;
	
	@ManyToOne
    private BoardColumn boardColumn;
	
	@ManyToOne
    private Board board;
	
	@Column(name="create_date", columnDefinition="DATETIME")
    private LocalDateTime createDate;
	
	@Column(name="start_date", columnDefinition="DATETIME")
    private LocalDateTime startDate;
	
	@Column(name="end_date", columnDefinition="DATETIME")
    private LocalDateTime endDate;
	
	@Column(name="last_update", columnDefinition="DATETIME")
    private LocalDateTime lastUpdate;

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

	@JsonIgnore
	public BoardRow getBoardRow() {
		return boardRow;
	}

	public void setBoardRow(BoardRow boardRow) {
		this.boardRow = boardRow;
	}

	@JsonIgnore
	public BoardColumn getBoardColumn() {
		return boardColumn;
	}

	public void setBoardColumn(BoardColumn boardColumn) {
		this.boardColumn = boardColumn;
	}

	@JsonIgnore
	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public LocalDateTime getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(LocalDateTime lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
}