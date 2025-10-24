package classes;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class EmployeTache {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    private Employe employe;

    @ManyToOne(optional = false)
    private Tache tache;

    // Dates réelles
    private LocalDate dateDebutReelle;
    private LocalDate dateFinReelle;

    // getters/setters
    public Integer getId() { return id; }
    public Employe getEmploye() { return employe; }
    public void setEmploye(Employe employe) { this.employe = employe; }
    public Tache getTache() { return tache; }
    public void setTache(Tache tache) { this.tache = tache; }
    public LocalDate getDateDebutReelle() { return dateDebutReelle; }
    public void setDateDebutReelle(LocalDate d) { this.dateDebutReelle = d; }
    public LocalDate getDateFinReelle() { return dateFinReelle; }
    public void setDateFinReelle(LocalDate d) { this.dateFinReelle = d; }
}
