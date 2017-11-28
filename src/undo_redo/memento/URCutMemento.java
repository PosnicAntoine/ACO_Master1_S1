package undo_redo.memento;

import memento.CutMemento;
import memento.Gardian;
import receiver.Moteur;

public class URCutMemento extends CutMemento implements URCommandMementoable {

	URMementoableConversation conversation;
	
	public URCutMemento(Moteur m, Gardian gardian, URMementoableConversation conversation) {
		super(m, gardian);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void undo(undo_redo.memento.URCommandMementoable.Memento memento) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void redo(undo_redo.memento.URCommandMementoable.Memento memento) {
		// TODO Auto-generated method stub
		
	}

}
