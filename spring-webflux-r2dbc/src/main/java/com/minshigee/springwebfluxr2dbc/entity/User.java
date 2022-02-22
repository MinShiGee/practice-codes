package com.minshigee.springwebfluxr2dbc.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@ToString
@Table("User")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    Long id;
    @Column()
    String name;
    @Column()
    String email;
    @Column()
    String phoneNumber;
}
