package com.gaebaljip.exceed.member.adapter.out.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

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
    private double height;

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
    private Boolean checked;

    @Column(name = ENTITY_PREFIX + "_ETC")
    private String etc;

    @Enumerated(EnumType.STRING)
    @Column(name = ENTITY_PREFIX + "_ACTIVITY")
    private Activity activity;

    @Enumerated(EnumType.STRING)
    @Column(name = ENTITY_PREFIX + "_ROLE")
    private MemberRole role;

    @Builder.Default
    @OneToMany(mappedBy = "memberEntity")
    private List<WeightEntity> weightEntities = new ArrayList<>();

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
        this.etc = etc;
        WeightEntity weightEntity =
                WeightEntity.builder().weight(weight).targetWeight(targetWeight).build();
        addWeightEntity(weightEntity);
    }

    private void addWeightEntity(WeightEntity weightEntity) {
        weightEntity.mappingMember(this);
        this.weightEntities.add(weightEntity);
    }
}
