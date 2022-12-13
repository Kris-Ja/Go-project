public class State
{
	private final int state;//0-empty, 1-black, 2-white

	public boolean empty(){
		if(this.state==0)return true;
		else return false;
	}
	public boolean white(){
		if(this.state==2)return true;
		else return false;
	}
	public boolean black(){
		if(this.state==1)return true;
		else return false;
	}
	public State(String string){
		if(string.equals("empty")||string.equals("Empty"))
			this.state=0;
		else if(string.equals("black")||string.equals("Black"))
			this.state=1;
		else if(string.equals("white")||string.equals("White"))
			this.state=2;
		else this.state=0;
	}
}
