package com.restapi.karthik.controllers;

import com.restapi.karthik.dtos.ProductDTO;
import com.restapi.karthik.dtos.UserDTO;
import com.restapi.karthik.entities.Product;
import com.restapi.karthik.mappers.ProductMapper;
import com.restapi.karthik.repositories.CategoryRepository;
import com.restapi.karthik.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    @GetMapping
    public List<ProductDTO> getAllProducts(
            @RequestParam(name = "category_id",required = false)Byte category_id
    ){

        List<Product>products;
        if(category_id!=null){
            products = productRepository.findByCategory_Id(category_id);
        }else{
            products = productRepository.findAllWithCategory();
        }

        return products
                .stream()
                .map(productMapper::toDTO)
                .toList();

    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductId(@PathVariable int id){
        var prod =  productRepository.findById(id).orElse(null);

        if(prod == null){
            return ResponseEntity.notFound().build();
        }


        return ResponseEntity.ok(productMapper.toDTO(prod));


    }




    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(
            @RequestBody ProductDTO productDTO,
            UriComponentsBuilder uriBuilder

    ){
        var category = categoryRepository.findById(productDTO.getCategory_id()).orElse(null);
        if(category == null){
            return ResponseEntity.badRequest().build();
        }

        var prod = productMapper.toEntity(productDTO);

        prod.setCategory(category);
        productRepository.save(prod);

        productDTO.setId(prod.getId());
        var uri = uriBuilder.path("/products/{id}").buildAndExpand(prod.getId()).toUri();

        return ResponseEntity.created(uri).body(productDTO);

    }
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable int id,
            @RequestBody ProductDTO productDTO
    )
    {
       var cat = categoryRepository.findById(productDTO.getCategory_id()).orElse(null);
       if(cat == null){
           return ResponseEntity.badRequest().build();
       }
        var prod = productRepository.findById(id).orElse(null);
        if(prod == null){
            return ResponseEntity.notFound().build();
        }
        productMapper.update(productDTO, prod);
        prod.setCategory(cat);
        productRepository.save(prod);
        productDTO.setId(prod.getId());

        return ResponseEntity.ok(productDTO);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>deleteProduct(@PathVariable int id){
        var prod = productRepository.findById(id).orElse(null);
        if(prod == null){
            return ResponseEntity.notFound().build();
        }
        productRepository.delete(prod);
        return ResponseEntity.noContent().build();
    }
}
