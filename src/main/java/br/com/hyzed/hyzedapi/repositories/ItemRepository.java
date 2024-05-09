package br.com.hyzed.hyzedapi.repositories;

import br.com.hyzed.hyzedapi.domain.item.Item;
import br.com.hyzed.hyzedapi.domain.item.pk.ItemPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, ItemPK> {
}
