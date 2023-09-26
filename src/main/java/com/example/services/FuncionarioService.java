package com.example.services;


import com.example.repositorys.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import com.example.models.Funcionario;

@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;

    @Autowired
    public FuncionarioService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }
    
    public void crearFuncionario(Funcionario fun1){

        this.funcionarioRepository.save(fun1);
    }
    
    public List<Funcionario> obtenerFuncionarios(){

        return this.funcionarioRepository.findAll();
    }

}
