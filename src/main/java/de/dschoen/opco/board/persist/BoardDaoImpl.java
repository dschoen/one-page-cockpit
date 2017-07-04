package de.dschoen.opco.board.persist;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.dschoen.opco.board.model.Board;
import de.dschoen.opco.board.model.Column;
import de.dschoen.opco.board.model.Row;

@Repository
public class BoardDaoImpl implements BoardDao{
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	// table name and Columns
	static final String boards 		= "boards";
	static final String board_id	= "board_id";
	static final String name		= "name";
	static final String create_date	= "create_date";
	static final String last_update	= "last_update";	
	static final String rows		= "rows";
	static final String row_id		= "row_id";
	static final String columns		= "columns";
	static final String column_id	= "column_id";
	
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
	
	// ----------------------------------------------------

    @Override
    public void createBoardTable() {
    	String sql = "CREATE TABLE IF NOT EXISTS "+boards
    			+ " ("
    			+ " "  +board_id+ " INTEGER NOT NULL AUTO_INCREMENT"
    			+ ", " +name+" VARCHAR(100)"
    			+ ", " +create_date+" TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
    			+ ", " +last_update+" TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
    			+ ", PRIMARY KEY ("+board_id+")"
    			+ " );";
    	this.jdbcTemplate.update(sql);
    }
    
    // ----------------------------------------------------

    @Override
    public void createColumnTable() {
    	String sql = "CREATE TABLE IF NOT EXISTS "+columns
    			+ " ("
    			+ "  "+column_id+" INTEGER NOT NULL AUTO_INCREMENT"
    			+ ", "+board_id+" INTEGER NOT NULL"
    			+ ", "+name+" VARCHAR(100)"
    			+ ", PRIMARY KEY ("+column_id+")"
    			+ " );";
    	this.jdbcTemplate.update(sql);
    }
    
    // ----------------------------------------------------

    @Override
    public void createRowTable() {
    	String sql = "CREATE TABLE IF NOT EXISTS "+rows
    			+ " ("
    			+ "  "+row_id+" INTEGER NOT NULL AUTO_INCREMENT"
    			+ ", "+board_id+" INTEGER NOT NULL"
    			+ ", "+name+" VARCHAR(100)"
    			+ ", PRIMARY KEY ("+row_id+")"
    			+ " );";
    	this.jdbcTemplate.update(sql);
    }
    
    // ----------------------------------------------------
    
	@Override
	public int countBoards() {
		String sql = "SELECT count(*) FROM "+boards;
		return jdbcTemplate.queryForObject(sql, Integer.class);		
	}
    
    // ----------------------------------------------------
    
	@Override
	public boolean boardExistsById(int id) {
		String sql = "SELECT count(*) FROM "+boards+" WHERE "+board_id+"=?;";
		Object[] params = {id};
		int count = jdbcTemplate.queryForObject(sql, params, Integer.class);
		return ( count > 0 ? true : false);
	}
	
	// ----------------------------------------------------
	
	@Transactional
	@Override
	public Board addBoard(Board board) {		
		this.insertBoard(board);
		int id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID();", Integer.class);
		return this.getBoardById(id);
	}
	
	
	// ----------------------------------------------------
	
	public void insertBoard(Board board) {
		String sql = "INSERT INTO "
				+ boards 
				+ " ("+name
				+ " )"
				+ " VALUES"
				+ " (?)"
				+ ";";		
		Object[] params = {
				board.getName(),
		};		
		jdbcTemplate.update(sql, params);
	}
	
	// ----------------------------------------------------
	
	@Override
	public Board getBoardById(int id) {
		String sql = "SELECT * FROM "+boards+" WHERE "+board_id+"=? ;";
		Object[] params = {id};	
		return jdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(Board.class));
	}

	// ----------------------------------------------------
	
	@Override
	@Transactional(readOnly=true)
	public ArrayList<Board> getBoards() {
		String sql = "SELECT * FROM "+boards+" ORDER BY "+board_id+";";
		
		List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
		
		return this.mapQueryResultToList( result );
	}
	
	// ----------------------------------------------------
	
	@Override
	@Transactional
	public Board updateBoard(Board board) {
		String sql = "UPDATE "
				+ boards 
				+ " SET"
				+ "  "+name+"=?"
				+ ", "+last_update+"=?"
				+ " WHERE "
				+ board_id+"=?"
				+ ";";		
		Object[] params = {
				board.getName(),
				Timestamp.from(Instant.now()),
				board.getBoardId()
		};		
		jdbcTemplate.update(sql, params);
		int id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID();", Integer.class);
		return this.getBoardById(id);
	}

	// ----------------------------------------------------

	@Override
	public void deleteBoard(Board board) {
		String sql = "DELETE FROM "+boards+" WHERE "+board_id+"=?;";
	    jdbcTemplate.update(sql, board.getBoardId());	
	}
	
	// ----------------------------------------------------

	@Override
	public ArrayList<Column> getColumnsOfBoard(Board board) {
		// TODO Auto-generated method stub
		return null;
	}

	// ----------------------------------------------------
	
	@Override
	public Column getColumnById(int id) {
		String sql = "SELECT * FROM "+columns+" WHERE "+column_id+"=? ;";
		Object[] params = {id};	
		return jdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(Column.class));
	}

	// ----------------------------------------------------
	
	@Transactional
	@Override
	public Column addColumnToBoard(Column column, Board board) {
		String sql = "INSERT INTO "
				+ columns 
				+ " ("+name+", "+board_id
				+ " )"
				+ " VALUES"
				+ " (?, ?)"
				+ ";";		
		Object[] params = {
				column.getName(),
				board.getBoardId()
		};		
		jdbcTemplate.update(sql, params);
		int id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID();", Integer.class);
		return this.getColumnById(id);
	}

	// ----------------------------------------------------
	
	@Override
	public Column updateColumn(Column column) {
		String sql = "UPDATE "
				+ columns 
				+ " SET"
				+ "  "+name+"=?"
				+ " WHERE "
				+ column_id+"=?"
				+ ";";		
		Object[] params = {
				column.getName(),
				column.getColumnId()
		};		
		jdbcTemplate.update(sql, params);
		int id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID();", Integer.class);
		return this.getColumnById(id);
	}

	// ----------------------------------------------------
	
	@Override
	public void deleteColumn(Column column) {
		String sql = "DELETE FROM "+columns+" WHERE "+column_id+"=?;";
	    jdbcTemplate.update(sql, column.getColumnId());	
	}

	// ----------------------------------------------------
	
	@Override
	public ArrayList<Row> getRowsOfBoard(Board board) {
		// TODO Auto-generated method stub
		return null;
	}

	// ----------------------------------------------------
	
	@Override
	public Row getRowById(int id) {
		String sql = "SELECT * FROM "+rows+" WHERE "+row_id+"=? ;";
		Object[] params = {id};	
		return jdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(Row.class));
	}

	// ----------------------------------------------------
	
	@Override
	public Row addRowToBoard(Row row, Board board) {
		String sql = "INSERT INTO "
				+ rows 
				+ " ("+name+", "+board_id
				+ " )"
				+ " VALUES"
				+ " (?, ?)"
				+ ";";		
		Object[] params = {
				row.getName(),
				board.getBoardId()
		};		
		jdbcTemplate.update(sql, params);
		int id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID();", Integer.class);
		return this.getRowById(id);
	}

	// ----------------------------------------------------
	
	@Override
	public Row updateRow(Row row) {
		String sql = "UPDATE "
				+ rows 
				+ " SET"
				+ "  "+name+"=?"
				+ " WHERE "
				+ row_id+"=?"
				+ ";";		
		Object[] params = {
				row.getName(),
				row.getRowId()
		};		
		jdbcTemplate.update(sql, params);
		int id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID();", Integer.class);
		return this.getRowById(id);
	}

	// ----------------------------------------------------
	
	@Override
	public void deleteRow(Row row) {
		String sql = "DELETE FROM "+rows+" WHERE "+row_id+"=?;";
	    jdbcTemplate.update(sql, row.getRowId());	
	}
	
	//  --------------------------------------------------------------------------------
	//  --------------------------------------------------------------------------------

	private ArrayList<Board> mapQueryResultToList(List<Map<String, Object>> result) {		
		ArrayList<Board> boards = new ArrayList<Board>();
		for (Map<String, Object> row : result) {
			
			boards.add( this.mapRowToBoard(row) );
		}
		return boards;		
	}
	
	//  --------------------------------------------------------------------------------
	
	private Board mapRowToBoard(Map<String, Object> row) {
		Board board = new Board();
		
		board.setBoardId((int)row.get(board_id));
		board.setName((String)row.get(name));		
		board.setCreateDate( ((Timestamp)row.get(create_date)).toInstant() );
		board.setLastUpdate( ((Timestamp)row.get(last_update)).toInstant() );
		
		return board;
	}	
}
