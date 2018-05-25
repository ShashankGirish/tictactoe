package com.nokia.webui.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PlayerRepository extends CrudRepository<Player, Long> {

	List<Player> findBygameId(String gameid);

	@Query("SELECT t FROM Player t WHERE t.id = :id")
	List<Player> findbyid(@Param("id") String id);

}
