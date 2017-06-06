package de.dschoen.opco.board;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class CardDAO implements ICardDAO {

	@PersistenceContext	
	private EntityManager entityManager;	
	
	// ----------------------------------------------------
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Card> getAllCards() {
		String hql = "FROM Card as brd ORDER BY brd.cardId";
		return (List<Card>) entityManager.createQuery(hql).getResultList();
	}

	// ----------------------------------------------------
	
	@Override
	public Card getCardById(int id) {
		return entityManager.find(Card.class, id);
	}

	// ----------------------------------------------------
	
	@Override
	public void addCard(Card card) {		
		card.setCreateDate(Instant.now());
		entityManager.persist(card);		
	}

	// ----------------------------------------------------
	
	@Override
	public void updateCard(Card card) {
		Card crd = getCardById(card.getCardId());
		crd.setTitle(card.getTitle());
		crd.setText(card.getText());
		crd.setStatus(card.getStatus());
		crd.setEffort(card.getEffort());
		crd.setStartDate(card.getStartDate());
		crd.setEndDate(card.getEndDate());
		
		crd.setBoardColumn(card.getBoardColumn());
		crd.setBoardRow(card.getBoardRow());		
		
		crd.setLastUpdate(Instant.now());
		
		entityManager.flush();		
	}

	// ----------------------------------------------------
	
	@Override
	public void deleteCard(int id) {
		entityManager.remove(getCardById(id));		
	}

	// ----------------------------------------------------
	
}
