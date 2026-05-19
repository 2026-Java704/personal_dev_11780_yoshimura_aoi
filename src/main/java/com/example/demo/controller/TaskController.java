package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Task;
import com.example.demo.repository.TaskRepository;

@Controller
public class TaskController {

	private final Task task;
	private final TaskRepository taskRepository;

	public TaskController(Task task, TaskRepository taskRepository) {
		this.task = task;
		this.taskRepository = taskRepository;
	}

	// カート内容を表示
	@GetMapping("/cart")
	public String index() {
		// cart.htmlの出力
		return "cart";
	}

	// 指定した商品をカートに追加する
	@PostMapping("/cart/add")
	public String addCart(
			@RequestParam Integer itemId) {

		//		// 商品コードをキーに商品情報を取得する
		//		Task task = taskRepository.findById(itemId).get();
		//
		//		// 「/cart」にリダイレクト
		return "redirect:/cart";
	}

	// 指定した商品をカートから削除
	@PostMapping("/cart/delete")
	public String deleteCart(
	//			@RequestParam 
	) {

		// カート情報から削除
		//		cart.delete(itemId);
		// 「/cart」にリダイレクト
		return "redirect:/cart";
	}
}
