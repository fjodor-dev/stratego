package application;

public class Spy extends Piece
{
	
	public Spy(Team team)
	{
		this.setName("spy");
		this.setRank(1);
		this.setTeam(team);
		this.setMaxRange(1);
	}
	
	//overide this because if it is the marshall or the highest ranking piece the spy wins 
	@Override
	protected void checkRank(Node node)//in attack method
	{
		if (GameSettings.getSpyDefeatsCurrentHighestRankingPiece())
		{
			if(node.getPiece().getRank()==Bord.calculateCurrentHighestRankThatIsNotOfThisTeam(this.getTeam()))
			{
				this.victory(node);
				return;
			}
		}
		if(node.getPiece().getRank()==10)//then it is the marshal and the spy can defeat that Piece
		{
			this.victory(node);
			return;
		}
		if (this.getRank()>node.getPiece().getRank())//attacker has higher rank then defender
		{
			this.victory(node);
			return;
		}
		if (this.getRank()<node.getPiece().getRank())//defender has higher rank then attacker
		{
			this.defeat();
			return;
		}
		if (this.getRank()==node.getPiece().getRank())//attacker has equal rank as defender//
		{
			this.defeat();
			node.getPiece().defeat();
			return;
		}
	}

}
