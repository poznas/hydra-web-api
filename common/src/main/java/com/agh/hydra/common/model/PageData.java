package com.agh.hydra.common.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PageData {

    int pageSize;

    long offset;
}
