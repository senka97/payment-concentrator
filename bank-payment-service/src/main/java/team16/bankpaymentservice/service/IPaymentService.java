package team16.bankpaymentservice.service;

import team16.bankpaymentservice.model.Payment;

public interface IPaymentService {

    Payment findById(Long id);
    Payment findByTransactionId(Long transactionId);
    Payment update(Payment payment);
}
