package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.User;
import com.example.demo.model.Account;
import com.example.demo.repository.MedicineRepository;
import com.example.demo.repository.UserRepository;

@Controller
public class userController {
	private final UserRepository userRepository;
	private final MedicineRepository medicineRepository;
	private final Account account;
	private final HttpSession session;

	public userController(
			HttpSession session,
			UserRepository userRepository,
			MedicineRepository medicineRepository,
			Account account) {
		this.userRepository = userRepository;
		this.medicineRepository = medicineRepository;
		this.account = account;
		this.session = session;
	}

	@GetMapping({ "/login", "/logout", "/" })
	public String index() {
		session.invalidate();
		return "login";

	}

	@PostMapping("/login")
	public String index(
			@RequestParam(defaultValue = "") String name,
			@RequestParam(defaultValue = "") String password,
			Model model) {

		if (name.length() == 0 || password.length() == 0) {
			model.addAttribute("message", "未入力の項目があります。");
			return "/login";
		}

		List<User> usersList = userRepository.findByNameAndPassword(name, password);
		if (usersList == null || usersList.size() == 0) {
			model.addAttribute("message", "メールアドレスとパスワードが一致しませんでした");
			return "/login";

		}
		User user = usersList.get(0);

		account.setId(user.getId());
		account.setName(user.getName());

		return "redirect:/main";
	}

	@GetMapping("/account")
	public String store() {
		return "userForm";
	}

	@PostMapping("/account")
	public String store(
			@RequestParam(defaultValue = "") String name,
			@RequestParam(defaultValue = "") String password,
			Model model) {
		List<User> usersList = userRepository.findByNameAndPassword(name, password);
		List<String> errorList = new ArrayList<>();
		if (name.length() == 0) {
			errorList.add("名前は必須です");
		}
		if (password.length() == 0) {
			errorList.add("パスワードは必須です");
		}
		if (usersList.size() > 0)
			errorList.add("登録済みのユーザーです");

		if (errorList.size() > 0) {
			model.addAttribute("errorList", errorList);
			model.addAttribute("name", name);
			return "userForm";
		}

		User user = new User(name, password);

		userRepository.save(user);

		return "login";

	}
}
