package application;

public class Scout extends Piece
{

	public Scout(Team team)
	{
		this.setName("scout");
		this.setRank(2);
		this.setTeam(team);
		this.setMaxRange(10);
	}

}
