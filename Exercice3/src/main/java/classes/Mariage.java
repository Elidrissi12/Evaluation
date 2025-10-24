package classes;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@NamedNativeQuery(
        name = "Mariage.nbEnfantsFemmeEntre",
        query = """
    SELECT COALESCE(SUM(m.nbr_enfant),0) 
    FROM mariage m 
    WHERE m.femme_id = :fid 
      AND m.date_debut BETWEEN :d1 AND :d2
  """
)
public class Mariage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate dateDebut;
    private LocalDate dateFin;     // null => mariage en cours
    private int nbrEnfant;

    @ManyToOne(optional=false) private Homme homme;
    @ManyToOne(optional=false) private Femme femme;

    // getters/setters
    public Integer getId() { return id; }
    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }
    public LocalDate getDateFin() { return dateFin; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }
    public int getNbrEnfant() { return nbrEnfant; }
    public void setNbrEnfant(int n) { this.nbrEnfant = n; }
    public Homme getHomme() { return homme; }
    public void setHomme(Homme h) { this.homme = h; }
    public Femme getFemme() { return femme; }
    public void setFemme(Femme f) { this.femme = f; }
}
