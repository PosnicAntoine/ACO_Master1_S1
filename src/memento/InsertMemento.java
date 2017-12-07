package memento;

import commands.com.InsertCommand;
import invoker.Invoker;
import receiver.Moteur;

public class InsertMemento extends InsertCommand implements CommandMementoable {

	private Gardian gardian;

	public InsertMemento(Moteur m, Invoker ui, Gardian gardian) {
		super(m, ui);
		this.gardian = gardian;
	}
	
	@Override
	public void execute(String insertion) {
		super.execute(insertion); 
		this.gardian.register(new Memento(this, this.insertion));
	}
	
	@Override
	public void play(CommandMementoable.Memento memento) {
		super.execute((String) memento.getState());
	}
}
