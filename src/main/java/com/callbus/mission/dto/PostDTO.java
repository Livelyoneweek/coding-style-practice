package com.callbus.mission.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class PostDTO {

    @AllArgsConstructor
    @Getter
    public static class Update {
        private String title;
        private String content;
    }
}
