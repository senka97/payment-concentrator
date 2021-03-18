export const ROUTES = {};

export const SERVICES_ENDPOINTS = {
  BANK_PAYMENT_SERVICE: "/bank-payment-service",
  PAYPAL_PAYMENT_SERVICE: "/paypal-payment-service",
  BITCOIN_PAYMENT_SERVICE: "/bitcoin-payment-service",
  PAYMENT_PROVIDER_SERVICE: "/psp-service/api",
};

export const BANK_SERVICE_ENDPOINTS = {
  TEST: "/api/test",
};

export const PAYPAL_SERVICE_ENDPOINTS = {
  TEST: "/api/test",
  PAY: "/api/pay/",
  EXECUTE: "/api/pay/execute",
  CANCEL: "/api/pay/cancel/",
  CREATE_SUBSCRIPTION: "/api/subscription/create",
  EXECUTE_SUBSCRIPTION: "/api/subscription/execute",
  CANCEL_SUBSCRIPTION: "/api/subscription/cancel/",
};

export const BITCOIN_SERVICE_ENDPOINTS = {
  TEST: "/api/test",
};

export const PSP_ENDPOINTS = {
  AVAILABLE_SERVICES: "/test/available-services",
  PAYMENTS: "/payments",
  ORDER: "/order",
  APP: "/app",
  SUBSCRIPTION: "/subscriptions/subscription",
  AUTH_LOGIN: "/auth/login",
  AUTH_CHANGE_PASSWORD: "/auth/changePassword",
  MERCHANT_INFO: "/merchant/info",
  MERCHANT_ADD_PAYMENT_METHOD: "/merchant/paymentMethod",
  PAYMENT_METHOD_FORMS_DATA: "/paymentMethod/formsData",
  PAYMENT_METHOD: "/paymentMethod",
  CREATE_BILLING_PLAN: "/billing-plans/create",
};
