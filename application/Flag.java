package application;

public class Flag extends Piece
{
	public Flag(Team team)
	{
		this.setName("flag");
		this.setTeam(team);
		this.setRank(0);
		
		if(GameSettings.getMoveableFlag())
		{
			this.setMaxRange(1);
			return;
		}
		this.setMaxRange(0);
	}
	
	@Override
	protected void defeat()
	{
		Bord.setCapturedFlagOfTeam(this.getTeam());
	}
	
	@Override//if we are allowt to move with this piece we but we still connot attack another piece so we hef to override
	protected void calculateCanMoveTo()//in select method in Bord Class 
	{	
		if (GameSettings.getGameHasStarted()==false)
		{
			//if the game hasn't started we don't hef to figure out where we can move towards
			return;
		}
		
		for (int direction = 0; direction < this.getStandingOn().getSidesLenght(); direction++)
		{
			Node nodeWeAreMovingTowards = this.getStandingOn().getSides(direction);
			//we use a try catch so we don't constantly hef to check if the object is null
			try
			{
				for(int i = 0; i < this.getMaxRange(); i++)
				{
					if (nodeWeAreMovingTowards.getIsWater())
					{
							break;
					}
					if(nodeWeAreMovingTowards.getPiece()==null)
					{
						//there no piece here and we can move here safely
						this.getCanMoveTo().add(nodeWeAreMovingTowards);
						nodeWeAreMovingTowards = nodeWeAreMovingTowards.getSides(direction);
						continue;
					}
				}			
			}
			catch (NullPointerException e)
			{}
		}	
	}
	
}
