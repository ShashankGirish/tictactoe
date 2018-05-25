package com.nokia.webui.jpa;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetController {

	@Autowired
	PlayerRepository playerRepository;

	@Autowired
	MoveRepository moveRepository;

	@RequestMapping(method = RequestMethod.GET, value = "/game")
	public String GetGameInformation(@RequestParam("gameId") String gameId) {
		int numberOfFill = 0;
		int xCount = 0;
		int oCount = 0;
		String gameState = "";
		String gameStatus = "";
		List<String> movesList = new ArrayList<String>();
		List<Move> listOfMoves = new ArrayList<Move>();
		listOfMoves = (List<Move>) moveRepository.findBygameId(gameId);
		List<Player> listOfPlayers = new ArrayList<Player>();
		listOfPlayers = (List<Player>) playerRepository.findBygameId(gameId);
		String AA = "null", AB = "null", AC = "null", BA = "null", BB = "null", BC = "null", CA = "null", CB = "null",
				CC = "null";

		if (listOfPlayers.isEmpty()) {
			return "<h1>Invalid game id</h1>";
		}
		for (Move move : listOfMoves) {
			String rowName = move.getRowName();
			String columnName = move.getColumnName();
			if (rowName.equals("A") && columnName.equals("A")) {
				AA = move.getCharacter();
				numberOfFill++;
				movesList.add(AA);
			} else if (rowName.equals("A") && columnName.equals("B")) {
				AB = move.getCharacter();
				numberOfFill++;
				movesList.add(AB);
			} else if (rowName.equals("A") && columnName.equals("C")) {
				AC = move.getCharacter();
				numberOfFill++;
				movesList.add(AC);
			} else if (rowName.equals("B") && columnName.equals("A")) {
				BA = move.getCharacter();
				numberOfFill++;
				movesList.add(BA);
			} else if (rowName.equals("B") && columnName.equals("B")) {
				BB = move.getCharacter();
				numberOfFill++;
				movesList.add(BB);
			} else if (rowName.equals("B") && columnName.equals("C")) {
				BC = move.getCharacter();
				numberOfFill++;
				movesList.add(BC);
			} else if (rowName.equals("C") && columnName.equals("A")) {
				CA = move.getCharacter();
				numberOfFill++;
				movesList.add(CA);
			} else if (rowName.equals("C") && columnName.equals("B")) {
				CB = move.getCharacter();
				numberOfFill++;
				movesList.add(CB);
			} else if (rowName.equals("C") && columnName.equals("C")) {
				CC = move.getCharacter();
				numberOfFill++;
				movesList.add(CC);
			}
		}
		if (numberOfFill < 8 && numberOfFill > 0) {
			gameState = "Game ongoing";
			for (int i = 0; i < movesList.size(); i++) {
				System.out.println("Size of movesList is:" + movesList.size());
				if ((movesList.get(i).equals("x"))) {
					xCount++;
				} else if ((movesList.get(i).equals("o"))) {
					oCount++;
				}
			}
			System.out.println("xCount: " + xCount + "oCount: " + oCount);
			if (xCount > oCount) {
				for (Player player : listOfPlayers) {
					String playerCharacter = player.getSecondPlayerCharacter();
					if (playerCharacter.equals("x")) {
						System.out.println("xCount > oCount");
						gameStatus = gameState.concat(",").concat(player.getFirstPlayerName()).concat("'s")
								.concat("turn");
						System.out.println("gameStatus is:" + gameStatus);
					} else {
						System.out.println("xCount > oCount");
						gameStatus = gameState.concat(",").concat(player.getSecondPlayerName()).concat("'s")
								.concat("turn");
						System.out.println("gameStatus is:" + gameStatus);
					}
				}
			} else if (oCount > xCount) {
				for (Player player : listOfPlayers) {
					String playerCharacter = player.getSecondPlayerCharacter();
					if (playerCharacter.equals("o")) {
						System.out.println("oCount > xCount");
						gameStatus = gameState.concat(",").concat(player.getFirstPlayerName()).concat("'s")
								.concat("turn");
						System.out.println("gameStatus is:" + gameStatus);
					} else {
						System.out.println("oCount > xCount");
						gameStatus = gameState.concat(",").concat(player.getSecondPlayerName()).concat("'s")
								.concat("turn");
						System.out.println("gameStatus is:" + gameStatus);
					}
				}

			}
		} else if (numberOfFill == 0) {
			gameStatus = "Game not started yet";
		}
		return "<h1>Welcome to tic tac toe game</h1>" + "game id is:" + gameId + "<h2>Game status</h2>" + "<tr>"
				+ "<td>" + AA + "</td>" + "    " + "<td>" + AB + "</td>" + "    " + "<td>" + AC + "</td>" + "<br>"
				+ "</tr>" + "<tr>" + "<td>" + BA + "</td>" + "    " + "<td>" + BB + "</td>" + "    " + "<td>" + BC
				+ "</td>" + "<br>" + "</tr>" + "<tr>" + "<td>" + CA + "</td>" + "    " + "<td>" + CB + "</td>" + "    "
				+ "<td>" + CC + "</td>" + "</tr>" + "<br>" + "status: " + gameStatus;

	}

}
