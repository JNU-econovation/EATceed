package com.gaebaljip.exceed.member.application;

import com.gaebaljip.exceed.member.application.port.in.CreateMemberCommand;
import com.gaebaljip.exceed.member.domain.Activity;
import com.gaebaljip.exceed.member.exception.InvalidAgeException;
import com.gaebaljip.exceed.member.exception.InvalidHeightException;
import com.gaebaljip.exceed.member.exception.InvalidWeightException;
import com.gaebaljip.exceed.member.exception.MemberExceptionHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;

@Import(MemberExceptionHandler.class)
@ExtendWith(MockitoExtension.class)
class CreateMemberServiceTest {

    @InjectMocks
    private CreateMemberService createMemberService;

    private static CreateMemberCommand createMemberCommand(double height, double weight, int age) {
        CreateMemberCommand command = CreateMemberCommand.builder()
                .height(height)
                .weight(weight)
                .age(age)
                .activity(Activity.NOT_ACTIVE)
                .etc("뭐든 잘 먹습니다.")
                .build();
        return command;
    }
    @Test
    @DisplayName("몸무게가 0 이하일 경우 예외가 발생한다.")
    void createMember_weight() {
        //given
        CreateMemberCommand command = createMemberCommand(0, 61, 25);

        //when then
        Assertions.assertThrows(InvalidHeightException.class, () -> {
            createMemberService.execute(command);
        });
    }

    @Test
    @DisplayName("키가 0 이하일 경우 예외가 발생한다.")
    void createMember_height() {
        //given
        CreateMemberCommand command = createMemberCommand(171, 0, 25);

        //when then
        Assertions.assertThrows(InvalidWeightException.class, () -> {
            createMemberService.execute(command);
        });
    }

    @Test
    @DisplayName("나이가 0 이하일 경우 예외가 발생한다.")
    void createMember_age() {
        //given
        CreateMemberCommand command = createMemberCommand(171, 61, 0);

        //when then
        Assertions.assertThrows(InvalidAgeException.class, () -> {
            createMemberService.execute(command);
        });
    }
}