package com.qa.app.utilities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserswithLombok {
    private int id;
    private String name;
    private String email;
    private String status;
    private String gender;
}
