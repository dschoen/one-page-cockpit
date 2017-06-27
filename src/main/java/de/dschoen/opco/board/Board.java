package de.dschoen.opco.board;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;


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
    private Instant createDate;
	
	@Column(name="last_update", columnDefinition="DATETIME")
    private Instant lastUpdate;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Collection<BoardColumn> boardColumns = new ArrayList<BoardColumn>();
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Collection<BoardRow> boardRows = new ArrayList<BoardRow>();
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy="board", cascade = CascadeType.ALL)
	@Where(clause = "active = true")
	private Collection<Card> cards = new ArrayList<Card>();
	
	@JsonIgnore
	@OneToMany(mappedBy="board", cascade = CascadeType.ALL)
	@Where(clause = "active = false")
	private Collection<Card> disabledCards = new ArrayList<Card>();

	// --- Constructor -------------------------------
	
	public Board() {
	}
	
	public Board(String name) {
		this.name = name;
	}
	
	// -----------------------------------------------
	
	/**
	 * Updates the board's Rows with the data of the given rows
	 * 
	 * @param boardRows
	 */
	public void updateBoardRows(Collection<BoardRow> boardRows) {
		for (BoardRow row : boardRows) {
			// if new row does not exist
			if (this.getBoardRowById(row.getBoardRowId()) == null) {
				this.boardRows.add(new BoardRow(row.getName()));			
			}
			// if existing Row edit
			else if (row.getBoardRowId() > 0) {
				this.getBoardRowById(row.getBoardRowId()).setName(row.getName());
			}			
		}
		this.removeDeletedRows(boardRows);
	}
	
	// -----------------------------------------------
	
	/**
	 * Updates the board's Columns with the data of the given cols
	 * 
	 * @param boardColumns
	 */
	public void updateBoardColumns(Collection<BoardColumn> boardColumns) {
		for (BoardColumn col : boardColumns) {
			// if new col does not exist
			if (this.getBoardColumnById(col.getBoardColumnId()) == null) {
				this.boardColumns.add(new BoardColumn(col.getName()));			
			}
			// if existing Column edit
			else if (col.getBoardColumnId() > 0) {
				this.getBoardColumnById(col.getBoardColumnId()).setName(col.getName());
			}			
		}
		this.removeDeletedColumns(boardColumns);
	}
	
	// ----------------------------------------------
	
	/**
	 * Returns the row of this board with a given id.
	 * 
	 * If row is not found return null
	 * 
	 * @param id
	 * @return
	 */
	public BoardRow getBoardRowById(int id) {		
		for (BoardRow row : this.getBoardRows()) {
			if (id == row.getBoardRowId()) {
				return row;
			}
		}
		return null;
	}
	
	// ----------------------------------------------
	
	/**
	 * Returns the column of this board with a given id.
	 * 
	 * If row is not found return null
	 * 
	 * @param id
	 * @return
	 */
	public BoardColumn getBoardColumnById(int id) {		
		for (BoardColumn col : this.getBoardColumns()) {
			if (id == col.getBoardColumnId()) {
				return col;
			}
		}
		return null;
	}
	
	// -----------------------------------------------
	
	public void addBoardRow(BoardRow row) {
		this.boardRows.add(row);
	}
	
	// -----------------------------------------------
	
	public void removeBoardRow(BoardRow row) {
		this.boardRows.remove(row);
		//TODO handle Cards
	}
	
	// -----------------------------------------------
	
	public void addBoardColumn(BoardColumn col) {
		this.boardColumns.add(col);
	}
	
	// -----------------------------------------------
	
	public void removeBoardColumn(BoardColumn col) {
		this.boardColumns.remove(col);
		//TODO handle Cards
	}
	
	// -----------------------------------------------
	
	public void addCard(Card card) {
		this.cards.add(card);
	}
	
	// -----------------------------------------------
	
	public void removeCard(Card card) {
		this.cards.remove(card);
		//TODO handle Cards
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

	public Collection<Card> getDisabledCards() {
		return disabledCards;
	}

	public void setDisabledCards(Collection<Card> disabledCards) {
		this.disabledCards = disabledCards;
	}
	
	// ------------------------------------------------
	
	private void removeDeletedRows(Collection<BoardRow> boardRows) {
		ArrayList<BoardRow> rowsToRemove = new ArrayList<BoardRow>();
		for (BoardRow row : this.boardRows) {
			if (!boardRowsContainRow(boardRows, row)) {
				rowsToRemove.add(row);
			}
		}
		this.boardRows.removeAll(rowsToRemove);
	}
	
	
	
	private boolean boardRowsContainRow(Collection<BoardRow> rows, BoardRow row) {
		for (BoardRow r : rows) {
			if (r.getBoardRowId() == row.getBoardRowId()) {
				return true;
			}
		}
		return false;
	}
	
	
	
	private void removeDeletedColumns(Collection<BoardColumn> boardColumns) {
		ArrayList<BoardColumn> columnsToRemove = new ArrayList<BoardColumn>();
		for (BoardColumn column : this.boardColumns) {
			if (!boardColumnsContainColumn(boardColumns, column)) {
				columnsToRemove.add(column);
			}
		}
		this.boardColumns.removeAll(columnsToRemove);
	}
	
	
	
	private boolean boardColumnsContainColumn(Collection<BoardColumn> columns, BoardColumn column) {
		for (BoardColumn c : columns) {
			if (c.getBoardColumnId() == column.getBoardColumnId()) {
				return true;
			}
		}
		return false;
	}
}
