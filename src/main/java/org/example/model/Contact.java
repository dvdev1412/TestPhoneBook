package org.example.model;
import lombok.Data;
import org.springframework.stereotype.Component;


@Component
@Data
public class Contact {

    private String fullName;
    private String phoneName;
    private String email;


}

