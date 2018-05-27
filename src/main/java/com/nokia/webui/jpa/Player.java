package com.nokia.webui.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Player")
public class Player implements Serializable {

	private static final long serialVersionUID = -3009157732242241606L;

	@Column(name = "firstplayername")
	private String firstPlayerName;

	@Column(name = "secondplayername")
	private String secondPlayerName;

	@Column(name = "firstplayercharacter")
	private String firstPlayerCharacter;

	@Column(name = "secondplayercharacter")
	private String secondPlayerCharacter;

	@Id
	@Column(name = "gameid")
	private String gameId;

	public String getFirstPlayerName() {
		return firstPlayerName;
	}

	public void setFirstPlayerName(String firstPlayerName) {
		this.firstPlayerName = firstPlayerName;
	}

	public String getSecondPlayerName() {
		return secondPlayerName;
	}

	public void setSecondPlayerName(String secondPlayerName) {
		this.secondPlayerName = secondPlayerName;
	}

	public String getFirstPlayerCharacter() {
		return firstPlayerCharacter;
	}

	public void setFirstPlayerCharacter(String firstPlayerCharacter) {
		this.firstPlayerCharacter = firstPlayerCharacter;
	}

	public String getSecondPlayerCharacter() {
		return secondPlayerCharacter;
	}

	public void setSecondPlayerCharacter(String secondPlayerCharacter) {
		this.secondPlayerCharacter = secondPlayerCharacter;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Player() {

		super();
	}

	public Player(String firstPlayerName, String secondPlayerName, String firstPlayerCharacter,
			String secondPlayerCharacter, String gameId) {
		super();
		this.firstPlayerName = firstPlayerName;
		this.secondPlayerName = secondPlayerName;
		this.firstPlayerCharacter = firstPlayerCharacter;
		this.secondPlayerCharacter = secondPlayerCharacter;
		this.gameId = gameId;
	}

	@Override
	public String toString() {
		return "Player [firstPlayerName=" + firstPlayerName + ", secondPlayerName=" + secondPlayerName
				+ ", firstPlayerCharacter=" + firstPlayerCharacter + ", secondPlayerCharacter=" + secondPlayerCharacter
				+ ", gameId=" + gameId + "]";
	}

}
