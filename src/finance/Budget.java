package finance;

import java.util.ArrayList;

public class Budget {
    private double montantMax;
    private ArrayList<Transaction> transactions;

    public Budget(double montantMax) {
        this.montantMax = montantMax;
        this.transactions = new ArrayList<>();
    }

    public void ajouterTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public void modifierTransaction(int index, Transaction transaction) {
        transactions.set(index, transaction);
    }

    public double totalDepenses() {
        return transactions.stream()
                .filter(t -> t.getType().equalsIgnoreCase("Dépense"))
                .mapToDouble(Transaction::getMontant)
                .sum();
    }

    public double totalRevenus() {
        return transactions.stream()
                .filter(t -> t.getType().equalsIgnoreCase("Revenu"))
                .mapToDouble(Transaction::getMontant)
                .sum();
    }

    public boolean depassementBudget() {
        return totalDepenses() > montantMax;
    }

    // Getters et Setters
    public double getMontantMax() { return montantMax; }
    public void setMontantMax(double montantMax) { this.montantMax = montantMax; }

    public ArrayList<Transaction> getTransactions() { return transactions; }
}
