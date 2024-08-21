package com.gaebaljip.exceed.application.domain.meal;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.gaebaljip.exceed.application.domain.member.MemberEntity;
import com.gaebaljip.exceed.common.BaseEntity;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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
    private List<MealFoodEntity> mealFoodEntities = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_FK")
    @ToString.Exclude
    private MemberEntity memberEntity;

    @Enumerated(EnumType.STRING)
    @Column(name = ENTITY_PREFIX + "_TYPE", nullable = false)
    private MealType mealType;

    public static MealEntity createMeal(MemberEntity memberEntity, MealType mealType) {
        return MealEntity.builder().memberEntity(memberEntity).mealType(mealType).build();
    }

    @Override
    public String toString() {
        return "MealEntity{" + "id=" + id + ", mealType=" + mealType + '}';
    }
}
