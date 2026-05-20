package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Medicine;
import com.example.demo.entity.User;
import com.example.demo.repository.MedicineRepository;
import com.example.demo.repository.userRepository;

@Controller
public class userController {
	private final userRepository userRepository;
	private final MedicineRepository medicineRepository;

	public userController(
			userRepository userRepository,
			MedicineRepository medicineRepository) {
		this.userRepository = userRepository;
		this.medicineRepository = medicineRepository;
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
		List<Medicine> medicines = medicineRepository.findByUserId(user.getId());

		model.addAttribute("medicines", medicines);
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

		return "login";

	}
}
