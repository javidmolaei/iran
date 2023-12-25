package ir.javid.iran.controller;

import ir.javid.iran.model.Driver;
import ir.javid.iran.model.User;
import ir.javid.iran.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * author: Mr.javidmolaei
 */

public class test {
    static class driver{
        DriverRepository driverRepository;

        public Driver saveDriver(Driver driver){
            return driverRepository.save(driver);
        }}


    public static void main(String[] args) {
    }

}
