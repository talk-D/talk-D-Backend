package talkd.talkd.DTO;

import lombok.*;
import talkd.talkd.Entity.BoardEntity;
import talkd.talkd.Entity.MemberEntity;

@Getter
@Setter
@ToString
@NoArgsConstructor // 기본생성자
@AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자
public class MemberDTO {
    private Long memberId;
    private String memberEmail;
    private String memberPassword;
    private String memberName;
    private String memberNickname;
    private String memberIntroduction;

    public static MemberDTO toMemberDTO(MemberEntity memberEntity){
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDTO.setMemberName(memberEntity.getMemberName());
        memberDTO.setMemberNickname(memberEntity.getMemberNickname());
        memberDTO.setMemberPassword(memberEntity.getMemberPassword());
        memberDTO.setMemberIntroduction(memberEntity.getMemberIntroduction());
        return memberDTO;
    }
}
