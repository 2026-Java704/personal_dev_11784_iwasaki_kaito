package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.User;
import com.example.demo.repository.userRepository;

@Controller
public class userController {
	private final userRepository userRepository;

	public userController(userRepository userRepository) {
		this.userRepository = userRepository;
	}

	@GetMapping("/login")
	public String index() {
		return "login";

	}

	@PostMapping("/login")
	public String index(
			@RequestParam(defaultValue = "") String name,
			@RequestParam(defaultValue = "") String password,
			Model model) {

		List<String> errorList = new ArrayList<>();
		if (name.length() == 0) {
			errorList.add("名前は必須です");
		}
		if (password.length() == 0) {
			errorList.add("パスワードは必須です");
		}

		if (errorList.size() > 0) {
			model.addAttribute("errorList", errorList);
			return "redirect:/login";
		}

		List<User> usersList = userRepository.findByNameAndPassword(name, password);

		if (usersList == null) {
			model.addAttribute("errorList", "メールアドレスとパスワードが一致しませんでした");
			return "redirect:/login";
		}
		return "main";
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

		List<String> errorList = new ArrayList<>();
		if (name.length() == 0) {
			errorList.add("名前は必須です");
		}
		if (password.length() == 0) {
			errorList.add("パスワードは必須です");
		}

		if (errorList.size() > 0) {
			model.addAttribute("errorList", errorList);
			return "redirect:/account";
		}

		User user = new User(name, password);

		if (errorList.size() > 0) {
			model.addAttribute("errorList", errorList);
			return "accountForm";
		}
		userRepository.save(user);

		return "main";

	}
}
