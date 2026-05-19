package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Category;
import com.example.demo.entity.Task;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.TaskRepository;

@Controller
public class TaskController {

	private final TaskRepository taskRepository;
	private final CategoryRepository categoryRepository;

	public TaskController(TaskRepository taskRepository, CategoryRepository categoryRepository) {
		this.taskRepository = taskRepository;
		this.categoryRepository = categoryRepository;
	}

	@GetMapping("/tasks")
	public String index(@RequestParam(defaultValue = "") Integer categoryId,
			Model model) {

		List<Category> categoryList = categoryRepository.findAll();
		model.addAttribute("categories", categoryList);

		// タスク一覧情報の取得
		List<Task> taskList = null;
		if (categoryId == null) {
			taskList = taskRepository.findAll();
		} else {
			// tasksテーブルをカテゴリーIDを指定して一覧を取得
			taskList = taskRepository.findByCategoryId(categoryId);
		}
		model.addAttribute("tasks", taskList);

		return "tasks";
	}

	@GetMapping("/tasks/add")
	public String create(Model model) {
		List<Category> categoryList = categoryRepository.findAll();
		model.addAttribute("categories", categoryList);
		return "addTask";
	}

	@PostMapping("/tasks/add")
	public String store(
			@RequestParam(defaultValue = "") Integer categoryId,
			@RequestParam(defaultValue = "") String title,
			@RequestParam(defaultValue = "") LocalDate deadline,
			@RequestParam(defaultValue = "") Integer importance,
			@RequestParam(defaultValue = "") Integer routine,
			@RequestParam(defaultValue = "") String memo,
			Model model) {

		Task task = new Task(categoryId, title, deadline, importance, routine, memo, false);
		// tasksテーブルへの反映（INSERT）
		taskRepository.save(task);

		return "redirect:/tasks";
	}

	@GetMapping("/tasks/{id}/detail")
	public String show(@PathVariable Integer id) {

		return "detailTask";
	}

	@PostMapping("/tasks/{id}/edit")
	public String edit(@PathVariable Integer id) {

		return "editTask";
	}

	@PostMapping("/tasks/{id}/detail")
	public String update(
			@RequestParam String title,
			@RequestParam Integer categoryId,
			@RequestParam LocalDate deadline,
			@RequestParam Integer importance,
			@RequestParam Integer routine,
			@RequestParam String memo,
			Model model) {

		return "redirect:/tasks";
	}

	@PostMapping("/tasks/{id}/delete")
	public String delete(@PathVariable Integer id) {

		//		task.delete(taskId);

		return "redirect:/tasks";
	}

	@PostMapping("/tasks/{id}/addtoday")
	public String addToday(@PathVariable Integer id) {

		return "redirect:/tasks";
	}

	@PostMapping("/tasks/{id}/complete")
	public String complete(@PathVariable Integer id) {

		return "redirect:/tasks";
	}
}
