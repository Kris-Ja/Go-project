package GoGame;
import java.util.List;

public class Goban implements Board
{
	private Stone[][] points = new Stone[19][19];
	private Coordinates last_removed = null;
	private final Stone empty_stone = new Stone();

	private void removeGroup(Coordinates coordinates){
		List<Coordinates> to_remove=points[coordinates.x()][coordinates.y()].group().listOfCoordinates();
		to_remove.forEach((Coordinates coor) -> {this.removeStone(coor);});
	}

	private Stone point(Coordinates coordinates){
		return this.points[coordinates.x()][coordinates.y()];
	}

	private void putStone(Coordinates coordinates, Stone stone){
		this.points[coordinates.x()][coordinates.y()] = stone;
	}

	private void removeStone(Coordinates coordinates){
		last_removed = coordinates;
		int num_opponent_groups=0;
		Group[] opponent_groups = new Group[4];
		boolean black = point(coordinates).state().black();
		putStone(coordinates, empty_stone);
		for(Coordinates coor : coordinates.neighbours()){
			if(this.point(coor).state().empty()==false && this.point(coor).state().black()!=black){
				boolean test=true;
				for(int i=0; i<num_opponent_groups; i++)
					if(this.point(coor).group()==opponent_groups[i])
						test=false;
				if(test)opponent_groups[num_opponent_groups++] = this.point(coor).group();
			}
		}
		for(int i=0; i<num_opponent_groups; i++)opponent_groups[i].addLiberty();
	}
	
	private void mergeGroups(Group old_group, Group group) throws IllegalPlacementException{
		for(Coordinates coordinates : old_group.listOfCoordinates()){
			int liberties=0;
			for(Coordinates neighbours : coordinates.neighbours()){
				boolean test=true;
				for(Coordinates coor : neighbours.neighbours())
					if(point(coor).group()==group){
						test=false;
						break;
					}
				if(test==true)liberties++;
			}
			group.addStone(coordinates, liberties);
			this.putStone(coordinates, new Stone(point(coordinates).state(), group));
		}
	}

	public void placeStone(Coordinates placement, State state) throws IllegalPlacementException{
		if(point(placement).state().empty()==false)throw new IllegalPlacementException("Illegal stone placement: Point is not empty\n");
		boolean possible_ko = false;
		boolean removed_group = false;
		int num_opponent_groups=0;
		Group[] opponent_groups = new Group[4];
		int num_own_groups=0;
		int[] liberties = new int[4];
		Group[] own_groups = new Group[4];
		int empty_neighbours=0;

		if(last_removed!=null && placement.equals(last_removed)){
			boolean ko = true;
			for(Coordinates neighbours : placement.neighbours())if(this.point(neighbours).state().empty()==true)ko=false;
			if(ko==true)throw new IllegalPlacementException("Illegal stone placement: Ko rule\n");
		}

		for(Coordinates coor : placement.neighbours()){
			if(this.point(coor).state().empty()==false && this.point(coor).state().white()!=state.white()){
				if(this.point(coor).group().liberties()==1){
					removeGroup(coor);
					removed_group = true;
					continue;
				}
				boolean test=true;
				for(int i=0; i<num_opponent_groups; i++)
					if(this.point(coor).group()==opponent_groups[i])
						test=false;
				if(test)opponent_groups[num_opponent_groups++] = this.point(coor).group();	
			}
		}
		
		for(Coordinates coor : placement.neighbours()){
			if(this.point(coor).state().empty()==false && this.point(coor).state().white()==state.white()){
				boolean test=true;
				for(int i=0; i<num_own_groups; i++)
					if(this.point(coor).group()==own_groups[i])
						test=false;
				if(test)own_groups[num_own_groups++] = this.point(coor).group();
			}
		}
		
		for(Coordinates coor : placement.neighbours()){
			if(this.point(coor).state().empty()==true){
				empty_neighbours++;
				for(int i=0; i<num_own_groups; i++){
					liberties[i]++;
					for(Coordinates neighbour : coor.neighbours()){
						if(this.point(neighbour).group()==own_groups[i]){
							liberties[i]--;
							break;
						}
					}
				}
			}
		}
		boolean test=false;
		for(int i=0; i<num_own_groups-1 && test==false; i++){
			try{
				own_groups[i].addStone(placement, liberties[i]);
				putStone(placement, new Stone(state, own_groups[i]));
				test=true;
			}catch(IllegalPlacementException exception){;}
		}
		if(num_own_groups>0 && test==false){
			own_groups[num_own_groups-1].addStone(placement, liberties[num_own_groups-1]);
			putStone(placement, new Stone(state, own_groups[num_own_groups-1]));
		}
		else if(test==false){
			putStone(placement, new Stone(state, new Group(placement, empty_neighbours)));
			possible_ko = true;
		}

		for(int i=0; i<num_opponent_groups; i++)opponent_groups[i].takeLiberty();
		
		for(int i=0; i<num_own_groups; i++)
			if(own_groups[i]!=point(placement).group())
				mergeGroups(own_groups[i], point(placement).group());
		
		if(possible_ko==false || removed_group==false)last_removed = null;
	}

	public void print(){
		System.out.println("   A B C D E F G H I J K L M N O P Q R S");
		for(int i=19; i>=1; i--){
			if(i<10)System.out.printf(" %d", i);
			else System.out.printf("%d", i);
			for(int j=1; j<=19; j++){
				Coordinates coordinates = new Coordinates(j-1,i-1);
				if(this.point(coordinates).state().empty())System.out.print(" .");
				if(this.point(coordinates).state().black())System.out.print(" x");
				if(this.point(coordinates).state().white())System.out.print(" o");
			}
			if(i<10)System.out.printf("  %d", i);
			else System.out.printf(" %d", i);
			System.out.print("\n");
		}
		System.out.println("   A B C D E F G H I J K L M N O P Q R S");
	}

	public Goban(){
		for(int i=0; i<19; i++)
			for(int j=0; j<19; j++)
				this.points[i][j]=empty_stone;
	}
}

