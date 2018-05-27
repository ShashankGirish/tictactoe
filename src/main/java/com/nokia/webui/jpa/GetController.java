package com.nokia.webui.jpa;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetController {

	private static final int CAPITAL_INTEGER_OFFSET = 64;

	@Autowired
	PlayerRepository playerRepository;

	@Autowired
	MoveRepository moveRepository;

	@RequestMapping(method = RequestMethod.GET, value = "/game/{gameId}")
	public String GetGameInformation(@PathVariable("gameId") final String gameId) {
		
		String gameStatus = null;
		new ArrayList<String>();
		List<Move> listOfMoves = new ArrayList<Move>();
		listOfMoves = moveRepository.findBygameId(gameId);
		List<Player> listOfPlayers = new ArrayList<Player>();
		listOfPlayers = playerRepository.findBygameId(gameId);
		

		if (listOfPlayers.isEmpty()) {
			return "<h1>Invalid game id</h1>";
		}
		
		final Player playerInfo = listOfPlayers.get(0);
		final String firstPlayerCharacter = playerInfo.getFirstPlayerCharacter();
		final String firstPlayerName = playerInfo.getFirstPlayerName();
		final String secondPlayerName = playerInfo.getSecondPlayerName();
		int firstPlayerCounter = 0;
		int secondPlayerCounter = 0;
		int totalMoveCount = 0;
		
		final int order = 3;
		final int maxNumberOfMoves = order*order;
		final int minNumberOfMovesToWin = order;
		final String[][] table = new String[order][order];
		
		for (final Move move : listOfMoves) {
			final String rowName = move.getRowName();
			final String columnName = move.getColumnName();
			final int rowNumber = rowName.toCharArray()[0] - CAPITAL_INTEGER_OFFSET ;
			final int columnNumber =columnName.toCharArray()[0] - CAPITAL_INTEGER_OFFSET;
			System.out.println("row: " + rowNumber + ", column: " + columnNumber);
			table[rowNumber-1][columnNumber-1] = move.getCharacter();
			
			if(firstPlayerCharacter.equals(move.getCharacter())){
				firstPlayerCounter++;
			}else{
				secondPlayerCounter++;
			}
			totalMoveCount++;
		}
		
		if (totalMoveCount == 0) {
			gameStatus = "Game has not started.";
		} else if (totalMoveCount <= maxNumberOfMoves) {
			if(totalMoveCount < minNumberOfMovesToWin){
				gameStatus = getTurnOngoingStatus(playerInfo, firstPlayerCounter, secondPlayerCounter);
			}else{
				gameStatus = checkForWins(gameStatus, firstPlayerCharacter, firstPlayerName, secondPlayerName, table);
				if(null == gameStatus){
					if( totalMoveCount < maxNumberOfMoves){
						gameStatus = getTurnOngoingStatus(playerInfo, firstPlayerCounter, secondPlayerCounter);
					}else{
						gameStatus = "Game tied.";			
					}
				}
			}
		}
		
		final StringBuffer tableBody = new StringBuffer();
		for (int i = 0; i < table.length; i++) {
			tableBody.append("<tr>");
			for (int j = 0; j < table[i].length; j++) {
				tableBody.append("<td>").append(table[i][j]).append("</td>");
			}
			tableBody.append("</tr>");
		}
		
		return "<h1>Tic Tac Toe</h1>" 
		+ "<p><b>Game ID: </b>" + gameId
		+ "<p><b>Game status</b> "+gameStatus 
		+ "<br>"
		
		+"<table border=\"1\" cellpadding=\"10\">"
			+ "<tbody align=\"center\">"
				+ tableBody.toString()
			+ "</tbody>"
		+ "</table>"


		;
	}

	public String checkForWins(String gameStatus, final String firstPlayerCharacter, final String firstPlayerName,
			final String secondPlayerName, final String[][] table) {
		gameStatus = checkForRowWin(firstPlayerCharacter, firstPlayerName, secondPlayerName, table);
		if(null != gameStatus){
			return gameStatus;
		}
		
		final int order = table.length;
		final String[] columns = new String[order];
		String primaryDiagonal = "";
		String secondaryDiagonal = "";
		for (int i = 0; i < order; i++) {
			for (int j = 0; j < table[i].length; j++) {
				final String character = table[i][j];
				columns[j] = (null == columns[j])? character : (columns[j] + character);
			}	
			primaryDiagonal += table[i][i];
			secondaryDiagonal += table[i][order - i - 1];
		}
		
		String winningCharacter = "";
		if(!isNotWinningLane(primaryDiagonal)){
			System.out.println("Primary diagonal winner.");
			winningCharacter = getFirstCharacter(primaryDiagonal);
		}else if (!isNotWinningLane(secondaryDiagonal)) {
			System.out.println("Secondary diagonal winner.");
			winningCharacter = getFirstCharacter(secondaryDiagonal);
		}
		
		if(!winningCharacter.isEmpty()){
			return "Game over. Winner: " + (firstPlayerCharacter.equals(winningCharacter)?firstPlayerName:secondPlayerName);
		}
		

		boolean columnWin = false;
		for (final String column : columns) {
			if(isNotWinningLane(column)){
				continue;
			}
			columnWin = true;
			winningCharacter = getFirstCharacter(column);
			break;
		}
		
		if (columnWin) {
			System.out.println("Column winner.");
			return "Game over. Winner: " + (firstPlayerCharacter.equals(winningCharacter)?firstPlayerName:secondPlayerName);
		}
		
		return null;
	}

	public String getFirstCharacter(final String column) {
		return column.substring(0,1);
	}

	public boolean isNotWinningLane(final String lane) {
		return (lane.contains("x") && lane.contains("o")) || lane.contains("null");
	}

	public String checkForRowWin(final String firstPlayerCharacter, final String firstPlayerName,
			final String secondPlayerName, final String[][] table) {
		for (int i = 0; i < table.length; i++) {
			final String rowFirstCharacter = table[i][0];
			if(rowFirstCharacter == null){
				continue;
			}
			
			boolean rowWin = true;
			for (int j = 1; j < table[i].length; j++) {
				if(!rowFirstCharacter.equals(table[i][j])){
					rowWin = false;
					break;
				}
			}
			if(rowWin){
				System.out.println("Row winner");
				return "Game over. Winner: " + (firstPlayerCharacter.equals(rowFirstCharacter)?firstPlayerName:secondPlayerName);
			}
		}
		return null;
	}

	public String getTurnOngoingStatus(final Player playerInfo, final int firstPlayerCounter, final int secondPlayerCounter) {
		String gameStatus;
		final String turn;
		if(firstPlayerCounter > secondPlayerCounter){
			turn = playerInfo.getSecondPlayerName() +"'s turn.";
		}else{
			turn = playerInfo.getFirstPlayerName() +"'s turn.";
		}
		System.out.println(turn);
		
		gameStatus = "Game ongoing. " + turn;
		return gameStatus;
	}

}
