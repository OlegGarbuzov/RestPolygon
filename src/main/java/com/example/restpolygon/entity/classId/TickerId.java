package com.example.restpolygon.entity.classId;

import com.example.restpolygon.entity.User;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;


@Data
public class TickerId implements Serializable {

	private String symbol;
	private LocalDate tickerDate;
	private User user;

}
