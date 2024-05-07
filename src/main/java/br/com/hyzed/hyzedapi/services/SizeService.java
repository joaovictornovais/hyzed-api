package br.com.hyzed.hyzedapi.services;

import br.com.hyzed.hyzedapi.domain.product.Product;
import br.com.hyzed.hyzedapi.domain.product.Size;
import br.com.hyzed.hyzedapi.domain.product.SizeDTO;
import br.com.hyzed.hyzedapi.domain.product.Sizes;
import br.com.hyzed.hyzedapi.exceptions.InvalidArgumentsException;
import br.com.hyzed.hyzedapi.repositories.ProductSizeRepository;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SizeService {

    private final ProductSizeRepository sizeRepository;

    public SizeService(ProductSizeRepository sizeRepository) {
        this.sizeRepository = sizeRepository;
    }

    public void saveSize(Product product, SizeDTO sizeDTO) {
        Optional<Size> size = findBySizeAndProduct(sizeDTO.size(), product);

        if (size.isEmpty() && sizeDTO.quantity() > 0) {
            Size newSize = new Size();
            newSize.setSize(sizeDTO.size());
            newSize.setQuantity(sizeDTO.quantity());
            newSize.setProduct(product);
            sizeRepository.save(newSize);

        } else if (sizeDTO.quantity() > 0) {
            size.get().setQuantity(size.get().getQuantity() + sizeDTO.quantity());
            sizeRepository.save(size.get());
        } else {
            throw new InvalidArgumentsException("Quantity should not be less than 0");
        }
    }

    public void removeSize(Product product, SizeDTO sizeDTO) {
        Optional<Size> size = findBySizeAndProduct(sizeDTO.size(), product);

        if (size.isEmpty()) throw new InvalidArgumentsException("There's no variety for this size");
        if (sizeDTO.quantity() < 0) throw new InvalidArgumentsException("Quantity to be removed should not be less than 0");
        if (size.get().getQuantity() < sizeDTO.quantity()) throw new InvalidArgumentsException(
                "The quantity to be excluded is greater than the total quantity");

        size.get().setQuantity(size.get().getQuantity() - sizeDTO.quantity());
        sizeRepository.save(size.get());

    }

    public Optional<Size> findBySizeAndProduct(Sizes size, Product product) {
        return sizeRepository.findBySizeAndProduct(size, product);
    }

    public void removeAllSizesFromProduct(Product product) {
        List<Size> sizes = sizeRepository.findAllByProduct(product);
        sizeRepository.deleteAllInBatch(sizes);
    }

}
