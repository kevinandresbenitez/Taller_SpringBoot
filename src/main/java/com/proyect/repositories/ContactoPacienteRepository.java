package com.proyect.repositories;

import com.proyect.models.ContactoPaciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactoPacienteRepository extends JpaRepository<ContactoPaciente,Long> {
}
