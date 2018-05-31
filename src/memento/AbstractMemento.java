package memento;

public abstract class AbstractMemento<C, S> {

	private C c;
	private S state;
	
	protected AbstractMemento(C c, S state) {
		this.c = c;
		this.state = state;
	}
	
	public final S getState() {
		return this.state;
	}
	
	public final C getSource() {
		return this.c;
	}
	
}
