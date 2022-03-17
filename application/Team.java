package application;


public class Team
{
	private static int numberOfTeamsCreated = 0;
	private final String name;
	private final Piece[] piecesOfThisTeam;
	//private ArrayList<Piece> piecesOfThisTeamInPool = new ArrayList<Piece>();
	
	public Team()
	{
		numberOfTeamsCreated++;//needed so we can give the team a name automatically
		
		switch (numberOfTeamsCreated)//make a name for the team
		{
			case 1: this.name = "blue"; break;
			case 2: this.name = "red"; break;
			default: this.name = "defaultName";
			System.out.println(numberOfTeamsCreated + "you cant create that much teams");//leave this here in case it goes wrong
		}
		
		if(GameSettings.getPlayASmallGame()==true)//make a team existing of 10 pieces
		{
			this.piecesOfThisTeam = return10Pieces();
			
			return;//we are done here
		}// else we do this down here
		this.piecesOfThisTeam = return40Pieces();//make a team existing of 40 pieces
	}

	private Piece[] return10Pieces()//in constructor, generate 10 pieces
	{
		Piece[] piecesForThisTeam = new Piece[10];
		
		piecesForThisTeam[0] = new Flag(this);
		piecesForThisTeam[1] = new Spy(this);
		piecesForThisTeam[2] = new Bom(this);
		piecesForThisTeam[3] = new Bom(this);
		piecesForThisTeam[4] = new Scout(this);
		piecesForThisTeam[5] = new Scout(this);
		piecesForThisTeam[6] = new Miner(this);
		piecesForThisTeam[7] = new Miner(this);
		piecesForThisTeam[8] = new BasicRank(this,9);
		piecesForThisTeam[9] = new BasicRank(this,10);

		return piecesForThisTeam;
	}

	private Piece[] return40Pieces()//in constructor, generate 40 pieces
	{
		Piece[] piecesForThisTeam = new Piece[40];
		
		piecesForThisTeam[0] = new Flag(this);
		piecesForThisTeam[1] = new Spy(this);
		piecesForThisTeam[2] = new Miner(this);//extra miner the rest we generate
		piecesForThisTeam[3] = new BasicRank(this,8);
		piecesForThisTeam[4] = new BasicRank(this,8);
		piecesForThisTeam[5] = new BasicRank(this,9);
		piecesForThisTeam[6] = new BasicRank(this,10);

		int NumOfPiecesMade = 7;//needed to generate
		
		for (int i = 0; i < 3; i++)
		{	
			piecesForThisTeam[NumOfPiecesMade] = new BasicRank(this,7); NumOfPiecesMade++;//3 needed
			piecesForThisTeam[NumOfPiecesMade] = new Bom(this); NumOfPiecesMade++;//total of 6 Boms needed (2*3=6)
			piecesForThisTeam[NumOfPiecesMade] = new Bom(this); NumOfPiecesMade++;//total of 6 Boms needed (2*3=6)
		}
		for (int i = 0; i < 4; i++)//make 4 of each Piece
		{
			piecesForThisTeam[NumOfPiecesMade] = new BasicRank(this,6); NumOfPiecesMade++;
			piecesForThisTeam[NumOfPiecesMade] = new BasicRank(this,5); NumOfPiecesMade++;
			piecesForThisTeam[NumOfPiecesMade] = new BasicRank(this,4); NumOfPiecesMade++;
			piecesForThisTeam[NumOfPiecesMade] = new Miner(this); NumOfPiecesMade++; ////we need 4+1
			piecesForThisTeam[NumOfPiecesMade] = new Scout(this); NumOfPiecesMade++; //we need 8 scouts
			piecesForThisTeam[NumOfPiecesMade] = new Scout(this); NumOfPiecesMade++; //we need 8 scouts
		}
		return piecesForThisTeam;
	}

	
	//simple_getterS//COMPLETED//simple_getterS//COMPLETED//simple_getterS//COMPLETED
	public String getName()//simple_getter//COMPLETED
	{
		return name;
	}
	public Piece[] getPiecesOfThisTeam()//simple_getter//COMPLETED
	{
		return piecesOfThisTeam.clone();//you get a clone so you can't change anything, because everything breaks if you touch it YOU MORON!!!
	}
	//simple_getterS//COMPLETED//simple_getterS//COMPLETED//simple_getterS//COMPLETED
	
	public String toString()
	{
		return "Team " + name + " \n"
		+ "total of " + piecesOfThisTeam.length + " Pieces ";
	}
	public String toStringExtended()
	{
		return "Team " + name + " \n"
		+ "this team has a total of " + piecesOfThisTeam.length + "Pieces "
		+ "\n";

	}
	
}
