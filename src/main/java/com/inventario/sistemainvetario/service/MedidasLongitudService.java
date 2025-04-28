package com.inventario.sistemainvetario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventario.sistemainvetario.model.MedidasLongitud;
import com.inventario.sistemainvetario.repository.MedidasLongitudRepository;

@Service
public class MedidasLongitudService {
    @Autowired
    private MedidasLongitudRepository medidasLongitudRepository;

    public List<MedidasLongitud> obtenerTodos() {
        return medidasLongitudRepository.findAll();
    }

    public MedidasLongitud obtenerPorId(Integer id) {
        return medidasLongitudRepository.findById(id).orElse(null);
    }

    public MedidasLongitud guardar(MedidasLongitud rolUsuario) {
        return medidasLongitudRepository.save(rolUsuario);
    }

    public void eliminar(Integer id) {
        medidasLongitudRepository.deleteById(id);
    }
}
