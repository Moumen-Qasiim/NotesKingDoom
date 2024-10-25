package notes.king.doom.notes_king_doom.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import notes.king.doom.notes_king_doom.models.Account;



public interface  AccountRepo extends JpaRepository<Account, Long> {
   public Account findByEmail(String email);


  
}