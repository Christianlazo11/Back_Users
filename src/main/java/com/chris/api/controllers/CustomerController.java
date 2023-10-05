package com.chris.api.controllers;

import com.chris.api.models.entity.Customer;
import com.chris.api.models.services.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Customer customer = null;
        Map<String, Object> response = new HashMap<>();

        try {
            customer = customerService.findById(id);
        } catch (DataAccessException e) {
            response.put("message", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().toString()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(customer == null) {
            response.put("message", "El cliente ID: ".concat(id.toString()).concat(" No existe en la base de datos"));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Customer>(customer, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody Customer customer) {
        Customer newCustomer = null;
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
        Customer currentCustomer = customerService.findById(id);
        Customer customerUpdate = null;
        Map<String, Object> response = new HashMap<>();

        if(currentCustomer == null) {
            response.put("message", "Error: no se pudo editar, el cliente ID: ".concat(id.toString()
                    .concat(" No existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {
            currentCustomer.setName(customer.getName());
            currentCustomer.setLastName(customer.getLastName());
            currentCustomer.setEmail(customer.getEmail());
            currentCustomer.setCreateAt(customer.getCreateAt());

            customerUpdate = customerService.save(currentCustomer);

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

        Customer currentCustomer = customerService.findById(id);

        if(currentCustomer == null) {
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
