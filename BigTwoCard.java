/**
 * subclass of Card that is used to model a card in Big Two Game
 * @author jeffsin
 *
 */
public class BigTwoCard extends Card{
	/**
	 * Constructor for building a card with specified suit and rank
	 * @param suit card built with specific suit
	 * @param rank card built with speecific rank
	 */
	public BigTwoCard(int suit, int rank){
		super(suit, rank);
	}
	
	
	/**
	 * Compares this card with the specified card for order.
	 * 
	 * @param card the card to be compared
	 * @return a negative integer, zero, or a positive integer as this card is less
	 *         than, equal to, or greater than the specified card
	 */
	public int compareTo(Card card) {
		int rankk = this.rank;
		int cardrank = card.rank;
		if (rankk == 0 || rankk == 1) {
			rankk += 13;
		}
		if (cardrank == 0 || cardrank == 1) {
			cardrank += 13;
		}
		if (rankk > cardrank) {
			return 1;
		}
		else if(rankk < cardrank) {
			return -1;
		}
		else if(this.suit > card.suit) {
			return 1;
		}
		else if (this.suit < card.suit) {
			return -1;
		}
		else {
			return 0;
		}
	}
}
