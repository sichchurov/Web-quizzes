package com.shchurov.webquizzes.model;

import java.util.List;
import java.util.Objects;

public class Answer {

    private List<Integer> answers;

    public Answer(List<Integer> answersList) {
        this.answers = answersList;
    }

    public Answer() {
    }

    public List<Integer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Integer> answers) {
        this.answers = answers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Answer)) return false;
        Answer answer = (Answer) o;
        return getAnswers().equals(answer.getAnswers());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAnswers());
    }

    @Override
    public String toString() {
        return "Answer{" +
                "answers=" + answers +
                '}';
    }
}
