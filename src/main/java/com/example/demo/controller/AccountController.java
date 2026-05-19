package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Account;
import com.example.demo.repository.UserRepository;

@Controller
public class AccountController {

	private final HttpSession session;
	private final Account account;
	private final UserRepository userRepository;

	public AccountController(HttpSession session, Account account, UserRepository userRepository) {
		this.session = session;
		this.account = account;
		this.userRepository = userRepository;
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
		//		List<String> errorList = new ArrayList<>();
		//
		//		if (customerRepository.existsByEmail(email) == true
		//				&& customerRepository.existsByEmail(password) == true) {
		//			User user = customerRepository.findByEmailAndPassword(email, password).getFirst();
		//			if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
		//
		//				account.setName(user.getName());
		//				account.setId(user.getId());
		//
		//				return "redirect:/items";
		//			}
		//		} else {
		//			errorList.add("メールアドレスとパスワードが一致しませんでした");
		//		}
		//
		//		if (errorList.size() > 0) {
		//			model.addAttribute("errorList", errorList);
		//		}

		return "login";
	}

	@GetMapping("/account")
	public String create() {

		return "accountForm";
	}

	@PostMapping("/account")
	public String store(
			@RequestParam(defaultValue = "") String name,
			@RequestParam(defaultValue = "") String address,
			@RequestParam(defaultValue = "") String tel,
			@RequestParam(defaultValue = "") String email,
			@RequestParam(defaultValue = "") String password,
			Model model) {
		//		List<String> errorList = new ArrayList<>();
		//		User user = new User(name, address, tel, email, password);
		//
		//		if (name.length() == 0) {
		//			errorList.add("名前は必須です");
		//		}
		//
		//		if (address.length() == 0) {
		//			errorList.add("住所は必須です");
		//		}
		//
		//		if (tel.length() == 0) {
		//			errorList.add("電話番号は必須です");
		//		}
		//
		//		if (email.length() == 0) {
		//			errorList.add("メールアドレスは必須です");
		//		}
		//
		//		if (password.length() == 0) {
		//			errorList.add("パスワードは必須です");
		//		}
		//
		//		if (customerRepository.existsByEmail(email) == true) {
		//			errorList.add("登録済みのメールアドレスです");
		//		}
		//
		//		if (errorList.size() > 0) {
		//			model.addAttribute("errorList", errorList);
		//			return "accountForm";
		//		}
		//
		//		customerRepository.save(user);
		return "redirect:/login";

	}

}
