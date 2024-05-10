package br.com.hyzed.hyzedapi.repositories;

import br.com.hyzed.hyzedapi.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, String> {
}
