
package MeshX.HypeLink.common;

import MeshX.HypeLink.auth.model.entity.Member;
import MeshX.HypeLink.auth.model.entity.MemberRole;
import MeshX.HypeLink.auth.model.entity.Store;
import MeshX.HypeLink.auth.repository.MemberJpaRepositoryVerify;
import MeshX.HypeLink.auth.repository.StoreJpaRepositoryVerify;
import MeshX.HypeLink.direct_store.item.model.entity.StoreCategory;
import MeshX.HypeLink.direct_store.item.model.entity.StoreItem;
import MeshX.HypeLink.direct_store.item.model.entity.StoreItemDetail;
import MeshX.HypeLink.direct_store.item.repository.StoreCategoryRepository;
import MeshX.HypeLink.direct_store.item.repository.StoreItemDetailRepository;
import MeshX.HypeLink.direct_store.item.repository.StoreItemRepository;
import MeshX.HypeLink.direct_store.payment.model.entity.Payments;
import MeshX.HypeLink.direct_store.payment.repository.PaymentRepository;
import MeshX.HypeLink.head_office.customer.model.entity.Customer;
import MeshX.HypeLink.head_office.customer.model.entity.CustomerReceipt;
import MeshX.HypeLink.head_office.customer.model.entity.OrderItem;
import MeshX.HypeLink.head_office.customer.model.entity.PaymentStatus;
import MeshX.HypeLink.head_office.customer.repository.CustomerReceiptRepository;
import MeshX.HypeLink.head_office.customer.repository.CustomerRepository;
import MeshX.HypeLink.head_office.item.model.entity.*;
import MeshX.HypeLink.head_office.item.repository.*;
import MeshX.HypeLink.head_office.order.model.entity.PurchaseOrder;
import MeshX.HypeLink.head_office.order.model.entity.PurchaseOrderState;
import MeshX.HypeLink.head_office.order.repository.PurchaseOrderRepository;
import MeshX.HypeLink.head_office.shipment.model.entity.Parcel;
import MeshX.HypeLink.head_office.shipment.model.entity.ParcelItem;
import MeshX.HypeLink.head_office.shipment.model.entity.Shipment;
import MeshX.HypeLink.head_office.shipment.model.entity.ShipmentStatus;
import MeshX.HypeLink.head_office.shipment.repository.ParcelRepository;
import MeshX.HypeLink.head_office.shipment.repository.ParcelItemRepository;
import MeshX.HypeLink.head_office.shipment.repository.ShipmentRepository;
import MeshX.HypeLink.auth.model.entity.Driver;
import MeshX.HypeLink.auth.repository.DriverRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.context.annotation.DependsOn;

@Slf4j
@Component
@RequiredArgsConstructor
@DependsOn({"baseMember", "dataInitialize"})
public class BaseDataGenerator {
    private final CustomerRepository customerRepository;
    private final CategoryRepository categoryRepository;
    private final StoreCategoryRepository storeCategoryRepository;
    private final ItemRepository itemRepository;
    private final ItemDetailRepository itemDetailRepository;
    private final StoreItemRepository storeItemRepository;
    private final StoreItemDetailRepository storeItemDetailRepository;
    private final CustomerReceiptRepository customerReceiptRepository;
    private final PaymentRepository paymentRepository;
    private final StoreJpaRepositoryVerify storeRepository;
    private final MemberJpaRepositoryVerify memberRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final ColorRepository colorRepository;
    private final SizeRepository sizeRepository;
    private final ParcelRepository parcelRepository;
    private final ParcelItemRepository parcelItemRepository;
    private final ShipmentRepository shipmentRepository;
    private final DriverRepository driverRepository;

    @PostConstruct
    @Transactional
    public void init() {
        // Check if data already exists
        long itemCount = itemRepository.count();
        long receiptCount = customerReceiptRepository.count();

        log.info("📊 Current data counts - Items: {}, CustomerReceipts: {}", itemCount, receiptCount);

        if (itemCount > 0 && receiptCount >= 3000) {
            log.info("✅ Additional base data already exists (Items: {}, Receipts: {}). Skipping generation.", itemCount, receiptCount);
            return;
        }

        if (itemCount > 0 && receiptCount < 3000) {
            log.warn("⚠️ Data exists but CustomerReceipts count is low ({}). Consider regenerating data.", receiptCount);
            log.warn("⚠️ To regenerate: Drop all tables and restart the application.");
        }

        log.info("✅ Generating additional base data...");

        List<Store> stores = storeRepository.findAll();
        if (stores.isEmpty()) {
            log.warn("⚠️ No stores found. Skipping data generation.");
            return;
        }

        // Check and create customers if needed
        long customerCount = customerRepository.count();
        if (customerCount < 300) {
            log.info("✅ Creating customers... (current: {})", customerCount);
            createCustomers();
        } else {
            log.info("✅ Customers already exist: {}", customerCount);
        }

        // Create items if needed
        if (itemCount == 0) {
            log.info("✅ Creating items and inventory...");
            createItemsAndInventory(stores);
        } else {
            log.info("✅ Items already exist: {}", itemCount);
        }

        // Create receipts if needed
        if (receiptCount < 3000) {
            log.info("✅ Creating customer receipts and purchase orders...");
            createCustomerReceipts(stores); // B2C 고객 주문 데이터
            createPurchaseOrders(stores);   // B2B 발주 데이터 (통계용)
        } else {
            log.info("✅ Customer receipts already exist: {}", receiptCount);
        }

        // Create Parcels and Shipments for testing
        long parcelCount = parcelRepository.count();
        if (parcelCount == 0) {
            log.info("✅ Creating test parcels and shipments...");
            createTestParcelsAndShipments(stores);
        } else {
            log.info("✅ Parcels already exist: {}", parcelCount);
        }

        log.info("✅ Additional base data generation complete.");
    }

    private void createPurchaseOrders(List<Store> stores) {
        Member supplier = memberRepository.findByEmail("hq@company.com");
        List<ItemDetail> allItemDetails = itemDetailRepository.findAllWithItem();
        Random random = new Random();

        if (allItemDetails.isEmpty()) {
            log.warn("⚠️ No ItemDetails found. Skipping PurchaseOrder generation.");
            return;
        }
        List<PurchaseOrder> purchaseOrders = new ArrayList<>();
        PurchaseOrderState[] states = PurchaseOrderState.values();

        for (Store store : stores) {
            Member requester = store.getMember();
            if (requester == null) continue;

            // Create 50 orders per store
            for (int i = 0; i < 50; i++) {
                ItemDetail itemDetail = allItemDetails.get(random.nextInt(allItemDetails.size()));
                int quantity = random.nextInt(50) + 1;
                int totalPrice = itemDetail.getItem().getUnitPrice() * quantity;
                LocalDateTime orderDate = LocalDateTime.now().minusDays(random.nextInt(365));

                PurchaseOrder purchaseOrder = PurchaseOrder.builder()
                        .requester(requester)
                        .supplier(supplier)
                        .itemDetail(itemDetail)
                        .quantity(quantity)
                        .totalPrice(totalPrice)
                        .purchaseOrderState(states[random.nextInt(states.length)])
                        .build();

                // Manually set createdAt because it's in BaseEntity and might not be set before save
                // This is a workaround. A better approach is to use AuditingEntityListener.
                // But for test data generation, this is acceptable.
                try {
                    java.lang.reflect.Field createdAtField = PurchaseOrder.class.getSuperclass().getDeclaredField("createdAt");
                    createdAtField.setAccessible(true);
                    createdAtField.set(purchaseOrder, orderDate);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    log.error("Failed to set createdAt field via reflection", e);
                }

                purchaseOrders.add(purchaseOrder);
            }
        }

        purchaseOrderRepository.saveAll(purchaseOrders);
        log.info("✅ Created " + purchaseOrders.size() + " PurchaseOrders for analytics.");
    }

    private void createCustomers() {
        List<Customer> customers = new ArrayList<>();
        for (int i = 1; i <= 300; i++) {
            customers.add(Customer.builder()
                    .name("고객" + i)
                    .phone("010-1234-" + String.format("%04d", i))
                    .birthDate(LocalDate.now().minusYears(20 + i % 30).minusDays(i % 365))
                    .build());
        }
        customerRepository.saveAll(customers);
        log.info("✅ Created 300 customers.");
    }

    private void createItemsAndInventory(List<Store> stores) {
        // Fetch existing Categories, Colors, and Sizes created by DataInitialize
        List<Category> categories = categoryRepository.findAll();
        List<Color> colors = colorRepository.findAll();
        List<Size> sizes = sizeRepository.findAll();

        if (categories.isEmpty() || colors.isEmpty() || sizes.isEmpty()) {
            log.warn("⚠️ Categories, Colors, or Sizes not found. Skipping Item and Inventory generation.");
            return;
        }

        // Use existing categories
        for (Category category : categories) { // Loop through fetched categories
            List<Item> items = new ArrayList<>();
            for (int i = 1; i <= 10; i++) {
                Item item = Item.builder()
                        .koName(category.getCategory() + " 상품 " + i) // Use category.getCategory()
                        .itemCode("ITEM-" + category.getCategory() + "-" + i) // Use category.getCategory()
                        .category(category)
                        .unitPrice(10000 + (i * 1000))
                        .amount(10000 + (i * 1000))
                        .build();
                items.add(item);
            }
            itemRepository.saveAll(items);

            List<ItemDetail> itemDetails = new ArrayList<>();
            for(Item item : items) {
                for (Color color : colors) { // Use fetched colors
                    for (Size size : sizes) { // Use fetched sizes
                        itemDetails.add(ItemDetail.builder()
                                .item(item)
                                .color(color)
                                .size(size)
                                .stock(500)
                                .itemDetailCode(item.getItemCode() + "-" + color.getColorName() + "-" + size.getSize())
                                .build());
                    }
                }
            }
            itemDetailRepository.saveAll(itemDetails);

            for (Store store : stores) {
                // Find or create StoreCategory based on existing Category
                StoreCategory storeCategory = storeCategoryRepository.findByCategoryAndStore(category.getCategory(), store)
                        .orElseGet(() -> storeCategoryRepository.save(StoreCategory.builder().category(category.getCategory()).store(store).build()));

                for(Item item : items) {
                    StoreItem storeItem = StoreItem.builder()
                            .store(store)
                            .category(storeCategory)
                            .itemCode(item.getItemCode())
                            .koName(item.getKoName())
                            .unitPrice(item.getUnitPrice())
                            .amount(item.getAmount())
                            .build();
                    storeItemRepository.save(storeItem);

                    List<StoreItemDetail> storeItemDetails = new ArrayList<>();
                    Random stockRandom = new Random();
                    for (Color color : colors) {
                        for (Size size : sizes) {
                            // 20% of items will have low stock for testing low-stock analytics
                            int stock;
                            int stockRoll = stockRandom.nextInt(100);
                            if (stockRoll < 5) {
                                stock = 0; // 5% critical (out of stock)
                            } else if (stockRoll < 10) {
                                stock = stockRandom.nextInt(5) + 1; // 5% high alert (1-5)
                            } else if (stockRoll < 20) {
                                stock = stockRandom.nextInt(10) + 6; // 10% medium alert (6-15)
                            } else {
                                stock = stockRandom.nextInt(91) + 10; // 80% normal (10-100)
                            }

                            storeItemDetails.add(StoreItemDetail.builder()
                                    .item(storeItem)
                                    .color(color.getColorName())
                                    .colorCode(color.getColorCode())
                                    .size(size.getSize())
                                    .stock(stock)
                                    .itemDetailCode(item.getItemCode() + "-" + color.getColorName() + "-" + size.getSize())
                                    .build());
                        }
                    }
                    storeItemDetailRepository.saveAll(storeItemDetails);
                }
            }
        }
        log.info("✅ Created items and inventory.");
    }

    private void createCustomerReceipts(List<Store> stores) {
        List<Customer> customers = customerRepository.findAll();
        Random random = new Random();

        if (customers.isEmpty()) {
            log.warn("⚠️ No customers found. Skipping CustomerReceipt generation.");
            return;
        }

        int totalReceipts = 3000;
        int receiptsPerStore = totalReceipts / stores.size();
        int createdCount = 0;

        for (Store store : stores) {
            // Fetch StoreItemDetails for this specific store using repository method
            List<StoreItemDetail> storeItemDetailsInStore = storeItemDetailRepository.findAllByStoreId(store.getId());

            if (storeItemDetailsInStore.isEmpty()) {
                log.warn("⚠️ No StoreItemDetails found for Store ID: {}. Skipping receipts for this store.", store.getId());
                continue;
            }

            for (int i = 0; i < receiptsPerStore; i++) {
                Customer customer = customers.get(random.nextInt(customers.size()));

                // Generate order date with realistic distribution
                LocalDateTime orderDate = generateRealisticOrderDate(random);

                List<OrderItem> orderItems = new ArrayList<>();
                int totalAmount = 0;
                int itemCount = 1 + random.nextInt(5);

                for (int j = 0; j < itemCount; j++) {
                    StoreItemDetail storeItemDetail = storeItemDetailsInStore.get(random.nextInt(storeItemDetailsInStore.size()));
                    int quantity = 1 + random.nextInt(3);
                    int unitPrice = storeItemDetail.getItem().getUnitPrice();
                    int price = unitPrice * quantity;

                    orderItems.add(OrderItem.builder()
                            .storeItemDetail(storeItemDetail)
                            .quantity(quantity)
                            .unitPrice(unitPrice)
                            .totalPrice(price)
                            .build());
                    totalAmount += price;
                }

                // 95% PAID, 3% READY, 2% CANCELLED
                PaymentStatus status;
                int statusRoll = random.nextInt(100);
                if (statusRoll < 95) {
                    status = PaymentStatus.PAID;
                } else if (statusRoll < 98) {
                    status = PaymentStatus.READY;
                } else {
                    status = PaymentStatus.CANCELLED;
                }

                CustomerReceipt receipt = CustomerReceipt.builder()
                        .pgProvider("portone")
                        .pgTid("pg_" + UUID.randomUUID().toString().replace("-", ""))
                        .merchantUid("order_" + UUID.randomUUID().toString().replace("-", ""))
                        .totalAmount(totalAmount)
                        .discountAmount(0)
                        .couponDiscount(0)
                        .finalAmount(totalAmount)
                        .store(store)
                        .customer(customer)
                        .status(status)
                        .paidAt(status == PaymentStatus.PAID ? orderDate : null)
                        .build();

                // Set createdAt to orderDate for historical data
                try {
                    java.lang.reflect.Field createdAtField = CustomerReceipt.class.getSuperclass().getDeclaredField("createdAt");
                    createdAtField.setAccessible(true);
                    createdAtField.set(receipt, orderDate);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    log.error("Failed to set createdAt field via reflection", e);
                }

                for (OrderItem orderItem : orderItems) {
                    receipt.addOrderItem(orderItem);
                }
                customerReceiptRepository.save(receipt);

                if (status == PaymentStatus.PAID) {
                    Payments payment = Payments.builder()
                            .customerReceipt(receipt)
                            .amount(totalAmount)
                            .status(MeshX.HypeLink.direct_store.payment.model.entity.PaymentStatus.PAID)
                            .paidAt(orderDate)
                            .build();
                    paymentRepository.save(payment);
                }

                createdCount++;
            }
        }
        log.info("✅ Created " + createdCount + " CustomerReceipts.");
    }

    /**
     * Generate realistic order date with time-of-day and day-of-week distribution
     */
    private LocalDateTime generateRealisticOrderDate(Random random) {
        // Random day within last 365 days
        int daysAgo = random.nextInt(365);
        LocalDateTime baseDate = LocalDateTime.now().minusDays(daysAgo);

        // Reset to midnight
        baseDate = baseDate.withHour(0).withMinute(0).withSecond(0).withNano(0);

        // Generate hour with realistic distribution
        // Peak hours: 11-14 (lunch), 18-21 (dinner)
        // Normal hours: 9-11, 14-18, 21-22
        // Low hours: 6-9, 22-24
        int hourWeight = random.nextInt(100);
        int hour;

        if (hourWeight < 30) { // 30% - Lunch peak (11-14)
            hour = 11 + random.nextInt(3);
        } else if (hourWeight < 60) { // 30% - Dinner peak (18-21)
            hour = 18 + random.nextInt(3);
        } else if (hourWeight < 85) { // 25% - Normal hours
            int slot = random.nextInt(3);
            if (slot == 0) hour = 9 + random.nextInt(2);      // 9-11
            else if (slot == 1) hour = 14 + random.nextInt(4); // 14-18
            else hour = 21 + random.nextInt(2);                // 21-22
        } else { // 15% - Low hours
            if (random.nextBoolean()) hour = 6 + random.nextInt(3); // 6-9
            else hour = 22 + random.nextInt(2);                     // 22-24
        }

        int minute = random.nextInt(60);
        int second = random.nextInt(60);

        return baseDate.withHour(hour).withMinute(minute).withSecond(second);
    }

    /**
     * Create test Parcels and Shipments for driver assignment testing
     */
    private void createTestParcelsAndShipments(List<Store> stores) {
        Member supplier = memberRepository.findByEmail("hq@company.com");
        List<Driver> drivers = driverRepository.findAll();
        Random random = new Random();

        if (stores.isEmpty() || supplier == null) {
            log.warn("⚠️ Stores or Supplier not found. Skipping Parcel/Shipment generation.");
            return;
        }

        // REQUESTED 상태인 발주 조회
        List<PurchaseOrder> requestedOrders = purchaseOrderRepository.findAllByPurchaseOrderState(PurchaseOrderState.REQUESTED);

        if (requestedOrders.isEmpty()) {
            log.warn("⚠️ No REQUESTED PurchaseOrders found. Creating some for testing...");
            // 테스트용 REQUESTED 발주 생성
            List<ItemDetail> allItemDetails = itemDetailRepository.findAllWithItem();
            if (allItemDetails.isEmpty()) return;

            requestedOrders = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                Store requester = stores.get(random.nextInt(stores.size()));
                ItemDetail itemDetail = allItemDetails.get(random.nextInt(allItemDetails.size()));

                PurchaseOrder po = PurchaseOrder.builder()
                        .requester(requester.getMember())
                        .supplier(supplier)
                        .itemDetail(itemDetail)
                        .quantity(random.nextInt(50) + 10)
                        .totalPrice(itemDetail.getItem().getUnitPrice() * (random.nextInt(50) + 10))
                        .purchaseOrderState(PurchaseOrderState.REQUESTED)
                        .build();
                requestedOrders.add(po);
            }
            purchaseOrderRepository.saveAll(requestedOrders);
            log.info("✅ Created {} REQUESTED PurchaseOrders for testing", requestedOrders.size());
        }

        // (requester, supplier) 기준으로 그룹화
        Map<String, List<PurchaseOrder>> grouped = requestedOrders.stream()
                .filter(po -> po.getRequester().getId() != 1) // 본사 제외
                .collect(Collectors.groupingBy(po -> po.getRequester().getId() + "-" + po.getSupplier().getId()));

        int unassignedCount = 0;
        int assignedCount = 0;

        // 각 그룹별로 Parcel + Shipment 생성
        for (Map.Entry<String, List<PurchaseOrder>> entry : grouped.entrySet()) {
            List<PurchaseOrder> groupOrders = entry.getValue();
            if (groupOrders.isEmpty()) continue;

            Member requester = groupOrders.get(0).getRequester();

            // Parcel 생성
            Parcel parcel = Parcel.builder()
                    .trackingNumber("TRK-TEST-" + System.currentTimeMillis() + "-" + random.nextInt(1000))
                    .requester(requester)
                    .supplier(supplier)
                    .build();
            Parcel savedParcel = parcelRepository.save(parcel);

            // ParcelItem 생성
            for (PurchaseOrder order : groupOrders) {
                ParcelItem item = ParcelItem.builder()
                        .parcel(savedParcel)
                        .purchaseOrder(order)
                        .build();
                parcelItemRepository.save(item);
            }

            // Shipment 생성 (70% 배정 안 됨, 30% 배정됨)
            boolean shouldAssign = !drivers.isEmpty() && random.nextInt(100) < 30;
            Driver assignedDriver = shouldAssign ? drivers.get(random.nextInt(drivers.size())) : null;
            ShipmentStatus status = assignedDriver != null ? ShipmentStatus.DRIVER_ASSIGNED : ShipmentStatus.PREPARING;

            Shipment shipment = Shipment.builder()
                    .parcel(savedParcel)
                    .driver(assignedDriver)
                    .shipmentStatus(status)
                    .build();
            shipmentRepository.save(shipment);

            if (assignedDriver != null) {
                assignedCount++;
            } else {
                unassignedCount++;
            }
        }

        log.info("✅ Created {} Parcels/Shipments (Unassigned: {}, Assigned: {})",
                 grouped.size(), unassignedCount, assignedCount);
    }
}
