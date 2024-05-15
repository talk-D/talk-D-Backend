package talkd.talkd.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import talkd.talkd.DTO.MemberDTO;
import talkd.talkd.DTO.PasswdDTO;
import talkd.talkd.Entity.MemberEntity;
import talkd.talkd.Repository.MemberRepository;
import talkd.talkd.KakaoApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final KakaoApi kakaoApi;
    private final MemberRepository memberRepository;

    public void save(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.toSaveMemberEntity(memberDTO);
        memberRepository.save(memberEntity);
    }

    public MemberDTO login(MemberDTO memberDTO) {
        /*
            1. 회원이 입력한 이메일로 DB에서 조회를 함
            2. DB에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 판단
         */
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        if (byMemberEmail.isPresent()) {
            // 조회 결과가 있다(해당 이메일을 가진 회원 정보가 있다)
            MemberEntity memberEntity = byMemberEmail.get();
            if (memberEntity.getMemberPassword().equals(memberDTO.getMemberPassword())) {
                // 비밀번호 일치
                // entity -> dto 변환 후 리턴
                MemberDTO dto = MemberDTO.toMemberDTO(memberEntity);
                return dto;
            } else {
                // 비밀번호 불일치(로그인실패)
                return null;
            }
        } else {
            // 조회 결과가 없다(해당 이메일을 가진 회원이 없다)
            return null;
        }
    }
    public MemberDTO kakaoLoginCheck(String code){
        String accessToken = kakaoApi.getAccessToken(code);
        Map<String, Object> userInfo = kakaoApi.getUserInfo(accessToken);
        String email = (String)userInfo.get("email");
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(email);
        if (byMemberEmail.isPresent()) {
            // 조회 결과가 있다(해당 이메일을 가진 회원 정보가 있다)
            // entity -> dto 변환 후 리턴
            MemberEntity memberEntity = byMemberEmail.get();
            MemberDTO dto = MemberDTO.toMemberDTO(memberEntity);
            return dto;
        } else {
            // 조회 결과가 없다(해당 이메일을 가진 회원이 없다)
            return null;
        }
    }

    public List<MemberDTO> findAll() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();
        for (MemberEntity memberEntity: memberEntityList) {
            memberDTOList.add(MemberDTO.toMemberDTO(memberEntity));
//            MemberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity);
//            memberDTOList.add(memberDTO);
        }
        return memberDTOList;
    }

    public MemberDTO findById(Long id) {
        //객체 하나를 조회할 땐 optional로 감싸서 넘겨준다.
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
        if (optionalMemberEntity.isPresent()) {
//            MemberEntity memberEntity = optionalMemberEntity.get();
//            MemberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity);
//            return memberDTO;
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        } else {
            return null;
        }
    }

    public MemberDTO updateForm(String myEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(myEmail);
        if (optionalMemberEntity.isPresent()){
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        }else {
            return null;
        }
    }
    public void update(MemberDTO memberDTO) {
        memberRepository.save(MemberEntity.toUpdateMemberEntity(memberDTO));
    }

    // 비밀번호 확인 매서드

    public PasswdDTO passwordChange(String passwd) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberPassword(passwd);
        if (optionalMemberEntity.isPresent()){
            return PasswdDTO.toPasswdDTO(optionalMemberEntity.get());
        }else {
            return null;
        }
    }
    public MemberDTO passwordCheck(PasswdDTO passwd) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberPassword(String.valueOf(passwd.getCurrentPassword()));
        if (optionalMemberEntity.isPresent()){
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        }else {
            return null;
        }
    }

    public void change(MemberDTO memberDTO, PasswdDTO passwd) {
        memberRepository.save(MemberEntity.topwChangeMemberEntity(memberDTO, passwd));
        //memberRepository.save(MemberEntity.toUpdateMemberEntity(memberDTO));
    }


}
