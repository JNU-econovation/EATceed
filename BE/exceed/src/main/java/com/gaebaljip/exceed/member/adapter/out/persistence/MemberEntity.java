package com.gaebaljip.exceed.member.adapter.out.persistence;

import javax.persistence.*;

import org.hibernate.annotations.ColumnDefault;

import com.gaebaljip.exceed.common.BaseEntity;
import com.gaebaljip.exceed.member.domain.Activity;
import com.gaebaljip.exceed.member.domain.Gender;
import com.gaebaljip.exceed.member.domain.MemberRole;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Entity
@Table(name = MemberEntity.ENTITY_PREFIX + "_TB")
@Builder(toBuilder = true)
public class MemberEntity extends BaseEntity {

    public static final String ENTITY_PREFIX = "MEMBER";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ENTITY_PREFIX + "_PK", nullable = false)
    private Long id;

    @Column(name = ENTITY_PREFIX + "_HEIGHT")
    private Double height;

    @Convert(converter = GenderConvert.class)
    @Column(name = ENTITY_PREFIX + "_GENDER", columnDefinition = "tinyint")
    private Gender gender;

    @Column(name = ENTITY_PREFIX + "_AGE")
    private Integer age;

    @Column(name = ENTITY_PREFIX + "_EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = ENTITY_PREFIX + "_PASSWORD", nullable = false)
    private String password;

    @Column(name = ENTITY_PREFIX + "_CHECKED", nullable = false)
    @ColumnDefault("false")
    @Builder.Default
    private Boolean checked = false;

    @Column(name = ENTITY_PREFIX + "_ETC")
    private String etc;

    @Enumerated(EnumType.STRING)
    @Column(name = ENTITY_PREFIX + "_ACTIVITY")
    private Activity activity;

    @Enumerated(EnumType.STRING)
    @Column(name = ENTITY_PREFIX + "_ROLE", nullable = false)
    @Builder.Default
    private MemberRole role = MemberRole.MEMBER;

    @Column(name = ENTITY_PREFIX + "_WEIGHT")
    private Double weight;

    @Column(name = ENTITY_PREFIX + "_TARGET_WEIGHT")
    private Double targetWeight;

    public void updateChecked() {
        this.checked = true;
    }

    public void updateMember(
            double height,
            Gender gender,
            int age,
            Activity activity,
            String etc,
            double weight,
            double targetWeight) {
        this.height = height;
        this.gender = gender;
        this.age = age;
        this.activity = activity;
        this.weight = weight;
        this.targetWeight = targetWeight;
        this.etc = etc;
    }

    public void updateWeight(Double weight, Double targetWeight) {
        this.weight = weight;
        this.targetWeight = targetWeight;
    }
}
