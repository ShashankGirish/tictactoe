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
		String returnStatus = "";
		System.out.println(String.format(" ===========  gameId: [%s], name: [%s], character: [%s], row: [%s], column: [%s].", gameId, move.getName(), move.getCharacter(), move.getRowName(), move.getColumnName()));

		final String uniqueID = UUID.randomUUID().toString();
		List<Player> players = new ArrayList<Player>();
		players = playerRepository.findBygameId(gameId);
		if (players.isEmpty()) {
			returnStatus = "Invalid game id.";
		}
		
		for (final Player player : players) {
			final String moveGameId = gameId; 
			move.setGameId(moveGameId);
			final String playerGameId = player.getGameId();
			if (playerGameId.equals(moveGameId)) {
				final boolean isCellEmpty = checkIfMoveCellIsEmpty(move, gameId);
				if (isCellEmpty) {
					final String name = move.getName();
					if (name.equals(player.getSecondPlayerName())) {
						move.setCharacter(player.getSecondPlayerCharacter());
					} else {
						move.setCharacter(player.getFirstPlayerCharacter());
					}
					move.setSerialNumber(uniqueID);
					moveRepository.save(move);
					returnStatus = "200 OK";
					break;
				} else {
					returnStatus = String.format("Invalid move. Cell [%s,%s] already occupied.",move.getRowName(), move.getColumnName());
					break;
				}
			} else {
				returnStatus = "Invalid gameId";
			}
		}
		return returnStatus;
	}

	private boolean checkIfMoveCellIsEmpty(final Move currentMove, final String gameId) {
		final boolean returnValue = true;
		final String rowName = currentMove.getRowName();
		final String columnName = currentMove.getColumnName();
		
		final List<Move> previousMoves = moveRepository.findBygameId(gameId);
		System.out.println("Moves: " + previousMoves);
		if (previousMoves.isEmpty()) {
			System.out.println("no moves present");
			return true;
		}
		for (final Move existingMove : previousMoves) {
			if (existingMove.getRowName().equals(rowName) && existingMove.getColumnName().equals(columnName)) {
				return false;
			} 
		}
		return returnValue;
	}

}
