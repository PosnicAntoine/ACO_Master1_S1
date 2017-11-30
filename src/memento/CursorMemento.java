package memento;

import commands.selection.CursorCommand;
import invoker.Invoker;
import receiver.Moteur;

public class CursorMemento extends CursorCommand implements CommandMementoable {

	private Gardian gardian;
	
	public CursorMemento(Moteur m, Invoker ui, Gardian gardian) {
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
