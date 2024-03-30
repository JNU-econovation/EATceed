package com.gaebaljip.exceed.member.adapter.out.persistence;

import com.gaebaljip.exceed.common.BaseEntity;
import com.gaebaljip.exceed.member.domain.Activity;
import com.gaebaljip.exceed.member.domain.MemberRole;
import lombok.*;

import javax.persistence.*;


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

    @Column(name = ENTITY_PREFIX + "_GENDER", columnDefinition = "tinyint")
    private Integer gender;

    @Column(name = ENTITY_PREFIX + "_WEIGHT")
    private Double weight;

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

    // todo : MemberEntity의 History 테이블 필요

    public void updateChecked(){
        this.checked = true;
    }

}
