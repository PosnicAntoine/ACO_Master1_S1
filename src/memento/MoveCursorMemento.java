package memento;

import commands.selection.MoveCursorCommand;
import invoker.Invoker;
import receiver.Moteur;

public class MoveCursorMemento extends MoveCursorCommand implements CommandMementoable {

	private Gardian gardian;
	
	public MoveCursorMemento(Moteur m, Invoker ui, Gardian gardian) {
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
