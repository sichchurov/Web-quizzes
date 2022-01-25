package com.shchurov.webquizzes.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Completed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long idCompleted;

    private Long id;

    @Column(name="completed_At")
    @JsonFormat(pattern = "YYYY-mm-dd HH:mm:ss")
    private LocalDateTime completedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User user;

    public Completed(Long id, LocalDateTime completedAt, User user) {
        this.id = id;
        this.completedAt = completedAt;
        this.user = user;
    }

    public Completed() {
    }

    public Long getIdCompleted() {
        return idCompleted;
    }

    public void setIdCompleted(Long idCompleted) {
        this.idCompleted = idCompleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Completed)) return false;
        Completed completed = (Completed) o;
        return getIdCompleted().equals(completed.getIdCompleted()) && getId().equals(completed.getId()) && getCompletedAt().equals(completed.getCompletedAt()) && getUser().equals(completed.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdCompleted(), getId(), getCompletedAt(), getUser());
    }
}
