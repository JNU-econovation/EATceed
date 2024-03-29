package com.gaebaljip.exceed.member.application;

import com.gaebaljip.exceed.dto.request.SignUpMemberRequest;
import com.gaebaljip.exceed.member.adapter.out.persistence.MemberEntity;
import com.gaebaljip.exceed.member.application.port.in.CreateMemberUsecase;
import com.gaebaljip.exceed.member.application.port.out.MemberPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateMemberService implements CreateMemberUsecase {

    private final MemberPort memberPort;
    @Override
    public void execute(SignUpMemberRequest signUpMemberRequest) {
        if(!memberPort.findEmailOrChecked(signUpMemberRequest.email())){
            MemberEntity memberEntity = MemberEntity.builder()
                    .email(signUpMemberRequest.email())
                    .password(signUpMemberRequest.password())
                    .checked(false)
                    .build();
            memberPort.command(memberEntity);
        }
    }
}
