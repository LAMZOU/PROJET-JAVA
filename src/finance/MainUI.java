package finance;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.ArrayList;


public class MainUI {
    private JFrame frame;
    private JTextArea textArea;
    private Budget budget;

    public MainUI() {
        frame = new JFrame("GesFin: Gestion de Finances Personnelles");
        textArea = new JTextArea(20, 50);
        textArea.setEditable(false);
        budget = new Budget(0); // Budget initial

        JPanel panel = new JPanel(new BorderLayout());

        // Boutons d'options
        JPanel buttonPanel = new JPanel();
        JButton btnDepense = new JButton("Enregistrer/Modifier une dépense");
        JButton btnRevenu = new JButton("Enregistrer/Modifier un revenu");
        JButton btnBudget = new JButton("Enregistrer/Modifier le budget");

        btnDepense.addActionListener(e -> gererTransaction("Dépense"));
        btnRevenu.addActionListener(e -> gererTransaction("Revenu"));
        btnBudget.addActionListener(e -> gererBudget());

        buttonPanel.add(btnDepense);
        buttonPanel.add(btnRevenu);
        buttonPanel.add(btnBudget);

        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        frame.getContentPane().add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        afficherResume();
    }

    private void gererTransaction(String type) {
        String[] options = {"Enregistrer", "Modifier"};
        int choix = JOptionPane.showOptionDialog(frame, "Voulez-vous enregistrer ou modifier une " + type.toLowerCase() + " ?", "Gestion " + type,
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (choix == 0) { // Enregistrer
            ajouterTransaction(type);
        } else if (choix == 1) { // Modifier
            modifierTransaction(type);
        }
    }

    private void ajouterTransaction(String type) {
        String description = JOptionPane.showInputDialog("Description :");
        if (description == null) return;

        String montantStr = JOptionPane.showInputDialog("Montant :");
        if (montantStr == null) return;
        double montant = Double.parseDouble(montantStr);

        String[] categories = {"Restauration", "Location", "Mission&Deplacement", "Loisirs", "Télécommunication","Formations","Autres"};
        String categorieStr = (String) JOptionPane.showInputDialog(frame, "Choisissez une catégorie :", "Catégorie",
                JOptionPane.QUESTION_MESSAGE, null, categories, categories[0]);
        if (categorieStr == null) return;
        Categorie categorie = new Categorie(categorieStr);

        Transaction transaction = new Transaction(montant, new Date(), description, categorie, type);
        budget.ajouterTransaction(transaction);

        textArea.append(type + " enregistrée : " + description + " - " + montant + " XOF\n");
        if (budget.depassementBudget()) {
            JOptionPane.showMessageDialog(frame, "Attention : Vous êtes en dépassement budgetaire !");
        }
        afficherResume();
    }

    private void modifierTransaction(String type) {
        // Filtrer les transactions du type spécifié
        java.util.List<Transaction> transactionsType = new ArrayList<>();
        for (Transaction t : budget.getTransactions()) {
            if (t.getType().equalsIgnoreCase(type)) {
                transactionsType.add(t);
            }
        }

        if (transactionsType.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Aucune " + type.toLowerCase() + " à modifier.");
            return;
        }

        String[] descriptions = new String[transactionsType.size()];
        for (int i = 0; i < transactionsType.size(); i++) {
            descriptions[i] = i + ": " + transactionsType.get(i).getDescription() + " - " + transactionsType.get(i).getMontant() + " XOF";
        }

        String choixStr = (String) JOptionPane.showInputDialog(frame, "Sélectionnez la " + type.toLowerCase() + " à modifier :", "Modifier " + type,
                JOptionPane.QUESTION_MESSAGE, null, descriptions, descriptions[0]);
        if (choixStr == null) return;
        int index = Integer.parseInt(choixStr.split(":")[0]);

        Transaction transaction = transactionsType.get(index);

        String nouvelleDescription = JOptionPane.showInputDialog("Nouvelle description :", transaction.getDescription());
        if (nouvelleDescription != null) transaction.setDescription(nouvelleDescription);

        String nouveauMontantStr = JOptionPane.showInputDialog("Nouveau montant :", transaction.getMontant());
        if (nouveauMontantStr != null) transaction.setMontant(Double.parseDouble(nouveauMontantStr));

        String[] categories = {"Restauration", "Location", "Mission&Deplacement", "Loisirs", "Télécommunication","Formations","Autres"};
        String nouvelleCategorie = (String) JOptionPane.showInputDialog(frame, "Choisissez une catégorie :", "Catégorie",
                JOptionPane.QUESTION_MESSAGE, null, categories, transaction.getCategorie().getNom());
        if (nouvelleCategorie != null) transaction.setCategorie(new Categorie(nouvelleCategorie));

        textArea.append(type + " modifiée : " + transaction.getDescription() + " - " + transaction.getMontant() + " XOF\n");
        afficherResume();
    }

    private void gererBudget() {
        String montantStr = JOptionPane.showInputDialog("Montant du budget :", budget.getMontantMax());
        if (montantStr == null) return;
        double montant = Double.parseDouble(montantStr);
        budget.setMontantMax(montant);

        textArea.append("Budget mis à jour : " + montant + " XOF\n");
        afficherResume();
    }

    private void afficherResume() {
        double totalDepenses = budget.totalDepenses();
        double totalRevenus = budget.totalRevenus();
        double solde = totalRevenus - totalDepenses;

        textArea.append("\n=== Résumé ===\n");
        textArea.append("Total des revenus : " + totalRevenus + " XOF\n");
        textArea.append("Total des dépenses : " + totalDepenses + " XOF\n");
        textArea.append("Solde : " + solde + " XOF\n");
        textArea.append("Budget : " + budget.getMontantMax() + " XOF\n");
        if (budget.depassementBudget()) {
            textArea.append("Attention : Vous avez dépassé votre budget !\n");
        }
        textArea.append("====================\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainUI::new);
    }
}
