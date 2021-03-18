package team16.paymentserviceprovider.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import team16.paymentserviceprovider.converter.SensitiveDataConverter;
import team16.paymentserviceprovider.dto.MerchantPCDTO;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
@Entity
@DynamicUpdate
@DiscriminatorValue("Merchant")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Merchant extends User {

    private String merchantName;

    @Column(unique = true)
    @Convert(converter = SensitiveDataConverter.class)
    private String merchantId;

    @Convert(converter = SensitiveDataConverter.class)
    private String merchantPassword;

    private boolean passwordChanged;
    private boolean pmChosen; //tek kada izabere paymentMethods postaje aktivan na LA
    private String activationUrl;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private App app;

    @OneToMany(mappedBy = "merchant", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Order> orders = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "merchants_paymentMethods", joinColumns = @JoinColumn(name = "merchant_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "payment_method_id", referencedColumnName = "id"))
    private Set<PaymentMethod> paymentMethods = new HashSet<>();

    @Column(name = "success_url")
    private String merchantSuccessUrl;

    @Column(name = "failed_url")
    private String merchantFailedUrl;

    @Column(name = "error_url")
    private String merchantErrorUrl;


    public Merchant(MerchantPCDTO merchantPCDTO, App app){
        this.merchantName = merchantPCDTO.getMerchantName();
        super.setEmail(merchantPCDTO.getMerchantEmail());
        this.passwordChanged = false;
        this.pmChosen = false;
        this.activationUrl = merchantPCDTO.getActivationUrl();
        this.merchantSuccessUrl = merchantPCDTO.getSuccessUrl();
        this.merchantFailedUrl = merchantPCDTO.getFailedUrl();
        this.merchantErrorUrl = merchantPCDTO.getErrorUrl();
        this.app = app;
    }
}
