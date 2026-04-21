package com.example.javaquizz;

import java.util.Scanner;

/**
 * Classe de test simple pour vérifier l'intégration avec l'API Trivia
 * Lancez cette classe pour tester manuellement l'API sans avoir à lancer l'application JavaFX
 */
public class TriviaAPITest {
    public static void main(String[] args) {
        try {
            System.out.println("📡 Connexion à l'API Trivia...");
            TriviaAPI.Question question = TriviaAPI.fetchQuestion();

            System.out.println("\n❓ Question:");
            System.out.println(question.questionText);
            System.out.println("\n📋 Réponses:");
            for (int i = 0; i < question.answers.length; i++) {
                System.out.println((i + 1) + ". " + question.answers[i]);
            }

            System.out.println("\n✅ Bonne réponse: " + question.correctAnswer);
            System.out.println("\nTest réussi! L'API fonctionne correctement.");
        } catch (Exception e) {
            System.err.println("❌ Erreur lors du test:");
            e.printStackTrace();
        }
    }
}

