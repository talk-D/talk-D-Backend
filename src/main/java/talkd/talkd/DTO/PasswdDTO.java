package talkd.talkd.DTO;

import lombok.*;
import talkd.talkd.Entity.MemberEntity;

@Getter
@Setter
@ToString
@NoArgsConstructor // 기본생성자
@AllArgsConstructor
public class PasswdDTO {
    private String currentPassword;
    private String newPassword;

    public static PasswdDTO toPasswdDTO(MemberEntity memberEntity){
        PasswdDTO passwd = new PasswdDTO();
        passwd.setCurrentPassword(memberEntity.getMemberPassword());
        return passwd;
    }
}
