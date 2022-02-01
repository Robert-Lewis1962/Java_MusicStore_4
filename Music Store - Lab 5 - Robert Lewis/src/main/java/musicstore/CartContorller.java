package musicstore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import musicstore.models.CartEntry;
import musicstore.models.Product;

@Controller // This means that this class is a Controller
//This means URL's start with /demo (after Application path)
public class CartContorller {

	@Autowired
	private ProductRepository productRepo;

	public static List<CartEntry> getCartEntries() {
		ServletRequestAttributes servletAtt = (ServletRequestAttributes) RequestContextHolder
				.currentRequestAttributes();
		HttpSession session = servletAtt.getRequest().getSession(true);

		@SuppressWarnings("unchecked")
		List<CartEntry> entries = (List<CartEntry>) session.getAttribute("cart");

		if (entries == null) {
			entries = new ArrayList<CartEntry>();
			session.setAttribute("cart", entries);
		}

		return entries;
	}

	@GetMapping(path = "/searchResults")
	public String getProducts(Model model) {
		model.addAttribute("products", productRepo.findAll());
		return "searchResults";
	}

	@GetMapping(path = "/cartEntries")
	public @ResponseBody Iterable<CartEntry> getCartEntriresList() {
		return getCartEntries();
	}

	@PostMapping(path = "/cartEntries")
	public String updateEntry(@RequestParam String productCode, @RequestParam(required = false) String qtyString)
			throws ServletException, IOException {
		Product product = productRepo.findProductByCode(productCode);
		List<CartEntry> entries = getCartEntries();
		boolean alreadyInCart = false;

		Iterator<CartEntry> it = entries.iterator();
		while (it.hasNext()) {
			CartEntry entry = it.next();

			if (!product.getCode().equals(entry.getProduct().getCode())) {
				continue;
			}

			if (qtyString != null) {
				// Updating qty in cart
				int qty = Integer.parseInt(qtyString);

				if (qty == 0) {
					it.remove();
				} else {
					entry.setQty(qty);
				}
			} else {
				// Adding an item to cart
				entry.setQty(entry.getQty() + 1);
			}

			alreadyInCart = true;
			break;
		}

		if (!alreadyInCart) {
			entries.add(new CartEntry(product, 1));
		}

		return "redirect:cart.html";
	}
}
