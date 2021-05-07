package ru.geekbrains.springmarket.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.springmarket.entities.Cart;

@Repository
public interface CartRepository extends CrudRepository<Cart, Long> {
}
