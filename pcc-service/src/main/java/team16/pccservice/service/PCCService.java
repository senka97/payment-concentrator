package team16.pccservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import team16.pccservice.config.EndpointConfig;
import team16.pccservice.config.RestConfig;
import team16.pccservice.dto.PCCRequestDTO;
import team16.pccservice.dto.PCCResponseDTO;
import team16.pccservice.enums.Status;
import team16.pccservice.exceptions.BankException;
import team16.pccservice.exceptions.ExistingAcquirerOrder;
import team16.pccservice.exceptions.InvalidDataException;
import team16.pccservice.model.Bank;
import team16.pccservice.model.PaymentRequest;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class PCCService {

    @Autowired
    private BankService bankService;

    @Autowired
    private PaymentRequestService paymentRequestService;

    private ValidationService validationService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RestConfig configuration;

    Logger logger = LoggerFactory.getLogger(PCCService.class);

    public PCCService() {
        validationService = new ValidationService();
    }

    public PCCResponseDTO cratePaymentRequest(PCCRequestDTO dto) throws InvalidDataException,
            ExistingAcquirerOrder, BankException, URISyntaxException {

        validatePCCRequestDTO(dto);

        PaymentRequest paymentRequest = paymentRequestService.create(dto);
        logger.info("PCC Payment Request created");

        // dodati na request banke
        Bank acquirerBank = findBank(dto.getMerchantPan(), "acquirer");
        Bank issuerBank = findBank(dto.getClientPan(), "issuer");

        paymentRequest.setAcquirerBank(acquirerBank);
        paymentRequest.setIssuerBank(issuerBank);
        paymentRequest.setStatus(Status.CREATED);

        PaymentRequest newRequest = paymentRequestService.update(paymentRequest);
        logger.info("PCC Payment Request updated");

        PCCRequestDTO requestDTO = new PCCRequestDTO(dto.getClientPan(), dto.getSecurityNumber(), dto.getCardHolderName(),
                dto.getExpirationDate(), dto.getMerchantOrderId(), dto.getMerchantTimestamp(), dto.getPaymentId(),
                newRequest.getId(), dto.getAcquirerOrderId(), dto.getAcquirerTimestamp(), dto.getMerchantPan());

        // zahtev se salje na Issuer Bank
        HttpEntity<PCCRequestDTO> request = new HttpEntity<>(requestDTO);
        ResponseEntity<PCCResponseDTO> response = null;

        try {
            logger.info("Sending request to Issuer Bank service");
            response = restTemplate.exchange(
                    getEndpoint(), HttpMethod.POST, request, PCCResponseDTO.class);
            logger.info("Received response from Issuer Bank service");
        } catch (RestClientException e) {
            logger.error("RestTemplate error");
            e.printStackTrace();
        }

        PCCResponseDTO pccResponseDTO = response.getBody();

        return pccResponseDTO;
    }

    private URI getEndpoint() throws URISyntaxException {
        return new URI(configuration.url() + EndpointConfig.ISSUER_BANK_BASE_URL + "/api/issuer/pcc-payment-request");
    }

    private Bank findBank(String pan, String bankName) throws BankException {
        Bank bank;
        try {
            bank = bankService.findByCode(pan.substring(0, 3));
            if(bank == null) {
                throw new BankException("Nonexistent " + bankName + "  bank");
            }
        } catch (Exception e) {
            throw new BankException("Error while finding " + bankName + " bank");
        }
        return bank;
    }

    private void validatePCCRequestDTO(PCCRequestDTO dto) throws InvalidDataException, ExistingAcquirerOrder {
        if(!validationService.validateString(dto.getClientPan()) ||
           !validationService.validateString(dto.getSecurityNumber()) ||
           !validationService.validateString(dto.getCardHolderName()) ||
           !validationService.validateString(dto.getExpirationDate())) {
            throw new InvalidDataException("Empty client card information");
        }
        if(!validationService.validateString(dto.getMerchantPan())) {
            throw new InvalidDataException("Empty merchant card information");
        }
        if(dto.getAcquirerOrderId() == null) {
            throw new InvalidDataException("Empty acquirer order id");
        }
        if(dto.getAcquirerTimestamp() == null) {
            throw new InvalidDataException("Empty acquirer timestamp");
        }
        if(paymentRequestService.findByAcquirerOrderId(dto.getAcquirerOrderId()) != null) {
            throw new ExistingAcquirerOrder("Payment request with existing acquirer order id already exists");
        }
    }

    public PCCResponseDTO makeFailureResponse(Long merchantOrderId) {

        logger.info("Make failure response function is called");

        PCCResponseDTO responseDTO = new PCCResponseDTO();
        responseDTO.setMerchantOrderId(merchantOrderId);
        responseDTO.setStatus(Status.FAILED);

        return responseDTO;
    }

    public PCCResponseDTO makeSuccessResponse(PaymentRequest request) {

        logger.info("Make success response function is called");

        PCCResponseDTO responseDTO = new PCCResponseDTO();

        responseDTO.setAcquirerOrderId(request.getAcquirerOrderID());
        responseDTO.setAcquirerTimestamp(request.getAcquirerTimestamp());
        responseDTO.setMerchantOrderId(request.getMerchantOrderId());
        responseDTO.setPaymentId(request.getPaymentId());
        responseDTO.setIssuerOrderId(request.getIssuerOrderID());
        responseDTO.setIssuerTimestamp(request.getIssuerTimestamp());

        responseDTO.setStatus(Status.COMPLETED);

        return responseDTO;
    }

}
