package GoGame;
import java.util.List;
import java.util.ArrayList;

public class Group
{
	private List<Coordinates> points = new ArrayList<Coordinates>();
	private int num_liberties;
	
	public void addStone(Coordinates point, int liberties) throws IllegalPlacementException{
		if(num_liberties+liberties-1 <= 0)throw new IllegalPlacementException("Illegal stone placement: Cannot kill own group\n");
		this.points.add(point);
		this.num_liberties+=liberties-1;
	}

	public int liberties(){
		return num_liberties;
	}

	public void takeLiberty() throws IllegalStateException{
		if(num_liberties==1)throw new IllegalStateException("Illegal operation (taking group's liberty): Group must have at least one liberty\n");
		this.num_liberties--;
	}

	public void addLiberty(){
		this.num_liberties++;
	}
	
	public List<Coordinates> listOfCoordinates(){
		return this.points;
	}

	public Group(Coordinates point, int liberties) throws IllegalPlacementException{
		if(liberties<1)throw new IllegalPlacementException("Illegal stone placement: Cannot kill own stone\n");
		this.num_liberties=liberties;
		this.points.add(point);
	}
}
