package classes;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("F")
@NamedQuery(
        name = "Femme.marieesDeuxFoisOuPlus",
        query = """
      SELECT f FROM Femme f
      WHERE (SELECT COUNT(m) FROM Mariage m WHERE m.femme = f) >= 2
  """
)
public class Femme extends Personne { }
