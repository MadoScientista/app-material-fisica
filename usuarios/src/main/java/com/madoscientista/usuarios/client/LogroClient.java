package com.madoscientista.usuarios.client;

import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(name = "logros", url = "localhost:8083")
public interface LogroClient {

}
