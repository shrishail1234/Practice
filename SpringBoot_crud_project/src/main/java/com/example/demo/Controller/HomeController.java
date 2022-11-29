package com.example.demo.Controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.example.demo.Entity.Products;
import com.example.demo.Repository.ProductRepository;

import antlr.collections.List;

@Controller
public class HomeController {
	@Autowired
	private ProductRepository productRepo;

	@GetMapping("/index")
	public String home(Model m) {

		ArrayList<Products> list = (ArrayList<Products>) productRepo.findAll();
		m.addAttribute("all_products", list);

		return "index";

	}

	@GetMapping("/load_form")
	public String loadForm() {
		return "add";
	}

	@GetMapping("/edit_form/{id}")
	public String editForm(@PathVariable(value = "id") int id, Model m) {

		Optional<Products> product = productRepo.findById((long) id);
		Products pro = product.get();
		m.addAttribute("product", pro);

		return "edit";
	}

	@PostMapping("/save_products")
	public String saveProduct(@ModelAttribute Products product, HttpSession session) {

		productRepo.save(product);
		session.setAttribute("product Added Successfully.......!", session);

		return "redirect:/load_form";

	}

	@PostMapping("/update_products")
	public String updateProduct(@ModelAttribute Products product, HttpSession session) {

		productRepo.save(product);
		session.setAttribute("product updated Successfully.......!", session);

		return "redirect:/index";

	}

	@GetMapping("/delete/{id}")
	public String delete_products(@PathVariable(value = "id") long id, HttpSession session) {

		productRepo.deleteById(id);
		session.setAttribute("msg", "product deleted successfully...!");
		return "redirect:/index";

	}
}
