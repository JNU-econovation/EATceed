package com.gaebaljip.exceed.meal.adapter.out;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.gaebaljip.exceed.common.BaseEntity;
import com.gaebaljip.exceed.meal.domain.MealType;
import com.gaebaljip.exceed.member.adapter.out.persistence.MemberEntity;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Entity
@Table(name = MealEntity.ENTITY_PREFIX + "_TB")
@Builder()
public class MealEntity extends BaseEntity {

    public static final String ENTITY_PREFIX = "MEAL";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ENTITY_PREFIX + "_PK", nullable = false)
    private Long id;

    @Builder.Default
    @OneToMany(mappedBy = "mealEntity")
    private List<MealFoodEntity> mealFoodEntity = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_FK")
    private MemberEntity memberEntity;

    @Enumerated(EnumType.STRING)
    @Column(name = ENTITY_PREFIX + "_TYPE", nullable = false)
    private MealType mealType;

    public static MealEntity createMeal(MemberEntity memberEntity, MealType mealType) {
        return MealEntity.builder().memberEntity(memberEntity).mealType(mealType).build();
    }
}
