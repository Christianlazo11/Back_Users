package com.chris.api.controllers;

import com.chris.api.entities.Customer;
import com.chris.api.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/clientes")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    @GetMapping
    public List<Customer> findAll() {
        return customerService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        Optional<Customer> customer;
        Map<String, Object> response = new HashMap<>();

        try {
            customer = customerService.findById(id);
        } catch (DataAccessException e) {
            response.put("message", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().toString()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(!customer.isPresent()) {
            response.put("message", "El cliente ID: ".concat(id.toString()).concat(" No existe en la base de datos"));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Customer>(customer.get(), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody Customer customer) {
        Optional<Customer> newCustomer;
        Map<String, Object> response = new HashMap<>();

        try {
            newCustomer = customerService.save(customer);
        } catch (DataAccessException e) {
            response.put("message", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "El cliente ha sido creado con éxito!");
        response.put("cliente", newCustomer);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> update(@RequestBody Customer customer, @PathVariable Long id) {
        Optional<Customer> currentCustomer = customerService.findById(id);
        Optional<Customer> customerUpdate;
        Map<String, Object> response = new HashMap<>();

        if(!currentCustomer.isPresent()) {
            response.put("message", "Error: no se pudo editar, el cliente ID: ".concat(id.toString()
                    .concat(" No existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {
            currentCustomer.get().setName(customer.getName());
            currentCustomer.get().setLastName(customer.getLastName());
            currentCustomer.get().setEmail(customer.getEmail());
            currentCustomer.get().setCreateAt(customer.getCreateAt());

            customerUpdate = customerService.save(currentCustomer.get());

        } catch (DataAccessException e) {
            response.put("message", "Error al actualizar el cliente en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "El cliente ha sido actualizado con éxito!");
        response.put("cliente", customerUpdate);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        Optional<Customer> currentCustomer = customerService.findById(id);

        if(!currentCustomer.isPresent()) {
            response.put("message", "Error: no se pudo eliminar, el cliente ID: ".concat(id.toString()
                    .concat(" No existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {
            customerService.delete(id);
        } catch (DataAccessException e) {
            response.put("message", "Error al eliminar el cliente en la base de datos!");
            response.put("cliente", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "Cliente eliminado con éxito!");

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

    }

}
