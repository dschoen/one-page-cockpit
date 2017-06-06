package de.dschoen.opco.board;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.dschoen.opco.user.User;

@Transactional
@Repository
public class BoardDao implements IBoardDAO{

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
	public void addBoard(Board board) {		
		board.setCreateDate(Instant.now());
		entityManager.persist(board);		
	}

	// ----------------------------------------------------
	
	@Override
	public void updateBoard(Board board) {
		Board brd = getBoardById(board.getBoardId());
		brd.setName(board.getName());
		entityManager.flush();		
	}

	// ----------------------------------------------------
	
	@Override
	public void deleteBoard(int id) {
		entityManager.remove(getBoardById(id));		
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
}
