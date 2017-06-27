package de.dschoen.opco.board;

import java.util.List;

public interface CardDao {

	public List<Card> getAllCards();
	public Card getCardById(int cardId);	
	
	public Card addCard(Card card);
	public Card updateCard(Card card);
	public void deleteCard(Card card);
	
	public void disableCard(Card card);
	public void enableCard(Card card);
	
	public int countCards();
}
