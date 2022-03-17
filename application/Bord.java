package application;
import java.util.ArrayList;

public final class Bord
{
	private static  Node[] playingBord;
	private static  Node selectedNode;
	private static final Team teamNumber1 = new Team();
	private static final Team teamNumber2 = new Team();
	private static Team currentTurnForTeam;
	private static Team capturedFlagOfTeam;
	private static int bordWidth;
	
	private static ArrayList<Node> piecesInPool = new ArrayList<Node>();
	
	 
	private Bord()//private so we cant instantiate this Class
	{
		
	}
	
	public static void makeNewGame()
	{
		if(GameSettings.getGameIsMade()||GameSettings.getGameHasStarted())
		{
			return;
		}
			generatePlayingBord();
			placeAllThePiecesOnTheBord();
			GameSettings.setGameIsMade();
			setCurrentTurnForTeam();
			return;
	}
	
	private static void generatePlayingBord()
	{
		bordWidth = 10; //generate 10 by 10 bord (classic)
		
		if (GameSettings.getPlayASmallGame())//generate 8 by 8 bord
		{
			bordWidth = 8;
		}
		
		int heigthOfEachTeamsTerritory = (bordWidth/2)-1;
		//System.out.println(bordWidth * heigthOfEachTeamsTerritory);
		
		playingBord = new Node[bordWidth*bordWidth];
		for (int i = 0; i<playingBord.length; i++)
		{
			if (i<bordWidth * heigthOfEachTeamsTerritory)
			{
				playingBord[i] = new Node(teamNumber1);
				//System.out.println(playingBord[i]);
				continue;
			}
			if (i>=(bordWidth*bordWidth)-(bordWidth * heigthOfEachTeamsTerritory))
			{
				playingBord[i] = new Node(teamNumber2);
				//System.out.println(playingBord[i]);
				continue;
			}
			playingBord[i] = new Node(null);
			
			//filter out: the first 2, the middle , and the last 2, this works for a bordWidth of 8 and 10 and setWater on those Node's
			if(i%bordWidth>=2 && i%bordWidth<bordWidth-2 && i%bordWidth!= bordWidth/2 && i%bordWidth!= (bordWidth/2)-1)
			{
				playingBord[i].setWater();
				//System.out.println(playingBord[i]);
			}
			
		}
		linkNodesOfThisBord();
	}
	
	private static void linkNodesOfThisBord()//TODO remake whit try catch
	{
		int quarter = getPlayingBord(1).getSidesLenght()/4;
		
		for (int i = 0; i<playingBord.length; i++)
		{
			if (i%bordWidth<bordWidth-1)//link node with the node to the east for this we need to filter out the last y/vertical row
			{ //here we filter out the last y/vertical row of the playingBord so we can connect the node with the node to the east
				playingBord[i].setSidesForBoth(quarter, playingBord[i+1]);
				//System.out.println(i + " connects to: " + (i+1));//for testing
			}
			if (i>bordWidth-1)// link node with the node to the north for this we need to filter out the first x/horizontal row
			{//here we filter out the first x/horizontal of the playingBord so we can connect the node with the node to the north
				playingBord[i].setSidesForBoth(0, playingBord[i-bordWidth]);
				//System.out.println(i + " connects to: " + (i-bordWidth));//for testing
			}
			if(GameSettings.getAllowDiagonalMovement()==false)//only if we can move diagonally (in 8 directions)
			{
				continue;//guard-close if we can't move diagonally go to the next iteration
			}
			//only if we can move diagonally (in 8 directions)
			if (i<playingBord.length-bordWidth && i%bordWidth<bordWidth-1)
			{ //here we filter out the last x/horizontal row and the last y/vertical row of the playingBord so we can connect the rest of the nodes diagonally to the south east node of this Node
				playingBord[i].setSidesForBoth(3, playingBord[i+bordWidth+1]);
				//System.out.println(i + " connect to: " + (i+bordWidth+1));//for testing
			}
			//only if we can move diagonally (in 8 directions)
			if (i>=bordWidth && i%bordWidth<bordWidth-1)
			{ //here we filter out the first x/horizontal row and the last y/vertical row of the playingBord so we can connect the rest of the nodes diagonally to the north east node of this Node
				playingBord[i].setSidesForBoth(1, playingBord[i-bordWidth+1]);
				//System.out.println(i + " connects to: " + (i-bordWidth+1));//for testing
			}
		}
		//System.out.println(playingBord[0]);//for testing
	}

	public static void select(int indexOfNOdeOnPlayingBord)
	{
		try 
		{	
			Node newSelectedNode = Bord.getPlayingBord(indexOfNOdeOnPlayingBord);//we do this declaration because it is shorter
			//if we didn't got a Node/square selected then check if there is actually a piece here of the team that is on set
			//so we can select this Node/square. 
			//!the order of the statements matter with using &&, because if the first statement is false then we go to the next if
			if(Bord.getSelectedNode()==null && newSelectedNode.getPiece().getTeam().equals(getCurrentTurnForTeam())) 
			{
				Bord.setSelectedNode(newSelectedNode);
				Bord.getSelectedNode().getPiece().calculateCanMoveTo();
				//System.out.println("you hef now selected: (select) \n" + getSelectedNode().getIdOfThisNode());
				return;
			}
			//if the node equals the newSelectedNode than we deselect the selected node like a checkbox
			if(Bord.getSelectedNode().equals(newSelectedNode))
			{
				Bord.getSelectedNode().getPiece().getCanMoveTo().clear();//we clear this because we dont need this
				Bord.setSelectedNode(null);
				//System.out.println("nothing selected (select)\n");
				return;
			}
			//System.out.println("we are now moving from: " + Bord.getSelectedNode().getIdOfThisNode() + "\ntowards: " + newSelectedNode.getIdOfThisNode());
			//now we can move the piece on the selected node to the newSelectedNode with calling the move method in the Piece Class
			Bord.getSelectedNode().getPiece().moveTo(newSelectedNode);
			Bord.getSelectedNode().getPiece().getCanMoveTo().clear();
			Bord.setSelectedNode(null);
		}
		catch (NullPointerException nPE) {
			 //System.out.println(nPE + "(select)");
			 Bord.setSelectedNode(null);
		}
		catch (ArrayIndexOutOfBoundsException notOnBord){
			System.out.println(notOnBord + " notOnBord!");
			Bord.setSelectedNode(null);
		}
	}
	
	
	//_____________________________________Setters_____________________________________//
	public static void setCurrentTurnForTeam()//setter//COMPLETED
	{
		if (currentTurnForTeam==null)
		{
			Bord.currentTurnForTeam = teamNumber1;
			return;
		}
		if (currentTurnForTeam==teamNumber1)
		{
			Bord.currentTurnForTeam = teamNumber2;
			return;
		}
		Bord.currentTurnForTeam = teamNumber1;
	}
	
	public static void setCapturedFlagOfTeam(Team capturedFlagOfTeam)//setter//COMPLETED
	{ Bord.capturedFlagOfTeam = capturedFlagOfTeam; }
	
	public static void setSelectedNode(Node node)//setter//COMPLETED
	{ Bord.selectedNode = node; }
	//_____________________________________etters_____________________________________//
	
	
	//_____________________________________Getters_____________________________________//
	public static int getBordWidth()
	{ return bordWidth; }
	
	public static Node getPlayingBord(int index)// simple_getter//COMPLETED
	{ return Bord.playingBord[index]; }

	public static Node getSelectedNode()//getter//COMPLETED
	{ return selectedNode; }
	
	public static Team getTeam(int getTeamNumber)//getter//COMPLETED
	{
		switch (getTeamNumber)
		{
			case 1: return Bord.teamNumber1;
			case 2: return Bord.teamNumber2;
		}
		return Bord.teamNumber1;
	}
	
	public static Team getCurrentTurnForTeam()//getter//COMPLETED
	{ return currentTurnForTeam; }
	
	public static Team getCapturedFlagOfTeam()
	{ return capturedFlagOfTeam; }
	//_____________________________________Getters_____________________________________//
	
	
	
	private static void placeAllThePiecesOnTheBord()
	{
		if (GameSettings.getGameHasStarted())
		{
			System.out.println("the game has already started (placeAllThePiecesOnTheBord)");
			return;
		}
		for (int i = 0; i < teamNumber1.getPiecesOfThisTeam().length; i++)//place the pieces on the first part of the board
		{
			getPlayingBord(i).setPiece(teamNumber1.getPiecesOfThisTeam()[i]);
			teamNumber1.getPiecesOfThisTeam()[i].setStandingOn(getPlayingBord(i));
			//System.out.println(teamNumber1.getPiecesOfThisTeam()[i]);
		}
		int endOfBord = playingBord.length;
		for (int i = 0; i < teamNumber2.getPiecesOfThisTeam().length; i++)//place the pieces on the last part of the board
		{
			endOfBord--;
			getPlayingBord(endOfBord).setPiece(teamNumber2.getPiecesOfThisTeam()[i]);
			teamNumber2.getPiecesOfThisTeam()[i].setStandingOn(getPlayingBord(endOfBord));
			//System.out.println(teamNumber2.getPiecesOfThisTeam()[i]);
		}
	}
	
	

	
	
	
	

	public static int calculateCurrentHighestRankThatIsNotOfThisTeam(Team team)
	{
		int highestRank = 0;
		
		for(int i = 0; i < playingBord.length; i++)
		{
			if(getPlayingBord(i).getPiece()==null||getPlayingBord(i).getPiece().getTeam().equals(team))
			{
				continue;
			}
			if(getPlayingBord(i).getPiece().getRank()>highestRank)
			{
				highestRank = getPlayingBord(i).getPiece().getRank();
			}
		}
		return highestRank;
	}
	

	
	
}
