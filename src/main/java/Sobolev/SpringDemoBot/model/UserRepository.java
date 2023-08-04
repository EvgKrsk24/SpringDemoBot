package Sobolev.SpringDemoBot.model;

import org.springframework.data.repository.CrudRepository;

import java.security.Timestamp;
import java.util.Date;

public interface UserRepository extends CrudRepository<User, Long> {
}
