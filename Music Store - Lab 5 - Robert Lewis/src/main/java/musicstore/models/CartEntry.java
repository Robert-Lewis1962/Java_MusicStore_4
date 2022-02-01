package musicstore.models;

import java.io.Serializable;

public class CartEntry implements Serializable {
	private static final long serialVersionUID = 1L;

	private Product product;
	private int qty;

	public CartEntry(Product product, int qty) {
		this.product = product;
		this.qty = qty;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}
}
