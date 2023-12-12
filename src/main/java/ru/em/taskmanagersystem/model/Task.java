package ru.em.taskmanagersystem.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.em.taskmanagersystem.common.PriorityEnum;
import ru.em.taskmanagersystem.common.StatusEnum;

import javax.persistence.*;
import java.util.Set;

@SuperBuilder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "header", nullable = false)
    private String header;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusEnum statusEnum;

    @Column(name = "priority", nullable = false)
    @Enumerated(EnumType.STRING)
    private PriorityEnum priorityEnum;

    @ManyToOne
    @JoinTable(name = "tasks_authors",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private User author;

    @ManyToOne
    @JoinTable(name = "tasks_employees",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private User employee;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tasks_comments",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "comment_id"))
    private Set<Comment> commentList;

}
