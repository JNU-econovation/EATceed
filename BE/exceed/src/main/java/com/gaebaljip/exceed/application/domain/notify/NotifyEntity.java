package com.gaebaljip.exceed.application.domain.notify;

import javax.persistence.*;

import com.gaebaljip.exceed.application.domain.member.MemberEntity;
import com.gaebaljip.exceed.common.BaseEntity;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Entity
@Table(name = NotifyEntity.ENTITY_PREFIX + "_TB")
@Builder()
public class NotifyEntity extends BaseEntity {
    public static final String ENTITY_PREFIX = "NOTIFY";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ENTITY_PREFIX + "_PK", nullable = false)
    private Long id;

    @Column(name = ENTITY_PREFIX + "_URL", nullable = false)
    private String url;

    @Column(name = ENTITY_PREFIX + "_CONTENT", nullable = false)
    private String content;

    @Column(name = ENTITY_PREFIX + "_IS_READ", nullable = false)
    private Boolean isRead;

    @Column(name = ENTITY_PREFIX + "_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_FK")
    private MemberEntity receiver;

    public static NotifyEntity createNotify(
            MemberEntity receiver, String content, String url, NotificationType type) {
        return NotifyEntity.builder()
                .receiver(receiver)
                .content(content)
                .url(url)
                .isRead(false)
                .type(type)
                .build();
    }
}
