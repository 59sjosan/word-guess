package com.example.spring_word_guess;
import jakarta.persistence.*;
@Entity
@Table(name = "tbl_word")
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String word;
    private String hints;
    private String level;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getWord() {
        return word;
    }
    public void setWord(String word) {
        this.word = word;
    }
    public String getHints() {
        return hints;
    }
    public void setHints(String hints) {
        this.hints = hints;
    }
    public String getLevel() {
        return level;
    }
    public void setLevel(String level) {
        this.level = level;
    }
// Constructors, getters, and setters
}
