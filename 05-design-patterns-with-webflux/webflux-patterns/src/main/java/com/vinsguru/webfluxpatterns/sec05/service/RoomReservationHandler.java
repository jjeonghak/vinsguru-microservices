package com.vinsguru.webfluxpatterns.sec05.service;

import org.springframework.stereotype.Service;

import com.vinsguru.webfluxpatterns.sec05.client.RoomClient;
import com.vinsguru.webfluxpatterns.sec05.dto.ReservationItemRequest;
import com.vinsguru.webfluxpatterns.sec05.dto.ReservationItemResponse;
import com.vinsguru.webfluxpatterns.sec05.dto.ReservationType;
import com.vinsguru.webfluxpatterns.sec05.dto.RoomReservationRequest;
import com.vinsguru.webfluxpatterns.sec05.dto.RoomReservationResponse;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class RoomReservationHandler extends ReservationHandler {

	private final RoomClient client;

	@Override
	protected ReservationType getType() {
		return ReservationType.ROOM;
	}

	@Override
	protected Flux<ReservationItemResponse> reserve(Flux<ReservationItemRequest> requestFlux) {
		return requestFlux.map(this::toRoomRequest)
				.transform(this.client::reserve)
				.map(this::toResponse);
	}

	private RoomReservationRequest toRoomRequest(ReservationItemRequest request) {
		return RoomReservationRequest.create(
				request.getCity(),
				request.getFrom(),
				request.getTo(),
				request.getCategory()
		);
	}

	private ReservationItemResponse toResponse(RoomReservationResponse response) {
		return ReservationItemResponse.create(
				response.getReservationId(),
				this.getType(),
				response.getCategory(),
				response.getCity(),
				response.getCheckIn(),
				response.getCheckOut(),
				response.getPrice()
		);
	}

}
