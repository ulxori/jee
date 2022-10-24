package pl.edu.pg.eti.kask.lab.opinion.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.lab.opinion.entity.Opinion;

import java.util.function.Function;

@Builder
@Setter
@Getter
public class OpinionViewModel {
    private String content;
    private String userName;
    private String email;

    public static Function<Opinion, OpinionViewModel> entityToModelMapper() {
        return  opinion -> OpinionViewModel.builder()
                .content(opinion.getContent())
                .userName(opinion.getUser().getUserName())
                .email(opinion.getUser().getEmail())
                .build();
    }
}
