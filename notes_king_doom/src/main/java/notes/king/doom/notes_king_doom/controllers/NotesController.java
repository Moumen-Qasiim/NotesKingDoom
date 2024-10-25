package notes.king.doom.notes_king_doom.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import notes.king.doom.notes_king_doom.models.Account;
import notes.king.doom.notes_king_doom.models.Note;
import notes.king.doom.notes_king_doom.repo.NotesRepo;

@Controller
@RequestMapping({"","/","notes"})
public class NotesController {

    @Autowired
    private HttpSession session;

    @Autowired
    private NotesRepo repo;
    @GetMapping({"","/","index"})
    public String Index(Model model)
    {
        Account acc =(Account) session.getAttribute("user");
        if(acc == null)
            return "redirect:/accounts/login";
        List<Note> notes = repo.findByOwner(acc);
        model.addAttribute("notes",notes);
        return "/notes/Index";
    }

    @GetMapping("/add")
    public String addNote(Model model)
    {
        model.addAttribute("note", new Note());
        return "/notes/add";
    }
    @PostMapping("/add")
    public String AddNote(@Valid @ModelAttribute("note") Note note,BindingResult result)
    {
        note.setId(0);
        if(!result.hasErrors())
        {
            Account acc =(Account) session.getAttribute("user");
            if(acc == null)
                return "redirect:/accounts/login";
            note.setOwner(acc);
            repo.save(note);
            return "redirect:index";
        }
        return  "/notes/add";
        
    }
    @GetMapping("/update/{id}")
    public String UpdateNote(@PathVariable("id") long id,Model model)
    {
        
        Account acc =(Account) session.getAttribute("user");
        if(acc == null)
            return "redirect:/accounts/login";
        Optional<Note> NoteOptional = repo.findById(id);
        List<Note> notes = repo.findByOwner(acc);
        if(!NoteOptional.isEmpty() & notes.contains(NoteOptional.get()))
        {
            model.addAttribute("note", NoteOptional.get());
            return "/notes/update";
        }
        return "/notes/add";
    }
    @PostMapping("/update")
    public String UpdateNote(Note note,BindingResult result)
    {
        if(!result.hasErrors())
        {

            Account acc =(Account) session.getAttribute("user");
            if(acc == null)
                return "redirect:/accounts/login";
            Optional<Note> NoteOptional = repo.findById(note.getId());
            List<Note> notes = repo.findByOwner(acc);
            if(!NoteOptional.isEmpty() & notes.contains(NoteOptional.get()))
            {
                note.setOwner(acc);
                repo.save(note);
                return "redirect:index";
            }   
        }
        return "/notes/update";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") long id)
    {
        //check if the note is for the user
        //delete
        Account acc =(Account) session.getAttribute("user");
            if(acc == null)
                return "redirect:/accounts/login";
            Optional<Note> NoteOptional = repo.findById(id);
            List<Note> notes = repo.findByOwner(acc);
            if(!NoteOptional.isEmpty() & notes.contains(NoteOptional.get()))
            {
                Note note = NoteOptional.get();
                note.setOwner(acc);
                repo.delete(note);
            }

        return "redirect:/notes/index";
    }
    
}