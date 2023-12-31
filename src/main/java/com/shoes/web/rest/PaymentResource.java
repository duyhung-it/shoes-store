package com.shoes.web.rest;

import com.shoes.config.Constants;
import com.shoes.domain.*;
import com.shoes.repository.*;
import com.shoes.service.MailService;
import com.shoes.service.OrderService;
import com.shoes.service.PaymentService;
import com.shoes.service.dto.PaymentDTO;
import com.shoes.service.mapper.OrderMapper;
import com.shoes.web.rest.errors.BadRequestAlertException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.shoes.domain.Payment}.
 */
@RestController
@RequestMapping("/api")
public class PaymentResource {

    private final Logger log = LoggerFactory.getLogger(PaymentResource.class);

    private static final String ENTITY_NAME = "payment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaymentService paymentService;

    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final ShoesDetailsRepository shoesDetailsRepository;
    private final OrderDetailsRepository orderDetailsRepository;
    private final CartDetailsRepository cartDetailsRepository;
    private final MailService mailService;
    private final CartRepository cartRepository;

    public PaymentResource(
        PaymentService paymentService,
        UserRepository userRepository,
        PaymentRepository paymentRepository,
        OrderService orderService,
        OrderMapper orderMapper,
        OrderRepository orderRepository,
        ShoesDetailsRepository shoesDetailsRepository,
        OrderDetailsRepository orderDetailsRepository,
        CartDetailsRepository cartDetailsRepository,
        MailService mailService,
        CartRepository cartRepository
    ) {
        this.paymentService = paymentService;
        this.userRepository = userRepository;
        this.paymentRepository = paymentRepository;
        this.orderService = orderService;
        this.orderMapper = orderMapper;
        this.orderRepository = orderRepository;
        this.shoesDetailsRepository = shoesDetailsRepository;
        this.orderDetailsRepository = orderDetailsRepository;
        this.cartDetailsRepository = cartDetailsRepository;
        this.mailService = mailService;
        this.cartRepository = cartRepository;
    }

    @GetMapping("/create-payment")
    public String newPayments(
        @RequestParam("price") BigDecimal price,
        @RequestParam("receivedBy") String receivedBy,
        @RequestParam("phone") String phone,
        @RequestParam("email") String email,
        @RequestParam("address") String address,
        @RequestParam("province") Integer province,
        @RequestParam("district") Integer district,
        @RequestParam("ward") Integer ward,
        @RequestParam("shipPrice") BigDecimal shipPrice,
        @RequestParam("idOwner") String idOwner,
        @RequestParam("arrSanPham") String arrSanPham,
        @RequestParam("arrQuantity") String arrQuantity,
        @RequestParam("arrPriceDiscount") String arrPriceDiscount
    ) throws UnsupportedEncodingException {
        String paymentUrl = paymentService.createPayment(
            price,
            receivedBy,
            phone,
            email,
            address,
            province,
            district,
            ward,
            shipPrice,
            idOwner,
            arrSanPham,
            arrQuantity,
            arrPriceDiscount
        );
        return paymentUrl;
    }

    @GetMapping("/payment-callback")
    public void GetMapping(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Order order = paymentService.payCallBack(request, response);
        if (Objects.nonNull(order)) {
            System.out.println(order.getId());
            byte[] byteArrayResource = this.orderService.getMailVerify(order.getId());
            //            System.out.println(byteArrayResource);
            mailService.sendEmail1(order.getMailAddress(), "[SPORT-KICK] Thông báo đặt hàng thành công", "", byteArrayResource, true, true);
            response.sendRedirect("http://localhost:4200/client/pay-success");
        } else {
            response.sendRedirect("http://localhost:4200/client/pay-faile");
        }
    }

    @PostMapping("/payments")
    public ResponseEntity<PaymentDTO> createPayment(@RequestBody PaymentDTO paymentDTO) throws URISyntaxException {
        log.debug("REST request to save Payment : {}", paymentDTO);
        if (paymentDTO.getId() != null) {
            throw new BadRequestAlertException("A new payment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaymentDTO result = paymentService.save(paymentDTO);
        return ResponseEntity
            .created(new URI("/api/payments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payments/:id} : Updates an existing payment.
     *
     * @param id         the id of the paymentDTO to save.
     * @param paymentDTO the paymentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentDTO,
     * or with status {@code 400 (Bad Request)} if the paymentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paymentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payments/{id}")
    public ResponseEntity<PaymentDTO> updatePayment(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PaymentDTO paymentDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Payment : {}, {}", id, paymentDTO);
        if (paymentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PaymentDTO result = paymentService.update(paymentDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, paymentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /payments/:id} : Partial updates given fields of an existing payment, field will ignore if it is null
     *
     * @param id         the id of the paymentDTO to save.
     * @param paymentDTO the paymentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentDTO,
     * or with status {@code 400 (Bad Request)} if the paymentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the paymentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the paymentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/payments/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PaymentDTO> partialUpdatePayment(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PaymentDTO paymentDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Payment partially : {}, {}", id, paymentDTO);
        if (paymentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PaymentDTO> result = paymentService.partialUpdate(paymentDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, paymentDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /payments} : get all the payments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of payments in body.
     */
    @GetMapping("/payments")
    public ResponseEntity<List<PaymentDTO>> getAllPayments(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Payments");
        Page<PaymentDTO> page = paymentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /payments/:id} : get the "id" payment.
     *
     * @param id the id of the paymentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paymentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payments/{id}")
    public ResponseEntity<PaymentDTO> getPayment(@PathVariable Long id) {
        log.debug("REST request to get Payment : {}", id);
        Optional<PaymentDTO> paymentDTO = paymentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paymentDTO);
    }

    /**
     * {@code DELETE  /payments/:id} : delete the "id" payment.
     *
     * @param id the id of the paymentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payments/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        log.debug("REST request to delete Payment : {}", id);
        paymentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
