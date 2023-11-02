package com.hackernews.repository;

import java.time.LocalDateTime;
import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hackernews.entity.Content;

public interface ContentRepositroy extends JpaRepository<Content, Integer>{

	@Query("select c from Content c where c.catagory= 'show' or c.catagory='normal' or c.catagory = null")
	public List<Content> getAllShowAndNormal();

	@Query("select c from Content c where c.catagory= 'ask'")
	public List<Content> getAllAsk();
	@Query("select c from Content c where c.catagory= 'show'")
	public List<Content> getAllShow();
	@Query("select c from Content c where c.catagory= 'job'")
	public List<Content> getAllJob();

	@Query(
            "SELECT c " + "FROM Content c " +
                    "WHERE c.submitTime " +
                    "BETWEEN :startDateTime " + "AND :endDateTime " +
                    "ORDER BY c.submitTime DESC"
    )
    List<Content> findAllBySubmitTimeBetween(
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime
    );
	@Query("SELECT c FROM Content c WHERE c.title ilike %:searchText% ")
	List<Content> findByTitleContaining(String searchText);

	@Query("SELECT c FROM Content c WHERE c.url ilike %:searchText% ")
	List<Content> findByUrlContaining(String searchText);

	@Query("SELECT c FROM Content c WHERE c.text ilike %:searchText% ")
	List<Content> findByTextContaining(String searchText);

	@Query("SELECT c FROM Content c " +
            "WHERE( c.title LIKE concat('%',:searchText,'%')"
            +"OR c.url LIKE concat('%',:searchText,'%')"
            + "OR c.text LIKE concat('%',:searchText,'%'))" +
            "AND (:catagory IS NULL OR c.catagory LIKE concat('%',:catagory,'%'))")
    Set<Content> findFilteredContent(@Param("catagory") String catagory, @Param("searchText") String searchText);
}
