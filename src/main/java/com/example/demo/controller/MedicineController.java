package com.example.demo.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	private LocalDate date;

	public MedicineController(
			MedicineRepository medicineRepository,
			UserRepository userRepository,
			Account account) {
		this.medicineRepository = medicineRepository;
		this.userRepository = userRepository;
		this.account = account;
	}

	@GetMapping("/main")
	public String main(Model model) {

		List<Medicine> medicines = medicineRepository.findByUserIdOrderById(account.getId());

		if (account.getId() == null) {
			return "login";
		}
		model.addAttribute("medicines", medicines);

		return "main";
	}

	//更新画面表示
	@GetMapping("/medicine/{id}/edit")
	public String edit(@PathVariable Integer id,
			Model model) {

		if (account.getId() == null) {
			return "login";
		}

		Medicine medicine = medicineRepository.findById(id).get();

		if (medicine.getUser().getId() != account.getId()) {
			return "redirect:/main";
		}

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
			@RequestParam(defaultValue = "") String dtime,
			@RequestParam(defaultValue = "") String around) {

		Medicine medicine = medicineRepository.findById(id).get();

		if (medicine.getUser().getId() != account.getId()) {
			return "redirect:/login";
		}
		medicine.setName(name);
		medicine.setCount(count);
		medicine.setNote(note);
		medicine.setDtime(dtime);
		medicine.setAround(around);

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

		if (account.getId() == null) {
			return "login";
		}

		List<User> user = userRepository.findByName(account.getName());
		User User = user.get(0);
		model.addAttribute("user", User);
		return "medicineadd";
	}

	//add処理
	@PostMapping("/medicine/add")
	public String store(
			@RequestParam User userid,
			@RequestParam(defaultValue = " ") String name,
			@RequestParam(defaultValue = "") Integer count,
			@RequestParam(defaultValue = "") String note,
			@RequestParam(defaultValue = "false") Boolean mCheck,
			@RequestParam(defaultValue = "") String dtime,
			@RequestParam(defaultValue = "") String around,
			@RequestParam(defaultValue = "") Integer count2,
			Model model) {

		for (int i = 0; i < count2; i++) {
			Medicine medicine = new Medicine(name, count, note, mCheck, userid, dtime, around);
			medicineRepository.save(medicine);
		}
		return "redirect:/main";
	}

	@PostMapping("/medicine/{id}/check")

	public String check(
			@PathVariable Integer id,
			@RequestParam(defaultValue = "") String check,
			RedirectAttributes redirectAttributes) {

		Medicine medicine = medicineRepository.findById(id).get();
		if (check.equals("true")) {
			medicine.setCheck(true);
		} else {
			medicine.setCheck(false);
		}

		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd日HH時mm分");
		String currentTime = now.format(formatter);

		redirectAttributes.addFlashAttribute("clickedTime", currentTime);
		medicine.setMtime(currentTime);
		medicineRepository.save(medicine);

		return "redirect:/main";
	}

}
