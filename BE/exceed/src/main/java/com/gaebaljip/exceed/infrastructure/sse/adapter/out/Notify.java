package com.gaebaljip.exceed.infrastructure.sse.adapter.out;

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
