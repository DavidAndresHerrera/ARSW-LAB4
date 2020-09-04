/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cinema.controllers;


import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.persistence.CinemaException;
import edu.eci.arsw.cinema.service.CinemaServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/cinemas")
public class CinemaAPIController {

    @Autowired
    @Qualifier("cinemaServices")
    CinemaServices cs;


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> manejadorGetRecursoCinemas() {
        try {
            Set<Cinema> data = cs.getAllCinemas();
            return new ResponseEntity<>(data, HttpStatus.ACCEPTED);
        } catch (CinemaException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
    }
    @RequestMapping(value="/{name}")
    public ResponseEntity<?> manejadorGetRecursoFunciones(@PathVariable String name){
        try {
            Cinema data= cs.getCinemaByName(name);
            return new ResponseEntity<>(data, HttpStatus.ACCEPTED);
        } catch (CinemaException ex) {
            return new ResponseEntity<>("No se encontró ese cinema", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value="/{name}/{date}")
    public ResponseEntity<?> manejadorGetRecursoFuncionesXCinemaAndDate(@PathVariable String name,@PathVariable String date){

        try {
            return new ResponseEntity<>(cs.getFunctionsbyCinemaAndDate(name, date),HttpStatus.ACCEPTED);
        } catch (CinemaException e) {
            return new ResponseEntity<>("No hay funciones en esa fecha", HttpStatus.NOT_FOUND);

        }
    }

    @RequestMapping(value="/{name}/{date}/{moviename}")
    public ResponseEntity<?> manejadorGetRecursoFuncionesXCinemaDateAndMovie(@PathVariable String name, @PathVariable String date, @PathVariable String moviename){

        try {
            return new ResponseEntity<>(cs.getMovieByNameAndDate(name,date,moviename),HttpStatus.ACCEPTED);
        } catch (CinemaException e) {
            return new ResponseEntity<>("No hay peliculas con ese nombre",HttpStatus.NOT_FOUND);

        }
    }



}


    

