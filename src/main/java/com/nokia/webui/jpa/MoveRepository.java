package com.nokia.webui.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface MoveRepository extends CrudRepository<Move, Long> {

	List<Move> findBygameId(String gameid);

	@Query("SELECT t FROM Move t WHERE t.id = :id")
	List<Move> findbyid(@Param("id") String id);

}
