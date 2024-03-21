package GoGame;

public interface Board
{
	public void placeStone(Coordinates placement, State state) throws IllegalPlacementException;
	public void print();
}
