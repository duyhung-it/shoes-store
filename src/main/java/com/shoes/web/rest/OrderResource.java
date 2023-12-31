package com.shoes.web.rest;

import com.shoes.domain.Order;
import com.shoes.domain.User;
import com.shoes.repository.OrderRepository;
import com.shoes.service.MailService;
import com.shoes.service.OrderService;
import com.shoes.service.dto.*;
import com.shoes.web.rest.errors.BadRequestAlertException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.*;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.shoes.domain.Order}.
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderResource {

    private final Logger log = LoggerFactory.getLogger(OrderResource.class);

    private static final String ENTITY_NAME = "order";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderService orderService;

    private final OrderRepository orderRepository;
    private final MailService mailService;

    /**
     * {@code POST  /orders} : Create a new order.
     *
     * @param orderDTO the orderDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderDTO, or with status {@code 400 (Bad Request)} if the order has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/orders")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody @Valid OrderCreateDTO orderDTO) throws URISyntaxException {
        log.debug("REST request to save Order : {}", orderDTO);
        if (orderDTO.getId() != null) {
            throw new BadRequestAlertException("A new order cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderDTO result = orderService.save(orderDTO);
        byte[] byteArrayResource = this.orderService.getMailVerify(result.getId());
        //            System.out.println(byteArrayResource);
        mailService.sendEmail1(result.getMailAddress(), "[SPORT-KICK] Thông báo đặt hàng thành công", "", byteArrayResource, true, true);
        return ResponseEntity
            .created(new URI("/api/orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /orders/:id} : Updates an existing order.
     *
     * @param id       the id of the orderDTO to save.
     * @param orderDTO the orderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderDTO,
     * or with status {@code 400 (Bad Request)} if the orderDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/orders/{id}")
    public ResponseEntity<OrderDTO> updateOrder(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrderDTO orderDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Order : {}, {}", id, orderDTO);
        if (orderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orderDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrderDTO result = orderService.update(orderDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, orderDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /orders/:id} : Partial updates given fields of an existing order, field will ignore if it is null
     *
     * @param id       the id of the orderDTO to save.
     * @param orderDTO the orderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderDTO,
     * or with status {@code 400 (Bad Request)} if the orderDTO is not valid,
     * or with status {@code 404 (Not Found)} if the orderDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the orderDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/orders/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OrderDTO> partialUpdateOrder(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrderDTO orderDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Order partially : {}, {}", id, orderDTO);
        if (orderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orderDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrderDTO> result = orderService.partialUpdate(orderDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, orderDTO.getId().toString())
        );
    }

    @GetMapping("orders/revenue/monthly")
    public ResponseEntity<BigDecimal[]> getMonthlyRevenue() {
        List<Object[]> monthlyRevenues = orderRepository.getMonthlyRevenue();
        BigDecimal[] revenues = new BigDecimal[12];
        Arrays.fill(revenues, BigDecimal.ZERO);
        for (Object[] monthRevenue : monthlyRevenues) {
            int month = (Integer) monthRevenue[0] - 1; // Chuyển đổi sang chỉ số mảng (0 đến 11)
            BigDecimal revenue = (BigDecimal) monthRevenue[1];
            revenues[month] = revenue;
        }
        return ResponseEntity.ok().body(revenues);
    }

    @GetMapping("revenue/growth-percentage")
    public BigDecimal getRevenueGrowthPercentage() {
        Map<String, BigDecimal> revenues = orderRepository.getRevenueComparison();
        BigDecimal thisWeekRevenue = revenues.get("this_week_revenue");
        BigDecimal lastWeekRevenue = revenues.get("last_week_revenue");

        if (lastWeekRevenue == null || lastWeekRevenue.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO; // Tránh chia cho zero
        }

        return thisWeekRevenue
            .subtract(lastWeekRevenue)
            .divide(lastWeekRevenue, 2, BigDecimal.ROUND_HALF_UP)
            .multiply(new BigDecimal("100"));
    }

    /**
     * {@code GET  /orders} : get all the orders.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orders in body.
     */
    @GetMapping("/orders")
    public ResponseEntity<List<OrderDTO>> getAllOrders(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Orders");
        Page<OrderDTO> page = orderService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/orders/count")
    public ResponseEntity<Long> getAllOrders() {
        log.debug("REST request to get a page of Orders");
        return ResponseEntity.ok().body(orderRepository.count());
    }

    @GetMapping("/orders/price")
    public ResponseEntity<BigDecimal> getAllOrderss() {
        log.debug("REST request to get a page of Orders");
        return ResponseEntity.ok().body(orderRepository.sumTotalPriceForStatusThree());
    }

    @GetMapping("/orders/seven-day")
    public ResponseEntity<BigDecimal> getAllOrdersss() {
        log.debug("REST request to get a page of Orders");
        Instant endDate = ZonedDateTime.now().toInstant();
        Instant startDate = endDate.minusSeconds(60 * 60 * 24 * 7);
        return ResponseEntity.ok().body(orderRepository.calculateRevenueForLastSevenDays(startDate, endDate));
    }

    @GetMapping("/order-owner/{id}")
    public ResponseEntity<List<OrderDTO>> getOrderByOwnerId(Pageable pageable, @PathVariable Long id) {
        Page<OrderDTO> page = orderService.getOrderByOwnerId(id, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /orders/:id} : get the "id" order.
     *
     * @param id the id of the orderDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderResDTO> getOrder(@PathVariable Long id) {
        log.debug("REST request to get Order : {}", id);
        OrderResDTO orderDTO = orderService.findOne(id);
        return ResponseEntity.ok(orderDTO);
    }

    /**
     * {@code DELETE  /orders/:id} : delete the "id" order.
     *
     * @param id the id of the orderDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        log.debug("REST request to delete Order : {}", id);
        orderService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    @PostMapping("/orders/search")
    public ResponseEntity<List<OrderSearchResDTO>> search(@RequestBody OrderSearchReqDTO orderSearchReqDTO) {
        return ResponseEntity.ok(orderService.search(orderSearchReqDTO));
    }

    @GetMapping("/orders/quantity")
    public ResponseEntity<Map<Integer, Integer>> getQuantity() {
        return ResponseEntity.ok(orderService.getQuantityPerOrderStatus());
    }

    @PostMapping("/orders/verifyOrder")
    public ResponseEntity<Void> verifyOrder(@RequestBody List<Long> orderIds) {
        this.orderService.verifyOrder(orderIds);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/orders/update-status/{id}")
    public ResponseEntity<Void> updateStatus(@PathVariable("id") Long id) {
        this.orderService.updateStatus(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/orders/cancel/{id}")
    public ResponseEntity<Void> cancelOrder(@PathVariable("id") List<Long> id) {
        this.orderService.cancelOrder(id);
        for (Long ids : id) {
            Order order = orderRepository.findById(ids).orElse(new Order());
            byte[] byteArrayResource = this.orderService.getCancelOrderMail(ids);
            mailService.sendEmail1(
                order.getMailAddress(),
                "[SPORT-KICK] Thông báo đơn hàng của bạn đã bị hủy",
                "",
                byteArrayResource,
                true,
                true
            );
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/orders/get-mail/{id}")
    public ResponseEntity<byte[]> getMail(@PathVariable("id") Long id) {
        byte[] byteArrayResource = this.orderService.getMailVerify(id);
        mailService.sendEmail1("hungndph26995@fpt.edu.vn", "[SPORT-KICK] Thông báo đặt hàng thành công", "", byteArrayResource, true, true);
        return ResponseEntity.ok(byteArrayResource);
    }

    @GetMapping("/users/find")
    public ResponseEntity<?> findByLogin(@RequestParam Integer status, @RequestParam String login) {
        List<Order> user = orderService.getOrderByStatusAndOwnerLogin(status, login);
        return ResponseEntity.ok(user);
    }
}
