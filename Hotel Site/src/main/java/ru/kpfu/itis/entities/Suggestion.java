package ru.kpfu.itis.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Suggestion {

    private Integer id;
    private String name;
    private User user;
    private String description;
    private Integer price;
    private String photoRefer;
}
