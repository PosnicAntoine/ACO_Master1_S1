package Compensation;

import memento.AbstractMemento;

public interface Conversation<T extends AbstractMemento<Y, Z>, Y, Z> {

	public void register(T memento);
	public void undo();
	public void redo();
	
}
