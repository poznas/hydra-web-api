package com.agh.hydra.wiki.entity;

import com.agh.hydra.wiki.model.Vote;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VoteEntity {

    /**
     * Information identifier
     */
    private Long informationId;

    /**
     * Author user identifier
     */
    private String authorId;

    /**
     * Vote
     */
    private Vote vote;
}
