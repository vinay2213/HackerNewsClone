package com.hackernews.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hackernews.entity.Content;
import com.hackernews.entity.User;
import com.hackernews.repository.ContentRepositroy;
import com.hackernews.repository.UserRepository;
import com.hackernews.service.ContentService;


@Controller
public class ContentController {
	
	@Autowired
	ContentService contentService;
	@Autowired
	ContentRepositroy contentRepositroy;
	@Autowired
	UserRepository userRepository;
	
	public User getUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User author = userRepository.getUserByUserName(username);
        return author;
	}
	
	@GetMapping("/submit")
	public String newContent() {
		return "newContent";
	}
	
	@PostMapping("/addcontent")
	public String addPost(@ModelAttribute Content content) {
		User user = getUser();
		contentService.addContent(content,user);
		return "redirect:/";
	}
	@GetMapping("/ask")
	public String askContent(Model model) {
		List<Content> contents = contentRepositroy.getAllAsk();
		User user = getUser();
		model.addAttribute("contents",contents);
		model.addAttribute("user",user);
		return "showAsk";
	}
	@GetMapping("/show")
	public String showContent(Model model) {
		List<Content> contents = contentRepositroy.getAllShow();
		User user = getUser();
		model.addAttribute("contents",contents);
		model.addAttribute("user",user);
		return "showAsk";
	}
	@GetMapping("/job")
	public String jobContent(Model model) {
		List<Content> contents = contentRepositroy.getAllJob();
		User user = getUser();
		model.addAttribute("contents",contents);
		model.addAttribute("user",user);
		return "job";
	}
	@GetMapping("/newest")
	public String sortedPostFromLatestDate(Model model) {
		List<Content> sortedList = contentRepositroy.findAll(Sort.by(Sort.Order.desc("submitTime")));
		User user = getUser();
		model.addAttribute("contents", sortedList);
		model.addAttribute("user",user);
		return "home";
	}

	@GetMapping("/front")
	public String sortedPostFromPreviousDate(Model model, @RequestParam(value="day", required = false) String day) {
		DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		User user = getUser();
		
		if(day == null){
			LocalDate currentDate = LocalDate.now();
			LocalDate previousDay = currentDate.minusDays(1);
			day = previousDay.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}
		LocalDate ld = LocalDate.parse(day, DATEFORMATTER);

		LocalDateTime startOfDay = ld.atStartOfDay();
		LocalDateTime endOfDay = ld.atTime(LocalTime.MAX);
		List<Content> sortedList = contentRepositroy.findAllBySubmitTimeBetween(startOfDay, endOfDay);
		model.addAttribute("contents", sortedList);
		model.addAttribute("date",ld);
		model.addAttribute("user",user);
		
		return "past";
	}

	@GetMapping("/search")
	public String searchBlogPosts(@RequestParam(name = "q", required = false) String query,
								  Model model) {
		
		User user = getUser();
		Set<Content> searchResults = contentService.findContentsByTitle(query);
		System.out.println(searchResults.size());
		model.addAttribute("contents", searchResults);
		model.addAttribute("user",user);
		model.addAttribute("query", query);
		return "searchResults";
	}
	 @PostMapping("/search")
	    public String filterContent(
	            @RequestParam(name = "catagory", required = false) String catagory,
	            @RequestParam(name = "q") String query,
	            Model model
	    ) {
	        // Implement filtering logic using category, sort, and timeframe
	        // Query the contentRepository based on the filter criteria
		 	User user = getUser();
	        Set<Content> searchResults = contentRepositroy.findFilteredContent(catagory, query);
	        System.out.println(searchResults.size()+" "+catagory);
	        model.addAttribute("contents", searchResults);
	        model.addAttribute("query", query);
	        model.addAttribute("user",user);
	        return "searchResults"; // The view to display filtered content
	    }
}
