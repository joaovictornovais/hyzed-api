package br.com.hyzed.hyzedapi.controllers;

import br.com.hyzed.hyzedapi.domain.image.ImageDTO;
import br.com.hyzed.hyzedapi.domain.product.Product;
import br.com.hyzed.hyzedapi.domain.product.ProductDTO;
import br.com.hyzed.hyzedapi.domain.size.SizeDTO;
import br.com.hyzed.hyzedapi.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(productService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Product> save(@RequestBody @Valid ProductDTO data) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.save(new Product(data)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> edit(@PathVariable Long id, @RequestBody @Valid ProductDTO data) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.update(id, new Product(data)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{id}/images")
    public ResponseEntity<Product> addImageToProduct(@PathVariable Long id, @Valid @RequestBody ImageDTO data) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.addImageToProduct(id, data));
    }

    @PostMapping("/{id}/sizes")
    public ResponseEntity<Product> addVarietyToProduct(@PathVariable Long id, @Valid @RequestBody SizeDTO data) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.addVarietyToProduct(id, data));
    }

    @DeleteMapping("/{id}/sizes")
    public ResponseEntity<Product> removeVarietyFromProduct(@PathVariable Long id, @Valid @RequestBody SizeDTO data) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.removeVarietyFromProduct(id, data));
    }

    @DeleteMapping("/{id}/images")
    public ResponseEntity<Product> removeImageFromProduct(@PathVariable Long id, @RequestParam("imageId") Long imageId) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.removeImageFromProduct(id, imageId));
    }

}
