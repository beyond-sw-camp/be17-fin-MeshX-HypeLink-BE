//
//package MeshX.HypeLink.common;
//
//import MeshX.HypeLink.auth.model.entity.Member;
//import MeshX.HypeLink.auth.model.entity.MemberRole;
//import MeshX.HypeLink.auth.model.entity.Store;
//import MeshX.HypeLink.auth.repository.MemberJpaRepositoryVerify;
//import MeshX.HypeLink.auth.repository.StoreJpaRepositoryVerify;
//import MeshX.HypeLink.direct_store.item.model.entity.StoreCategory;
//import MeshX.HypeLink.direct_store.item.model.entity.StoreItem;
//import MeshX.HypeLink.direct_store.item.model.entity.StoreItemDetail;
//import MeshX.HypeLink.direct_store.item.repository.StoreCategoryRepository;
//import MeshX.HypeLink.direct_store.item.repository.StoreItemDetailRepository;
//import MeshX.HypeLink.direct_store.item.repository.StoreItemRepository;
//import MeshX.HypeLink.direct_store.payment.model.entity.Payments;
//import MeshX.HypeLink.direct_store.payment.repository.PaymentRepository;
//import MeshX.HypeLink.head_office.customer.model.entity.Customer;
//import MeshX.HypeLink.head_office.customer.model.entity.CustomerReceipt;
//import MeshX.HypeLink.head_office.customer.model.entity.OrderItem;
//import MeshX.HypeLink.head_office.customer.model.entity.PaymentStatus;
//import MeshX.HypeLink.head_office.customer.repository.CustomerReceiptRepository;
//import MeshX.HypeLink.head_office.customer.repository.CustomerRepository;
//import MeshX.HypeLink.head_office.item.model.entity.*;
//import MeshX.HypeLink.head_office.item.repository.*;
//import MeshX.HypeLink.head_office.order.model.entity.PurchaseOrder;
//import MeshX.HypeLink.head_office.order.model.entity.PurchaseOrderState;
//import MeshX.HypeLink.head_office.order.repository.PurchaseOrderRepository;
//import jakarta.annotation.PostConstruct;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//import org.springframework.context.annotation.DependsOn;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//@DependsOn("baseMember")
//public class BaseDataGenerator {
//    private final CustomerRepository customerRepository;
//    private final CategoryRepository categoryRepository;
//    private final StoreCategoryRepository storeCategoryRepository;
//    private final ItemRepository itemRepository;
//    private final ItemDetailRepository itemDetailRepository;
//    private final StoreItemRepository storeItemRepository;
//    private final StoreItemDetailRepository storeItemDetailRepository;
//    private final CustomerReceiptRepository customerReceiptRepository;
//    private final PaymentRepository paymentRepository;
//    private final StoreJpaRepositoryVerify storeRepository;
//    private final MemberJpaRepositoryVerify memberRepository;
//    private final PurchaseOrderRepository purchaseOrderRepository;
//    private final ColorRepository colorRepository;
//    private final SizeRepository sizeRepository;
//
//    @PostConstruct
//    @Transactional
//    public void init() {
//        if (categoryRepository.findByCategory("상의").isPresent()) {
//            log.info("✅ Additional base data already exists (checked via Category).");
//            return;
//        }
//
//        log.info("✅ Generating additional base data...");
//
//        List<Store> stores = storeRepository.findAll();
//        if (stores.isEmpty()) {
//            log.warn("⚠️ No stores found. Skipping data generation.");
//            return;
//        }
//
//        createCustomers();
//        createItemsAndInventory(stores);
//        createCustomerReceipts(stores); // B2C 고객 주문 데이터
//        createPurchaseOrders(stores);   // B2B 발주 데이터 (통계용)
//
//        log.info("✅ Additional base data generation complete.");
//    }
//
//    private void createPurchaseOrders(List<Store> stores) {
//        Member supplier = memberRepository.findByEmail("hq@company.com");
//        List<ItemDetail> allItemDetails = itemDetailRepository.findAll();
//        Random random = new Random();
//
//        if (allItemDetails.isEmpty()) {
//            log.warn("⚠️ No ItemDetails found. Skipping PurchaseOrder generation.");
//            return;
//        }
//


//        List<PurchaseOrder> purchaseOrders = new ArrayList<>();
//        PurchaseOrderState[] states = PurchaseOrderState.values();
//
//        for (Store store : stores) {
//            Member requester = store.getMember();
//            if (requester == null) continue;
//
//            // Create 50 orders per store
//            for (int i = 0; i < 50; i++) {
//                ItemDetail itemDetail = allItemDetails.get(random.nextInt(allItemDetails.size()));
//                int quantity = random.nextInt(50) + 1;
//                int totalPrice = itemDetail.getItem().getUnitPrice() * quantity;
//                LocalDateTime orderDate = LocalDateTime.now().minusDays(random.nextInt(365));
//
//                PurchaseOrder purchaseOrder = PurchaseOrder.builder()
//                        .requester(requester)
//                        .supplier(supplier)
//                        .itemDetail(itemDetail)
//                        .quantity(quantity)
//                        .totalPrice(totalPrice)
//                        .purchaseOrderState(states[random.nextInt(states.length)])
//                        .build();
//
//                // Manually set createdAt because it's in BaseEntity and might not be set before save
//                // This is a workaround. A better approach is to use AuditingEntityListener.
//                // But for test data generation, this is acceptable.
//                try {
//                    java.lang.reflect.Field createdAtField = PurchaseOrder.class.getSuperclass().getDeclaredField("createdAt");
//                    createdAtField.setAccessible(true);
//                    createdAtField.set(purchaseOrder, orderDate);
//                } catch (NoSuchFieldException | IllegalAccessException e) {
//                    log.error("Failed to set createdAt field via reflection", e);
//                }
//
//                purchaseOrders.add(purchaseOrder);
//            }
//        }
//
//        purchaseOrderRepository.saveAll(purchaseOrders);
//        log.info("✅ Created " + purchaseOrders.size() + " PurchaseOrders for analytics.");
//    }
//
//    private void createCustomers() {
//        List<Customer> customers = new ArrayList<>();
//        for (int i = 1; i <= 100; i++) {
//            customers.add(Customer.builder()
//                    .name("고객" + i)
//                    .phone("010-1234-" + String.format("%04d", i))
//                    .birthDate(LocalDate.now().minusYears(20 + i % 30).minusDays(i % 365))
//                    .build());
//        }
//        customerRepository.saveAll(customers);
//        log.info("✅ Created 100 customers.");
//    }
//
//    private void createItemsAndInventory(List<Store> stores) {
//        String[] colorData = {"Red:#FF0000", "Green:#00FF00", "Blue:#0000FF", "Black:#000000", "White:#FFFFFF"};
//        List<Color> colors = new ArrayList<>();
//        for (String data : colorData) {
//            String[] parts = data.split(":");
//            colors.add(colorRepository.save(Color.builder().colorName(parts[0]).colorCode(parts[1]).build()));
//        }
//
//        String[] sizeNames = {"S", "M", "L", "XL"};
//        List<Size> sizes = new ArrayList<>();
//        for (String name : sizeNames) {
//            sizes.add(sizeRepository.save(Size.builder().size(name).build()));
//        }
//
//        String[] categoryNames = {"상의", "하의", "신발", "액세서리"};
//        for (String catName : categoryNames) {
//            Category category = categoryRepository.save(Category.builder().category(catName).build());
//
//            List<Item> items = new ArrayList<>();
//            for (int i = 1; i <= 10; i++) {
//                Item item = Item.builder()
//                        .koName(catName + " 상품 " + i)
//                        .itemCode("ITEM-" + catName + "-" + i)
//                        .category(category)
//                        .unitPrice(10000 + (i * 1000))
//                        .amount(10000 + (i * 1000))
//                        .build();
//                items.add(item);
//            }
//            itemRepository.saveAll(items);
//
//            List<ItemDetail> itemDetails = new ArrayList<>();
//            for(Item item : items) {
//                for (Color color : colors) {
//                    for (Size size : sizes) {
//                        itemDetails.add(ItemDetail.builder()
//                                .item(item)
//                                .color(color)
//                                .size(size)
//                                .stock(500)
//                                .itemDetailCode(item.getItemCode() + "-" + color.getColorName() + "-" + size.getSize())
//                                .build());
//                    }
//                }
//            }
//            itemDetailRepository.saveAll(itemDetails);
//
//            for (Store store : stores) {
//                StoreCategory storeCategory = storeCategoryRepository.save(StoreCategory.builder().category(catName).store(store).build());
//
//                for(Item item : items) {
//                    StoreItem storeItem = StoreItem.builder()
//                            .store(store)
//                            .category(storeCategory)
//                            .itemCode(item.getItemCode())
//                            .koName(item.getKoName())
//                            .unitPrice(item.getUnitPrice())
//                            .amount(item.getAmount())
//                            .build();
//                    storeItemRepository.save(storeItem);
//
//                    List<StoreItemDetail> storeItemDetails = new ArrayList<>();
//                    for (Color color : colors) {
//                        for (Size size : sizes) {
//                            storeItemDetails.add(StoreItemDetail.builder()
//                                    .item(storeItem)
//                                    .color(color.getColorName())
//                                    .colorCode(color.getColorCode())
//                                    .size(size.getSize())
//                                    .stock(100)
//                                    .itemDetailCode(item.getItemCode() + "-" + color.getColorName() + "-" + size.getSize())
//                                    .build());
//                        }
//                    }
//                    storeItemDetailRepository.saveAll(storeItemDetails);
//                }
//            }
//        }
//        log.info("✅ Created items and inventory.");
//    }
//
//    private void createCustomerReceipts(List<Store> stores) {
//        List<Customer> customers = customerRepository.findAll();
//        Random random = new Random();
//
//        if (customers.isEmpty()) {
//            log.warn("⚠️ No customers found. Skipping CustomerReceipt generation.");
//            return;
//        }
//
//        for (int i = 0; i < 500; i++) {
//            Customer customer = customers.get(random.nextInt(customers.size()));
//            Store store = stores.get(random.nextInt(stores.size()));
//            LocalDateTime orderDate = LocalDateTime.now().minusDays(random.nextInt(365)).minusHours(random.nextInt(24));
//
//            List<StoreItemDetail> storeItemDetailsInStore = storeItemDetailRepository.findAll().stream()
//                .filter(sid -> sid.getItem().getStore().getId().equals(store.getId()))
//                .collect(Collectors.toList());
//
//            if (storeItemDetailsInStore.isEmpty()) {
//                continue;
//            }
//
//            List<OrderItem> orderItems = new ArrayList<>();
//            int totalAmount = 0;
//            int itemCount = 1 + random.nextInt(5);
//
//            for (int j = 0; j < itemCount; j++) {
//                StoreItemDetail storeItemDetail = storeItemDetailsInStore.get(random.nextInt(storeItemDetailsInStore.size()));
//                int quantity = 1 + random.nextInt(3);
//                int unitPrice = storeItemDetail.getItem().getUnitPrice();
//                int price = unitPrice * quantity;
//
//                orderItems.add(OrderItem.builder()
//                        .storeItemDetail(storeItemDetail)
//                        .quantity(quantity)
//                        .unitPrice(unitPrice)
//                        .totalPrice(price)
//                        .build());
//                totalAmount += price;
//            }
//
//            CustomerReceipt receipt = CustomerReceipt.builder()
//                    .pgProvider("portone")
//                    .pgTid("pg_" + UUID.randomUUID().toString().replace("-", ""))
//                    .merchantUid("order_" + UUID.randomUUID().toString().replace("-", ""))
//                    .totalAmount(totalAmount)
//                    .discountAmount(0)
//                    .couponDiscount(0)
//                    .finalAmount(totalAmount)
//                    .store(store)
//                    .customer(customer)
//                    .status(PaymentStatus.PAID)
//                    .paidAt(orderDate)
//                    .build();
//
//            for (OrderItem orderItem : orderItems) {
//                receipt.addOrderItem(orderItem);
//            }
//            customerReceiptRepository.save(receipt);
//
//            Payments payment = Payments.builder()
//                    .customerReceipt(receipt)
//                    .amount(totalAmount)
//                    .status(MeshX.HypeLink.direct_store.payment.model.entity.PaymentStatus.PAID)
//                    .paidAt(orderDate)
//                    .build();
//            paymentRepository.save(payment);
//        }
//        log.info("✅ Created 500 CustomerReceipts.");
//    }
//}
