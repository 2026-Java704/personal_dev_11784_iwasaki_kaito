package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Medicine;
import com.example.demo.model.Account;
import com.example.demo.repository.MedicineRepository;

@Controller
public class MedicineController {
	private final MedicineRepository medicineRepository;
	private final Account account;

	public MedicineController(MedicineRepository medicineRepository,
			Account account) {
		this.medicineRepository = medicineRepository;
		this.account = account;
	}

	@GetMapping("main")
	public String main(Model model) {

		List<Medicine> medicines = medicineRepository.findByUserId(account.getId());

		model.addAttribute("medicines", medicines);

		return "main";
	}

	@GetMapping("/medicine/{id}/edit")
	public String edit(@PathVariable Integer id,
			Model model) {

		Medicine medicine = medicineRepository.findById(id).get();

		model.addAttribute("medicine", medicine);
		return "edit";
	}

	@PostMapping("/medicine/{id}/edit")
	public String update(
			@PathVariable Integer id,
			@RequestParam(defaultValue = "") String name,
			@RequestParam(defaultValue = "") Integer count,
			@RequestParam(defaultValue = "") String note) {
		Medicine medicine = medicineRepository.findById(id).get();

		medicine.setName(name);
		medicine.setCount(count);
		medicine.setNote(note);

		medicineRepository.save(medicine);
		return "redirect:/main";
	}

	@PostMapping("/medicine/{id}/delete")
	public String delete(@PathVariable Integer id) {

		medicineRepository.deleteById(id);
		return "redirect:/main";
	}

	@GetMapping("/medicine/add")
	public String create() {
		return "addmedicine";
	}

	@PostMapping("/items/add")
	public String store(
			@RequestParam(defaultValue = "") String name,
			@RequestParam(defaultValue = "") Integer count,
			@RequestParam(defaultValue = "") String note) {

		Medicine medicine = new Medicine(name, count, note);

		medicineRepository.save(medicine);

		return "redirect:/main";
	}

}
