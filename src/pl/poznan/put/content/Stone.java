package GoGame;

public class Stone
{
	private final State state;
	private final Group group;

	public State state(){
		return this.state;
	}
	
	public Group group(){
		return this.group;
	}

	public Stone(State state, Group group){
		this.state = state;
		this.group = group;
	}
	public Stone(){
		//this.state=empty_state;
		this.state = new State("empty");
		this.group = null;
	}
}
