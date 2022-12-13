import java.util.Scanner;

public class Main
{
	//	private Board board = new Board();
	//	private String pattern = new String("(?i)(\b[1-9][a-s]\b)|(\b1[0-9][a-s]\b)|(\b[a-s][1-9]\b)|(\b[a-s]1[0-9]\b)|(\bpas?s\b)|(\bexit\b)");
	//	private State white_state = new State("white");
	//	private State black_state = new State("black");

	public static void main(String args[]){
		Board board = new Board();
		String pattern = new String("(?i)([1-9][a-s])|(1[0-9][a-s])|([a-s][1-9])|([a-s]1[0-9])|(pas?s)|(exit)");
		State white_state = new State("white");
		State black_state = new State("black");
		Scanner scanner = new Scanner(System.in);
		boolean white_pass=false;
		boolean black_pass=false;
		boolean black_turn=true;
		State state;
		String input;

		System.out.print("\033[H\033[2J");
		board.print();	
		while(white_pass==false || black_pass==false){
			boolean wrong_move=false;
			do{input = scanner.next();}	while(!input.matches(pattern));
			if(input.matches("(?i)exit"))return;
			if(input.matches("(?i)pas?s")){
				if(black_turn){
					black_pass=true;
					System.out.println("Black passed.");
				}
				else{
					white_pass=true;
					System.out.println("White passed.");
				}
			}
			else{
				if(black_turn){
					black_pass=false;
					state = black_state;
				}
				else{
					white_pass=false;
					state = white_state;
				}
				input = input.toLowerCase();
				try{
					if(input.matches("[1-9][a-s]"))board.placeStone(new Coordinates(input.codePointAt(1)-'a', input.codePointAt(0)-'0'-1), state);
					else if(input.matches("1[0-9][a-s]"))board.placeStone(new Coordinates(input.codePointAt(2)-'a', input.codePointAt(1)-'0'+9), state);
					else if(input.matches("[a-s][1-9]"))board.placeStone(new Coordinates(input.codePointAt(0)-'a', input.codePointAt(1)-'0'-1), state);
					else if(input.matches("[a-s]1[0-9]"))board.placeStone(new Coordinates(input.codePointAt(0)-'a', input.codePointAt(2)-'0'+9), state);
				}catch(IllegalPlacementException exception){
					System.out.printf("%s\n", exception);
					wrong_move=true;
				}
				System.out.print("\033[H\033[2J");
				board.print();
			}
			if(wrong_move==false)black_turn=black_turn?false:true;
		}
	}
}
