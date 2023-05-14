package com.example.projekt.data;

import lombok.*;

@Data
@AllArgsConstructor
@Getter
@Setter
public class Information {
    private String id;
    private String name;
    private String descirption;

    private Category category;
}
