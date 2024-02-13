package com.vinsguru.msagraphql.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class WatchListResponse {

	private Status status;
	private List<Long> watchList;

}
