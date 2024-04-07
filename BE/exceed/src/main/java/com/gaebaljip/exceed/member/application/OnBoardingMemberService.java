package com.gaebaljip.exceed.member.application;

import com.gaebaljip.exceed.dto.response.OnBoardingMember;
import com.gaebaljip.exceed.member.adapter.out.persistence.MemberEntity;
import com.gaebaljip.exceed.member.application.port.in.OnBoardingMemberCommand;
import com.gaebaljip.exceed.member.application.port.in.OnBoardingMemberUsecase;
import com.gaebaljip.exceed.member.application.port.out.MemberPort;
import com.gaebaljip.exceed.member.domain.Member;
import com.gaebaljip.exceed.member.exception.InvalidAgeException;
import com.gaebaljip.exceed.member.exception.InvalidGenderException;
import com.gaebaljip.exceed.member.exception.InvalidHeightException;
import com.gaebaljip.exceed.member.exception.InvalidWeightException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OnBoardingMemberService implements OnBoardingMemberUsecase {

    public static final int MINIMUM_HEIGHT = 0;
    public static final int MINIMUM_WEIGHT = 0;
    public static final int MINIMUM_AGE = 0;

    private final MemberPort memberPort;
    private final MemberConverter memberConverter;

    @Override
    @Transactional
    public OnBoardingMember execute(OnBoardingMemberCommand command) {
        validateCommand(command);
        Member member = Member.create(command.height(), command.gender(), command.weight(), command.age(), command.activity());
        MemberEntity memberEntity = memberPort.command(memberConverter.toEntity(member, command.etc()));
        return OnBoardingMember.builder()
                .memberId(memberEntity.getId())
                .build();
    }

    private void validateCommand(OnBoardingMemberCommand command) {
        validateHeight(command.height());
        validateWeight(command.weight());
        validateAge(command.age());
        validGender(command.gender());
    }

    private void validGender(Integer gender) {
        if (gender < 0 || gender > 1) {
            throw InvalidGenderException.EXECPTION;
        }
    }

    private void validateHeight(Double height) {
        if (height <= MINIMUM_HEIGHT) {
            throw InvalidHeightException.EXECPTION;
        }
    }

    private void validateWeight(Double weight) {
        if (weight <= MINIMUM_WEIGHT) {
            throw InvalidWeightException.EXECPTION;
        }
    }

    private void validateAge(Integer age) {
        if (age <= MINIMUM_AGE) {
            throw InvalidAgeException.EXECPTION;
        }
    }
}
