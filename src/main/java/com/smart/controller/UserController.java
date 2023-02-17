package com.smart.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.smart.dao.UserRepository;
import com.smart.entities.Contact;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	// method to add common data to response
	public void addCommonData(Model model, Principal principal) {
		String userName = principal.getName();

		 //get user using username(mail)
		com.smart.entities.User user = userRepository.getUserByUserName(userName);
		model.addAttribute("user",user);
		
	}

	@RequestMapping("index")
	public String dashboard() {

		return "normal/userdashboard";
	}

	// open add contact handler
	@GetMapping("/addcontact")
	public String openAddContactForm(Model model) {

		model.addAttribute("title", "Add Contact");
		model.addAttribute("contact", new Contact());
		return "normal/add_contact_form";

	}

	// processing add contact handler
	@PostMapping("/process-contact")
	public String processContact(@ModelAttribute Contact contact,Principal principal) {
//		
//		String name = principal.getName();
//		com.smart.entities.User user = this.userRepository.getUserByUserName(name);
//		user.getContacts().add(contact);
//		
//		this.userRepository.save(user);
		
		System.out.println("Data"+contact);
		return "normal/add_contact_form";
	}
}