package entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    private String text;

    @Column()
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
