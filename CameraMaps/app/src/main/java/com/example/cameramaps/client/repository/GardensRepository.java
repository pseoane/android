package com.example.cameramaps.client.repository;

import com.example.cameramaps.client.model.Gardens;

public class GardensRepository extends Repository {
    public GardensRepository() {
        super(
                "https://datos.madrid.es/egob/catalogo/200761-0-parques-jardines.json",
                Gardens.class
        );
    }
}
