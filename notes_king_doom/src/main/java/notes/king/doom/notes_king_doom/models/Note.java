package notes.king.doom.notes_king_doom.models;


import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name="notes")
@Data
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable=false)
    private long id;
    @Column(length=1000,nullable=false,updatable=true,columnDefinition = "TEXT")
    @NotEmpty
    @Size(min=5,message = "Note must be at least 5 charchters")
    @Size(max=1000,message = "Note  can't exced 1000 charchters")
    private String message;
    @Column(nullable=false,updatable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at;
    private String card_color = "#ffffff";
    private String text_color = "#000000";
    @ManyToOne
    private Account owner;

    @PrePersist
    protected void onCreate() {
        created_at = new Date();
    }
}