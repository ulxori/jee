package pl.edu.pg.eti.kask.lab.portrait.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Portrait {
    private Long id;
    private byte[] portrait;
}
