package my.chat.app;

import javax.servlet.http.HttpSession;

import my.chat.app.data.MessageCache;
import my.chat.app.data.MyMessage;
import my.chat.app.data.User;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Configuration
@Controller
public class MyController {

	@Value("${default.user.name}")
	private String name;

	@Autowired
	private HttpSession httpSession;

	@Autowired
	private ProducerTemplate producerTemplate;

	@GetMapping("/")
	public String welcome(Model model) {
		User user = new User();
		user.setUserName(name);
		model.addAttribute("userObject", user);
		httpSession.setAttribute("userObject", user);
		return "welcome";
	}

	@PostMapping("/startChat")
	public ModelAndView startChat(@ModelAttribute User userObject) {
		User user = (User) httpSession.getAttribute("userObject");
		String userName = userObject.getUserName();
		user.setUserName(userName);
		return createResultObj(userName);
	}

	@GetMapping("/sendMessage")
	public ModelAndView display(Model model) {
		String userName = getUserName();
		return createResultObj(userName);
	}

	@PostMapping("/sendMessage")
	public ModelAndView sendMessage(@ModelAttribute MyMessage msg) {
		String userName = getUserName();
		String currMessage = msg.format(userName);
		producerTemplate.sendBody("direct:kafkaStart", currMessage);
		return createResultObj(userName);
	}

	@RequestMapping("/fetchPrevMsg")
	public @ResponseBody String fetchPrevMsg(){
		return MessageCache.getAllCachedMessage();
	}
	private ModelAndView createResultObj(String userName) {
		ModelAndView res = new ModelAndView("mychat");
		MyMessage msg = new MyMessage();
		res.addObject("msg", msg);
		res.addObject("name", userName);
		return res;
	}

	private String getUserName() {
		User user = (User) httpSession.getAttribute("userObject");
		if(null == user){
			throw new RuntimeException("Session expired");
		}
		String userName = user.getUserName();
		return userName;
	}
}
