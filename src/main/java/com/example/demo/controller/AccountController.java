package com.example.demo.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import com.example.demo.model.Account;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.UserRepository;

@Controller
public class AccountController {

	private final HttpSession session;
	private final UserRepository userRepository;
	private final TaskRepository taskRepository;
	private final Account account;

	public AccountController(HttpSession session, Account account, UserRepository userRepository,
			TaskRepository taskRepository) {
		this.session = session;
		this.account = account;
		this.userRepository = userRepository;
		this.taskRepository = taskRepository;
	}

	// ログイン画面を表示
	@GetMapping({ "/", "/login", "/logout" })
	public String index() {
		// セッション情報を全てクリアする
		session.invalidate();
		return "login";
	}

	// ログインを実行

	@PostMapping("/login")
	public String login(
			@RequestParam String email,
			@RequestParam String password,
			Model model) {
		List<String> errorList = new ArrayList<>();

		if (email.length() == 0 || password.length() == 0) {
			errorList.add("入力してください");
			model.addAttribute("errorList", errorList);
			return "login";
		}

		List<User> user = userRepository.findAll();
		for (User login : user) {
			if (login.getEmail().equals(email) && login.getPassword().equals(password)) {
				LocalDate nowLoginDate = LocalDate.now();

				if (!(nowLoginDate.equals(login.getLastLoginDate()))) {
					List<Task> task = taskRepository.findAll();
					for (Task todayOn : task) {
						if (todayOn.getRoutine() > 0) {
							todayOn.setIsToday(true);
							taskRepository.save(todayOn);
						}
					}
				}
				login.setLastLoginDate(nowLoginDate);
				userRepository.save(login);
				account.setName(login.getName());
				account.setId(login.getId());

				return "redirect:/tasks";
			}
		}

		errorList.add("メールアドレスとパスワードが一致しませんでした");
		model.addAttribute("errorList", errorList);
		return "login";

	}

}

//	@GetMapping("/account")
//	public String create() {
//
//		return "accountForm";
//	}
//
//	@PostMapping("/account")
//	public String store(
//			@RequestParam(defaultValue = "") String name,
//			@RequestParam(defaultValue = "") String address,
//			@RequestParam(defaultValue = "") String tel,
//			@RequestParam(defaultValue = "") String email,
//			@RequestParam(defaultValue = "") String password,
//			Model model) {
//		//		List<String> errorList = new ArrayList<>();
//		//		User user = new User(name, address, tel, email, password);
//		//
//		//		if (name.length() == 0) {
//		//			errorList.add("名前は必須です");
//		//		}
//		//
//		//		if (address.length() == 0) {
//		//			errorList.add("住所は必須です");
//		//		}
//		//
//		//		if (tel.length() == 0) {
//		//			errorList.add("電話番号は必須です");
//		//		}
//		//
//		//		if (email.length() == 0) {
//		//			errorList.add("メールアドレスは必須です");
//		//		}
//		//
//		//		if (password.length() == 0) {
//		//			errorList.add("パスワードは必須です");
//		//		}
//		//
//		//		if (userRepository.existsByEmail(email) == true) {
//		//			errorList.add("登録済みのメールアドレスです");
//		//		}
//		//
//		//		if (errorList.size() > 0) {
//		//			model.addAttribute("errorList", errorList);
//		//			return "accountForm";
//		//		}
//		//
//		//		userRepository.save(user);
//		return "redirect:/login";
//
//	}
