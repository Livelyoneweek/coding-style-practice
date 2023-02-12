package com.callbus.mission.entity;

import com.callbus.mission.dto.PostDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Post extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 20)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column
    private Long likeCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="MEMBER_ID")
    private Member member;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Like> likeList = new ArrayList<>();

    public Post (PostDTO.Request.Save postDTO, Member member) {
        this.title = postDTO.getTitle();
        this.content = postDTO.getContent();
        this.likeCount = 0L;
        this.member = member;
    }

    public void update(PostDTO.Request.Update updateDTO) {
        this.title = updateDTO.getTitle();
        this.content = updateDTO.getContent();
    }

    public void updateLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }
}
