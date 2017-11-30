package memento;

import commands.selection.MoveCursorCommand;
import commands.selection.MoveSelectionCommand;
import invoker.Invoker;
import memento.CommandMementoable.Memento;
import receiver.Moteur;

public class MoveSelectionMemento extends MoveSelectionCommand implements CommandMementoable {

	private Gardian gardian;
	
	public MoveSelectionMemento(Moteur m, Invoker ui, Gardian gardian) {
		super(m, ui);
		this.gardian = gardian;
	}
	
	@Override
	public void execute(int dot) {
		super.execute(dot);
		this.gardian.register(new Memento(this, dot));
	}
	
	@Override
	public void play(Memento memento) {
		int dot = (Integer) memento.getState();
		super.execute(dot);
	}
}
