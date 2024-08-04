package com.gaebaljip.exceed.application.domain.member;

import javax.persistence.*;

import com.gaebaljip.exceed.adapter.out.jpa.member.GenderConvert;
import com.gaebaljip.exceed.common.BaseEntity;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Entity
@Table(name = HistoryEntity.ENTITY_PREFIX + "_TB")
@Builder(toBuilder = true)
public class HistoryEntity extends BaseEntity {
    public static final String ENTITY_PREFIX = "HISTORY";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ENTITY_PREFIX + "_PK", nullable = false)
    private Long id;

    @Column(name = ENTITY_PREFIX + "_HEIGHT", nullable = false)
    private double height;

    @Convert(converter = GenderConvert.class)
    @Column(name = ENTITY_PREFIX + "_GENDER", columnDefinition = "tinyint", nullable = false)
    private Gender gender;

    @Column(name = ENTITY_PREFIX + "_WEIGHT", nullable = false)
    private double weight;

    @Column(name = ENTITY_PREFIX + "_TARGET_WEIGHT", nullable = false)
    private double targetWeight;

    @Column(name = ENTITY_PREFIX + "_AGE", nullable = false)
    private Integer age;

    @Enumerated(EnumType.STRING)
    @Column(name = ENTITY_PREFIX + "_ACTIVITY", nullable = false)
    private Activity activity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_FK", referencedColumnName = "MEMBER_PK")
    private MemberEntity memberEntity;
}
