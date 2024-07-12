package com.gaebaljip.exceed.application.domain.notify;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Notify {
    private String content;
    private String url;
    private String type;
}
