package com.shchurov.webquizzes.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String title;
    @NotEmpty
    private String text;
    @Size(min = 2)
    @NotNull
    @ElementCollection
    private List<String> options;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ElementCollection
    private List<Integer> answer;

    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User user;

    public User getUser() {
        return user;
    }

    public Quiz() {
    }

    public Quiz(String title, String text, @Size(min = 2) @NotNull List<String> options, List<Integer> answer, User user) {
        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = answer == null ? new ArrayList<>() : answer;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public List<Integer> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Integer> answer) {
        this.answer = answer;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Quiz)) return false;

        Quiz quiz = (Quiz) o;

        if (!getId().equals(quiz.getId())) return false;
        if (!getTitle().equals(quiz.getTitle())) return false;
        if (!getText().equals(quiz.getText())) return false;
        if (!getOptions().equals(quiz.getOptions())) return false;
        if (!getAnswer().equals(quiz.getAnswer())) return false;
        return getUser().equals(quiz.getUser());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getTitle().hashCode();
        result = 31 * result + getText().hashCode();
        result = 31 * result + getOptions().hashCode();
        result = 31 * result + getAnswer().hashCode();
        result = 31 * result + getUser().hashCode();
        return result;
    }
}
