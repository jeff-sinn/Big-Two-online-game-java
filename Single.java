/**
 * subclass of class Hand that is used to provide a detailed implementation of the abstract
 * method of type Single
 * @author jeffsin
 *
 */
public class Single extends Hand{

	public Single(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}
	/**
	 * a method the get the type of the hand
	 * @return the type of the hand
	 */
	public String getType() {
		return "Single";
	}
	/**
	 * check if this hand is of Type single
	 * @return whether this hand is of type single
	 */
	public boolean isValid() {
		if(this.size() != 1) {
			return false;
		}
		else {
			return true;
		}
	}
}