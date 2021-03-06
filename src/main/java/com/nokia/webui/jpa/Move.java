package com.nokia.webui.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Move")
public class Move implements Serializable {

	private static final long serialVersionUID = -3009157732242241606L;

	@Id
	@Column(name = "serialnumber")
	private String serialNumber;

	@Column(name = "gameid")
	private String gameId;

	@Column(name = "playername")
	private String playerName;

	@Column(name = "rowname")
	private String rowName;

	@Column(name = "columnname")
	private String columnName;

	@Column(name = "character")
	private String character;

	public String getCharacter() {
		return character;
	}

	public void setCharacter(final String character) {
		this.character = character;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(final String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(final String gameId) {
		this.gameId = gameId;
	}

	public String getName() {
		return playerName;
	}

	public void setName(final String playerName) {
		this.playerName = playerName;
	}

	public String getRowName() {
		return rowName;
	}

	public void setRowName(final String rowName) {
		this.rowName = rowName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(final String columnName) {
		this.columnName = columnName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Move() {
		super();
	}

	public Move(final String gameId, final String name, final String rowName, final String columnName) {
		super();
		this.gameId = gameId;
		this.playerName = name;
		this.rowName = rowName;
		this.columnName = columnName;
	}

	@Override
	public String toString() {
		return "Move [gameId=" + gameId + ", playerName=" + playerName + ", rowName=" + rowName + ", columnName="
				+ columnName + "]";
	}

}
