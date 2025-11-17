package com.example.apidirect.customer.adapter.out.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerListSyncEvent {
    private List<CustomerSyncEvent> customers;
}
