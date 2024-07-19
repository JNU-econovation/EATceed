package com.gaebaljip.exceed.application.service.member;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaebaljip.exceed.application.domain.member.MemberEntity;
import com.gaebaljip.exceed.application.port.in.member.OnBoardingMemberCommand;
import com.gaebaljip.exceed.application.port.in.member.OnBoardingMemberQuery;
import com.gaebaljip.exceed.application.port.in.member.OnBoardingMemberUsecase;
import com.gaebaljip.exceed.application.port.out.member.MemberPort;
import com.gaebaljip.exceed.common.exception.member.InvalidAgeException;
import com.gaebaljip.exceed.common.exception.member.InvalidHeightException;
import com.gaebaljip.exceed.common.exception.member.InvalidWeightException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OnBoardingMemberService implements OnBoardingMemberUsecase {

    public static final int MINIMUM_HEIGHT = 0;
    public static final int MINIMUM_WEIGHT = 0;
    public static final int MINIMUM_AGE = 0;

    private final MemberPort memberPort;

    @Override
    @Transactional
    public void execute(OnBoardingMemberCommand command) {
        validateCommand(command);
        MemberEntity memberEntity = memberPort.query(command.memberId());
        memberEntity.updateMember(
                command.height(),
                command.gender(),
                command.age(),
                command.activity(),
                command.etc(),
                command.weight(),
                command.targetWeight());
    }

    private void validateCommand(OnBoardingMemberCommand command) {
        validateHeight(command.height());
        validateWeight(command.weight());
        validateTargetWeight(command.targetWeight());
        validateAge(command.age());
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

    private void validateTargetWeight(Double targetWeight) {
        if (targetWeight <= MINIMUM_WEIGHT) {
            throw InvalidWeightException.EXECPTION;
        }
    }

    private void validateAge(Integer age) {
        if (age <= MINIMUM_AGE) {
            throw InvalidAgeException.EXECPTION;
        }
    }

    @Override
    public boolean checkOnBoarding(OnBoardingMemberQuery query) {
        MemberEntity memberEntity = memberPort.query(query.memberId());
        return memberEntity.checkOnBoarding();
    }
}
