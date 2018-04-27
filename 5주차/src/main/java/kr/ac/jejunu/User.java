package kr.ac.jejunu;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class User {
    private Integer id;
    private String name;
    private String password;
}
