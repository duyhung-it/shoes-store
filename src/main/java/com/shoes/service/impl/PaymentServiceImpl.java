package com.shoes.service.impl;

import com.shoes.config.Constants;
import com.shoes.config.PaypalConfig;
import com.shoes.domain.*;
import com.shoes.repository.*;
import com.shoes.service.MailService;
import com.shoes.service.OrderService;
import com.shoes.service.PaymentService;
import com.shoes.service.dto.PaymentDTO;
import com.shoes.service.mapper.OrderMapper;
import com.shoes.service.mapper.PaymentMapper;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Payment}.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final Logger log = LoggerFactory.getLogger(PaymentServiceImpl.class);

    private final PaymentRepository paymentRepository;

    private final PaymentMapper paymentMapper;
    private final UserRepository userRepository;
    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final ShoesDetailsRepository shoesDetailsRepository;
    private final OrderDetailsRepository orderDetailsRepository;
    private final CartDetailsRepository cartDetailsRepository;
    private final MailService mailService;
    private final CartRepository cartRepository;

    @Override
    public PaymentDTO save(PaymentDTO paymentDTO) {
        log.debug("Request to save Payment : {}", paymentDTO);
        Payment payment = paymentMapper.toEntity(paymentDTO);
        payment = paymentRepository.save(payment);
        return paymentMapper.toDto(payment);
    }

    @Override
    public PaymentDTO update(PaymentDTO paymentDTO) {
        log.debug("Request to update Payment : {}", paymentDTO);
        Payment payment = paymentMapper.toEntity(paymentDTO);
        payment = paymentRepository.save(payment);
        return paymentMapper.toDto(payment);
    }

    @Override
    public Optional<PaymentDTO> partialUpdate(PaymentDTO paymentDTO) {
        log.debug("Request to partially update Payment : {}", paymentDTO);

        return paymentRepository
            .findById(paymentDTO.getId())
            .map(existingPayment -> {
                paymentMapper.partialUpdate(existingPayment, paymentDTO);

                return existingPayment;
            })
            .map(paymentRepository::save)
            .map(paymentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaymentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Payments");
        return paymentRepository.findAll(pageable).map(paymentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PaymentDTO> findOne(Long id) {
        log.debug("Request to get Payment : {}", id);
        return paymentRepository.findById(id).map(paymentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Payment : {}", id);
        paymentRepository.deleteById(id);
    }

    @Override
    public String createPayment(
        BigDecimal price,
        String receivedBy,
        String phone,
        String email,
        String address,
        BigDecimal shipPrice,
        String idOwner,
        String arrSanPham,
        String arrQuantity
    ) throws UnsupportedEncodingException {
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        BigDecimal amount = price.multiply(new BigDecimal("100")).setScale(0);
        String bankCode = "NCB";
        String vnp_TxnRef = PaypalConfig.getRandomNumber(8);
        String vnp_IpAddr = "127.0.0.1";
        String vnp_TmnCode = PaypalConfig.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");

        vnp_Params.put("vnp_BankCode", bankCode);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang: " + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);

        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put(
            "vnp_ReturnUrl",
            PaypalConfig.vnp_ReturnUrl +
            "?order=" +
            receivedBy +
            "_" +
            phone +
            "_" +
            email +
            "_" +
            address +
            "_" +
            shipPrice +
            "_" +
            idOwner +
            "_" +
            arrSanPham +
            "_" +
            arrQuantity
        );
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (!fieldValue.isEmpty())) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = PaypalConfig.hmacSHA512(PaypalConfig.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = PaypalConfig.vnp_PayUrl + "?" + queryUrl;
        return paymentUrl;
    }

    @Override
    public int orderReturn(HttpServletRequest request) {
        Map fields = new HashMap();
        for (Enumeration params = request.getParameterNames(); params.hasMoreElements();) {
            String fieldName = null;
            String fieldValue = null;
            fieldName = URLEncoder.encode((String) params.nextElement(), StandardCharsets.US_ASCII);
            fieldValue = URLEncoder.encode(request.getParameter(fieldName), StandardCharsets.US_ASCII);
            if ((fieldValue != null) && (!fieldValue.isEmpty())) {
                fields.put(fieldName, fieldValue);
            }
        }
        String vnp_SecureHash = request.getParameter("vnp_SecureHash");
        if (fields.containsKey("vnp_SecureHashType")) {
            fields.remove("vnp_SecureHashType");
        }
        if (fields.containsKey("vnp_SecureHash")) {
            fields.remove("vnp_SecureHash");
        }
        String signValue = PaypalConfig.hashAllFields(fields);
        if (signValue.equals(vnp_SecureHash)) {
            if ("00".equals(request.getParameter("vnp_TransactionStatus"))) {
                return 1;
            } else {
                return 0;
            }
        } else {
            return -1;
        }
    }

    @Override
    public Order payCallBack(HttpServletRequest request, HttpServletResponse response) {
        String vnp_ResponseCode = request.getParameter("vnp_ResponseCode");
        String orderCode = request.getParameter("vnp_TxnRef");
        String orderInfo = request.getParameter("order");
        long vnpAmountLong = Long.parseLong(request.getParameter("vnp_Amount")) / 100;
        BigDecimal price = BigDecimal.valueOf(vnpAmountLong);
        String[] orderInfoParts = orderInfo.split("_");

        if ("00".equals(vnp_ResponseCode)) {
            String receivedBy = orderInfoParts[0];
            String phone = orderInfoParts[1];
            String email = orderInfoParts[2];
            String address = orderInfoParts[3];
            BigDecimal shipPrice = new BigDecimal(orderInfoParts[4]);
            String arrSanPham = orderInfoParts[6];
            String arrQuantity = orderInfoParts[7];
            String idOwnerStr = orderInfoParts[5];
            String[] sanPhamParts = arrSanPham.split("a");
            String[] quantityParts = arrQuantity.split("b");
            User owner;
            if (!idOwnerStr.equalsIgnoreCase("null")) {
                long idOwner = Long.parseLong(idOwnerStr);
                owner = userRepository.findOneById(idOwner);
                Cart cart = cartRepository.findByOwnerId(owner.getId());
                List<CartDetails> cartDetailsList = cartDetailsRepository.findCartDetailsByCart(cart);
                for (CartDetails c : cartDetailsList) {
                    for (String idSP : sanPhamParts) {
                        Long shoesDetailId = Long.parseLong(idSP);
                        if (c.getShoesDetails().getId() == shoesDetailId) {
                            cartDetailsRepository.delete(c);
                        }
                    }
                }
            } else {
                owner = null;
            }

            Payment payment = new Payment();
            payment.setCode(orderCode);
            payment.setPaymentMethod(Constants.PAYMENT_METHOD.CREDIT);
            payment.setPaymentStatus(Constants.PAID_METHOD.ON);
            payment.setCreatedBy("system");
            payment.setCreatedDate(Instant.now());
            paymentRepository.save(payment);

            Order order = newOrder(orderCode, address, phone, shipPrice, price, receivedBy, owner, email, payment);
            List<OrderDetails> orderDetailsList = new ArrayList<>();
            ShoesDetails shoesDetails;
            OrderDetails orderDetails;
            for (int i = 0; i < sanPhamParts.length; i++) {
                orderDetails = new OrderDetails();
                long id = Long.parseLong(sanPhamParts[i]);
                Integer quantity = Integer.valueOf(quantityParts[i]);
                shoesDetails = shoesDetailsRepository.findByIdAndStatus(id, 1);
                orderDetails.setQuantity(quantity);
                orderDetails.setPrice(shoesDetails.getPrice());
                orderDetails.setStatus(1);
                orderDetails.setCreatedBy("system");
                orderDetails.setCreatedDate(Instant.now());
                orderDetails.setOrder(order);
                orderDetails.setShoesDetails(shoesDetails);
                orderDetailsList.add(orderDetails);

                orderRepository.save(order);
                shoesDetails.setQuantity(shoesDetails.getQuantity() - quantity);
                shoesDetailsRepository.save(shoesDetails);
            }
            orderDetailsRepository.saveAll(orderDetailsList);
            return order;
        }
        return null;
    }

    private Order newOrder(
        String orderCode,
        String address,
        String phone,
        BigDecimal shipPrice,
        BigDecimal price,
        String receivedBy,
        User owner,
        String email,
        Payment payment
    ) {
        Order order = new Order();
        order.setCode(orderCode);
        order.setAddress(address);
        order.setPhone(phone);
        order.setPaidMethod(Constants.PAYMENT_METHOD.CREDIT);
        order.setShipPrice(shipPrice);
        order.setTotalPrice(price);
        order.setReceivedBy(receivedBy);
        order.setStatus(Constants.ORDER_STATUS.PENDING);
        order.setCreatedBy("system");
        order.setCreatedDate(Instant.now());
        order.setOwner(owner);
        order.setMailAddress(email);
        order.setPayment(payment);
        return order;
    }
}
