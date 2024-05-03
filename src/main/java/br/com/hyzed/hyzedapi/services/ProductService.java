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
    private final ProductImageRepository productImageRepository;
    private final ProductSizeRepository productSizeRepository;

    public ProductService(
            ProductRepository productRepository,
            ProductImageRepository productImageRepository,
            ProductSizeRepository productSizeRepository
    ) {
        this.productRepository = productRepository;
        this.productImageRepository = productImageRepository;
        this.productSizeRepository = productSizeRepository;
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
        Image image = new Image();
        image.setUrl(imageDTO.url());
        image.setProduct(product);
        productImageRepository.save(image);
        return product;
    }

    public Product addVarietyToProduct(Long id, SizeDTO sizeDTO) {
        Product product = findById(id);
        Optional<Size> size = findBySizeAndProduct(sizeDTO.size(), product);

        if (size.isEmpty() && sizeDTO.quantity() > 0) {
            Size newSize = new Size();
            newSize.setSize(sizeDTO.size());
            newSize.setQuantity(sizeDTO.quantity());
            newSize.setProduct(product);
            productSizeRepository.save(newSize);
            return product;
        }

        if (sizeDTO.quantity() > 0) {
            size.get().setQuantity(size.get().getQuantity() + sizeDTO.quantity());
            productSizeRepository.save(size.get());
            return product;
        }

        throw new InvalidArgumentsException("Quantity should not be less than 0");
    }

    public Optional<Size> findBySizeAndProduct(Sizes size, Product product) {
        return productSizeRepository.findBySizeAndProduct(size, product);
    }

    public Product removeVarietyFromProduct(Long id, SizeDTO sizeDTO) {
        Product product = findById(id);
        Optional<Size> size = findBySizeAndProduct(sizeDTO.size(), product);

        if (size.isEmpty()) throw new InvalidArgumentsException("There's no variety for this size");
        if (sizeDTO.quantity() < 0) throw new InvalidArgumentsException("Quantity to be removed should not be less than 0");
        if (size.get().getQuantity() < sizeDTO.quantity()) throw new InvalidArgumentsException(
                "The quantity to be excluded is greater than the total quantity");

        size.get().setQuantity(size.get().getQuantity() - sizeDTO.quantity());
        productSizeRepository.save(size.get());

        return product;
    }

    public Product removeImageFromProduct(Long productId, Long imageId) {
        Product product = findById(productId);
        Image image = productImageRepository.findById(imageId).orElseThrow(
                () -> new EntityNotFoundException("Image not found"));

        if (product.getImages().contains(image)) {
            productImageRepository.deleteById(image.getId());
            product.getImages().remove(image);
            return product;
        }

        throw new InvalidArgumentsException("This image isn't from this product!");
    }


}
