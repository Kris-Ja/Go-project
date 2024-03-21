package GoGame;
import java.util.ArrayList;
import java.util.List;

public class Coordinates
{
	private final int x;
	private final int y;

	public List<Coordinates> neighbours(){
		List<Coordinates> list=new ArrayList<Coordinates>();
		if(x>0)list.add(new Coordinates(x-1, y));
		if(y>0)list.add(new Coordinates(x, y-1));
		if(x<18)list.add(new Coordinates(x+1, y));
		if(y<18)list.add(new Coordinates(x, y+1));
		return list;
	}
	public boolean equals(Coordinates b){
		if(this.x==b.x && this.y==b.y)return true;
		return false;
	}

	public int x(){
		return this.x;
	}
	public int y(){
		return this.y;
	}
	public Coordinates(int x, int y){
		this.x=x;
		this.y=y;
	}		
}
