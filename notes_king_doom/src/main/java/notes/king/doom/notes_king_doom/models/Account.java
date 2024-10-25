package notes.king.doom.notes_king_doom.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "accounts")
@Getter @Setter 
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    @Column(unique = true,nullable=false,updatable=false)
    @NotEmpty(message="email must be filled")
    @Email(message = "please provide a valid email")
    @Size(min=7 ,message="email is too short")
    @Size(max=200 , message = "email is too long")
    private String email;
    @Column(unique = false,nullable=false,updatable=true)
    @Size(min = 5, max = 200, message = "Password must be between 5 and 200 characters")
    private String password;
    private String phone_number; 
    @OneToMany(mappedBy = "owner")
    private List<Note> notes;
}