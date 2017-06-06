package de.dschoen.opco.board;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="boards")
public class Board implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="board_id")
    private int boardId;  
	
	@Column(name="name")
    private String name;
	
	@Column(name="create_date", columnDefinition="DATETIME")
    private LocalDateTime createDate;
	
	@Column(name="last_update", columnDefinition="DATETIME")
    private LocalDateTime lastUpdate;
	
	@OneToMany(cascade = CascadeType.ALL)
	private Collection<BoardColumn> boardColumns = new ArrayList<BoardColumn>();
	
	@OneToMany(cascade = CascadeType.ALL)
	private Collection<BoardRow> boardRows = new ArrayList<BoardRow>();
	
	@OneToMany(cascade = CascadeType.ALL)
	private Collection<Card> cards = new ArrayList<Card>();

	// --- Constructor -------------------------------
	
	public Board() {
	}
	
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

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public LocalDateTime getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(LocalDateTime lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Collection<BoardColumn> getBoardColumns() {
		return boardColumns;
	}

	public void setBoardColumns(Collection<BoardColumn> boardColumns) {
		this.boardColumns = boardColumns;
	}

	public Collection<BoardRow> getBoardRows() {
		return boardRows;
	}

	public void setBoardRows(Collection<BoardRow> boardRows) {
		this.boardRows = boardRows;
	}
	
	public Collection<Card> getCards() {
		return cards;
	}

	public void setCards(Collection<Card> cards) {
		this.cards = cards;
	}
}
