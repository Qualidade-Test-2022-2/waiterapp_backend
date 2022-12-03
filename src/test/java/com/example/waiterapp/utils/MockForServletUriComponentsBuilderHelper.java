package com.example.waiterapp.utils;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.waiterapp.interfaces.Identifiable;

/**
 * MockForServletUriComponentsBuilderHelper
 */
public class MockForServletUriComponentsBuilderHelper {

  public static ResponseEntity<Object> mockRequestResponse(Identifiable object) {
    MockHttpServletRequest request = new MockHttpServletRequest();
    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));


    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(object.getId()).toUri();

    return ResponseEntity.created(location).build();
  }
}
