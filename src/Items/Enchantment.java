package Items;


public class Enchantment implements Elements{
	int rank;
	String effect;
	public Enchantment(int rank, String effect)
	{
		this.rank = rank;
		this.effect = effect;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public String getEffect() {
		return effect;
	}
	public void setEffect(String effect) {
		this.effect = effect;
	}
	public String toString(){
		return effect+ " "+rank;
	}
}
