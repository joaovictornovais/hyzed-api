package br.com.hyzed.hyzedapi.controllers;

import br.com.hyzed.hyzedapi.domain.image.ImageDTO;
import br.com.hyzed.hyzedapi.domain.product.Product;
import br.com.hyzed.hyzedapi.domain.product.ProductDTO;
import br.com.hyzed.hyzedapi.domain.size.SizeDTO;
import br.com.hyzed.hyzedapi.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(description = "Busca todos os produtos do banco de dados")
    @ApiResponse(responseCode = "200", description = "Retorna todos os produtos cadastrados no banco de dados")
    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(productService.findAll());
    }

    @Operation(description = "Busca um produto especifico por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o produto com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.findById(id));
    }

    @Operation(description = "Salva um novo produto no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto salvo com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    @PostMapping
    public ResponseEntity<Product> save(@RequestBody @Valid ProductDTO data) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.save(new Product(data)));
    }

    @Operation(description = "Deve editar as informações de um produto específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto editado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Product> edit(@PathVariable Long id, @RequestBody @Valid ProductDTO data) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.update(id, data));
    }

    @Operation(description = "Deleta um produto da base de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(description = "Adiciona uma nova imagem a um produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imagem adicionada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @PostMapping("/{id}/images")
    public ResponseEntity<Product> addImageToProduct(@PathVariable Long id, @Valid @RequestBody ImageDTO data) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.addImageToProduct(id, data));
    }

    @Operation(description = "Adiciona um novo estoque para o produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estoque adicionado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @PostMapping("/{id}/sizes")
    public ResponseEntity<Product> addVarietyToProduct(@PathVariable Long id, @Valid @RequestBody SizeDTO data) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.addVarietyToProduct(id, data));
    }

    @Operation(description = "Remove uma quantia do estoque de um produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quantia removida com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @DeleteMapping("/{id}/sizes")
    public ResponseEntity<Product> removeVarietyFromProduct(@PathVariable Long id, @Valid @RequestBody SizeDTO data) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.removeVarietyFromProduct(id, data));
    }

    @Operation(description = "Deleta uma imagem")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imagem deletada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @DeleteMapping("/{id}/images")
    public ResponseEntity<Product> removeImageFromProduct(@PathVariable Long id, @RequestParam("imageId") Long imageId) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.removeImageFromProduct(id, imageId));
    }

}
