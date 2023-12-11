package com.ufncc.workorder.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String clinicNumber;

    @Column(nullable = false)
    private LocalDate creationDate;

    @Column(nullable = false, length = 100)
    private String boxNumber;

    @Column(nullable = false, length = 100)
    private String equipment;

    @Column(nullable = false, length = 2000)
    private String problem;

    @Column(nullable = false, length = 2000)
    private String solution;

    @ManyToOne
    @JoinColumn(name="fk_userID", nullable=false)
    private User user;

    public Order(String clinicNumber, LocalDate creationDate, String boxNumber, String equipment, String problem, String solution, User user) {
        this.clinicNumber = clinicNumber;
        this.creationDate = creationDate;
        this.boxNumber = boxNumber;
        this.equipment = equipment;
        this.problem = problem;
        this.solution = solution;
        this.user = user;
    }

    public Order(User user, LocalDate creationDate) {
        this.user = user;
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", clinicNumber='" + clinicNumber + '\'' +
                ", creationDate=" + creationDate +
                ", boxNumber='" + boxNumber + '\'' +
                ", equipment='" + equipment + '\'' +
                ", problem='" + problem + '\'' +
                ", solution='" + solution + '\'' +
                ", user=" + user +
                '}';
    }
}
