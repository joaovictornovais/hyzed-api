package br.com.hyzed.hyzedapi.services;

import br.com.hyzed.hyzedapi.domain.item.Item;
import br.com.hyzed.hyzedapi.domain.item.ItemDTO;
import br.com.hyzed.hyzedapi.domain.order.Order;
import br.com.hyzed.hyzedapi.domain.product.Product;
import br.com.hyzed.hyzedapi.repositories.ItemRepository;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final ProductService productService;


    public ItemService(ItemRepository itemRepository, ProductService productService) {
        this.itemRepository = itemRepository;
        this.productService = productService;
    }

    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public Item createItem(Order order, ItemDTO itemDTO) {
        Product product = productService.findById(itemDTO.productId());
        return new Item(product, order, itemDTO.quantity());
    }

}
