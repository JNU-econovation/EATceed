package com.gaebaljip.exceed.member.application;

import com.gaebaljip.exceed.member.application.port.in.CreateMemberCommand;
import com.gaebaljip.exceed.member.application.port.in.CreateMemberUsecase;
import com.gaebaljip.exceed.member.application.port.out.RecordMemberPort;
import com.gaebaljip.exceed.member.exception.InvalidAgeException;
import com.gaebaljip.exceed.member.exception.InvalidHeightException;
import com.gaebaljip.exceed.member.exception.InvalidWeightException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateMemberService implements CreateMemberUsecase {

    private final RecordMemberPort recordMemberPort;

    @Override
    public void createMember(CreateMemberCommand command) {
        validateCommand(command);
        recordMemberPort.save();
    }

    private void validateCommand(CreateMemberCommand command) {
        validateHeight(command.height());
        validateWeight(command.weight());
        validateAge(command.age());
    }

    private void validateHeight(Integer height) {
        if (height <= 0) {
            throw new InvalidHeightException("height must be greater than 0");
        }
    }

    private void validateWeight(Integer weight) {
        if (weight <= 0) {
            throw new InvalidWeightException("weight must be greater than 0");
        }
    }

    private void validateAge(Integer age) {
        if (age < 0) {
            throw new InvalidAgeException("age must be greater than 0");
        }
    }
}
