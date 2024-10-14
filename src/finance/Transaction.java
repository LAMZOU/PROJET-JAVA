package finance;

import java.util.Date;

public class Transaction {
    private double montant;
    private Date date;
    private String description;
    private Categorie categorie;
    private String type; // "Revenu" ou "Dépense"

    public Transaction(double montant, Date date, String description, Categorie categorie, String type) {
        this.montant = montant;
        this.date = date;
        this.description = description;
        this.categorie = categorie;
        this.type = type;
    }

    // Getters et Setters
    public double getMontant() { return montant; }
    public void setMontant(double montant) { this.montant = montant; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Categorie getCategorie() { return categorie; }
    public void setCategorie(Categorie categorie) { this.categorie = categorie; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}
