package com.vinsguru.rsocket;

import com.vinsguru.rsocket.service.BatchJobServiceSocketAcceptorImpl;
import com.vinsguru.rsocket.service.FastProducerSocketAcceptorImpl;
import com.vinsguru.rsocket.service.MathServiceSocketAcceptorImpl;

import io.rsocket.core.RSocketServer;
import io.rsocket.transport.netty.server.CloseableChannel;
import io.rsocket.transport.netty.server.TcpServerTransport;

public class RSocketApplication {

	public static void main(String[] args) {

		RSocketServer mathSocketServer = RSocketServer.create(new MathServiceSocketAcceptorImpl());
		CloseableChannel mathCloseableChannel = mathSocketServer.bindNow(TcpServerTransport.create(6565));

		RSocketServer batchSocketServer = RSocketServer.create(new BatchJobServiceSocketAcceptorImpl());
		CloseableChannel batchCloseableChannel = batchSocketServer.bindNow(TcpServerTransport.create(6570));

		RSocketServer fastSocketServer = RSocketServer.create(new FastProducerSocketAcceptorImpl());
		CloseableChannel fastCloseableChannel = fastSocketServer.bindNow(TcpServerTransport.create(6575));

		//keep listening
		mathCloseableChannel.onClose().block();
		batchCloseableChannel.onClose().block();
		fastCloseableChannel.onClose().block();

	}

}
