package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Medicine;
import com.example.demo.entity.User;
import com.example.demo.model.Account;
import com.example.demo.repository.MedicineRepository;
import com.example.demo.repository.UserRepository;

@Controller
public class MedicineController {
	private final MedicineRepository medicineRepository;
	private final UserRepository userRepository;
	private final Account account;

	public MedicineController(
			MedicineRepository medicineRepository,
			UserRepository userRepository,
			Account account) {
		this.medicineRepository = medicineRepository;
		this.userRepository = userRepository;
		this.account = account;
	}

	@GetMapping("main")
	public String main(Model model) {

		List<Medicine> medicines = medicineRepository.findByUserIdOrderById(account.getId());

		model.addAttribute("medicines", medicines);

		return "main";
	}

	//更新画面表示
	@GetMapping("/medicine/{id}/edit")
	public String edit(@PathVariable Integer id,
			Model model) {

		Medicine medicine = medicineRepository.findById(id).get();

		model.addAttribute("medicine", medicine);
		return "edit";
	}

	//更新処理
	@PostMapping("/medicine/{id}/edit")
	public String update(
			@PathVariable Integer id,
			@RequestParam(defaultValue = "") String name,
			@RequestParam(defaultValue = "") Integer count,
			@RequestParam(defaultValue = "") String note,
			@RequestParam(defaultValue = "false") Boolean check) {

		Medicine medicine = medicineRepository.findById(id).get();

		medicine.setName(name);
		medicine.setCount(count);
		medicine.setNote(note);
		medicine.setCheck(check);

		medicineRepository.save(medicine);

		return "redirect:/main";

	}

	//削除処理
	@PostMapping("/medicine/{id}/delete")
	public String delete(@PathVariable Integer id) {

		medicineRepository.deleteById(id);
		return "redirect:/main";
	}

	//add画面表示
	@GetMapping("/medicine/add")
	public String create(
			Model model) {

		List<User> user = userRepository.findByName(account.getName());
		User User = user.get(0);
		model.addAttribute("user", User);
		return "medicineadd";
	}

	//add処理
	@PostMapping("/medicine/add")
	public String store(
			@RequestParam User userid,
			@RequestParam(defaultValue = "") String name,
			@RequestParam(defaultValue = "") Integer count,
			@RequestParam(defaultValue = "") String note,
			@RequestParam(defaultValue = "") Boolean mCheck,
			Model model) {

		Medicine medicine = new Medicine(name, count, note, mCheck, userid);

		medicineRepository.save(medicine);

		return "redirect:/main";
	}

	@PostMapping("/medicine/check")

	public String check(
			Model model) {

		return "redirect;/main";
	}

}
