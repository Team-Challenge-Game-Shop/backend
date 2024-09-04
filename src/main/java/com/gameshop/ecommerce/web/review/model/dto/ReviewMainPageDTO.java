package com.gameshop.ecommerce.web.review.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Relation(collectionRelation = "review")
public class ReviewMainPageDTO extends RepresentationModel<ReviewMainPageDTO> {
    private UUID id;
    private UUID productId;
    private Integer rate;
    private String comment;
    private String userName;
    private String userPhoto;
}
