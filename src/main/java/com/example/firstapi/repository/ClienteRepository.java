package com.example.firstapi.repository;

import com.example.firstapi.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// herdando de jpaRepository
// Spring data jpa forne uma implementação em tempo de execução, essa implementação com varios métodos
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
