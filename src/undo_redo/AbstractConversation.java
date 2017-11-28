package undo_redo;

import java.util.Stack;

import memento.AbstractMemento;

public abstract class AbstractConversation<C extends AbstractMemento<Y, Z>, Y, Z> implements Conversation<C, Y, Z> {

	protected Stack<C> undos;
	protected Stack<C> redos;
	
	public AbstractConversation() {
		this.undos = new Stack<C>();
		this.redos = new Stack<C>();
	}
}
