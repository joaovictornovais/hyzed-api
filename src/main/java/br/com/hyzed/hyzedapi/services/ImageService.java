package br.com.hyzed.hyzedapi.services;

import br.com.hyzed.hyzedapi.domain.product.Image;
import br.com.hyzed.hyzedapi.domain.product.ImageDTO;
import br.com.hyzed.hyzedapi.domain.product.Product;
import br.com.hyzed.hyzedapi.exceptions.EntityNotFoundException;
import br.com.hyzed.hyzedapi.exceptions.InvalidArgumentsException;
import br.com.hyzed.hyzedapi.repositories.ProductImageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageService {

    private final ProductImageRepository imageRepository;

    public ImageService(ProductImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public void saveImage(Product product, String url) {
        Image image = new Image();
        image.setUrl(url);
        image.setProduct(product);
        imageRepository.save(image);
    }

    public Image removeImage(Product product, Long imageId) {
        Image image = findImageById(imageId);
        if (!product.getImages().contains(image)) {
            throw new InvalidArgumentsException("This image is not associated with this product.");
        }
        imageRepository.deleteById(imageId);
        return image;
    }

    public Image findImageById(Long id) {
        return imageRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Image not found"));
    }

    public void removeAllImagesFromProduct(Product product) {
        List<Image> images = imageRepository.findAllByProduct(product);
        imageRepository.deleteAllInBatch(images);
    }

}
