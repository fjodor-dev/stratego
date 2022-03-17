package application;

public class BasicRank extends Piece
{

	public BasicRank(Team team, int rank)
	{
		this.setRank(rank);
		this.setTeam(team);
		this.setMaxRange(1);
		
		switch (rank)
		{
			case 4: this.setName("sergeant"); break;
			case 5: this.setName("lieutenant"); break;
			case 6: 
				this.setName("captain");
				if(GameSettings.getCaptainCanMoveTwoSquares())
				{
					this.setMaxRange(2);
				}
				break;
			case 7: this.setName("major"); break;
			case 8: this.setName("colonel"); break;
			case 9: this.setName("general"); break;
			case 10: this.setName("marshall"); break;
			default: this.setName("rankt"); break;
		}
	}

}
