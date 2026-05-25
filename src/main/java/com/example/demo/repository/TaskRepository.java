package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {
	//	userId
	List<Task> findByUserId(Integer userId);

	List<Task> findByUserIdOrderByDeadlineAsc(Integer userId);

	List<Task> findByUserIdOrderByImportanceDesc(Integer userId);

	List<Task> findByUserIdOrderByRoutineAsc(Integer userId);

	//	userId categoryId
	List<Task> findByUserIdAndCategoryId(Integer userId, Integer categoryId);

	List<Task> findByUserIdAndCategoryIdOrderByDeadlineAsc(Integer userId, Integer categoryId);

	List<Task> findByUserIdAndCategoryIdOrderByImportanceDesc(Integer userId, Integer categoryId);

	List<Task> findByUserIdAndCategoryIdOrderByRoutineAsc(Integer userId, Integer categoryId);

	//	userId keyword
	List<Task> findByUserIdAndTitleContaining(Integer userId, String keyword);

	List<Task> findByUserIdAndTitleContainingOrderByDeadlineAsc(Integer userId, String keyword);

	List<Task> findByUserIdAndTitleContainingOrderByImportanceDesc(Integer userId, String keyword);

	List<Task> findByUserIdAndTitleContainingOrderByRoutineAsc(Integer userId, String keyword);

	//	3つ
	List<Task> findByUserIdAndCategoryIdAndTitleContainingOrderByDeadlineAsc(Integer userId,
			Integer categoryId, String keyword);

	List<Task> findByUserIdAndCategoryIdAndTitleContainingOrderByImportanceDesc(Integer userId,
			Integer categoryId, String keyword);

	List<Task> findByUserIdAndCategoryIdAndTitleContainingOrderByRoutineAsc(Integer userId,
			Integer categoryId, String keyword);

	List<Task> findByUserIdAndCategoryIdAndTitleContaining(Integer userId,
			Integer categoryId, String keyword);
}
