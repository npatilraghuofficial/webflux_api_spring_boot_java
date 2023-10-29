package com.poc.webflux_api.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;

import org.springframework.web.reactive.function.server.ServerResponse;

import com.poc.webflux_api.dao.CustomerDao;
import com.poc.webflux_api.dto.Customer;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerHandler {

@Autowired  
private CustomerDao dao;
    
    public Mono<ServerResponse> loadCustomers(ServerRequest request){
        Flux<Customer> customerList= dao.getCustomerList();
        return ServerResponse.ok().body(customerList,Customer.class);
    }


    public Mono<ServerResponse> findCustomer(ServerRequest request){
        int customerId =Integer.valueOf(request.pathVariable("input"));
        Mono<Customer> customerMono = dao.getCustomerList().filter(c->c.getId()==customerId).next();
        return ServerResponse.ok().body(customerMono,Customer.class);
        }



        //save customer
        public Mono<ServerResponse> saveCustomer(ServerRequest request){
            Mono<Customer> customerMono = request.bodyToMono(Customer.class);
            Mono<String> saveResponse = customerMono.map(dto-> dto.getId()+" "+dto.getName());
            return ServerResponse.ok().body(saveResponse,String.class);
            }





}

