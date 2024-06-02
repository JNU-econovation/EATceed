package com.gaebaljip.exceed.member.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;

import com.gaebaljip.exceed.common.exception.GlobalExceptionHandler;
import com.gaebaljip.exceed.member.application.port.in.OnBoardingMemberCommand;
import com.gaebaljip.exceed.member.domain.Activity;
import com.gaebaljip.exceed.member.exception.InvalidAgeException;
import com.gaebaljip.exceed.member.exception.InvalidHeightException;
import com.gaebaljip.exceed.member.exception.InvalidWeightException;

@Import(GlobalExceptionHandler.class)
@ExtendWith(MockitoExtension.class)
class OnBoardingMemberServiceTest {

    @InjectMocks private OnBoardingMemberService onBoardingMemberService;

    private static OnBoardingMemberCommand createMemberCommand(
            double height, double weight, double targetWeight, int age) {
        OnBoardingMemberCommand command =
                OnBoardingMemberCommand.builder()
                        .height(height)
                        .weight(weight)
                        .targetWeight(targetWeight)
                        .age(age)
                        .activity(Activity.NOT_ACTIVE)
                        .etc("뭐든 잘 먹습니다.")
                        .build();
        return command;
    }

    @Test
    @DisplayName("몸무게가 0 이하일 경우 예외가 발생한다.")
    void createMember_weight() {
        // given
        OnBoardingMemberCommand command = createMemberCommand(172, 61, 0, 25);

        // when then
        Assertions.assertThrows(
                InvalidWeightException.class,
                () -> {
                    onBoardingMemberService.execute(command);
                });
    }

    @Test
    @DisplayName("목표 몸무게가 0 이하일 경우 예외가 발생한다.")
    void createMember_targetWeight() {
        // given
        OnBoardingMemberCommand command = createMemberCommand(171, 61, 0, 25);

        // when then
        Assertions.assertThrows(
                InvalidWeightException.class,
                () -> {
                    onBoardingMemberService.execute(command);
                });
    }

    @Test
    @DisplayName("키가 0 이하일 경우 예외가 발생한다.")
    void createMember_height() {
        // given
        OnBoardingMemberCommand command = createMemberCommand(0, 60, 65, 25);

        // when then
        Assertions.assertThrows(
                InvalidHeightException.class,
                () -> {
                    onBoardingMemberService.execute(command);
                });
    }

    @Test
    @DisplayName("나이가 0 이하일 경우 예외가 발생한다.")
    void createMember_age() {
        // given
        OnBoardingMemberCommand command = createMemberCommand(171, 61, 65, 0);

        // when then
        Assertions.assertThrows(
                InvalidAgeException.class,
                () -> {
                    onBoardingMemberService.execute(command);
                });
    }
}
