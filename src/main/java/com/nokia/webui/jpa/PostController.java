package com.nokia.webui.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

	@Autowired
	PlayerRepository playerRepository;

	@Autowired
	MoveRepository moveRepository;

	@RequestMapping(method = RequestMethod.POST, value = "/game")
	public String savePlayerDetails(@RequestBody Player playerInfo) {
		String uniqueID = UUID.randomUUID().toString();
		System.out.println("UUID value is:" + uniqueID);
		System.out.println("In post controller, json string is:" + playerInfo.getSecondPlayerCharacter());
		if (playerInfo.getSecondPlayerCharacter().equalsIgnoreCase("x")) {
			playerInfo.setFirstPlayerCharacter("o");
		} else {
			playerInfo.setFirstPlayerCharacter("x");
		}
		playerInfo.setFirstPlayerName("computer");
		playerInfo.setGameId(uniqueID);
		playerRepository.save(playerInfo);
		return uniqueID;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/game/move")
	public String saveMoves(@RequestBody Move move) {
		String returnValue = "";

		String uniqueID = UUID.randomUUID().toString();
		List<Player> listOfPlayers = new ArrayList<Player>();
		listOfPlayers = (List<Player>) playerRepository.findAll();
		if (listOfPlayers.isEmpty()) {
			returnValue = "Invalid game id";
		}
		for (Player player : listOfPlayers) {
			String moveGameId = move.getGameId();
			String playerGameId = player.getGameId();
			if (playerGameId.equals(moveGameId)) {
				int isMovePresent = checkIfMoveAlreadyPresent(move);
				if (isMovePresent == 0) {
					String name = move.getPlayerName();
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

	private int checkIfMoveAlreadyPresent(Move move) {
		int returnValue = 1;
		List<Move> listOfMoves = new ArrayList<Move>();
		listOfMoves = (List<Move>) moveRepository.findAll();
		if (listOfMoves.isEmpty()) {
			System.out.println("no moves present");
			returnValue = 0;
		}
		for (Move moveObject : listOfMoves) {
			if ((moveObject.getPlayerName().equals(move.getPlayerName()))
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
