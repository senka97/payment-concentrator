package team16.bitcoinservice.service;

import team16.bitcoinservice.dto.FormFieldDTO;
import team16.bitcoinservice.model.Merchant;

import java.util.List;

public interface MerchantService {

    Merchant findByEmail(String email);
    List<FormFieldDTO> getFormFields();
    Merchant addNewMerchant(String merchantData, String email);
}
