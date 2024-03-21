package GoGame;

public interface Game
{
	public void play(String input) throws IllegalPlacementException;
	public void pass();
	public void restart();
	public void printBoard();
}	
