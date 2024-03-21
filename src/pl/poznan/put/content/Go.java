package GoGame;

public class Go implements Game
{
	private Board board;
	private final State white_state = new State("white");
	private final State black_state = new State("black");
	private boolean white_pass = false;
	private boolean black_pass = false;
	private boolean black_turn = true;
	private State state;
	private boolean game_over;
	private String input;

	public void play(String input) throws IllegalPlacementException{
		if(game_over == true)
			throw new IllegalPlacementException("Game is already over\n");
		if(black_turn){
			black_pass=false;
			state = black_state;
		}
		else{
			white_pass=false;
			state = white_state;
		}
		input = input.toLowerCase();

		if(input.matches("[1-9][a-s]"))board.placeStone(new Coordinates(input.codePointAt(1)-'a', input.codePointAt(0)-'0'-1), state);
		else if(input.matches("1[0-9][a-s]"))board.placeStone(new Coordinates(input.codePointAt(2)-'a', input.codePointAt(1)-'0'+9), state);
		else if(input.matches("[a-s][1-9]"))board.placeStone(new Coordinates(input.codePointAt(0)-'a', input.codePointAt(1)-'0'-1), state);
		else if(input.matches("[a-s]1[0-9]"))board.placeStone(new Coordinates(input.codePointAt(0)-'a', input.codePointAt(2)-'0'+9), state);

		System.out.print("\033[H\033[2J");
		board.print();
		black_turn=black_turn?false:true;
	}

	public void pass(){
		if(game_over == true)return;
		if(black_turn){
			black_pass=true;
			System.out.println("Black passed.");
		}
		else{
			white_pass=true;
			System.out.println("White passed.");
		}

		black_turn=black_turn?false:true;
		if(black_pass==true && white_pass==true)
			game_over = true;
	}

	public void printBoard(){
		System.out.print("\033[H\033[2J");
		board.print();	
	}

	public void restart(){
		board = new Goban();
		white_pass = false;
		black_pass = false;
		black_turn = true;
		game_over = false;
	}

	public Go(){
		board = new Goban();
		white_pass = false;
		black_pass = false;
		black_turn = true;
		game_over = false;
	}
}
