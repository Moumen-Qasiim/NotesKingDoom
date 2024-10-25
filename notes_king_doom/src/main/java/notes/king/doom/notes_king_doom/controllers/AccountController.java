package notes.king.doom.notes_king_doom.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import notes.king.doom.notes_king_doom.models.Account;
import notes.king.doom.notes_king_doom.repo.AccountRepo;
import notes.king.doom.notes_king_doom.services.AccountServices;

@Controller
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    private AccountRepo repo;
    @Autowired
    private AccountServices service;

    @GetMapping("/create")
    public String createAccount(Model model)
    {
        model.addAttribute("account",new Account());
        return "/accounts/create";
    }
    @PostMapping("/create")
    public String createAccount(@Valid @ModelAttribute Account account,  BindingResult result)
    {   
        if(service.isValidSignUp(account,result))
        {
            try 
            {
                repo.save(account);
                return "redirect:/accounts/login";
    
            } catch (Exception e)
            {
              service.report();  
            }
        }
        return "/accounts/create";
    }

    @GetMapping("/login")
    public String login(Model model,HttpSession session)
    {
        
        var account = (Account) session.getAttribute("user");
        if(account != null)
        {
            return "redirect:/notes/index";
        }
        model.addAttribute("account",new Account());
        return "/accounts/login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("account") Account account,BindingResult result, HttpSession session)
    {
        var foundaccount = service.isValidLogin(account,result);
        if(foundaccount != null)
        {
            session.setAttribute("user", foundaccount);
            return "redirect:/notes/index";
        }
        return "/accounts/login";
        
    }
    @GetMapping("logout")
    public String logout(HttpSession session)
    {
        session.removeAttribute("user");
        return "redirect:/accounts/login";
    }
 
}