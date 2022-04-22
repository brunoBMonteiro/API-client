package com.example.firstapi.repository;
import com.example.firstapi.model.Cliente;
import com.example.firstapi.requestsdto.ClientePutRequestBody;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
