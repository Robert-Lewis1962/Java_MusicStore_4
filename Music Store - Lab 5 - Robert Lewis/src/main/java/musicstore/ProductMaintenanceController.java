package musicstore;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import musicstore.models.Product;

@Controller // This means that this class is a Controller
// This means URL's start with /demo (after Application path)
public class ProductMaintenanceController {

	@Autowired
	private ProductRepository productRepo;

	@GetMapping(path = "/productMaintenance")
	public @ResponseBody Iterable<Product> getProducts() {
		return productRepo.findAll();
	}

	@GetMapping(path = "/editProduct")
	public String addProduct(@RequestParam(required = false) String productCode, Model model)
			throws ServletException, IOException {
		model.addAttribute("isNew", productCode == null);

		if (productCode != null) {
			model.addAttribute("product", productRepo.findProductByCode(productCode));
		}

		return "editProduct";
	}

	@PostMapping(path = "/editProduct")
	public String updateExistingProduct(@RequestParam boolean isNew,
			@RequestParam(required = false, defaultValue = "") String code,
			@RequestParam(required = false, defaultValue = "") String description,
			@RequestParam(required = false, defaultValue = "0.0") double price, Model model)
			throws ServletException, IOException {

		Product product;
		if (isNew) {
			product = new Product();
			product.setCode(code);
		} else {
			product = productRepo.findProductByCode(code);
		}

		product.setDescription(description);
		product.setPrice(price);

		if (code != null && code.length() != 0 && description != null && description.length() != 0 && price >= 0.01) {
			productRepo.save(product);
			return "redirect:productMaint.html";
		} else {
			model.addAttribute("product", product);
			model.addAttribute("isNew", isNew);
			model.addAttribute("error", "Invalid entry, try again!");
		}

		return "editProduct";
	}

	@PostMapping(path = "/deleteProduct")
	@Transactional
	public String deleteProduct(@RequestParam String productCode) throws ServletException, IOException {
		productRepo.deleteProductByCode(productCode);
		return "redirect:productMaint.html";
	}
}