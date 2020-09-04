/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cinema.persistence.impl;

import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.model.Movie;
import edu.eci.arsw.cinema.persistence.CinemaException;
import edu.eci.arsw.cinema.persistence.CinemaPersistenceException;
import edu.eci.arsw.cinema.persistence.CinemaPersitence;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author cristian
 */

@Service
@Qualifier("inMemoryCinema")
public class InMemoryCinemaPersistence implements CinemaPersitence {

    private final Map<String, Cinema> cinemas = new HashMap<>();

    public InMemoryCinemaPersistence() {
        //load stub data
        String functionDate = "2018-12-18 15:30";

        List<CinemaFunction> functions = new ArrayList<>();
        CinemaFunction funct1 = new CinemaFunction(new Movie("SuperHeroes Movie", "Action"), functionDate);
        CinemaFunction funct2 = new CinemaFunction(new Movie("The Night", "Horror"), functionDate);
        functions.add(funct1);
        functions.add(funct2);
        Cinema c = new Cinema("cinemaX", functions);
        cinemas.put("cinemaX", c);
        /*
        Creacion de los dos cinemas
         */
        String functionDate2 = "2020-09-03 12:00";
        List<CinemaFunction> functions2 = new ArrayList<>();
        CinemaFunction funcion1 = new CinemaFunction(new Movie("Capitana Marvel", "Action"), functionDate2);
        CinemaFunction funcion2 = new CinemaFunction(new Movie("Deadpool 2", "Action"), functionDate2);
        functions2.add(funcion1);
        functions2.add(funcion2);
        Cinema c2 = new Cinema("Fontanar", functions2);
        Cinema c3 = new Cinema("Centro Chia", functions2);
        cinemas.put("Fontanar", c2);
        cinemas.put("Centro Chia", c3);

    }

    @Override
    public void buyTicket(int row, int col, String cinema, String date, String movieName) throws CinemaException {

        List<CinemaFunction> temp = cinemas.get(cinema).getFunctions();
        for (CinemaFunction j : temp) {
            if (j.getMovie().getName().equals(movieName) && j.getDate().equals(date)) {
                try {
                    j.buyTicket(row, col);
                } catch (CinemaException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public Set<Cinema> getAllCinemas() {
        Set<Cinema> list = new HashSet<>();
        cinemas.forEach((key, value) -> {
            list.add(value);
        });
        return list;
    }

    @Override
    public List<CinemaFunction> getFunctionsbyCinemaAndDate(String cinema, String date) {
        List<CinemaFunction> temp = cinemas.get(cinema).getFunctions();
        List<CinemaFunction> funcionesFinales = new ArrayList<>();
        for (CinemaFunction j : temp) {
            if (j.getDate().equals(date)) {
                funcionesFinales.add(j);
            }
        }
        return funcionesFinales;
    }

    @Override
    public void saveCinema(Cinema c) throws CinemaPersistenceException {
        if (cinemas.containsKey(c.getName())) {
            throw new CinemaPersistenceException("The given cinema already exists: " + c.getName());
        } else {
            cinemas.put(c.getName(), c);
        }
    }

    @Override
    public Cinema getCinema(String name) throws CinemaPersistenceException {
        return cinemas.get(name);
    }

    @Override
    public void addCinema(Cinema cinema) {
        cinemas.put(cinema.getName(), cinema);
    }

    @Override
    public Movie getMovieByCinemaAndDate(String cinema, String date, String movie) {
        List<CinemaFunction> temp = getFunctionsbyCinemaAndDate(cinema, date);
        for (CinemaFunction c :
                temp) {
            if(c.getMovie().getName().equals(movie)){ return c.getMovie();}
        }
       return null;
    }


}
