package com.smart.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.dao.UserRepository;
import com.smart.entities.User;
import com.smart.helper.Message;

@Controller
@ComponentScan(basePackages = { "com.smart.controller" })
public class HomeController {

	
	// dao object to save user to database
	@Autowired
	private UserRepository userRepository;
	
	//injecting password encoder object
//	@Autowired
//	private BCryptPasswordEncoder bCryptPasswordEncoder;

//	@GetMapping("/test")
//	@ResponseBody
//	public String test() {
//		
//		User user = new User();
//		user.setName("Aditya");
//		user.setEmail("aditya@gmail.com");
//		
//		userRepository.save(user);
//		
//		return "working";
//	}

	// home handler
	@RequestMapping("/")
	public String home(Model model) { // model is used to send data to template(html files)

		model.addAttribute("title", "Home-Smart contact manager");
		return "home";
	}

	// about handler
	@RequestMapping("/about")
	public String about(Model model) { // model is used to send data to template(html files)

		model.addAttribute("title", "About-Smart contact manager");
		return "about";
	}

	// signup handler
	@RequestMapping("/signup")
	public String signup(Model model) { // model is used to send data to template(html files)
		model.addAttribute("title", "Sign up-Smart contact manager");
		model.addAttribute("user", new User());
		return "signup";
	}

	// register user handler
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String registerUser(@Valid @ModelAttribute("user") User user , BindingResult result1,
			@RequestParam(value = "agreement", defaultValue = "false") boolean agreement, Model model, HttpSession session ) {

		try {
			
			if (!agreement) {
				System.out.println("you have not agreed terms and conditions");
				throw new Exception("you have not agreed terms and conditions");
			}
			
			//server side validation of fields
			if(result1.hasErrors()) {
				System.out.println("ERROR"+result1.toString());
				model.addAttribute("user",user);
				return "signup";
			}

			user.setRole("NORMAL_USER");
			user.setEnabled(true);
			user.setImageUrl("default.png");
			user.setPassword(user.getPassword());

			System.out.println("Agreement" + agreement);
			System.out.println("user" + user);
			
			User result = this.userRepository.save(user);

			model.addAttribute("user", new User());
			session.setAttribute("message", new Message("Successfully Registered !!" ,"alert-success"));
			return "signup";
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			model.addAttribute("user", user);
			session.setAttribute("message", new Message("Something went wrong " +e.getMessage(),"alert-danger"));
			return "signup";
		}

		
	}

	//login handler
	@RequestMapping("/login")
	public String customLogin(Model model) {
		model.addAttribute("title", "Login-Smart contact manager");
		return "login";
	}
	
}