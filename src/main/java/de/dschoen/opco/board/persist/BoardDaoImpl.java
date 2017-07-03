package de.dschoen.opco.board.persist;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.dschoen.opco.board.model.Board;
import de.dschoen.opco.board.model.Column;
import de.dschoen.opco.board.model.Row;

@Repository
public class BoardDaoImpl implements BoardDao{
	
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
	
	// ----------------------------------------------------

    @Override
    public void init() {
    	String sql = "CREATE TABLE IF NOT EXISTS boards"
    			+ " (board_id integer"
    			+ ", name varchar(100)"
    			+ ", create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
    			+ ", last_update TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
    			+ " );";
    	this.jdbcTemplate.execute(sql);
    }
    
    // ----------------------------------------------------
    
	@Override
	@Transactional(readOnly=true)
	public ArrayList<Board> getAllBoards() {
		String sql = "SELECT FROM boards ORDER BY board.board_id";
		return (ArrayList<Board>)jdbcTemplate.queryForList(sql, Board.class);
	}

	// ----------------------------------------------------
	
	@Override
	public Board getBoardById(int id) {
		//TODO
		return new Board("TEST");
	}

	// ----------------------------------------------------
	
	@Override
	public Board addBoard(Board board) {		
		board.setCreateDate(Instant.now());
//		entityManager.persist(board);	
		return board;
	}

	
	// ----------------------------------------------------
	
	@Override
	public Board updateBoard(Board board) {
		board.setLastUpdate(Instant.now());			
//		entityManager.flush();
		return board;
	}

	// ----------------------------------------------------
	
	@Override
	public void deleteBoard(Board board) {
//		entityManager.remove(board);		
	}

	// ----------------------------------------------------
	
	@Override
	public int countBoards() {
		String hql = "FROM Board";
//		return entityManager.createQuery(hql).getResultList().size();	
		return 6;
	}
	
	// ----------------------------------------------------
	
	@Override
	public Column getBoardColumnById(int id) {
//		return entityManager.find(Column.class, id);
		return new Column("TEST");
	}
	
	// ----------------------------------------------------
		
	@Override
	public Row getBoardRowById(int id) {
//		return entityManager.find(Row.class, id);
		return new Row("test");
	}
	
	// ----------------------------------------------------

}
