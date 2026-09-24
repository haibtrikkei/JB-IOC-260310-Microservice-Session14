package ra.productservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @GetMapping
    public ResponseEntity<List<String>> getProducts(){
        return ResponseEntity.ok(List.of("Product 1", "Product 2", "Product 3"));
    }

    @PostMapping
    public ResponseEntity<String> insertProduct(){
        return ResponseEntity.status(HttpStatus.CREATED).body("Đã tạo sản phẩm 1");
    }

    @PutMapping("/{proId}")
    public ResponseEntity<String> updateProduct(@PathVariable("proId") String proId){
        return ResponseEntity.ok("Đã cập nhật sản phẩm "+proId);
    }
}
