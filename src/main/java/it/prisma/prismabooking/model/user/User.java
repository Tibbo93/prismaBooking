package it.prisma.prismabooking.model.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User {
    private String id;
    private String firstName;
    private String lastName;
    private UserRole role;
    private String email;
    private String telephoneNumber;
}
