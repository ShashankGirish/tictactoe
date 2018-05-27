package com.nokia.webui.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

	private static final String O = "o";

	private static final String X = "x";

	@Autowired
	PlayerRepository playerRepository;

	@Autowired
	MoveRepository moveRepository;

	@RequestMapping(method = RequestMethod.POST, value = "/game")
	public String savePlayerDetails(@RequestBody final Player playerInfo) {
		final String uniqueID = UUID.randomUUID().toString();
		System.out.println("UUID value is:" + uniqueID);
		System.out.println("First Player Name:" + playerInfo.getFirstPlayerName());
		System.out.println("First Player Character:" + playerInfo.getFirstPlayerCharacter().toLowerCase());
		
		playerInfo.setSecondPlayerName("computer");
		playerInfo.setSecondPlayerCharacter(X.equalsIgnoreCase(playerInfo.getFirstPlayerCharacter())?O:X);
		
		playerInfo.setGameId(uniqueID);
		playerRepository.save(playerInfo);
		return uniqueID;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/game/{gameId}/move")
	public String saveMoves(@PathVariable("gameId") final String gameId, @RequestBody final Move move) {
		String returnValue = "";
		System.out.println(String.format(" ===========  gameId: [%s], name: [%s], character: [%s], row: [%s], column: [%s].", gameId, move.getName(), move.getCharacter(), move.getRowName(), move.getColumnName()));

		final String uniqueID = UUID.randomUUID().toString();
		List<Player> listOfPlayers = new ArrayList<Player>();
		listOfPlayers = playerRepository.findBygameId(gameId);
		if (listOfPlayers.isEmpty()) {
			returnValue = "Invalid game id";
		}
		for (final Player player : listOfPlayers) {
			final String moveGameId = gameId; 
			move.setGameId(moveGameId);
			final String playerGameId = player.getGameId();
			if (playerGameId.equals(moveGameId)) {
				final int isMovePresent = checkIfMoveAlreadyPresent(move, gameId);
				if (isMovePresent == 0) {
					final String name = move.getName();
					if (name.equals(player.getSecondPlayerName())) {
						move.setCharacter(player.getSecondPlayerCharacter());
					} else {
						move.setCharacter(player.getFirstPlayerCharacter());
					}
					move.setSerialNumber(uniqueID);
					moveRepository.save(move);
					returnValue = "200 OK";
					break;
				} else {
					returnValue = "Invalid move.Field already occupied";
					break;
				}
			} else {
				returnValue = "Invalid gameId";
			}
		}
		return returnValue;
	}

	private int checkIfMoveAlreadyPresent(final Move move, final String gameId) {
		int returnValue = 1;
		List<Move> listOfMoves = new ArrayList<Move>();
		listOfMoves = moveRepository.findBygameId(gameId);
		System.out.println("Moves: " + listOfMoves);
		if (listOfMoves.isEmpty()) {
			System.out.println("no moves present");
			returnValue = 0;
		}
		for (final Move moveObject : listOfMoves) {
			if ((moveObject.getName()
					.equals(move.getName()))
					&& (moveObject.getRowName().equals(move.getRowName()))
					&& (moveObject.getColumnName().equals(move.getColumnName()))
					&& (moveObject.getGameId().equals(move.getGameId()))) {
				returnValue = 1;
				break;
			} else {
				returnValue = 0;
			}
		}
		return returnValue;
	}

}
