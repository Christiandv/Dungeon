package Game;
import Items.Elements;


public class Damage implements Elements{// damage obj to be used to describe damage and effects applied
	int amount;
	boolean physical;
	int elemental;//use a interface
	public Damage(int amount, boolean physical, int elemental){
		this.amount = amount;
		this.physical = physical;
		this.elemental = elemental;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public boolean isPhysical() {
		return physical;
	}
	public void setPhysical(boolean physical) {
		this.physical = physical;
	}
	public int getElemental() {
		return elemental;
	}
	public void setElemental(int elemental) {
		this.elemental = elemental;
	}
	
}
