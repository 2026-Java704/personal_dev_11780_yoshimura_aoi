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
import com.example.demo.model.Account;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.TaskRepository;

@Controller
public class TaskController {

	private final TaskRepository taskRepository;
	private final CategoryRepository categoryRepository;
	private final Account account;

	public TaskController(TaskRepository taskRepository, CategoryRepository categoryRepository, Account account) {
		this.taskRepository = taskRepository;
		this.categoryRepository = categoryRepository;
		this.account = account;

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

		// ログイン中のユーザーIDを取得
		Integer userId = account.getId();
		List<Task> taskList = null;

		// カテゴリーIDキーワード
		if (categoryId != null && keyword.length() > 0) {
			if ("deadlineAsc".equals(sort)) {
				taskList = taskRepository.findByUserIdAndCategoryIdAndTitleContainingOrderByDeadlineAsc(userId,
						categoryId, keyword);
			} else if ("importanceAsc".equals(sort)) {
				taskList = taskRepository.findByUserIdAndCategoryIdAndTitleContainingOrderByImportanceDesc(userId,
						categoryId, keyword);
			} else if ("routineAsc".equals(sort)) {
				taskList = taskRepository.findByUserIdAndCategoryIdAndTitleContainingOrderByRoutineAsc(userId,
						categoryId, keyword);
			} else {
				taskList = taskRepository.findByUserIdAndCategoryIdAndTitleContaining(userId, categoryId, keyword);
			}
		}
		// カテゴリーID
		else if (categoryId != null && keyword.length() == 0) {
			if ("deadlineAsc".equals(sort)) {
				taskList = taskRepository.findByUserIdAndCategoryIdOrderByDeadlineAsc(userId, categoryId);
			} else if ("importanceAsc".equals(sort)) {
				taskList = taskRepository.findByUserIdAndCategoryIdOrderByImportanceDesc(userId, categoryId);
			} else if ("routineAsc".equals(sort)) {
				taskList = taskRepository.findByUserIdAndCategoryIdOrderByRoutineAsc(userId, categoryId);
			} else {
				taskList = taskRepository.findByUserIdAndCategoryId(userId, categoryId);
			}
		}
		// キーワード
		else if (categoryId == null && keyword.length() > 0) {
			if ("deadlineAsc".equals(sort)) {
				taskList = taskRepository.findByUserIdAndTitleContainingOrderByDeadlineAsc(userId, keyword);
			} else if ("importanceAsc".equals(sort)) {
				taskList = taskRepository.findByUserIdAndTitleContainingOrderByImportanceDesc(userId, keyword);
			} else if ("routineAsc".equals(sort)) {
				taskList = taskRepository.findByUserIdAndTitleContainingOrderByRoutineAsc(userId, keyword);
			} else {
				taskList = taskRepository.findByUserIdAndTitleContaining(userId, keyword);
			}
		}
		// 条件なし
		else {
			if ("deadlineAsc".equals(sort)) {
				taskList = taskRepository.findByUserIdOrderByDeadlineAsc(userId);
			} else if ("importanceAsc".equals(sort)) {
				taskList = taskRepository.findByUserIdOrderByImportanceDesc(userId);
			} else if ("routineAsc".equals(sort)) {
				taskList = taskRepository.findByUserIdOrderByRoutineAsc(userId);
			} else {
				taskList = taskRepository.findByUserId(userId);
			}
		}
		for (Task taskT : taskList) {
			if (taskT.getIsToday() == true) {
				taskT.setDeadline(LocalDate.now());
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
		Task task = new Task(0, account.getId(), "", todayDate, 0, 0, "", false);
		if (task.getRoutine() > 0) {
			task.setIsToday(true);
		}
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
		Task task = new Task(categoryId, account.getId(), title, deadline, importance, routine, memo, false);
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

		if (task.getRoutine() > 0) {
			task.setIsToday(true);
			task.setDeadline(LocalDate.now());
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
			task.setDeadline(LocalDate.now());
			taskRepository.save(task);
		} else {
			taskRepository.deleteById(id);
		}
		return "redirect:/tasks";
	}
}
