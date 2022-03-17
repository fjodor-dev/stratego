package application;

public final class GameSettings
{
	private static boolean gameIsMade = false;
	private static boolean gameHasStarted = false;
	
	private static boolean allowDiagonalMovement = false;
	private static boolean playASmallGame = false;
	
	private static boolean bomsDestroyOnImpact = false;
	private static boolean moveableBombs = false;
	private static boolean moveableFlag = false;
	private static boolean captainCanMoveTwoSquares = false;
	private static boolean spyDefeatsCurrentHighestRankingPiece = false;

	
	
//	private static boolean identityStaysRevealed = false;
//	private static boolean useOfCannons = false;
//	private static boolean reducedArmy = false;
//	private static boolean flagHasToBeTakenHomeToWin = false;
//	private static boolean spyDefeatsAll = false;
//	private static boolean useCapturedPiecesAsYourOwn = false;
//	private static boolean continueMovingOnVictory = false;
//	private static boolean playWith3Players = false;
//	private static boolean playWith4Players = false;
//	private static boolean useHexagons = false;
//	private static boolean randomWaterPlacement = false;
//	private static boolean AttackerPlus1InRank = false;
//	private static boolean onEqaulRankAttackerWins = false;
//	private static boolean unknownVictor = false;
//	private static boolean randomSetup = false;
//	private static boolean strikingIsMandatory = false;

	private GameSettings()//private so we cant instantiate this Class
	{}
	
	
	
	//_____________________________________Setters_____________________________________//
	public static void setGameIsMade()
	{
		GameSettings.gameIsMade = true;
	}
	public static void setGameHasStarted()
	{
		if (getGameIsMade()) gameHasStarted = true;
	}
 	public static void setAllowDiagonalMovement()
	{
 		allowDiagonalMovement = saveSetter(allowDiagonalMovement);
	}
	public static void setPlayASmallGame()
	{
		playASmallGame = saveSetter(playASmallGame);
	}
	public static void setBomsDestroyOnImpact()
	{
		GameSettings.bomsDestroyOnImpact = saveSetter(bomsDestroyOnImpact);
	}
	public static void setSpyDefeatsCurrentHighestRankingPiece()
	{
		GameSettings.spyDefeatsCurrentHighestRankingPiece = saveSetter(spyDefeatsCurrentHighestRankingPiece);
	}
	public static void setMoveableBombs()
	{
		GameSettings.moveableBombs = saveSetter(moveableBombs);
	}
	public static void setCaptainCanMoveTwoSquares()
	{
		GameSettings.captainCanMoveTwoSquares = saveSetter(captainCanMoveTwoSquares);
	}
	public static void setMoveableFlag()
	{
		GameSettings.moveableFlag = saveSetter(moveableFlag);
	}
	//_____________________________________Setters_____________________________________//
	
	
	
	//_____________________________________Getters_____________________________________//
	public static boolean getAllowDiagonalMovement()
	{
		return allowDiagonalMovement;
	}
	public static boolean getPlayASmallGame()
	{
		return playASmallGame;
	}
	public static boolean getGameHasStarted()
	{
		return gameHasStarted;
	}
	public static boolean getGameIsMade()
	{
		return gameIsMade;
	}
	public static boolean getSpyDefeatsCurrentHighestRankingPiece()
	{
		return spyDefeatsCurrentHighestRankingPiece;
	}
	public static boolean getBomsDestroyOnImpact()
	{
		return bomsDestroyOnImpact;
	}
	public static boolean getMoveableBombs()
	{
		return moveableBombs;
	}
	public static boolean getCaptainCanMoveTwoSquares()
	{
		return captainCanMoveTwoSquares;
	}
	public static boolean getMoveableFlag()
	{
		return moveableFlag;
	}
	//_____________________________________Getters_____________________________________//
	
	//we use this here in evry setter 
	//it bassicly works as a simple on and of switch that we can use in setters
	private static boolean saveSetter(boolean setting)
	{
		if (setting == false && getGameIsMade() == false && getGameHasStarted()==false)//if it is not set yet we can set it to true once
		{
			return true;
		}
		return false;
	}

	

}
