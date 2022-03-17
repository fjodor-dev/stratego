package application;

public class Node
{
	private Piece piece = null;
	private boolean isWater = false;
	private final Node[] sides;
	private final Team territoryFrom;
	private final int idOfThisNode;
	private static int numberOfNodesCreated = 0;
	
	//constructors with different parameters

	public Node(Team territoryOrigonallyBelongsToThisTeam)//constructor
	{
		this.idOfThisNode = numberOfNodesCreated;
		numberOfNodesCreated++;
		this.isWater = false; //no water on this node
		this.territoryFrom = territoryOrigonallyBelongsToThisTeam;//the territory belongs to this team
		if(GameSettings.getAllowDiagonalMovement())
		{
			this.sides = new Node[8];//can move in 8 directions
			return;
		}
		this.sides = new Node[4];//only can move in 4 directions
	}
	
	
	
	// simple_setters//COMPLETED//simple_setters//COMPLETED//simple_setters//COMPLETED//simple_setters//COMPLETED
	public void setPiece(Piece piece)// simple_setter//COMPLETED
	{
		this.piece = piece;
	}
	public void setWater()
	{
		if (getIsWater()==false)
		{
			this.isWater = true;
		}
	}
	public void setSides(int index, Node valueOfTypeNode) 
	{	
		if (index>=0 && index<sides.length)//making sure the index is correct
		{
			if (sides[index] == null)
			{
				sides[index] = valueOfTypeNode;
				return;
			}
			System.out.println("this side of index: " + index + " != null that means that it is allready set for " + this);
			return;
		}
		System.out.println("index of: " + index + " not legit! (setSides method)");
	}
	public void setSidesForBoth(int index, Node nodeYouWantToConnectToThisNode) 
	{	
		if (index>=0 && index<sides.length)//making sure the index is correct
		{
			int newIndex; //get the opposite side for the nodeYouWantToConnectToThisNode
			
			if (index < (sides.length/2))//less then half we do plus half the length to get the opposite side so we don't go over the "top" 
			{
				newIndex = index+(sides.length/2);
			}
			else //more then half we do minus half the length to get the opposite side so we don't go over the "top" 
			{
				newIndex = index-(sides.length/2);
			}
			
			if (sides[index] == null && nodeYouWantToConnectToThisNode.sides[newIndex]==null)//so we can only assign it once
			{
				nodeYouWantToConnectToThisNode.sides[newIndex] = this;
				sides[index] = nodeYouWantToConnectToThisNode;
				return;
			}
			return;
		}
		System.out.println("index of: " + index + " not legit! (setSides method)");
	}

	
	// simple_getters//COMPLETED//simple_getters//COMPLETED//simple_getters//COMPLETED//simple_getters//COMPLETED
	public static int getNumberOfNodesCreated()// simple_getter//COMPLETED
	{
		return numberOfNodesCreated;
	}
	public boolean getIsWater()// simple_getter//COMPLETED
	{
		return this.isWater;
	}
	public int getSidesLenght()// simple_getter//COMPLETED
	{
		
		return sides.length;
	}
	public Node getSides(int index)// simple_getter//COMPLETED
	{
		if (index>=0 && index<sides.length)//making sure the index is correct
		{
			return this.sides[index];
		}
		System.out.println("index of: " + index + " not legit! (getSides method)");
		return null;
	}
	public Piece getPiece()// simple_getter//COMPLETED
	{
		return this.piece;
	}
	public Team getTerritoryFrom()// simple_getter//COMPLETED
	{
		return this.territoryFrom;
	}
	public int getIdOfThisNode()// simple_getter//COMPLETED
	{
		return this.idOfThisNode;
	}
	// simple_getters//COMPLETED//simple_getters//COMPLETED//simple_getters//COMPLETED//simple_getters//COMPLETED
	
	@Override
	public String toString()
	{
		String thisPieceString = "NULL";
		
		if(piece != null)
		{
			thisPieceString = piece.getName() + ", rank: " + piece.getRank() + ", ";
		}
		return "ID of this Node: " + idOfThisNode + "\n"
		+ "Piece: " + thisPieceString + "\n"
		+ "isWater: " + isWater + "\n"
		+ "borders: " + sidesToString()+ "\n"
		+ "territoryFrom: " + territoryFrom + "\n";
	}
	private String sidesToString()
	{
		String sidesToString = "";
		
		for (Node side : sides) 
		{ 
			if (side != null)
			{
				sidesToString += side.idOfThisNode ;
			}
			sidesToString += ", ";
		}
		
		return sidesToString;
	}
	
//	public static void main(String[] args)//for testing
//	{	
//		Node node1 = new Node(false);
//		Node node2 = new Node(false);
//		Node node3 = new Node(false);
//		
//		Scout scout = new Scout(null);
//		
//		
//		node3.setSides(0, node2);
//		
//		node3.getSides()[0].setPiece(scout);
//		System.out.println(node3.getSides()[0].getPiece());
//		
//	}
	
	

	
	
}
