package br.com.hyzed.hyzedapi.services;

import br.com.hyzed.hyzedapi.domain.product.*;
import br.com.hyzed.hyzedapi.exceptions.EntityNotFoundException;
import br.com.hyzed.hyzedapi.exceptions.InvalidArgumentsException;
import br.com.hyzed.hyzedapi.repositories.ProductImageRepository;
import br.com.hyzed.hyzedapi.repositories.ProductRepository;
import br.com.hyzed.hyzedapi.repositories.ProductSizeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        productRepository.deleteById(id);
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
        imageService.removeImage(product, imageId);
        return product;
    }


}
