package team16.bankpaymentservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import team16.bankpaymentservice.dto.MerchantDTO;
import team16.bankpaymentservice.model.Merchant;
import team16.bankpaymentservice.service.MerchantService;

@RestController
@RequestMapping(value="/api/merchant")
public class MerchantController {

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private RestTemplate restTemplate;

    Logger logger = LoggerFactory.getLogger(MerchantController.class);

    @GetMapping(value = "/formFields")
    public ResponseEntity getFormFields(){

        return new ResponseEntity(this.merchantService.getFormFields(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity addNewMerchant(@RequestHeader("Authorization") String authToken, @RequestBody String merchantData){
        System.out.println(authToken);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authToken);
        HttpEntity<?> httpEntity = new HttpEntity<Object>(headers);
        String email = "";

        try {
            ResponseEntity<String> response = restTemplate.exchange("https://localhost:8083/psp-service/api/merchant/current", HttpMethod.GET, httpEntity, String.class);
            email = response.getBody();
        }catch(Exception e){
            e.printStackTrace();
            logger.error("Error occurred while authenticating merchant.");
            return ResponseEntity.badRequest().body("Error occurred while authenticating merchant.");
        }

        Merchant merchant = this.merchantService.findByEmail(email);
        if(merchant != null){
            logger.error("This merchant has already chosen bank payment method.");
            return ResponseEntity.badRequest().body("This merchant has already chosen bank payment method.");
        }

        Merchant saved = null;
        try {
            saved = merchantService.addNewMerchant(merchantData, email);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        // vratiti informacije o merchant id i merchant password koji su se sad generisali na psp
        System.out.println("New merchant: " + saved.getId());
        // zahtev se salje na PSP
        MerchantDTO merchantDTO = new MerchantDTO(saved.getMerchantEmail(), saved.getMerchantId(), saved.getPassword());
        HttpEntity<MerchantDTO> request = new HttpEntity<>(merchantDTO, headers);

        try {
            ResponseEntity<MerchantDTO> response = restTemplate.exchange("https://localhost:8083/psp-service/api/merchant/save-info-from-bank", HttpMethod.POST, request, MerchantDTO.class);
            if(response.getBody() == null) {
                logger.error("Error occurred while saving merchant on Payment Concentrator.");
                return ResponseEntity.badRequest().body("Error occurred while saving merchant on Payment Concentrator.");
            }
            System.out.println("Response: " + response.getBody().getMerchantId() + " | " + response.getBody().getMerchantPassword());
            logger.info("Merchant info successfully updated on PSP.");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error occurred while saving merchant on Payment Concentrator.");
            return ResponseEntity.badRequest().body("Error occurred while saving merchant on Payment Concentrator.");
        }

        return ResponseEntity.ok().build();

    }
}
