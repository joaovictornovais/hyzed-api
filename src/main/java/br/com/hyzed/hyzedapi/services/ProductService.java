package br.com.hyzed.hyzedapi.services;

import br.com.hyzed.hyzedapi.domain.image.Image;
import br.com.hyzed.hyzedapi.domain.image.ImageDTO;
import br.com.hyzed.hyzedapi.domain.product.Product;
import br.com.hyzed.hyzedapi.domain.size.SizeDTO;
import br.com.hyzed.hyzedapi.exceptions.EntityNotFoundException;
import br.com.hyzed.hyzedapi.repositories.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ImageService imageService;
    private final SizeService sizeService;

    public ProductService(ProductRepository productRepository, ImageService imageService, SizeService sizeService) {
        this.productRepository = productRepository;
        this.imageService = imageService;
        this.sizeService = sizeService;
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found"));
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product update(Long id, Product newProduct) {
        Product product = findById(id);
        BeanUtils.copyProperties(newProduct, product);
        return productRepository.save(product);
    }

    public void delete(Long id) {
        Product product = findById(id);
        imageService.removeAllImagesFromProduct(product);
        sizeService.removeAllSizesFromProduct(product);
        productRepository.delete(product);
    }

    public Product addImageToProduct(Long id, ImageDTO imageDTO) {
        Product product = findById(id);
        imageService.saveImage(product, imageDTO.url());
        return product;
    }

    public Product addVarietyToProduct(Long id, SizeDTO sizeDTO) {
        Product product = findById(id);
        sizeService.saveSize(product, sizeDTO);
        return product;
    }

    public Product removeVarietyFromProduct(Long id, SizeDTO sizeDTO) {
        Product product = findById(id);
        sizeService.removeSize(product, sizeDTO);
        return findById(id);
    }

    public Product removeImageFromProduct(Long productId, Long imageId) {
        Product product = findById(productId);
        Image image = imageService.removeImage(product, imageId);
        product.getImages().remove(image);
        return product;
    }


}
