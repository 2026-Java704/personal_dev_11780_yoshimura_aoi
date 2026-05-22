package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {
	// SELECT * FROM items WHERE category_id = ?
	List<Task> findByCategoryId(Integer categoryId);

	List<Task> findByTitleContaining(String keyword);

	List<Task> findByCategoryIdAndTitleContaining(Integer categoryId, String keyword);

	//ソート
	List<Task> findByCategoryIdAndTitleContainingOrderByDeadlineAsc(Integer categoryId, String keyword);

	List<Task> findByCategoryIdAndTitleContainingOrderByImportanceDesc(Integer categoryId, String keyword);

	List<Task> findByCategoryIdAndTitleContainingOrderByRoutineAsc(Integer categoryId, String keyword);

	List<Task> findByCategoryIdOrderByDeadlineAsc(Integer categoryId);

	List<Task> findByCategoryIdOrderByImportanceDesc(Integer categoryId);

	List<Task> findByCategoryIdOrderByRoutineAsc(Integer categoryId);

	List<Task> findByTitleContainingOrderByDeadlineAsc(String keyword);

	List<Task> findByTitleContainingOrderByImportanceDesc(String keyword);

	List<Task> findByTitleContainingOrderByRoutineAsc(String keyword);

	List<Task> findAllByOrderByDeadlineAsc();

	List<Task> findAllByOrderByImportanceDesc();

	List<Task> findAllByOrderByRoutineAsc();

}
