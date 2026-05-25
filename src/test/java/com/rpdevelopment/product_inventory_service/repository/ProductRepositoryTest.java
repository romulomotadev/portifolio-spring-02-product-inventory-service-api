package com.rpdevelopment.product_inventory_service.repository;

import com.rpdevelopment.product_inventory_service.dto.projection.ProductCategoryProjection;
import com.rpdevelopment.product_inventory_service.entity.Category;
import com.rpdevelopment.product_inventory_service.entity.Product;
import com.rpdevelopment.product_inventory_service.entity.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static com.rpdevelopment.product_inventory_service.factory.test.category.CategoryFactory.createNewCategory;
import static com.rpdevelopment.product_inventory_service.factory.test.product.ProductCategoryFactory.productCategoryNewFactory;
import static com.rpdevelopment.product_inventory_service.factory.test.product.ProductStockFactory.productStockNewFactory;
import static com.rpdevelopment.product_inventory_service.factory.test.stock.StockFactory.createNewStock;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties = {
        "spring.jpa.defer-datasource-initialization=false", // Garante que a inicialização não espere scripts
        "spring.sql.init.mode=never",                       // Desativa a inicialização de scripts SQL do Spring Boot
        "spring.jpa.properties.hibernate.hbm2ddl.import_files=" // Deixa a lista de arquivos de importação do Hibernate vazia
})
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private StockRepository stockRepository;

    private Product productCategory;
    private Product productStock;

    private Category category;
    private Stock stock;

    private String sku;
    private boolean active;
    private String productName;
    private String nameCategory;

    private Pageable pageable;


    //================== INICIALIZAÇÃO ==================

    @BeforeEach
    void setUp() {

        productCategory = productCategoryNewFactory();
        productStock = productStockNewFactory();

        category = createNewCategory();
        stock = createNewStock();

        sku = productCategory.getSku();
        active = productCategory.isActive();
        productName = productCategory.getName();
        nameCategory = productCategory.getCategories().iterator().next().getName();

        pageable = PageRequest.of(0, 10);
    }


    //============= PRODUCT ================

    // FIND BY SKU
    @Test
    @DisplayName("Deve retornar produto quando SKU existir")
    void findBySku_ShouldReturnProductWhenSkuExists() {

        // ========= ARRANGE =========

        Product save = productRepository.save(productCategory);

        // ========= ACT =========

        Product result = productRepository.findBySku(sku);

        // ========= ASSERT =========

        assertNotNull(result);
        assertEquals(sku, result.getSku());
        assertEquals(productCategory.getSku(), result.getSku());
        assertEquals(productCategory.getName(), result.getName());
        assertEquals(productCategory.getCategories() , result.getCategories());
    }


    // FIND ALL PRODUCT ACTIVE
    @Test
    @DisplayName("Deve retornar página de produtos ativos")
    void findByActiveTrue_ShouldReturnPage() {

        // ========= ARRANGE =========

        Product save = productRepository.save(productCategory);

        // ========= ACT =========

        Page<Product> result = productRepository.findAllByActive(pageable, active);

        // ========= ASSERT =========

        assertNotNull(result);
        assertFalse(result.isEmpty());

        assertEquals(productCategory.getId(), result.getContent().getFirst().getId());
        assertEquals(productCategory.getName(), result.getContent().getFirst().getName());
        assertEquals(productCategory.getCategories(), result.getContent().getFirst().getCategories());
    }


    // FIND ALL PRODUCT BY NAME
    @Test
    @DisplayName("Deve retornar página de produtos por nome")
    void ShouldReturnTheProductPageByName() {

        // ========= ARRANGE =========

        Product save = productRepository.save(productCategory);

        // ========= ACT =========

        Page<Product> result = productRepository.findByNameContainingIgnoreCaseOrderByNameAsc(productName, pageable);

        // ========= ASSERT =========

        assertNotNull(result);
        assertFalse(result.isEmpty());

        assertEquals(productCategory.getId(), result.getContent().getFirst().getId());
        assertEquals(productCategory.getName(), result.getContent().getFirst().getName());
        assertEquals(productCategory.getCategories(), result.getContent().getFirst().getCategories());
    }


    //========== PRODUCT -> CATEGORY ============

    // SEARCH PRODUCTS BY CATEGORY
    @Test
    @DisplayName("Deve retornar projection ao buscar produtos por categoria")
    void ShouldReturnProjection_WhenSearchingProductsByCategory() {

        // ========= ARRANGE =========

        Category saveCategory = categoryRepository.save(category);

        productCategory.getCategories().clear();
        productCategory.getCategories().add(saveCategory);

        Product saveProduct = productRepository.save(productCategory);


        // ========= ACT =========

        Page<ProductCategoryProjection> result =
                productRepository.findAllProductByCategory(nameCategory, pageable);

        // ========= ASSERT =========

        assertNotNull(result);
        assertFalse(result.isEmpty());

        ProductCategoryProjection projection = result.getContent().getFirst();

        assertEquals(productCategory.getName(), projection.getName());
        assertEquals(productCategory.getDescription(), projection.getDescription());
        assertTrue(projection.isActive());
    }


    //========== PRODUCT -> STOCK ============

    //FIND ALL PRODUCTS OUT OF STOCK
    @Test
    @DisplayName("Deve retornar página de produtos sem estoque")
    void ShouldReturnPage_ProductsOutOfStock() {

        // ========= ARRANGE =========

// ========= ARRANGE =========

// 1. Pegue a categoria que veio de dentro da factory do produto
        Category category = productStock.getCategories().iterator().next();

// 2. Salve a categoria PRIMEIRO usando o repository dela
        categoryRepository.save(category);

// 3. Modifique a quantidade para 0 (garantindo que está sem estoque)
        productStock.getStock().setQuantity(0);

// 4. Salva o produto e o estoque normalmente
        Product savedProduct = productRepository.save(productStock);

        Stock stockDoProduto = productStock.getStock();
        stockDoProduto.setProduct(savedProduct);

        Stock savedStock = stockRepository.save(stockDoProduto);
        savedProduct.setStock(savedStock);

        // ========= ACT =========

        Page<Product> result = productRepository.findAllProductsOutOfStock(pageable);

        // ========= ASSERT =========

        assertNotNull(result);
        assertFalse(result.isEmpty());

        assertEquals(productStock.getId(), result.getContent().getFirst().getId());
        assertEquals(productStock.getName(), result.getContent().getFirst().getName());
        assertEquals(productStock.getStock(), result.getContent().getFirst().getStock());
    }

}
