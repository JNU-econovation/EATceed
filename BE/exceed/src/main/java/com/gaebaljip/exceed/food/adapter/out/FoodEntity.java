package com.gaebaljip.exceed.food.adapter.out;

import javax.persistence.*;

import com.gaebaljip.exceed.member.adapter.out.persistence.MemberEntity;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Entity
@Table(name = FoodEntity.ENTITY_PREFIX + "_TB")
@Builder(toBuilder = true)
public class FoodEntity {

    public static final String ENTITY_PREFIX = "FOOD";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ENTITY_PREFIX + "_PK", nullable = false)
    private Long id;

    @Column(name = ENTITY_PREFIX + "_SUGARS", nullable = false)
    private Double sugars;

    @Column(name = ENTITY_PREFIX + "_DIETARY_FIBER", nullable = false)
    private Double dietaryFiber;

    @Column(name = ENTITY_PREFIX + "_SODIUM", nullable = false)
    private Double sodium;

    @Column(name = ENTITY_PREFIX + "_NAME", nullable = false)
    private String name;

    @Column(name = ENTITY_PREFIX + "_CALORIE", nullable = false)
    private Double calorie;

    @Column(name = ENTITY_PREFIX + "_CARBOHYDRATE", nullable = false)
    private Double carbohydrate;

    @Column(name = ENTITY_PREFIX + "_PROTEIN", nullable = false)
    private Double protein;

    @Column(name = ENTITY_PREFIX + "_FAT", nullable = false)
    private Double fat;

    @Column(name = ENTITY_PREFIX + "_SERVING_SIZE", nullable = false)
    private Double servingSize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_FK")
    private MemberEntity memberEntity;
}
