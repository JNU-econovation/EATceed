package com.gaebaljip.exceed.achieve.application.port.out;

import com.gaebaljip.exceed.achieve.domain.DailyTarget;

public interface LoadMonthTargetPort {
    // todo : Member에대한 키,몸무게,기타 등등이 수정됬다면, MemberHistoryEntity에 저장된 정보를 가져와서 계산해야함
    DailyTarget queryForMonthTargets(Long memberId);
}
