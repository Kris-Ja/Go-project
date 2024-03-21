package GoGame;
import java.util.Scanner;

public class Main
{
	public static void main(String args[]){
		Go game = new Go();
		String pattern = new String("(?i)([1-9][a-s])|(1[0-9][a-s])|([a-s][1-9])|([a-s]1[0-9])|(pas?s)|(print)|(exit)|(restart)");
		Scanner scanner = new Scanner(System.in);
		String input;

		game.printBoard();
		while(true){
			do{input = scanner.next();}	while(!input.matches(pattern));
			if(input.matches("(?i)exit"))return;
			if(input.matches("(?i)restart")){
				game.restart();
				game.printBoard();
			}
			else if(input.matches("(?i)pas?s")){
				game.pass();
			}
			else if(input.matches("(?i)print")){
				game.printBoard();
			}
			else{
				try{
					game.play(input);
				}catch(IllegalPlacementException exception){
					System.out.printf("%s\n", exception);
				}
			}
		}
	}
}
