package application;

public class Miner extends Piece
{

	public Miner(Team team)
	{
		this.setName("miner");
		this.setRank(3);
		this.setTeam(team);
		this.setMaxRange(1);
	}
	
	@Override
	protected boolean ifBom(Node node)
	{
		if (node.getPiece() instanceof Bom)//check if node.getPiece == bom
		{
			this.victory(node);
			return true;
		}
		return false;
	}

}
