package com.example.demo.model.persistence;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cart")
@Data
public class Cart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty
	@Column
	private Long id;
	
	@ManyToMany
	@JsonProperty
	@Column
	@NonNull
    private List<Item> items = new ArrayList<>();
	
	@OneToOne(mappedBy = "cart")
	@JsonProperty
    private User user;
	
	@Column
	@JsonProperty
	private BigDecimal total = BigDecimal.ZERO;

	public void addItem(Item item) {
		items.add(item);
		total = total.add(item.getPrice());
	}
	
	public void removeItem(Item item) {
		boolean removed = items.remove(item);
		total = removed ? total.subtract(item.getPrice()) : total;
	}
}
