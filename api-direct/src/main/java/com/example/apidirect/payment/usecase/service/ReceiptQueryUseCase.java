package com.example.apidirect.payment.usecase.service;

import MeshX.common.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.apidirect.item.domain.StoreItemDetail;
import com.example.apidirect.item.usecase.port.out.ItemDetailPersistencePort;
import com.example.apidirect.payment.adapter.in.web.dto.response.ReceiptItemResponse;
import com.example.apidirect.payment.adapter.in.web.dto.response.ReceiptListPagingResponse;
import com.example.apidirect.payment.adapter.in.web.dto.response.ReceiptResponse;
import com.example.apidirect.payment.adapter.in.web.mapper.ReceiptResponseMapper;
import com.example.apidirect.payment.domain.CustomerReceipt;
import com.example.apidirect.payment.domain.OrderItem;
import com.example.apidirect.payment.usecase.port.in.ReceiptQueryPort;
import com.example.apidirect.payment.usecase.port.out.CustomerReceiptPersistencePort;
import com.example.apidirect.payment.usecase.port.out.OrderItemPersistencePort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@UseCase
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReceiptQueryUseCase implements ReceiptQueryPort {

    private final CustomerReceiptPersistencePort customerReceiptPersistencePort;
    private final OrderItemPersistencePort orderItemPersistencePort;
    private final ItemDetailPersistencePort itemDetailPersistencePort;

    @Override
    public ReceiptListPagingResponse getReceipts(Pageable pageable) {
        log.info("영수증 목록 조회 시작 (전체) - page: {}, size: {}", pageable.getPageNumber(), pageable.getPageSize());

        // 1. CustomerReceipt 페이징 조회
        Page<CustomerReceipt> receiptPage = customerReceiptPersistencePort.findAllByOrderByPaidAtDesc(pageable);

        // 2. 각 Receipt에 대해 OrderItem + 상품 정보 조회
        List<ReceiptResponse> receiptResponses = receiptPage.getContent().stream()
                .map(this::toReceiptResponse)
                .collect(Collectors.toList());

        log.info("영수증 목록 조회 완료 - 총 {}건", receiptResponses.size());

        return ReceiptListPagingResponse.builder()
                .receipts(receiptResponses)
                .totalPages(receiptPage.getTotalPages())
                .totalElements(receiptPage.getTotalElements())
                .currentPage(receiptPage.getNumber())
                .pageSize(receiptPage.getSize())
                .build();
    }

    public ReceiptListPagingResponse getReceiptsByStoreId(Integer storeId, Pageable pageable) {
        log.info("영수증 목록 조회 시작 (storeId: {}) - page: {}, size: {}", storeId, pageable.getPageNumber(), pageable.getPageSize());

        // 1. CustomerReceipt 페이징 조회 (storeId 필터)
        Page<CustomerReceipt> receiptPage = customerReceiptPersistencePort.findByStoreIdOrderByPaidAtDesc(storeId, pageable);

        // 2. 각 Receipt에 대해 OrderItem + 상품 정보 조회
        List<ReceiptResponse> receiptResponses = receiptPage.getContent().stream()
                .map(this::toReceiptResponse)
                .collect(Collectors.toList());

        log.info("영수증 목록 조회 완료 (storeId: {}) - 총 {}건", storeId, receiptResponses.size());

        return ReceiptListPagingResponse.builder()
                .receipts(receiptResponses)
                .totalPages(receiptPage.getTotalPages())
                .totalElements(receiptPage.getTotalElements())
                .currentPage(receiptPage.getNumber())
                .pageSize(receiptPage.getSize())
                .build();
    }

    private ReceiptResponse toReceiptResponse(CustomerReceipt receipt) {
        // OrderItem 조회
        List<OrderItem> orderItems = orderItemPersistencePort.findByCustomerReceiptId(receipt.getId());

        // OrderItem -> ReceiptItemResponse 변환 (상품 정보 포함)
        List<ReceiptItemResponse> itemResponses = orderItems.stream()
                .map(this::toReceiptItemResponse)
                .collect(Collectors.toList());

        return ReceiptResponseMapper.toResponse(receipt, itemResponses);
    }

    private ReceiptItemResponse toReceiptItemResponse(OrderItem orderItem) {
        // StoreItemDetail 조회하여 상품명, 색상, 사이즈 가져오기
        StoreItemDetail itemDetail = itemDetailPersistencePort.findById(orderItem.getStoreItemDetailId())
                .orElse(null);

        if (itemDetail == null) {
            return ReceiptResponseMapper.toItemResponse(orderItem, "알 수 없음", "", "");
        }

        return ReceiptResponseMapper.toItemResponse(
                orderItem,
                itemDetail.getItemName(),
                itemDetail.getColorName(),
                itemDetail.getSizeName()
        );
    }
}
