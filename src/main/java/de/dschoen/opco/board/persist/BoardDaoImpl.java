package de.dschoen.opco.board.persist;

import java.time.Instant;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.dschoen.opco.board.model.Board;
import de.dschoen.opco.board.model.BoardColumn;
import de.dschoen.opco.board.model.BoardRow;

@Transactional
@Repository
public class BoardDaoImpl implements BoardDao{

	@PersistenceContext	
	private EntityManager entityManager;	
	
	// ----------------------------------------------------
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Board> getAllBoards() {
		String hql = "FROM Board as brd ORDER BY brd.boardId";
		return (List<Board>) entityManager.createQuery(hql).getResultList();
	}

	// ----------------------------------------------------
	
	@Override
	public Board getBoardById(int id) {
		return entityManager.find(Board.class, id);
	}

	// ----------------------------------------------------
	
	@Override
	public Board addBoard(Board board) {		
		board.setCreateDate(Instant.now());
		entityManager.persist(board);	
		return board;
	}

	
	// ----------------------------------------------------
	
	@Override
	public Board updateBoard(Board board) {
		board.setLastUpdate(Instant.now());			
		entityManager.flush();
		return board;
	}

	// ----------------------------------------------------
	
	@Override
	public void deleteBoard(Board board) {
		entityManager.remove(board);		
	}

	// ----------------------------------------------------
	
	@Override
	public int countBoards() {
		String hql = "FROM Board";
		return entityManager.createQuery(hql).getResultList().size();	
	}
	
	// ----------------------------------------------------
	
	@Override
	public BoardColumn getBoardColumnById(int id) {
		return entityManager.find(BoardColumn.class, id);
	}
	
	// ----------------------------------------------------
		
	@Override
	public BoardRow getBoardRowById(int id) {
		return entityManager.find(BoardRow.class, id);
	}
	
	// ----------------------------------------------------

}
