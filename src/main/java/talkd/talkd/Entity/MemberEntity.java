package talkd.talkd.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import talkd.talkd.DTO.MemberDTO;

@Entity
@Getter
@Setter
@Table(name = "member_table")
public class MemberEntity {
    @Id // pk 컬럼 지정. 필수
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment를 적용한
    private Long memberId;

    @Column // 크기 255
    private String memberEmail;

    @Column(nullable = false) // 크기 20, not null
    private String memberName;

    @Column // 크기 255
    private String memberNickname;

    @Column// 크기 255
    private String memberPassword;

    @Column(nullable = true)
    private String memberIntroduction;

    public static MemberEntity toSaveMemberEntity(MemberDTO memberDTO){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberNickname(memberDTO.getMemberNickname());
        return memberEntity;
    }
    public static MemberEntity toUpdateMemberEntity(MemberDTO memberDTO){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberId(memberDTO.getMemberId());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberNickname(memberDTO.getMemberNickname());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberIntroduction(memberDTO.getMemberIntroduction());
        return memberEntity;
    }
}
