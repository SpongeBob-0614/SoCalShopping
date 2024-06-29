package com.spongebob.socalshopping.db.po;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;

@Data
@Builder
@AllArgsConstructor
public class SoCalShoppingShardingOrder {
    private Long orderId;

    private String orderNo;

    private Integer orderStatus;

    private Long commodityId;

    private Long userId;

    private Long orderAmount;

    private Date createTime;

    private Date payTime;

}
