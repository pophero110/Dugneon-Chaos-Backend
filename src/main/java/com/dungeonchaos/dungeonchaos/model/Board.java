package com.dungeonchaos.dungeonchaos.model;

import javax.persistence.*;

@Entity
@Table(name="board")
public class Board {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String boardJson;
}
