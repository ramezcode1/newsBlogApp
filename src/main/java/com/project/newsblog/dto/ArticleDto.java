package com.project.newsblog.dto;

import lombok.*;
import org.ocpsoft.prettytime.PrettyTime;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleDto {

    private SourceDto source;
    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private LocalDateTime publishedAt;
    private String content;
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private String timeAgo;

    public String getTimeAgo() {
        PrettyTime prettyTime = new PrettyTime();
        return prettyTime.format(this.getPublishedAt());
    }
}
