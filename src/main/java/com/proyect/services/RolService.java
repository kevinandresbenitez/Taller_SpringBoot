/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyect.services;
import com.proyect.models.Funcionario;
import com.proyect.models.Rol;
import com.proyect.repositories.FuncionarioRepository;
import com.proyect.repositories.RolRepository;
import java.util.List;
import java.util.Optional;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author kevin
 */

/**
 * el servicio RolService maneja la logica asociada con los roles
 * interactua con el repositorio RolRepository para realizar operaciones de persistencia y recuperación de datos
 */
@Service
@Getter
@Setter
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    /**
     * obtiene una lista de todos los roles disponibles
     *
     * @return lista de objetos Rol
     */
    public List<Rol> listarRoles() {
        return this.rolRepository.findAll();
    }

    /**
     * crea un nuevo rol y lo guarda en el repositorio
     *
     * @param rol objeto Rol que se va a crear y almacenar
     */
    public void crear(Rol rol) {
        this.rolRepository.save(rol);
    }

    /**
     * elimina un rol por su identificador unico
     *
     * @param id identificador único del rol que se va a eliminar
     */
    public void eliminarPorId(long id) {
        this.rolRepository.deleteById(id);
    }

    /**
     * obtiene un rol por su identificador unico
     *
     * @param id identificador único del rol
     * @return objeto Optional que puede contener un objeto Rol si se encuentra
     */
    public Optional<Rol> obtenerPorId(long id) {
        return this.rolRepository.findById(id);
    }

    /**
     * obtiene una lista de roles asociados a un funcionario por su identificador unico
     *
     * @param id identificador único del funcionario
     * @return lista de objetos Rol asociados al funcionario
     */
    public List<Rol> obtenerRolesDelFuncionario(long id) {
        return this.rolRepository.findByFuncionariosId(id);
    }

    /**
     * verifica si existe un rol con el nombre proporcionado
     *
     * @param nombre nombre del rol a verificar.
     * @return `true` si existe un rol con el nombre, `false` en caso contrario
     */
    public boolean existsByNombre(String nombre) {
        return rolRepository.existsByNombre(nombre);
    }
}