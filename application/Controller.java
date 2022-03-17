package application;

import java.util.ArrayList;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Controller
{
	
	private static Team newTurn;
	
	@FXML
	Pane attackArrow;
	@FXML
	Pane result1;
	@FXML
	Pane result2;
	@FXML
	AnchorPane alert; 
	@FXML
	Text alertText;
	@FXML
	AnchorPane listHeader;
	@FXML
	AnchorPane coverForPieces;
	@FXML
	AnchorPane titleScreen;
	@FXML
	FlowPane bordSpace;
	@FXML
	Button makeTheGameButton;
	@FXML
	VBox list;
	
	static Button startTheGameButton = new Button("next Player");
	static GridPane grid = new GridPane();
	
	//colors
	static String canMoveToColorCSS = "-fx-background-color: rgba(255, 100, 0, 0.5); ";
	static String selectedColorCSS = "-fx-background-color: rgba(255, 220, 0, 0.4); ";
	static String redColorCSS = "-fx-background-color: #c90000;";
	static String blueColorCSS = "-fx-background-color: #0d7eff;";
	static String nullColorCSS = "-fx-background-color: transparent;";
	
	//id's we can give to objects so we can style them with css
	static String idForWater = "idForWater";
	static String idForFlag = "idForFlag";
	static String idForBom = "idForBom";
	static String idForSpy = "idForSpy";
	static String idForScout = "idForScout";
	static String idForMiner = "idForMiner";
	static String idForRank4 = "idForRank4";
	static String idForRank5 = "idForRank5";
	static String idForRank6 = "idForRank6";
	static String idForRank7 = "idForRank7";
	static String idForRank8 = "idForRank8";
	static String idForRank9 = "idForRank9";
	static String idForRank10 = "idForRank10";

	private static final ArrayList<StackPane> stackPaneForGrid = new ArrayList<StackPane>();
	private static final ArrayList<Pane> imagePaneForGrid = new ArrayList<Pane>();
	private static final ArrayList<Label> labelForGrid = new ArrayList<Label>();
	
	public void goAwayFromTheTitleScreen()
	{		
		if (bordSpace.isVisible()==false) 
		{
			listHeader.setMaxHeight(60);
			listHeader.setMaxWidth(200);
			listHeader.setLayoutY(-2);
			bordSpace.setId("bordSpaceNotLoaded");
			coverForPieces.setId("coverForPieces");
			list.setVisible(true);
			bordSpace.setVisible(true);
		}
	}
	
	public void makeTheGame()
	{
		
		if(GameSettings.getGameIsMade()==true)
		{
			return;
		}
		
		Bord.makeNewGame();
		bordSpace.setId("bordSpaceLoaded");
		
		//list.getChildren().TODO make it that we can still see the checkboxes but can acces them 
		
		startTheGameButton.setOnAction(e -> 
		{
			Bord.setCurrentTurnForTeam();
			updateGui();
			startTheGameButton.setText("start The Game");
			startTheGameButton.setOnAction(e2 -> 
			{
				Bord.setCurrentTurnForTeam();
				updateGui();
				GameSettings.setGameHasStarted();
				startTheGameButton.setVisible(false);
				startTheGameButton.setOnAction(null);
			});
		});
	
		list.getChildren().add(startTheGameButton);
		startTheGameButton.setMinWidth(list.getWidth());
		startTheGameButton.setMinHeight(31);
		
		int bordWidt = Bord.getBordWidth();
		int ZiseForEachGrid = 600/bordWidt;
		
		bordSpace.setStyle("-fx-background-size: " + ZiseForEachGrid*10 + "," + ZiseForEachGrid*10 + ";");
		
		int i = 0;
		for(int y = 0; y < bordWidt; y++)
		{
			for(int x = 0; x < bordWidt; x++)
			{
				
				labelForGrid.add(new Label());
				labelForGrid.get(i).setTextFill(Color.web("#000000"));
				
				imagePaneForGrid.add(new Pane());
				
				imagePaneForGrid.get(i).setMaxSize(ZiseForEachGrid,ZiseForEachGrid);
				imagePaneForGrid.get(i).getChildren().add(labelForGrid.get(i));
				
				stackPaneForGrid.add(new StackPane());
				stackPaneForGrid.get(i).setMinSize(ZiseForEachGrid, ZiseForEachGrid);
				stackPaneForGrid.get(i).getChildren().add(imagePaneForGrid.get(i));
				stackPaneForGrid.get(i).setId("" + i);
				stackPaneForGrid.get(i).setOnMousePressed(e -> 
				{
					resultOfBattle(Integer.parseInt(((StackPane)e.getSource()).getId()));//TODO make method to gather data to show the result of the battle
					
					Bord.select(Integer.parseInt(((StackPane)e.getSource()).getId()));
					
					updateGui();
				});
				grid.add(stackPaneForGrid.get(i), x, y);
				
				i++;
			}
		}
		newTurn = Bord.getCurrentTurnForTeam();
		updateGui();
		grid.setGridLinesVisible(true);
		bordSpace.getChildren().add(grid);
	}
	
	public void allowBomsDestroyOnImpact()
	{
		GameSettings.setBomsDestroyOnImpact();
	}
	public void allowCaptainCanMoveTwoSquares()
	{
		GameSettings.setCaptainCanMoveTwoSquares();
	}
	public void allowMoveableBombs()
	{
		GameSettings.setMoveableBombs();
	}
	public void allowMoveableFlag()
	{
		GameSettings.setMoveableFlag();
	}
	public void allowSpyDefeatsCurrentHighestRankingPiece()
	{
		GameSettings.setSpyDefeatsCurrentHighestRankingPiece();
	}

	public void allowDiagonalMovement()
	{
		GameSettings.setAllowDiagonalMovement();
	}
	
	public void playASmallGame()
	{
		GameSettings.setPlayASmallGame();
	}
	
	public void showCoverOnNewTurn()
	{
		if(newTurn.equals(Bord.getCurrentTurnForTeam())==false)
		{
			coverForPieces.setVisible(true);
			newTurn = Bord.getCurrentTurnForTeam();
		}
	}
	
	public void removeCover()
	{
		coverForPieces.setVisible(false);
	}
	
	private void updateGui()
	{
		
		if(checkOfFlagIsCaptured())
		{
			return;
		}
		
		showCoverOnNewTurn(); 
		
		for(int i = 0; i < stackPaneForGrid.size(); i++)
		{
			if (Bord.getPlayingBord(i).getIsWater())
			{
				imagePaneForGrid.get(i).setId(idForWater);
				continue;
			}
			
			highLightNode(i);
			
			setCSSForPiece(Bord.getPlayingBord(i).getPiece(), imagePaneForGrid.get(i), labelForGrid.get(i), true);
		}
	}
	
	private boolean checkOfFlagIsCaptured()
	{
		if(Bord.getCapturedFlagOfTeam()==null)
		{
			return false;
		}
		
		alert.setVisible(true);
		alertText.setText("The Flag of Team " + Bord.getCapturedFlagOfTeam().getName() + " is Captured!");
		
		return true;
	}
	
	private static void highLightNode(int i)
	{
		if (Bord.getSelectedNode()==null)
		{
			stackPaneForGrid.get(i).setStyle(nullColorCSS);
			return;
		}
		
		Bord.getSelectedNode().getPiece().getCanMoveTo().forEach((node) -> 
		{
			if(i==node.getIdOfThisNode())
			{
				//System.out.println( "can move to: " + i);
				stackPaneForGrid.get(i).setStyle(canMoveToColorCSS);
			}
		});
		
		if (i==Bord.getSelectedNode().getIdOfThisNode())
		{
			stackPaneForGrid.get(i).setStyle(selectedColorCSS);
			return;
		}
	}
	
	private void resultOfBattle(int i)
	{
		try
		{
			if(Bord.getSelectedNode().getPiece().getTeam().equals(Bord.getPlayingBord(i).getPiece().getTeam())==false)
			{
				attackArrow.setVisible(true);
				setCSSForPiece(Bord.getSelectedNode().getPiece(), result1, null, false);	
				setCSSForPiece(Bord.getPlayingBord(i).getPiece(), result2, null, false);
			}
		}
		catch (Exception e)
		{
			result1.setStyle(nullColorCSS);
			result1.setId("");
			result2.setStyle(nullColorCSS);
			result2.setId("");
			attackArrow.setVisible(false);
		}
	}
	
	
	private static void setCSSForPiece(Piece piece, Pane pane, Label label, boolean showOnlyPiecesOfCurrentTurnTeam )
	{
		
		if(piece==null) 
		{
			if(label!=null)
			{
				label.setText(null);
			}
			pane.setId(null);
			pane.setStyle(nullColorCSS);
			return;
		}
		
		if(piece.getTeam().equals(Bord.getTeam(1)))
		{
			pane.setStyle(redColorCSS);
		}
		if(piece.getTeam().equals(Bord.getTeam(2)))
		{
			pane.setStyle(blueColorCSS);
		}
		
		if (showOnlyPiecesOfCurrentTurnTeam) 
		{
			if(piece.getTeam().equals(Bord.getCurrentTurnForTeam())==false)
			{
				pane.setId("idSecret");
				label.setText("");
				return;
			}
		}
		
		
		if(piece instanceof Flag)
		{
			pane.setId(idForFlag);
			if(label!=null)
			{
				label.setText("");
			}
			return;
		}
		
		String resultOfSwitch = null;
		String textForLabel = null;
		
		switch(piece.getRank())
		{
			case 0:  resultOfSwitch = idForBom; 	textForLabel = "";  break;
			case 1:  resultOfSwitch = idForSpy; 	textForLabel = "1"; break;
			case 2:  resultOfSwitch = idForScout; 	textForLabel = "2"; break;
			case 3:  resultOfSwitch = idForMiner; 	textForLabel = "3"; break;
			case 4:  resultOfSwitch = idForRank4; 	textForLabel = "4"; break;
			case 5:  resultOfSwitch = idForRank5; 	textForLabel = "5"; break;
			case 6:  resultOfSwitch = idForRank6; 	textForLabel = "6"; break;
			case 7:  resultOfSwitch = idForRank7; 	textForLabel = "7"; break;
			case 8:  resultOfSwitch = idForRank8; 	textForLabel = "8"; break;
			case 9:  resultOfSwitch = idForRank9; 	textForLabel = "9"; break;
			case 10: resultOfSwitch = idForRank10;	textForLabel = "X"; break;
		}
		
		pane.setId(resultOfSwitch);
		
		if(label!=null)
		{
			label.setText(textForLabel);
		}
	}
	
}
