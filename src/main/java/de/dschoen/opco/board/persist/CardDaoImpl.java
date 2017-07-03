package de.dschoen.opco.board.persist;

import java.time.Instant;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.dschoen.opco.board.model.Card;

@Transactional
@Repository
public class CardDaoImpl implements CardDao {

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
	public Card addCard(Card card) {		
		card.setCreateDate(Instant.now());
		entityManager.persist(card);
		return card;
	}

	// ----------------------------------------------------
	
	@Override
	public Card updateCard(Card card) {
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
		return crd;
	}

	// ----------------------------------------------------
	
	@Override
	public void deleteCard(Card card) {
		entityManager.remove(getCardById(card.getCardId()));		
	}

	// ----------------------------------------------------
	
	@Override
	public void enableCard(Card card) {
		Card crd = getCardById(card.getCardId());
		crd.setActive(true);		
		crd.setLastUpdate(Instant.now());		
		entityManager.flush();		
	}
	
	// ----------------------------------------------------
	
	@Override
	public void disableCard(Card card) {
		Card crd = getCardById(card.getCardId());
		crd.setActive(false);		
		crd.setLastUpdate(Instant.now());		
		entityManager.flush();		
	}
	
	// ----------------------------------------------------
	
	@Override
	public int countCards() {
		String hql = "FROM Card";
		return entityManager.createQuery(hql).getResultList().size();
	}
	
}
