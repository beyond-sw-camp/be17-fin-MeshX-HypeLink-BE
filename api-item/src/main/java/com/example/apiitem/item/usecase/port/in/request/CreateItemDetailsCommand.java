package com.example.apiitem.item.usecase.port.in.request;

import lombok.Getter;

import java.util.List;

@Getter
public class CreateItemDetailsCommand {
    private Integer itemId;
    private List<CreateItemDetailCommand> details;
}
