package com.gaebaljip.exceed.member.adapter.out.persistence;

import javax.persistence.*;

import com.gaebaljip.exceed.common.BaseEntity;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Entity
@Table(name = WeightEntity.ENTITY_PREFIX + "_TB")
@Builder(toBuilder = true)
public class WeightEntity extends BaseEntity {
    public static final String ENTITY_PREFIX = "WEIGHT";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ENTITY_PREFIX + "_PK", nullable = false)
    private Long id;

    @Column(name = ENTITY_PREFIX + "_WEIGHT")
    private Double weight;

    @Column(name = ENTITY_PREFIX + "_TARGET_WEIGHT")
    private Double targetWeight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_FK", referencedColumnName = "MEMBER_PK")
    private MemberEntity memberEntity;

    public void mappingMember(MemberEntity memberEntity) {
        this.memberEntity = memberEntity;
    }
}
