package classes;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Employe {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable=false) private String nom;
    @Column(nullable=false) private String prenom;
    private String telephone;

    // Un employé peut être "chef" de plusieurs projets
    @OneToMany(mappedBy = "chefProjet")
    private List<Projet> projetsGerés = new ArrayList<>();

    // Lien vers les affectations (employé-tâche)
    @OneToMany(mappedBy = "employe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EmployeTache> affectations = new ArrayList<>();

    // getters/setters
    public Integer getId() { return id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
}
