package com.callbus.mission.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class PostDTO {

    public static class Request {
        @ToString
        @AllArgsConstructor
        @Getter
        public static class Save {
            @NotBlank
            private String title;
            @NotBlank
            private String content;
        }

        @ToString
        @AllArgsConstructor
        @Getter
        public static class Update {
            @NotBlank
            private Long id;
            @NotBlank
            private String title;
            @NotBlank
            private String content;
        }
    }

    public static class Response{

        @Getter
        @Setter
        @Builder
        public static class PostPage {
            private Long id;
            private String title;
            private String content;
            private Long likeCount;
            private LocalDateTime createDate;
            private LocalDateTime modifiedDate;
            private boolean likeCheck;
        }
    }

}
