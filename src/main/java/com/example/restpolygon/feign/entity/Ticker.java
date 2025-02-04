package com.example.restpolygon.feign.entity;


import com.example.restpolygon.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "ticker")
public class Ticker implements Comparable<Ticker> {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	private String symbol;

	private LocalDate tickerDate;

	@ManyToOne(cascade = CascadeType.MERGE)
	private User user;

	private BigDecimal open;
	private BigDecimal close;
	private BigDecimal high;
	private BigDecimal low;

	@Override
	public int compareTo(Ticker o) {
		return this.tickerDate.compareTo(o.tickerDate);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null) return false;
		Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
		Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
		if (thisEffectiveClass != oEffectiveClass) return false;
		Ticker ticker = (Ticker) o;
		return getId() != null && Objects.equals(getId(), ticker.getId());
	}

	@Override
	public int hashCode() {
		return this instanceof HibernateProxy
				? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
				: getClass().hashCode();
	}
}
