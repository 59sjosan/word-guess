package com.example.spring_word_guess;

import com.example.spring_word_guess.Word;
import com.example.spring_word_guess.WordRepository;
import com.example.spring_word_guess.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GameController {

    private final UserRepository userRepository;
    private final WordRepository wordRepository;

    @Autowired
    public GameController(UserRepository userRepository, WordRepository wordRepository) {
        this.userRepository = userRepository;
        this.wordRepository = wordRepository;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("user", user);
            return "redirect:/wordForm";
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "index";
        }
    }

    @GetMapping("/wordForm")
    public String showForm(Model model) {
        model.addAttribute("levels", new String[]{"Easy", "Medium", "Hard"});
        model.addAttribute("selectedLevel", "");
        return "wordForm";
    }

    @PostMapping("/word")
    public String getWord(@ModelAttribute("selectedLevel") String selectedLevel, Model model, HttpSession session) {
        Word word = wordRepository.findRandomWordByLevel(selectedLevel);
        model.addAttribute("word", word);
        session.setAttribute("word", word);
        return "redirect:/showWord";
    }

    @GetMapping("/showWord")
    public String showWord(HttpSession session, Model model) {
        Word word = (Word) session.getAttribute("word");
        model.addAttribute("GivenHints", word.getHints());
        return "wordInput";
    }

    @PostMapping("/checkWord")
    public String checkWord(@RequestParam String word, HttpSession session, Model model) {
        Word currentWord = (Word) session.getAttribute("word");
        model.addAttribute("GivenHints", currentWord.getHints());
        if (word != null && currentWord.getWord().equalsIgnoreCase(word)) {
            model.addAttribute("message", "Congratulations! You win.");
        } else {
            model.addAttribute("message", "Sorry! You lose.");
        }
        return "wordInput";
    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/";
    }
}
