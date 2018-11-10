package com.agh.hydra.common.util;

import com.agh.hydra.common.model.PageData;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Pageable;

@UtilityClass
public class PageableUtils {

    public static void setPageableParams(PageData pageData, Pageable pageable) {
        pageData.setPageSize(pageable.getPageSize());
        pageData.setOffset(pageable.getOffset());
    }
}
