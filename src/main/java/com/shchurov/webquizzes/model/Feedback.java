package com.shchurov.webquizzes.model;

import java.util.Objects;

public class Feedback {
    private boolean success;
    private String feedbacks;

    public Feedback() {
    }

    public Feedback(boolean success, String feedbacks) {
        this.success = success;
        this.feedbacks = feedbacks;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(String feedbacks) {
        this.feedbacks = feedbacks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Feedback)) return false;
        Feedback feedback = (Feedback) o;
        return isSuccess() == feedback.isSuccess() && getFeedbacks().equals(feedback.getFeedbacks());
    }

    @Override
    public int hashCode() {
        return Objects.hash(isSuccess(), getFeedbacks());
    }
}
