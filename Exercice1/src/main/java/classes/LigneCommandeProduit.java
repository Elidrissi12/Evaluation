package classes;

import jakarta.persistence.*;

@Entity
public class LigneCommandeProduit {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    private Produit produit;

    @ManyToOne(optional = false)
    private Commande commande;

    private int quantite;

    // getters/setters
    public Integer getId() { return id; }
    public Produit getProduit() { return produit; }
    public void setProduit(Produit produit) { this.produit = produit; }
    public Commande getCommande() { return commande; }
    public void setCommande(Commande commande) { this.commande = commande; }
    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }
}
