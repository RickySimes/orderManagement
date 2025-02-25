package Service;

import Entity.Item;
import Repository.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    private static final Logger logger = LoggerFactory.getLogger(ItemService.class);

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item createItem(String name) {
        Item item = new Item(name);
        logger.info("Item created: {}", item);
        return itemRepository.save(item);
    }

    public Item updateItem(Long id, String name) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));
        item.setName(name);

        Optional<Item> existingWithName = itemRepository.findByName(name);
        if (existingWithName.isPresent() && !existingWithName.get().getId().equals(id)) {
            throw new RuntimeException("Item with name " + name + " already exists");
        }

        itemRepository.save(item);
        logger.info("Item updated: {}", item);
        return item;
    }

    public void deleteItem(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));
        itemRepository.delete(item);
        logger.info("Item deleted: {}", id);
    }

    public Item getItem(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }
}
