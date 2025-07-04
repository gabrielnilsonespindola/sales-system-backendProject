package com.gabrielnilsonespindola.salesSystem.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.gabrielnilsonespindola.salesSystem.dto.ProductDTO;
import com.gabrielnilsonespindola.salesSystem.entities.Product;
import com.gabrielnilsonespindola.salesSystem.services.ProductService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/products")
public class ProductResource {

	@Autowired
	private ProductService productService;

	@GetMapping
	@PreAuthorize("hasAuthority('SCOPE_admin') or hasAuthority('SCOPE_basic') ")
	public ResponseEntity<List<ProductDTO>> findAll() {
		log.info("Inicio do Metodo findAll");
		List<Product> list = productService.findAll();
		List<ProductDTO> listDto = list.stream().map(x -> new ProductDTO(x)).collect(Collectors.toList());
		log.info("Chamada retorno da lista de produtos, metodo findAll");
		log.info("Final do metodo findAll");
		return ResponseEntity.ok().body(listDto);
	}

	@GetMapping(value = "/{id}")
	@PreAuthorize("hasAuthority('SCOPE_admin') or hasAuthority('SCOPE_basic') ")
	public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
		log.info("Inicio do Metodo findById");
		Product obj = productService.findById(id);
		log.info("Retorno Product por Id {}", id);
		log.info("Final do metodo findById");
		return ResponseEntity.ok().body(new ProductDTO(obj));
	}

	@PostMapping
	@PreAuthorize("hasAuthority('SCOPE_admin')")
	public ResponseEntity<ProductDTO> insertProduct(@RequestBody ProductDTO objDto) {
		log.info("Inicio do Metodo insertProduct");
		Product obj = productService.fromDTO(objDto);
		obj = productService.insertProduct(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		log.info("Retorno metodo insertProduct {}",uri);	
		log.info("Final do metodo insertProduct");
		return ResponseEntity.created(uri).build();

	}

}
