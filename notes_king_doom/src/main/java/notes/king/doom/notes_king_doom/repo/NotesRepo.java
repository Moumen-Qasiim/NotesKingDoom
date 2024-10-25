package notes.king.doom.notes_king_doom.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import notes.king.doom.notes_king_doom.models.Account;
import notes.king.doom.notes_king_doom.models.Note;


public interface  NotesRepo extends JpaRepository<Note, Long> {
  public List<Note> findByOwner(Account owner);
    
}