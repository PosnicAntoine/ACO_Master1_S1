package Memento;

import java.util.LinkedList;
import java.util.Queue;

public class Gardian {

	private boolean record = false;
	private Queue<CommandMementoable.Memento> mementos;
	
	public Gardian() {
		this.mementos = new LinkedList<CommandMementoable.Memento>();
	}
	
	public void startRecord() {
		this.record = true;
	}
	
	public boolean isEmpty() {
		return this.mementos.isEmpty();
	}
	
	public void register(CommandMementoable.Memento memento) {
		if(!this.isRecording()) return;
		this.mementos.add(memento);
	}
	
	public boolean isRecording() {
		return this.record;
	}
	
	public void stopRecord() {
		this.record = false;
	}
	
	public void playBack() {
		for(CommandMementoable.Memento m : this.mementos) {
			m.getCommand().play(m);
		}
	}
}
