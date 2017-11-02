package com.example.thinwebapp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Collection;
import java.util.stream.Stream;

@SpringBootApplication
public class ThinWebAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThinWebAppApplication.class, args);
    }
}

@RestController
class PersonRestController {

    private final PersonRepository personRepository;

    PersonRestController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping("/people")
    Collection<Person> people() {
        return this.personRepository.findAll();
    }
}

@Component
class DataInitializer implements ApplicationRunner {

    private final PersonRepository personRepository;

    DataInitializer(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Stream.of("A", "B", "C")
                .forEach(n -> personRepository.save(new Person(null, n)));
        personRepository.findAll().forEach(System.out::println);
    }
}

interface PersonRepository extends JpaRepository<Person, Long> {

}

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
class Person {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
}