package com.example.demo.controller;

import java.time.LocalDate;
import java.util.ArrayList;
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
	public String index(
			@RequestParam(defaultValue = "") Integer categoryId,
			@RequestParam(defaultValue = "") String keyword,
			@RequestParam(defaultValue = "") LocalDate deadline,
			@RequestParam(defaultValue = "") Integer importance,
			@RequestParam(defaultValue = "") Integer routine,
			@RequestParam(defaultValue = "") String sort,
			Model model) {

		List<Category> categoryList = categoryRepository.findAll();
		model.addAttribute("categories", categoryList);
		List<Task> taskList = null;

		//カテゴリーIDとキーワード
		if (categoryId != null && keyword.length() > 0) {
			if ("deadlineAsc".equals(sort)) {
				taskList = taskRepository.findByCategoryIdAndTitleContainingOrderByDeadlineAsc(categoryId, keyword);
			} else if ("importanceAsc".equals(sort)) {
				taskList = taskRepository.findByCategoryIdAndTitleContainingOrderByImportanceDesc(categoryId, keyword);
			} else if ("routineAsc".equals(sort)) {
				taskList = taskRepository.findByCategoryIdAndTitleContainingOrderByRoutineAsc(categoryId, keyword);
			} else {
				taskList = taskRepository.findByCategoryIdAndTitleContaining(categoryId, keyword);
			}
		}
		// カテゴリーID
		else if (categoryId != null && keyword.length() == 0) {
			if ("deadlineAsc".equals(sort)) {
				taskList = taskRepository.findByCategoryIdOrderByDeadlineAsc(categoryId);
			} else if ("importanceAsc".equals(sort)) {
				taskList = taskRepository.findByCategoryIdOrderByImportanceDesc(categoryId);
			} else if ("routineAsc".equals(sort)) {
				taskList = taskRepository.findByCategoryIdOrderByRoutineAsc(categoryId);
			} else {
				taskList = taskRepository.findByCategoryId(categoryId);
			}
		}
		// キーワード
		else if (categoryId == null && keyword.length() > 0) {
			if ("deadlineAsc".equals(sort)) {
				taskList = taskRepository.findByTitleContainingOrderByDeadlineAsc(keyword);
			} else if ("importanceAsc".equals(sort)) {
				taskList = taskRepository.findByTitleContainingOrderByImportanceDesc(keyword);
			} else if ("routineAsc".equals(sort)) {
				taskList = taskRepository.findByTitleContainingOrderByRoutineAsc(keyword);
			} else {
				taskList = taskRepository.findByTitleContaining(keyword);
			}
		}
		// 無し
		else {
			if ("deadlineAsc".equals(sort)) {
				taskList = taskRepository.findAllByOrderByDeadlineAsc(); // リポジトリの命名法に合わせます
			} else if ("importanceAsc".equals(sort)) {
				taskList = taskRepository.findAllByOrderByImportanceDesc();
			} else if ("routineAsc".equals(sort)) {
				taskList = taskRepository.findAllByOrderByRoutineAsc();
			} else {
				taskList = taskRepository.findAll();
			}
		}

		model.addAttribute("cId", categoryId);
		model.addAttribute("keyword", keyword);
		model.addAttribute("sort", sort);
		model.addAttribute("tasks", taskList);

		return "tasks";
	}

	@GetMapping("/tasks/add")
	public String create(Model model) {
		List<Category> categoryList = categoryRepository.findAll();
		model.addAttribute("categories", categoryList);
		LocalDate todayDate = LocalDate.now();
		Task task = new Task(0, "", todayDate, 0, 0, "", false);
		model.addAttribute("tasks", task);
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
		List<String> errorList = new ArrayList<>();
		List<Category> categoryList = categoryRepository.findAll();
		model.addAttribute("categories", categoryList);

		if (title.length() == 0) {
			errorList.add("タイトルを入力してください");
		}
		if (deadline == null) {
			errorList.add("期日は必須です");
		}
		if (errorList.size() > 0) {
			model.addAttribute("errorList", errorList);
			model.addAttribute("tasks", task);
			return "addTask";
		}
		taskRepository.save(task);
		return "redirect:/tasks";
	}

	@GetMapping("/tasks/{id}/detail")
	public String show(@PathVariable Integer id,
			Model model) {
		List<Category> categoryList = categoryRepository.findAll();
		model.addAttribute("categories", categoryList);
		Task task = taskRepository.findById(id).get();
		model.addAttribute("tasks", task);
		return "detailTask";
	}

	@GetMapping("/tasks/{id}/edit")
	public String edit(@PathVariable Integer id,
			Model model) {
		List<Category> categoryList = categoryRepository.findAll();
		model.addAttribute("categories", categoryList);

		Task task = taskRepository.findById(id).get();
		model.addAttribute("tasks", task);
		return "editTask";
	}

	@PostMapping("/tasks/{id}/edit")
	public String update(
			@PathVariable Integer id,
			@RequestParam(defaultValue = "") String title,
			@RequestParam(defaultValue = "") Integer categoryId,
			@RequestParam(defaultValue = "") LocalDate deadline,
			@RequestParam(defaultValue = "") Integer importance,
			@RequestParam(defaultValue = "") Integer routine,
			@RequestParam(defaultValue = "") String memo,
			Model model) {

		Task task = taskRepository.findById(id).get();
		task.setTitle(title);
		task.setCategoryId(categoryId);
		task.setDeadline(deadline);
		task.setImportance(importance);
		task.setRoutine(routine);
		task.setMemo(memo);
		taskRepository.save(task);
		return "redirect:/tasks";
	}

	@PostMapping("/tasks/{id}/delete")
	public String delete(@PathVariable Integer id) {
		taskRepository.deleteById(id);
		return "redirect:/tasks";
	}

	@PostMapping("/tasks/{id}/addtoday")
	public String addToday(@PathVariable Integer id) {
		Task task = taskRepository.findById(id).get();
		task.setIsToday(true);
		taskRepository.save(task);
		return "redirect:/tasks";
	}

	@PostMapping("/tasks/{id}/complete")
	public String complete(@PathVariable Integer id) {
		Task task = taskRepository.findById(id).get();
		if (task.getRoutine() > 0 && task.getIsToday() == true) {
			task.setIsToday(false);
			taskRepository.save(task);
		} else {
			taskRepository.deleteById(id);
		}
		return "redirect:/tasks";
	}
}
