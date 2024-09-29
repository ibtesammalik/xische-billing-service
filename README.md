com.xische.xischebilling.controller.CalculateDiscountController
have to end "/api/calculate"
it accept two parameters header and body 
@RequestHeader HttpHeaders headers ,@Valid @RequestBody CalculateBillRequest BillRequest)

CalculateBillRequest validation had added in DTO to get valid input

header bearer token will be send to valid endpoint


authentication server port and ip to secure endpoint
spring.security.oauth2.resourceserver.jwt.issuer-uri=http://localhost:1001
--------------------------------------------------------------------------------------------------
com.xische.xischebilling.service.CalculateService
aspecting CalculateBillRequest object in order to calculate bill.

//properties are set for customer to make everything configurable

#employee discount
employee.discount=30.0

#affiliate discount
affiliate.discount=10.0

#customer discount
customer.discount=5
#customer discount On tenture 2 years
customer.discount.tenture=2

#every 100 dollar 5 rupee discount
discount.hunderd=5
discount.bill=100




com.xische.xischebilling.logs.LoggingAspect
AOP had implemented in LoggingAspect class to log request and response

tag had created of log @LogExecution

com.xische.xischebilling.client.CurrencyConversionClient
@FeignClient is implement to fetch conversion rate

circuit breaker is implement and fallback method is define com.xische.xischebilling.client.FallbackConversionRate

properties file are 
resilience4j.circuitbreaker.instances.CurrencyConversionClientgetConversionRate.minimumNumberOfCalls=10
resilience4j.timelimiter.instances.CurrencyConversionClientgetConversionRate.timeoutDuration=10s

restcontroller exception is implemented
com.xische.xischebilling.exception.ExceptionHandler

common exception are override like
1) NullPointerException
2) BillingServiceException
3) MethodArgumentNotValidException
