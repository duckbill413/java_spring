package spring2.zenoinstagram.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DeleteUserReq {
    private String email;
    private String password;
}
