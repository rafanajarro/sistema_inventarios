package com.inventario.sistemainvetario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventario.sistemainvetario.model.MedidasPeso;
import com.inventario.sistemainvetario.repository.MedidasLongitudRepository;
import com.inventario.sistemainvetario.repository.MedidasPesoRepository;

@Service
public class MedidasPesoService {
    @Autowired
    private MedidasPesoRepository medidasPesoRepository;

    public List<MedidasPeso> obtenerTodos() {
        return medidasPesoRepository.findAll();
    }

    public MedidasPeso obtenerPorId(Integer id) {
        return medidasPesoRepository.findById(id).orElse(null);
    }

    public MedidasPeso guardar(MedidasPeso rolUsuario) {
        return medidasPesoRepository.save(rolUsuario);
    }

    public void eliminar(Integer id) {
        medidasPesoRepository.deleteById(id);
    }
}
