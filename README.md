# payment-concentrator

Predmetni projekat iz predmeta "Sistemi elektronskog plaćanja".

Veb aplikacija koncentratora plaćanja. 

Koncentrator plaćanja ima svoju frontend aplikaciju: **payment-concentrator-frontend** a za backend ima skup mikroservisa: 
 - **payment-service-provider** - kao poseban servis za čuvanje informacija o klijentima i načinima plaćanja
 - **bank-payment-service** 
 - **paypal-payment-service**
 - **bitcoin-payment-service** - kao posebni servisi za svaki od omogućenih načina plaćanja

Uz banku dolazi i posebna frontend aplikacija **bank-payment-service-frontend** i **pcc-service** koji predstavlja payment card center koji se korisi prilikom komunikacije između dve različite banke.

Korišćene tehnologije:

  - Backend
     * SpringBoot
     * MySql v8.0.18
  - Frontend
      * React

    
