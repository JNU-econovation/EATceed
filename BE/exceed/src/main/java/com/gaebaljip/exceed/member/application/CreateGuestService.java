package com.gaebaljip.exceed.member.application;

import com.gaebaljip.exceed.dto.response.CreateGuest;
import com.gaebaljip.exceed.member.adapter.out.persistence.MemberEntity;
import com.gaebaljip.exceed.member.application.port.in.CreateMemberCommand;
import com.gaebaljip.exceed.member.application.port.in.CreateGuestUsecase;
import com.gaebaljip.exceed.member.application.port.out.MemberPort;
import com.gaebaljip.exceed.member.domain.Guest;
import com.gaebaljip.exceed.member.exception.InvalidAgeException;
import com.gaebaljip.exceed.member.exception.InvalidGenderException;
import com.gaebaljip.exceed.member.exception.InvalidHeightException;
import com.gaebaljip.exceed.member.exception.InvalidWeightException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateGuestService implements CreateGuestUsecase {

    public static final int MINIMUM_HEIGHT = 0;
    public static final int MINIMUM_WEIGHT = 0;
    public static final int MINIMUM_AGE = 0;

    private final MemberPort memberPort;
    private final MemberConverter memberConverter;

    @Override
    @Transactional
    public CreateGuest execute(CreateMemberCommand command) {
        validateCommand(command);
        Guest guestModel = Guest.create(command.height(), command.gender(), command.weight(), command.age(), command.activity());
        MemberEntity memberEntity = memberPort.command(memberConverter.toEntity(guestModel, command.etc()));
        return CreateGuest.builder()
                .loginId(memberEntity.getLoginId())
                .password(guestModel.getPassword())
                .memberId(memberEntity.getId())
                .build();
    }

    private void validateCommand(CreateMemberCommand command) {
        validateHeight(command.height());
        validateWeight(command.weight());
        validateAge(command.age());
        validGender(command.gender());
    }

    private void validGender(Integer gender) {
        if (gender < 0 || gender > 1) {
            throw new InvalidGenderException();
        }
    }

    private void validateHeight(Double height) {
        if (height <= MINIMUM_HEIGHT) {
            throw new InvalidHeightException();
        }
    }

    private void validateWeight(Double weight) {
        if (weight <= MINIMUM_WEIGHT) {
            throw new InvalidWeightException();
        }
    }

    private void validateAge(Integer age) {
        if (age <= MINIMUM_AGE) {
            throw new InvalidAgeException();
        }
    }
}
