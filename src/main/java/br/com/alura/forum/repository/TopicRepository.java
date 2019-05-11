package br.com.alura.forum.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import br.com.alura.forum.model.OpenTopicsByCategory;
import br.com.alura.forum.model.User;
import br.com.alura.forum.model.topic.domain.Topic;

public interface TopicRepository extends Repository<Topic, Long>, JpaSpecificationExecutor<Topic> {

	@Query("select t from Topic t")
	List<Topic> list();

	List<Topic> findAll();
	
	Topic save(Topic topic);

	Topic findById(Long id);

	@Query("SELECT count(topic) FROM Topic topic "
			+ "JOIN topic.course course "
			+ "JOIN course.subcategory subcategory "
			+ "JOIN subcategory.category category "
			+ "WHERE category.id = :categoryId")
	int countTopicsByCategoryId(@Param("categoryId") Long categoryId);

	
	@Query("SELECT count(topic) FROM Topic topic "
			+ "JOIN topic.course course "
			+ "JOIN course.subcategory subcategory "
			+ "JOIN subcategory.category category "
			+ "WHERE category.id = :categoryId AND topic.creationInstant > :lastWeek")
	int countLastWeekTopicsByCategoryId(@Param("categoryId") Long categoryId,
			@Param("lastWeek") Instant lastWeek);

	
	@Query("SELECT count(topic) FROM Topic topic "
			+ "JOIN topic.course course "
			+ "JOIN course.subcategory subcategory "
			+ "JOIN subcategory.category category "
			+ "WHERE category.id = :categoryId AND topic.status = 'NOT_ANSWERED'")
	int countUnansweredTopicsByCategoryId(@Param("categoryId") Long categoryId);

	List<Topic> findByOwnerAndCreationInstantAfterOrderByCreationInstantAsc(User owner, Instant creationTime);

	@Query("select new br.com.alura.forum.model.OpenTopicsByCategory(t.course.subcategory.category.name as categoryName, count(t) as topicCount, now() as instant) from Topic t where t.status = 'NOT_ANSWERED' group by t.course.subcategory.category)") 
	List<OpenTopicsByCategory> findOpenTopicsByCategory();
}
