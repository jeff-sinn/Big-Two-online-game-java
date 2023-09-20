/**
 * subclass of Deck and used to model a deck of cards in Big Two game
 * @author jiffsin
 *
 */
public class BigTwoDeck extends Deck{
	/**
	 * initialize a deck of Big Two Card
	 */
	public void initialize() {
		removeAllCards();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 13; j++) {
				BigTwoCard card = new BigTwoCard(i, j);
				addCard(card);
			}
		}
	}
}
