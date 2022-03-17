package application;
import java.util.ArrayList;

public abstract class Piece 
{
	private String name;
	private int rank = 0;
	private int maxRange = 0;
	private Team team;
	private Node standingOn;
	private ArrayList<Node> canMoveTo = new ArrayList<Node>();

	//_____________________________________Setters_____________________________________//
	protected void setName(String name)// simple_setter//COMPLETED
	{ this.name = name; }

	protected void setRank(int rank)// simple_setter//COMPLETED
	{ this.rank = rank; }

	protected void setTeam(Team team)// simple_setter//COMPLETED
	{ this.team = team; }

	protected void setStandingOn(Node standingOn)// simple_setter//COMPLETED
	{ this.standingOn = standingOn; }

	protected void setMaxRange(int maxRange)// simple_setter//COMPLETED
	{ this.maxRange = maxRange; }
	//_____________________________________Setters_____________________________________//
	
	
	
	//_____________________________________Getters_____________________________________//
	public String getName()// simple_getter//COMPLETED
	{ return name; }

	public int getRank()// simple_getter//COMPLETED
	{ return rank; }

	public Team getTeam()// simple_getter//COMPLETED
	{ return team; }

	public Node getStandingOn()// simple_getter//COMPLETED
	{ return standingOn; }

	public ArrayList<Node> getCanMoveTo()// simple_getter//COMPLETED
	{ return canMoveTo; }

	public int getMaxRange()// simple_getter//COMPLETED
	{ return maxRange; }
	//_____________________________________Getters_____________________________________//
	
	//____________________________________Movement____________________________________//
	void moveTo(Node node)//out of Node
	{
		//checks if the game has started so we can attack or switch pieces for the setup face
		if (GameSettings.getGameHasStarted()==false)
		{	
			this.moveToSetup(node);
			return;
		}
		//else if we didnt use any return we are doing a regular move
		if (this.checkCanMoveTo(node))//return if we can't move there
		{
			this.attack(node);
			Bord.setCurrentTurnForTeam();
			Bord.setSelectedNode(null);
		}
	}
	
	private void moveToSetup(Node node)// in moveTo method
	{	
		//if the game isnt started yet we hef to be abble to switch pieces
		
		//check if the node is from the same team that is on set this is only importend for the setup
		if(node.getTerritoryFrom() == null || node.getTerritoryFrom().equals(this.getTeam())==false)
		{
			//System.out.println("this isnt your terretory! (moveToSetup)");
			return;
		}
		if (node.getPiece()!=null)//there is a piece here that means we need to switch pieces
		{
			node.getPiece().setStandingOn(this.getStandingOn());
		}
		this.getStandingOn().setPiece(node.getPiece());
		this.setStandingOn(node);
		node.setPiece(this);
	}
	
	private final boolean checkCanMoveTo(Node node)//in moveTo method
	{
		if (this.getCanMoveTo().contains(node))
		{
			this.getCanMoveTo().clear();//clear canMoveTo for the next time
			return true;
		}
		return false;
	}
	
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
					
					if(nodeWeAreMovingTowards.getPiece().getTeam().equals(this.getTeam())==false)
					{
						this.getCanMoveTo().add(nodeWeAreMovingTowards);
						break;
					}
				}			
			}
			catch (NullPointerException e)
			{}
		}	
	}
	//____________________________________Movement____________________________________//
	
	
	
	//____________________________________Attacking___________________________________//
	private void attack(Node node)// in move to
	{	//break if one of the methods returns true then we dont have to check the other methods in this method

			if (node.getPiece() == null)
			{//there is nothing here and the Piece can move there and wins
				//System.out.println("nothing here! (attack method)");
				this.victory(node);
				return;
			}
			if (this.ifBom(node))
			{// Everything imported happens in this method if needed //Miner class this is overridden
				return;
			}
			if (this.ifFlag(node))
			{// Everything imported happens in this method if needed
				return;
			}
			//if these above are all not true we do this basic thing
			this.checkRank(node);	
	}

	protected boolean ifFlag(Node node)//in attack method
	{
		if (node.getPiece() instanceof Flag)//check if node.getPiece == flag
		{
			this.victory(node);
			return true;
		}
		return false;
	}

	protected boolean ifBom(Node node)//in attack method
	{
		if (node.getPiece() instanceof Bom)//check if node.getPiece == bom
		{
			this.defeat();
			if(GameSettings.getBomsDestroyOnImpact()) 
			{
				node.getPiece().defeat();
			}
			return true;
		}
		return false;
	}

	protected void checkRank(Node node)//in attack method
	{
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
		if (this.getRank()==node.getPiece().getRank())//attacker has equal rank as defender
		{
			this.defeat();
			node.getPiece().defeat();
			return;
		}
	}
	//____________________________________Attacking___________________________________//
	
	
	
	//____________________________________Placement___________________________________//
	protected void victory(Node node)// method for method for attack method// move the piece to node and clear// old node
	{
		if (node.getPiece() != null)//check if there is a piece on the node this is going to
		{
			node.getPiece().defeat();//if there is a piece here then we call the defeat method on that piece
		}
		this.getStandingOn().setPiece(null);//empty the Node we came from
		this.setStandingOn(node);//does basically the same as node.setPiece(this); but we want to store the relating Node/Piece in both objects for good reasons
		node.setPiece(this);
	}

	protected void defeat()// in victory method, //this is cold to the piece on the node (this) that the piece where were we call the attack method on has won from
	{
		//System.out.println("defeated: " + this);
		this.getStandingOn().setPiece(null);
		this.setStandingOn(null);
		// TODO remove this piece from the bord towards some kind of defeated pool
	}
	//____________________________________Placement___________________________________//
	
	
	//_____________________________________ToString___________________________________//
	@Override
	public String toString()
	{
		return "\n Piece: " + "\n " + this.getClass() + "\n name: " + this.getName() + "\n rank: " + this.getRank()
				+ "\n Team: " + this.getTeam() + "\n maxRange: " + this.getMaxRange() + "\n standingOn: "
				+ this.getStandingOn().getIdOfThisNode() + "\n";
	}
	
	
}
